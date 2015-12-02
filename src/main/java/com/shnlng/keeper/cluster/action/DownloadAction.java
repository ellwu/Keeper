package com.shnlng.keeper.cluster.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shnlng.keeper.Bootstrap;
import com.shnlng.keeper.cluster.event.CmdAction;

public class DownloadAction implements CmdAction, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DownloadAction.class);

	@Override
	public void run(Map<String, Object> params) {
		logger.info("run download action");

		System.out.println("download begin. params: " + params);

		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("params", params);
		
		try {
			
			logger.info("tell others I am done.");
			Bootstrap.cluster.sendResp(values);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("download done resp error");
		}
		
		logger.info("download action done");
	}

}
