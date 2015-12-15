package com.shnlng.keeper.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shnlng.keeper.common.Beat;

public class BeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper om = new ObjectMapper();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id = req.getParameter("did");
		if (StringUtils.isEmpty(id)) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		PrintWriter writer = resp.getWriter();
		writer.write(om.writeValueAsString(new Beat(new Date())));
		writer.flush();
	}

	public static void main(String[] args) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
}
