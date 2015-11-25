package com.shnlng.keeper;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class Bootstrap {
	public static Logger logger = Logger.getLogger(Bootstrap.class);
	
	public static Properties keeperProps = null;
	public static int keeperServerPort;
	public static String keeperRepoPath;

	private static void init() {
		logger.info("Bootstrap init");
		loadProperties();
	}

	public static void loadProperties() {
		String configPath = System.getProperty("config");
		if(StringUtils.isEmpty(configPath)){
			configPath = "/keeper.conf";
			
		}
		logger.info("load config file from this path: " + configPath);
		
		InputStream is = Bootstrap.class.getClass().getResourceAsStream(
				configPath);
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

	public static void start() {
		logger.info("start");
		init();

		String strPort = keeperProps.getProperty("keeper.server.port");
		try {
			keeperServerPort = Integer.parseInt(strPort);
		} catch (Exception e) {
			e.printStackTrace();
			keeperServerPort = 8080;
		}

		KeeperServer server = new KeeperServer(keeperServerPort);

		logger.info("run server at " + keeperServerPort);
		server.run();

	}

	public static void main(String[] args) {
		Bootstrap.start();
	}

}
