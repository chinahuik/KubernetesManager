/**
 * 
 */
package com.chinahuik.KubernetesManager.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

/**
 * @author open-source@chinahuik.com
 *
 */
public class RepeatableHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private final byte[] bytes;

	/**
	 * @param request
	 * @throws IOException
	 */
	public RepeatableHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		bytes = IOUtils.toByteArray(request.getInputStream());
	}

	@Override
	public ServletInputStream getInputStream() {
		return new ServletInputStream() {
			private int lastIndexRetrived = -1;
			private ReadListener readListener = null;

			@Override
			public int read() throws IOException {
				int i;
				if (!isFinished()) {
					i = bytes[lastIndexRetrived + 1];
					lastIndexRetrived++;
					if (isFinished() && (readListener != null)) {
						try {
							readListener.onAllDataRead();
						} catch (Exception e) {
							readListener.onError(e);
							throw e;
						}
					}
					return i;
				} else {
					return -1;
				}
			}

			@Override
			public void setReadListener(ReadListener listener) {
				this.readListener = listener;
				if (!isFinished()) {
					try {
						readListener.onDataAvailable();
					} catch (Exception e) {
						readListener.onError(e);
					}
				} else {
					try {
						readListener.onAllDataRead();
					} catch (Exception e) {
						readListener.onError(e);
					}
				}
			}

			@Override
			public boolean isReady() {
				return isFinished();
			}

			@Override
			public boolean isFinished() {
				return (lastIndexRetrived == bytes.length - 1);
			}
		};
	}

	@Override
	public BufferedReader getReader() {
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}
}
