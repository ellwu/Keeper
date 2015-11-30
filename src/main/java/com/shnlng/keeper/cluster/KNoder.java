package com.shnlng.keeper.cluster;

import com.hazelcast.core.Member;

public class KNoder {
	private String id;
	private String ip;
	private int port;
	
	public KNoder(){
		
	}
	
	public KNoder(Member member){
		this.id = member.getUuid();
		this.ip  = member.getSocketAddress().getAddress().toString();
		this.port = member.getSocketAddress().getPort();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String toString() {
		return this.id + " " + this.ip + ":" + this.port;
	}
}
