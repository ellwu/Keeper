package com.shnlng.keeper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class Bootstrap {
	public static final Logger logger = Logger.getLogger(Bootstrap.class);

	public static Properties keeperProps = null;
	public static int keeperServerPort;
	public static String keeperRepoPath;

	public static KCluster cluster = null;
	public static KeeperServer server = null;

	private static void init() {
		logger.info("Bootstrap init");
		loadProperties();
	}

	public static void loadProperties() {
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

	private static void startCluster() {
		logger.info("start cluster");

		cluster = new KCluster();

		Thread clusterThread = new Thread(new Runnable() {

			@Override
			public void run() {
				cluster.start();
			}

		});

		clusterThread.start();
	}

	private static void startkeeperServer() {
		String strPort = keeperProps.getProperty("keeper.server.port");
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

		startCluster();

		startkeeperServer();
	}

	public static void main(String[] args) {
		Bootstrap.start();
	}

}
