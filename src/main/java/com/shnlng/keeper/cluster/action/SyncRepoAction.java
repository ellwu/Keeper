package com.shnlng.keeper.cluster.action;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.shnlng.keeper.Bootstrap;
import com.shnlng.keeper.cluster.CmdAction;
import com.shnlng.keeper.util.FileUtil;

public class SyncRepoAction implements CmdAction, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SyncRepoAction.class);

	@Override
	public void run(Map<String, Object> params) {
		logger.info("sysnc begin");

		if (params == null || params.size() == 0) {

			logger.info("no need to sysnc");
			return;
		}

		// params: key(ad id), value(url)
		String adId = null;
		String toAbsPath = null;
		String adUrl = null;
		for (Entry<String, Object> adEntry : params.entrySet()) {

			adId = adEntry.getKey();
			toAbsPath = Bootstrap.getAdAbsPath(adId);
			adUrl = (String) adEntry.getValue();

			logger.info("download ad: " + adId + " url: " + adUrl + " to: " + toAbsPath);

			try {
				FileUtil.download(adUrl, toAbsPath);
			} catch (Exception e) {
				e.printStackTrace();

				logger.error("download error. adId: " + adId + " url: " + adUrl);
			}
			logger.info("download ad: " + adId + " end");
		}

		logger.info("sysnc end");
	}

}
