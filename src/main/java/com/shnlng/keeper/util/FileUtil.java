package com.shnlng.keeper.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

public class FileUtil {
	public static final Logger logger = Logger.getLogger(FileUtil.class);

	private static final int BUFFER = 2048;

	public static void clean(String absPath) {
		logger.info("clean begin");

		File adFile = new File(absPath);

		if (adFile.exists()) {
			logger.info("remove file " + absPath);
			adFile.delete();
		} else {
			logger.info("file not exists");
		}

		logger.info("clean end");
	}

	public static void download(String url, String absPath) throws Exception {
		logger.info("download begin");

		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(absPath)) {
			logger.info("empty input");
			return;
		}

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);

		CloseableHttpResponse response = null;
		try {

			logger.info("download url: " + url + " to path: " + absPath);

			response = httpclient.execute(httpGet);
			HttpEntity respEntity = response.getEntity();

			if (respEntity.isStreaming()) {
				InputStream in = null;
				FileOutputStream out = null;

				try {
					in = respEntity.getContent();

					out = new FileOutputStream(new File(absPath));

					byte[] b = new byte[BUFFER];
					int len = 0;
					while ((len = in.read(b)) != -1) {
						out.write(b, 0, len);
					}
				} catch (Exception e) {
					logger.error("error in file copy");
					throw e;
				} finally {
					in.close();
					out.close();

					logger.error("close io.");
				}
			}

		} catch (IOException e) {
			logger.error("error in download file");
			throw e;
		} finally {
			try {
				if (response != null) {
					response.close();
					logger.error("close io.");
				}
			} catch (IOException e) {
				logger.error("error when close io");
				throw e;
			}
		}

		logger.info("download end");
	}
}
