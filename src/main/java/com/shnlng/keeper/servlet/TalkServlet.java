package com.shnlng.keeper.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("start talk download");

		//if ("application/json".equals(req.getContentType())) {

			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(req.getInputStream()));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			logger.info(sb.toString());

		//}

		String adHostURL = req.getRequestURL().toString().replace("talk", "res");

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");

		TalkReponse aTalkResp = new TalkReponse();

		aTalkResp.setDate(sdf.format(new Date()));
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
		aItem.setS(10);
		items.add(aItem);

		aItem = new TalkItem();
		aItem.setAid("300002");
		aItem.setRid("ADV0002");

		urls = new ArrayList<String>();
		urls.add(adHostURL + "?rid=ADV0002");
		aItem.setUrls(urls);
		aItem.setS(20);
		items.add(aItem);

		aItem = new TalkItem();
		aItem.setAid("300003");
		aItem.setRid("ADV0003");

		urls = new ArrayList<String>();
		urls.add(adHostURL + "?rid=ADV0003");
		aItem.setUrls(urls);
		aItem.setS(30);
		items.add(aItem);

		aTalkResp.setItems(items);

		ObjectMapper om = new ObjectMapper();

		PrintWriter writer = resp.getWriter();
		writer.write(om.writeValueAsString(aTalkResp));
		writer.flush();

		logger.info("end talk download");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
