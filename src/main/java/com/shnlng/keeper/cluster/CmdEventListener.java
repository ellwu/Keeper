package com.shnlng.keeper.cluster;

import java.util.Date;

import org.apache.log4j.Logger;

import com.hazelcast.core.Member;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class CmdEventListener implements MessageListener<CmdEvent> {
	public static final Logger logger = Logger.getLogger(CmdEventListener.class);

	@Override
	public void onMessage(Message<CmdEvent> cmdEventMsg) {

		Member member = cmdEventMsg.getPublishingMember();
		KNoder noder = new KNoder(member);
		logger.info("get message from noder: " + noder);
		logger.info("get message time: "
				+ new Date(cmdEventMsg.getPublishTime()));

		CmdEvent cmdEvent = cmdEventMsg.getMessageObject();

		Class<? extends CmdAction> cmdActionClass = cmdEvent.getActionClass();
		if (cmdActionClass != null) {
			CmdAction cmdAction = null;
			try {
				cmdAction = cmdActionClass.newInstance();

				cmdAction.run(cmdEvent.getParams());
			} catch (InstantiationException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}

	}

}
