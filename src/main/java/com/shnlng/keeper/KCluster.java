package com.shnlng.keeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Member;
import com.hazelcast.core.MessageListener;
import com.shnlng.keeper.cluster.ActRespEvent;
import com.shnlng.keeper.cluster.CmdAction;
import com.shnlng.keeper.cluster.CmdEvent;
import com.shnlng.keeper.cluster.KNoder;

public class KCluster {
	public static final Logger logger = Logger.getLogger(KCluster.class);

	public enum EventTopic {
		CommandEventTopic("CMD_EVENT_TOPIC"), ActResponseEventTopic(
				"ACT_RESP_EVENT_TOPIC");

		private String value;

		private EventTopic(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	private HazelcastInstance instance;

	public void start() {
		logger.info("start cluster node");

		startInstance();
	}

	public void stop() {
		logger.info("stop cluster node");

		stopInstance();
	}

	private void startInstance() {
		logger.info("start hazelcast instance");

		instance = Hazelcast.newHazelcastInstance();
	}

	private void stopInstance() {
		logger.info("stop hazelcast instance");

		if (instance != null) {
			instance.shutdown();
		}
	}

	public <T> void addListeners(EventTopic topicName,
			MessageListener<T> msgListener) throws Exception {
		logger.info("addListeners");

		if (instance == null) {
			throw new Exception("cluster is not ready.");
		}

		ITopic<T> topic = instance.getTopic(topicName.value);
		topic.addMessageListener(msgListener);
	}

	public void sendCmd(Class<? extends CmdAction> actionClass,
			Map<String, Object> params) throws Exception {
		logger.info("send cmd event");

		if (instance == null) {
			throw new Exception("cluster is not ready.");
		}

		ITopic<CmdEvent> eventTopic = instance
				.getTopic(EventTopic.CommandEventTopic.value);
		eventTopic.publish(new CmdEvent(actionClass, params));
	}

	public void sendResp(Map<String, Object> values) throws Exception {
		logger.info("send response event");

		if (instance == null) {
			throw new Exception("cluster is not ready.");
		}

		ITopic<ActRespEvent> eventTopic = instance
				.getTopic(EventTopic.ActResponseEventTopic.value);
		eventTopic.publish(new ActRespEvent(values));
	}

	public List<KNoder> listNoders() {
		logger.info("get Noders begin");
		List<KNoder> noders = new ArrayList<KNoder>();

		if (instance == null || instance.getCluster() == null) {
			return noders;
		}

		Set<Member> members = instance.getCluster().getMembers();

		for (Member m : members) {
			noders.add(new KNoder(m));
		}

		logger.info("get Noders end");
		return noders;
	}

}
