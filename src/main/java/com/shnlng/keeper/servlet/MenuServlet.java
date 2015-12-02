package com.shnlng.keeper.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shnlng.keeper.common.Item;
import com.shnlng.keeper.common.Menu;
import com.shnlng.keeper.util.IdGen;

public class MenuServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MenuServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("start menu download");

		String did = req.getParameter("did");
		if (StringUtils.isEmpty(did)) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");

		Menu aMenu = new Menu();

		aMenu.setMerchantId(IdGen.id32());
		aMenu.setMerchantName(IdGen.random(9));

		List<Item> items = new ArrayList<Item>();

		for (int i = 1; i < 4; i++) {
			Item e = new Item();

			String adId = IdGen.id32();
			e.setAdId(adId);
			e.setAdvertiserId(IdGen.id32());
			e.setPlaySequence(i * 10);

			List<String> adUrls = new ArrayList<String>();

			adUrls.add("http://192.168.10.1:8090/ad?id=" + adId);
			adUrls.add("http://192.168.10.2:8090/ad?id=" + adId);
			adUrls.add("http://192.168.10.3:8090/ad?id=" + adId);
			e.setAdUrls(adUrls);

			items.add(e);
		}

		aMenu.setItems(items);

		ObjectMapper om = new ObjectMapper();

		PrintWriter writer = resp.getWriter();
		writer.write(om.writeValueAsString(aMenu));
		writer.flush();

		logger.info("start menu download");
	}

}
