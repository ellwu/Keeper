package com.shnlng.keeper.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.shnlng.keeper.Bootstrap;

public class AdServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(AdServlet.class);
	private String keeperRepoPath;
	private int bufferSize = 2048;

	@Override
	public void init() throws ServletException {
		logger.info("init ShowServlet");

		keeperRepoPath = Bootstrap.getRepoPath();

		logger.info("set keeper's repository path to: " + keeperRepoPath);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");

		String showFileName = keeperRepoPath + File.separator + id;
		File file = new File(showFileName);

		logger.info("download file id: " + showFileName);

		if (!file.exists()) {
			logger.info("file not exists");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		} else {
			logger.info("file download starting...");
			resp.reset();
			resp.setHeader("Content-disposition", "attachment;filename=" + id);
			
			ServletOutputStream sops = null;
			FileInputStream fis = null;
			
			try{
				sops = resp.getOutputStream();
				fis = new FileInputStream(file);
				
				copyStream(fis, sops, true);
			}catch(Exception e){
				logger.error("closing io" + e.getMessage());
			}finally{
				logger.info("closing io");
				fis.close();
				sops.close();
			}
			
			fis = null;
			sops = null;
			file = null;
			logger.info("file download completed");
		}
	}

	private final long copyStream(InputStream source, OutputStream dest, boolean flush) {
		int bytes;
		long total = 0l;
		byte[] buffer = new byte[bufferSize];
		try {
			while ((bytes = source.read(buffer)) != -1) {
				if (bytes == 0) {
					bytes = source.read();
					if (bytes < 0)
						break;
					dest.write(bytes);
					if (flush)
						dest.flush();
					total += bytes;
				}
				dest.write(buffer, 0, bytes);
				if (flush)
					dest.flush();
				total += bytes;
			}

		} catch (IOException e) {
			logger.error("file content copying error");
			logger.error(e.getMessage());
			throw new RuntimeException("IOException caught while copying.", e);

		}
		return total;
	}
}
