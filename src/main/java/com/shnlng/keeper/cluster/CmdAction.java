package com.shnlng.keeper.cluster;

import java.util.Map;

public interface CmdAction {

	void run(Map<String, Object> params);
}
