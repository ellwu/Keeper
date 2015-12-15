package com.shnlng.keeper.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Beat {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String time;

	public Beat() {

	}

	public Beat(Date now) {
		this.time = SDF.format(now);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
