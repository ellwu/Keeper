package com.shnlng.keeper.common;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	private String adId;
	private List<String> adUrls;

	private String advertiserId;
	private int playSequence;

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public List<String> getAdUrls() {
		return adUrls;
	}

	public void setAdUrls(List<String> adUrls) {
		this.adUrls = adUrls;
	}

	public String getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}

	public int getPlaySequence() {
		return playSequence;
	}

	public void setPlaySequence(int playSequence) {
		this.playSequence = playSequence;
	}

}
