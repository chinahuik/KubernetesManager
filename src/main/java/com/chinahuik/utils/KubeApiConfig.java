/**
 * @Project: demo
 * @Package: com.chinahuik.utils
 * @Author: open-source@chinahuik.com
 * @Time: 2020年5月24日 下午11:57:58
 * @File: KubeApiConfig.java
 *
 */
package com.chinahuik.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * @author open-source@chinahuik.com
 *
 */
public class KubeApiConfig {
	private final ArrayList<Object> clusters;

	private final ArrayList<Object> contexts;
	private final ArrayList<Object> users;
	String currentContextName;

	// Note to the reader: I considered creating a Config object
	// and parsing into that instead of using Maps, but honestly
	// this seemed cleaner than a bunch of boilerplate classes

	Map<String, Object> currentContext;
	Map<String, Object> currentCluster;
	Map<String, Object> currentUser;
	String currentNamespace;
	Object preferences;
	private File file;
	private static final Logger log = LoggerFactory.getLogger(KubeApiConfig.class);
	// Defaults for where to find a kubeconfig file
	public static final String ENV_HOME = "HOME";
	public static final String KUBEDIR = ".kube";
	public static final String KUBECONFIG = "config";

	public KubeApiConfig(ArrayList<Object> contexts, ArrayList<Object> clusters,
			ArrayList<Object> users) {
		this.contexts = contexts;
		this.clusters = clusters;
		this.users = users;
	}

	@SuppressWarnings("unchecked")
	public String getAccessToken() {
		if (currentUser == null) {
			return null;
		}

		final Object authProvider = currentUser.get("auth-provider");
		if (authProvider != null) {
			final Map<String, Object> authProviderMap = (Map<String, Object>) authProvider;
			final Map<String, Object> authConfig = (Map<String, Object>) authProviderMap
					.get("config");
			if (authConfig != null) {
				final String name = (String) authProviderMap.get("name");
				log.error("Unknown auth provider: " + name);
			}
		}
		final String tokenViaExecCredential = tokenViaExecCredential(
				(Map<String, Object>) currentUser.get("exec"));
		if (tokenViaExecCredential != null) {
			return tokenViaExecCredential;
		}
		if (currentUser.containsKey("token")) {
			return (String) currentUser.get("token");
		}
		if (currentUser.containsKey("tokenFile")) {
			final String tokenFile = (String) currentUser.get("tokenFile");
			try {
				final byte[] data = Files
						.readAllBytes(FileSystems.getDefault().getPath(tokenFile));
				return new String(data, StandardCharsets.UTF_8);
			} catch (final IOException ex) {
				log.error("Failed to read token file", ex);
			}
		}
		return null;
	}

	public String getCertificateAuthorityData() {
		return getData(currentCluster, "certificate-authority-data");
	}

	public String getCertificateAuthorityFile() {
		return getData(currentCluster, "certificate-authority");
	}

	public String getClientCertificateData() {
		return getData(currentUser, "client-certificate-data");
	}

	public String getClientCertificateFile() {
		return getData(currentUser, "client-certificate");
	}

	public String getClientKeyData() {
		return getData(currentUser, "client-key-data");
	}

	public String getClientKeyFile() {
		return getData(currentUser, "client-key");
	}

	public ArrayList<Object> getClusters() {
		return clusters;
	}

	public ArrayList<Object> getContexts() {
		return contexts;
	}

	public String getCurrentContext() {
		return currentContextName;
	}

	public String getNamespace() {
		return currentNamespace;
	}

	public String getPassword() {
		return getData(currentUser, "password");
	}

	public Object getPreferences() {
		return preferences;
	}

	public String getServer() {
		return getData(currentCluster, "server");
	}

	public String getUsername() {
		return getData(currentUser, "username");
	}

	public ArrayList<Object> getUsers() {
		return users;
	}

	@SuppressWarnings("unchecked")
	public boolean setContext(String context) {
		if (context == null) {
			return false;
		}
		currentContextName = context;
		currentCluster = null;
		currentUser = null;
		final Map<String, Object> ctx = findObject(contexts, context);
		if (ctx == null) {
			return false;
		}
		currentContext = (Map<String, Object>) ctx.get("context");
		if (currentContext == null) {
			return false;
		}
		final String cluster = (String) currentContext.get("cluster");
		final String user = (String) currentContext.get("user");
		currentNamespace = (String) currentContext.get("namespace");
		if (cluster != null) {
			final Map<String, Object> obj = findObject(clusters, cluster);
			if (obj != null) {
				currentCluster = (Map<String, Object>) obj.get("cluster");
			}
		}
		if (user != null) {
			final Map<String, Object> obj = findObject(users, user);
			if (obj != null) {
				currentUser = (Map<String, Object>) obj.get("user");
			}
		}
		return true;
	}

	/**
	 * Indicates a file from which this configuration was loaded.
	 *
	 * @param file a file path, available for use when resolving relative file paths
	 */
	public void setFile(File file) {
		this.file = file;
	}

	public void setPreferences(Object preferences) {
		this.preferences = preferences;
	}

	public boolean verifySSL() {
		if (currentCluster == null) {
			return false;
		}
		if (currentCluster.containsKey("insecure-skip-tls-verify")) {
			return !((Boolean) currentCluster.get("insecure-skip-tls-verify"))
					.booleanValue();
		}
		return true;
	}

	private JsonElement runExec(String command, List<String> args,
			List<Map<String, String>> env) {
		final List<String> argv = new ArrayList<>();
		if (command.contains("/") || command.contains("\\")) {
			// Spec is unclear on what should be treated as a “relative command
			// path”.
			// This clause should cover anything not resolved from $PATH /
			// %Path%.
			final Path resolvedCommand = file.toPath().getParent().resolve(command)
					.normalize();
			if (!Files.exists(resolvedCommand)) {
				log.error("No such file: {}", resolvedCommand);
				return null;
			}
			// Not checking isRegularFile or isExecutable here; leave that to
			// ProcessBuilder.start.
			log.debug("Resolved {} to {}", command, resolvedCommand);
			argv.add(resolvedCommand.toString());
		} else {
			argv.add(command);
		}
		if (args != null) {
			argv.addAll(args);
		}
		final ProcessBuilder pb = new ProcessBuilder(argv);
		if (env != null) {
			for (final Map<String, String> entry : env) {
				pb.environment().put(entry.get("name"), entry.get("value"));
			}
		}
		pb.redirectError(ProcessBuilder.Redirect.INHERIT);
		try {
			final Process proc = pb.start();
			JsonElement root;
			try (InputStream is = proc.getInputStream();
					Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
				root = JsonParser.parseReader(r);
			} catch (final JsonParseException x) {
				log.error("Failed to parse output of " + command, x);
				return null;
			}
			final int r = proc.waitFor();
			if (r != 0) {
				log.error("{} failed with exit code {}", command, r);
				return null;
			}
			return root;
		} catch (IOException | InterruptedException x) {
			log.error("Failed to run " + command, x);
			return null;
		}
	}

	/**
	 * Attempt to create an access token by running a configured external program.
	 *
	 * @see <a href=
	 *      "https://kubernetes.io/docs/reference/access-authn-authz/authentication/#client-go-credential-plugins">
	 *      Authenticating » client-go credential plugins</a>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String tokenViaExecCredential(Map<String, Object> execMap) {
		if (execMap == null) {
			return null;
		}
		final String apiVersion = (String) execMap.get("apiVersion");
		if (!"client.authentication.k8s.io/v1beta1".equals(apiVersion)
				&& !"client.authentication.k8s.io/v1alpha1".equals(apiVersion)) {
			log.error("Unrecognized user.exec.apiVersion: {}", apiVersion);
			return null;
		}
		final String command = (String) execMap.get("command");
		final JsonElement root = runExec(command, (List) execMap.get("args"),
				(List) execMap.get("env"));
		if (root == null) {
			return null;
		}
		if (!"ExecCredential".equals(root.getAsJsonObject().get("kind").getAsString())) {
			log.error("Unrecognized kind in response");
			return null;
		}
		if (!apiVersion.equals(root.getAsJsonObject().get("apiVersion").getAsString())) {
			log.error("Mismatched apiVersion in response");
			return null;
		}
		final JsonObject status = root.getAsJsonObject().get("status").getAsJsonObject();
		final JsonElement token = status.get("token");
		if (token == null) {
			// TODO handle clientCertificateData/clientKeyData
			// (KubeconfigAuthentication is not yet set up for that to be
			// dynamic)
			log.warn("No token produced by {}", command);
			return null;
		}
		log.debug("Obtained a token from {}", command);
		return token.getAsString();
		// TODO cache tokens between calls, up to .status.expirationTimestamp
		// TODO a 401 is supposed to force a refresh,
		// but KubeconfigAuthentication hardcodes AccessTokenAuthentication
		// which does not support that
		// and anyway ClientBuilder only calls Authenticator.provide once per
		// ApiClient;
		// we would need to do it on every request
	}

	public static byte[] getDataOrFile(final String data, final String file)
			throws IOException {
		if (data != null) {
			return Base64.decodeBase64(data);
		}
		if (file != null) {
			return Files.readAllBytes(Paths.get(file));
		}
		return null;
	}

	/** Load a Kubernetes config from a Reader */
	@SuppressWarnings("unchecked")
	public static KubeApiConfig loadKubeConfig(Reader input) {
		final Yaml yaml = new Yaml(new SafeConstructor());
		final Object config = yaml.load(input);
		final Map<String, Object> configMap = (Map<String, Object>) config;

		final String currentContext = (String) configMap.get("current-context");
		final ArrayList<Object> contexts = (ArrayList<Object>) configMap.get("contexts");
		final ArrayList<Object> clusters = (ArrayList<Object>) configMap.get("clusters");
		final ArrayList<Object> users = (ArrayList<Object>) configMap.get("users");
		final Object preferences = configMap.get("preferences");

		final KubeApiConfig kubeConfig = new KubeApiConfig(contexts, clusters, users);
		kubeConfig.setContext(currentContext);
		kubeConfig.setPreferences(preferences);

		return kubeConfig;
	}

	public static KubeApiConfig loadKubeConfig(String fileName) {
		try (FileReader reader = new FileReader(fileName)) {
			return loadKubeConfig(reader);
		} catch (final Exception e) {
			log.error("load kube config from {} failed: {}", fileName, e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> findObject(ArrayList<Object> list, String name) {
		if (list == null) {
			return null;
		}
		for (final Object obj : list) {
			final Map<String, Object> map = (Map<String, Object>) obj;
			if (name.equals(map.get("name"))) {
				return map;
			}
		}
		return null;
	}

	private static String getData(Map<String, Object> obj, String key) {
		if (obj == null) {
			return null;
		}
		return (String) obj.get(key);
	}

}
