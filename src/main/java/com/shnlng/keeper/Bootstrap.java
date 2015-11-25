package com.shnlng.keeper;

import java.io.InputStream;
import java.util.Properties;

public class Bootstrap {
	public static Properties keeperProps = null;
	public static int keeperServerPort;
	public static String keeperRepoPath;

	private static void init() {
		loadProperties();
	}

	public static void loadProperties() {
		InputStream is = Bootstrap.class.getClass().getResourceAsStream(
				"/keeper.properties");
		keeperProps = new Properties();
		try {
			keeperProps.load(is);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void start() {
		init();

		String strPort = keeperProps.getProperty("keeper.server.port");
		try {
			keeperServerPort = Integer.parseInt(strPort);
		} catch (Exception e) {
			e.printStackTrace();
			keeperServerPort = 8080;
		}

		KeeperServer server = new KeeperServer(keeperServerPort);

		server.run();

	}

	public static void main(String[] args) {
		Bootstrap.start();
	}

}
