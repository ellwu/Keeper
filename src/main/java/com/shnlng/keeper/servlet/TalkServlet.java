package com.shnlng.keeper.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shnlng.keeper.common.TalkItem;
import com.shnlng.keeper.common.TalkReponse;

public class TalkServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TalkServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("start menu download");

		String adHostURL = req.getRequestURL().toString().replace("talk", "res");

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");

		TalkReponse aTalkResp = new TalkReponse();

		aTalkResp.setMid("20001");
		aTalkResp.setMname("YouXianMeiDi");
		aTalkResp.setMmaxitems(10);

		List<TalkItem> items = new ArrayList<TalkItem>();
		TalkItem aItem = new TalkItem();
		aItem.setAid("300001");
		aItem.setRid("ADV0001");

		List<String> urls = new ArrayList<String>();
		urls.add(adHostURL + "?rid=ADV0001");
		aItem.setUrls(urls);
		items.add(aItem);

		aTalkResp.setItems(items);

		ObjectMapper om = new ObjectMapper();

		PrintWriter writer = resp.getWriter();
		writer.write(om.writeValueAsString(aTalkResp));
		writer.flush();

		logger.info("start menu download");
	}

}
