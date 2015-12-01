package com.shnlng.keeper.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class EchoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id = req.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		PrintWriter writer = resp.getWriter();
		writer.write(new Date().getTime() + "");
		writer.flush();
	}
}
