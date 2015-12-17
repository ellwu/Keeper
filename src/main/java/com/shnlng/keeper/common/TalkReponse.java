package com.shnlng.keeper.common;

import java.io.Serializable;
import java.util.List;

public class TalkReponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String date;
	private String mid;
	private String mname;
	private int mmaxitems;
	private List<TalkItem> items;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public int getMmaxitems() {
		return mmaxitems;
	}

	public void setMmaxitems(int mmaxitems) {
		this.mmaxitems = mmaxitems;
	}

	public List<TalkItem> getItems() {
		return items;
	}

	public void setItems(List<TalkItem> items) {
		this.items = items;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
