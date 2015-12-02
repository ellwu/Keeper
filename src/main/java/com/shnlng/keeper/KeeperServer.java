package com.shnlng.keeper;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.shnlng.keeper.servlet.AdServlet;
import com.shnlng.keeper.servlet.EchoServlet;
import com.shnlng.keeper.servlet.MenuServlet;

public class KeeperServer extends Server {
	private static final Logger logger = Logger.getLogger(KeeperServer.class);

	private ServletContextHandler context;

	public KeeperServer() {
		super();
	}

	public KeeperServer(int port) {
		super(port);
	}

	private void init() {
		logger.info("init");

		initContext();

		setServlets();

	}

	private void initContext() {
		logger.info("init servlet context");
		this.context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		this.setHandler(this.context);
	}

	private void setServlets() {
		logger.info("set servlets");
		logger.info("set echo servlet to handle mapping /echo");
		this.context.addServlet(new ServletHolder(new EchoServlet()), "/echo");

		logger.info("set show servlet to handle mapping /ad");
		this.context.addServlet(new ServletHolder(new AdServlet()), "/ad");
		
		logger.info("set show servlet to handle mapping /menu");
		this.context.addServlet(new ServletHolder(new MenuServlet()), "/menu");
	}

	public void run() {
		logger.info("running keeper server for http requests");
		this.init();

		try {
			this.start();
			this.join();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("keeper server running into errors");
			logger.error(e.getMessage());
		}
	}

}