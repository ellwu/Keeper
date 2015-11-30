package com.shnlng.keeper.cluster;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hazelcast.core.Member;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public abstract class ActRespEventListener implements
		MessageListener<ActRespEvent> {
	public static final Logger logger = Logger
			.getLogger(ActRespEventListener.class);

	@Override
	public void onMessage(Message<ActRespEvent> actRespEventMsg) {
		Member member = actRespEventMsg.getPublishingMember();

		KNoder fromNoder = new KNoder(member);
		logger.info("get message from noder: " + fromNoder);
		logger.info("get message time: "
				+ new Date(actRespEventMsg.getPublishTime()));

		ActRespEvent actRespEvent = actRespEventMsg.getMessageObject();
		Map<String, Object> values = actRespEvent.getValues();

		onMsg(fromNoder, values);
	}

	public abstract void onMsg(KNoder fromNoder, Map<String, Object> values);

}
