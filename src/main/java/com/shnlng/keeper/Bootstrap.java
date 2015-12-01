package com.shnlng.keeper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.shnlng.keeper.cluster.CmdEventListener;

public class Bootstrap {
	public static final Logger logger = Logger.getLogger(Bootstrap.class);

	private static Properties keeperProps = null;
	private static int keeperServerPort;

	public static KCluster cluster = new KCluster();
	public static KeeperServer server = null;

	private static void init() {
		logger.info("Bootstrap init");
		loadProperties();
	}

	public static Properties getProps() {
		logger.info("Bootstrap getProps");

		if (keeperProps == null) {
			loadProperties();
		}

		return keeperProps;
	}

	public static void loadProperties() {
		logger.info("Bootstrap loadProperties");

		InputStream is = null;
		String configPath = System.getProperty("config");

		if (StringUtils.isEmpty(configPath)) {
			configPath = "/keeper.conf";

			is = Bootstrap.class.getClass().getResourceAsStream(configPath);
		} else {

			try {
				is = new BufferedInputStream(new FileInputStream(configPath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error("config file not fould");
				System.exit(1);
			}

		}

		logger.info("load config file from this path: " + configPath);
		keeperProps = new Properties();
		try {
			keeperProps.load(is);
			logger.info("finish load config file");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			logger.info("system exit");
			System.exit(1);
		}
	}

	public static String getKeeperPath() {
		String keeperRepoPath = Bootstrap.getProps().getProperty("keeper.repository.location");
		if (StringUtils.isEmpty(keeperRepoPath)) {
			keeperRepoPath = System.getProperty("KEEPER_PATH");

			if (StringUtils.isEmpty(keeperRepoPath)) {
				keeperRepoPath = System.getProperty("user.dir");
			}
		}

		return keeperRepoPath;
	}

	public static String getRepoPath() {
		return Bootstrap.getKeeperPath() + File.separator + "repository";
	}
	
	public static String getAdAbsPath(String adId){
		return getRepoPath() + File.separator + adId;
	}

	private static void startCluster() {
		logger.info("start cluster");

		Thread clusterThread = new Thread(new Runnable() {

			@Override
			public void run() {
				logger.info("start cluster in a new thread.");
				cluster.start();

				try {

					logger.info("set command event listener for stand alone keeper.");
					cluster.addListeners(KCluster.EventTopic.CommandEventTopic, new CmdEventListener());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("set command event listener for stand alone keeper. error occurs!");
				}
			}

		});

		clusterThread.start();
	}

	private static void startkeeperServer() {
		String strPort = getProps().getProperty("keeper.server.port");
		try {
			keeperServerPort = Integer.parseInt(strPort);
		} catch (Exception e) {
			e.printStackTrace();
			keeperServerPort = 8080;
		}

		server = new KeeperServer(keeperServerPort);

		logger.info("run server at " + keeperServerPort);
		server.run();
	}

	public static void start() {
		logger.info("start");
		init();

		if (Boolean.parseBoolean(getProps().getProperty("keeper.cluster.enable"))) {
			logger.info("cluster was enabled, starting....");
			startCluster();
		}

		if (Boolean.parseBoolean(getProps().getProperty("keeper.server.enable"))) {
			logger.info("server was enabled, starting....");
			logger.info("start");
			startkeeperServer();
		}

		logger.info("end");
	}

	public static void main(String[] args) {
		Bootstrap.start();
	}

}
