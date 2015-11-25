package com.shnlng.keeper;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.shnlng.keeper.servlet.EchoServlet;
import com.shnlng.keeper.servlet.ShowServlet;

public class KeeperServer extends Server {
	private ServletContextHandler context;
	
	public KeeperServer(){
		super();
	}
	
	public KeeperServer(int port){
		super(port);
	}
	
	private void init(){
		
		initContext();
		
		setServlets();
		
	}
	
	private void initContext() {
		this.context = new ServletContextHandler(ServletContextHandler.SESSIONS);  
        context.setContextPath("/");  
        this.setHandler(this.context);  
	}

	private void setServlets() {
		this.context.addServlet(new ServletHolder(new EchoServlet()), "/echo");
		this.context.addServlet(new ServletHolder(new ShowServlet()), "/show");
	}

	public void run(){
		this.init();
        
        try {
			this.start();
			this.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
