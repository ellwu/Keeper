package com.shnlng.keeper.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.shnlng.keeper.Bootstrap;
import com.shnlng.keeper.cluster.KNoder;
import com.shnlng.keeper.cluster.action.DownloadAction;

public class EchoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String id = req.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		List<KNoder> noders = Bootstrap.cluster.listNoders();
		System.out.println(noders);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		try {
			Bootstrap.cluster.sendCmd(DownloadAction.class, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Date().toString());
		writer.flush();
	}

}
