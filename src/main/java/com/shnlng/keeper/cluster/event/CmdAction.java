package com.shnlng.keeper.cluster.event;

import java.util.Map;

public interface CmdAction {

	void run(Map<String, Object> params);
}
