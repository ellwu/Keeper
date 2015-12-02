package com.shnlng.keeper.cluster.event;

import java.io.Serializable;
import java.util.Map;

public class ActRespEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public ActRespEvent() {
	}
	
	public ActRespEvent(Map<String, Object> values) {
		this.values = values;
	}

	private Map<String, Object> values;

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}
}
