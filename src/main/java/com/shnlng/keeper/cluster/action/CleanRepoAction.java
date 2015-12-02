package com.shnlng.keeper.cluster.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shnlng.keeper.Bootstrap;
import com.shnlng.keeper.cluster.event.CmdAction;
import com.shnlng.keeper.util.FileUtil;

public class CleanRepoAction implements CmdAction, Serializable {
	public static final String ID_LIST_KEY = "IDS";

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CleanRepoAction.class);

	@Override
	public void run(Map<String, Object> params) {
		logger.info("clean begin");

		if (params == null || params.size() == 0) {

			logger.info("no need to clean");
			return;
		}

		if (params.get(ID_LIST_KEY) == null) {

			logger.info("no need to clean");
			return;
		}

		@SuppressWarnings("unchecked")
		List<String> adIds = (List<String>) params.get(ID_LIST_KEY);

		if (adIds == null || adIds.size() == 0) {
			logger.info("no need to clean");
			return;
		}

		for (String adId : adIds) {
			logger.info("clean begin adId: " + adId);

			FileUtil.clean(Bootstrap.getAdAbsPath(adId));

			logger.info("clean end");
		}

		logger.info("clean end");
	}

	public static void main(String[] args) {
		System.setProperty("KEEPER_PATH", "/Users/ell/Desktop");
		System.out.println(Bootstrap.getRepoPath());

		CleanRepoAction cleanAction = new CleanRepoAction();

		Map<String, Object> params = new HashMap<String, Object>();
		
		List<String> ids = new ArrayList<String>();
		ids.add("ADV0001");
		ids.add("ADV0003");
		ids.add("ADV000X");
		
		params.put(CleanRepoAction.ID_LIST_KEY, ids);
		cleanAction.run(params);
	}

}
