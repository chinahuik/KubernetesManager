/**
 * @Project: demo
 * @Package: com.chinahuik.utils
 * @Author: open-source@chinahuik.com
 * @Time: 2020年5月25日 上午12:04:17
 * @File: KubeApiClient.java
 *
 */
package com.chinahuik.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okio.ByteString;

/**
 * @author open-source@chinahuik.com
 *
 */
@Slf4j
public class KubeApiClient {
	private static class AllHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	private static class TrustAllManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
				String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
				String authType) throws CertificateException {
		}

		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[] {};
		}
	}

	private String basePath = "http://localhost";

	private boolean debugging = false;
	private final Map<String, String> defaultHeaderMap = new HashMap<>();
	private InputStream sslCaCert;

	private boolean verifyingSsl;

	private KeyManager[] keyManagers;

	private OkHttpClient httpClient;

	private HttpLoggingInterceptor loggingInterceptor;

	/*
	 * Basic constructor for ApiClient
	 */
	public KubeApiClient(KubeApiConfig config) {
		init(config);

	}

	/**
	 * get请求。
	 *
	 * @param url 请求地址
	 * @return 响应对象json字符串
	 */
	public String doGet(String api) {
		return doGet(api, null, null);
	}

	/**
	 * get请求。
	 *
	 * @param url      请求地址
	 * @param queryMap url query
	 * @return 响应对象json字符串
	 */
	public String doGet(String api, Map<String, String> queryMap) {
		return doGet(api, queryMap, null);
	}

	/**
	 * get请求。
	 *
	 * @param url      请求地址
	 * @param queryMap url query
	 * @return 响应对象json字符串
	 */
	public String doGet(String api, Map<String, String> queryMap,
			Map<String, String> headerMap) {
		if (null == httpClient) {
			applySslSettings();
		}
		final Map<String, String> headers = new HashMap<>();
		headers.putAll(defaultHeaderMap);
		if (headerMap != null) {
			headers.putAll(headerMap);
		}
		final StringBuilder urlBuilder = new StringBuilder(basePath);
		if (!api.startsWith("/")) {
			urlBuilder.append('/');
		}
		urlBuilder.append(api);
		final Request.Builder builder = createRequestBuilder(urlBuilder.toString(),
				queryMap, headers);

		final Request request = builder.get().build();
		try {
			log.info("url: {}", request.url());
			final Response response = httpClient.newCall(request).execute();
			return response.body().string();
		} catch (final IOException e) {
			e.printStackTrace();
			log.error("exception:{}", e);
		}
		return "";
	}

	/**
	 * Check that whether debugging is enabled for this API client.
	 *
	 * @return True if debugging is enabled, false otherwise.
	 */
	public boolean isDebugging() {
		return debugging;
	}

	/**
	 * Enable/disable debugging for this API client.
	 *
	 * @param debugging To enable (true) or disable (false) debugging
	 * @return ApiClient
	 */
	public KubeApiClient setDebugging(boolean debugging) {
		if (debugging != this.debugging) {
			if (debugging) {
				loggingInterceptor = new HttpLoggingInterceptor();
				loggingInterceptor.setLevel(Level.BODY);
				httpClient = httpClient.newBuilder().addInterceptor(loggingInterceptor)
						.build();
			} else {
				httpClient.interceptors().remove(loggingInterceptor);
				loggingInterceptor = null;
			}
		}
		this.debugging = debugging;
		return this;
	}

	/**
	 * Apply SSL related settings to httpClient according to the current values of
	 * verifyingSsl and sslCaCert.
	 */
	private void applySslSettings() {
		try {
			TrustManager[] trustManagers;
			HostnameVerifier hostnameVerifier = null;
			if (!verifyingSsl) {
				trustManagers = new TrustManager[] { new TrustAllManager() };
				hostnameVerifier = new AllHostnameVerifier();
			} else {
				final TrustManagerFactory trustManagerFactory = createTrustManagerFactory();
				trustManagers = trustManagerFactory.getTrustManagers();
				hostnameVerifier = OkHostnameVerifier.INSTANCE;
			}

			final SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(keyManagers, trustManagers, new SecureRandom());
			httpClient = httpClient.newBuilder()
					.sslSocketFactory(sslContext.getSocketFactory(),
							(X509TrustManager) trustManagers[0])
					.hostnameVerifier(hostnameVerifier).build();
		} catch (final GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2020年5月25日 下午11:43:16
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws CertificateException
	 * @throws GeneralSecurityException
	 *
	 */
	private TrustManagerFactory createTrustManagerFactory()
			throws NoSuchAlgorithmException, KeyStoreException, CertificateException,
			GeneralSecurityException {
		final TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());

		if (sslCaCert == null) {
			trustManagerFactory.init((KeyStore) null);
		} else {
			final char[] password = null; // Any
											// password
											// will
											// work.
			final CertificateFactory certificateFactory = CertificateFactory
					.getInstance("X.509");
			final Collection<? extends Certificate> certificates = certificateFactory
					.generateCertificates(sslCaCert);
			if (certificates.isEmpty()) {
				throw new IllegalArgumentException(
						"expected non-empty set of trusted certificates");
			}
			final KeyStore caKeyStore = newEmptyKeyStore(password);
			int index = 0;
			for (final Certificate certificate : certificates) {
				final String certificateAlias = "ca" + Integer.toString(index++);
				caKeyStore.setCertificateEntry(certificateAlias, certificate);
			}
			trustManagerFactory.init(caKeyStore);
		}
		return trustManagerFactory;
	}

	private void init(KubeApiConfig config) {
		final OkHttpClient.Builder builder = new OkHttpClient.Builder();
		httpClient = builder.build();
		verifyingSsl = config.verifySSL();
		initBasePath(config);
		initSslCaCert(config);
		initDefaultHeaders(config);
		applySslSettings();
	}

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2020年5月25日 下午11:44:17
	 * @param config
	 *
	 */
	private void initBasePath(KubeApiConfig config) {
		String server = config.getServer();
		if (server != null) {
			if (!server.contains("://")) {
				if (server.contains(":443")) {
					server = "https://" + server;
				} else {
					server = "http://" + server;
				}
			}
			if (server.endsWith("/")) {
				server = server.substring(0, server.length() - 1);
			}
			this.basePath = server;
		}
	}

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2020年5月25日 下午11:46:36
	 * @param config
	 *
	 */
	private void initDefaultHeaders(KubeApiConfig config) {
		provideCertAuthorization(config);
		provideUserPassAuthorization(config);
		provideTokenAuthorization(config);
		// Set default User-Agent.
		defaultHeaderMap.put("User-Agent", "OpenAPI-Generator/1.0-SNAPSHOT/java");
	}

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2020年5月25日 下午11:45:10
	 * @param config
	 *
	 */
	private void initSslCaCert(KubeApiConfig config) {
		try {
			final byte[] caBytes = KubeApiConfig.getDataOrFile(
					config.getCertificateAuthorityData(),
					config.getCertificateAuthorityFile());
			if (caBytes != null) {
				this.sslCaCert = new ByteArrayInputStream(caBytes);
			}

		} catch (final IOException e) {
			log.error("get ssl ca cert from config failed: {}", e);
		}
	}

	private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
		try {
			final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null, password);
			return keyStore;
		} catch (final IOException e) {
			throw new AssertionError(e);
		}
	}

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2020年5月25日 下午11:47:37
	 * @param config
	 *
	 */
	private void provideCertAuthorization(KubeApiConfig config) {
		try {
			final byte[] clientCert = KubeApiConfig.getDataOrFile(
					config.getClientCertificateData(), config.getClientCertificateFile());
			final byte[] clientKey = KubeApiConfig
					.getDataOrFile(config.getClientKeyData(), config.getClientKeyFile());
			if (clientCert != null && clientKey != null) {
				final String dataString = new String(clientKey);
				String algo = "";
				if (dataString.indexOf("BEGIN EC PRIVATE KEY") != -1) {
					algo = "EC";
				}
				if (dataString.indexOf("BEGIN RSA PRIVATE KEY") != -1) {
					algo = "RSA";
				}
				try {
					final KeyManager[] keyManagers = SslUtil.keyManagers(clientCert,
							clientKey, algo, "", null, null);
					this.keyManagers = keyManagers;
				} catch (NoSuchAlgorithmException | UnrecoverableKeyException
						| CertificateException | KeyStoreException
						| InvalidKeySpecException | IOException e) {
					log.warn(
							"Could not create key manager for Client Certificate authentication.",
							e);
					throw new RuntimeException(e);
				}
			}
		} catch (final IOException e) {
			log.error("get cert failed: {}", e);
		}
	}

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2020年5月25日 下午11:48:38
	 * @param config
	 *
	 */
	private void provideTokenAuthorization(KubeApiConfig config) {
		final String token = config.getAccessToken();
		if (token != null) {
			defaultHeaderMap.put("authorization", "Bearer " + token);
		}
	}

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2020年5月25日 下午11:48:08
	 * @param config
	 *
	 */
	private void provideUserPassAuthorization(KubeApiConfig config) {
		final String username = config.getUsername();
		final String password = config.getPassword();
		if (username != null && password != null) {
			final String usernameAndPassword = username + ":" + password;
			final String auth = String.format("Basic %s",
					ByteString.of(
							usernameAndPassword.getBytes(Charset.forName("ISO-8859-1")))
							.base64());
			defaultHeaderMap.put("authorization", auth);
		}
	}

	// public static void main(String[] args) {
	// final KubeApiConfig config = KubeApiConfig.loadKubeConfig("qingdao.conf");
	// final KubeApiClient client = new KubeApiClient(config);
	// final String resp = client.doGet("/api/v1");
	// System.out.println(resp);
	// }

	/**
	 * 创建request
	 *
	 * @param url       链接
	 * @param queryMap  查询参数列表
	 * @param headerMap 请求头参数列表
	 * @return
	 */
	private static Request.Builder createRequestBuilder(String url,
			Map<String, String> queryMap, Map<String, String> headerMap) {
		final Request.Builder builder = new Request.Builder();
		if (queryMap != null) {
			final HttpUrl httpUrl = HttpUrl.parse(url);
			final HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
			for (final String key : queryMap.keySet()) {
				urlBuilder.addQueryParameter(key, queryMap.get(key));
			}
			builder.url(urlBuilder.build());
		} else {
			builder.url(url);
		}
		if (headerMap != null) {
			for (final String header : headerMap.keySet()) {
				builder.addHeader(header, headerMap.get(header));
			}
		}
		return builder;
	}
}
