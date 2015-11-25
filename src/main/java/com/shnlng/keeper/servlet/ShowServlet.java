package com.shnlng.keeper.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shnlng.keeper.Bootstrap;

public class ShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String keeperRepoPath;

	@Override
	public void init() throws ServletException {
		keeperRepoPath = Bootstrap.keeperProps.getProperty("keeper.repository.location");
		if (keeperRepoPath == null || "".equals(keeperRepoPath.trim())) {
			keeperRepoPath = System.getProperty("user.dir");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String id = req.getParameter("id");
		if (id == null || "".equals(id)) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		PrintWriter writer = resp.getWriter();
		writer.write(keeperRepoPath);
		writer.flush();
	}
}
