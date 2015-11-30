package com.shnlng.keeper.cluster;

import java.io.Serializable;
import java.util.Map;

public class CmdEvent implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public CmdEvent() {
	}
	
	public CmdEvent(Class<? extends CmdAction> actionClass, Map<String, Object> params) {
		this.actionClass = actionClass;
		this.params = params;
	}
	

	private Class<? extends CmdAction> actionClass;

	private Map<String, Object> params;

	public Class<? extends CmdAction> getActionClass() {
		return actionClass;
	}

	public void setActionClass(Class<? extends CmdAction> actionClass) {
		this.actionClass = actionClass;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
