package com.lizhi.miaosha.rabbitmq;

import com.lizhi.miaosha.convert.CommonConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息发送者
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/6/7
 */
@Slf4j
@Service
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(Object message){
        String msg = CommonConvert.beanToString(message);
        log.info("send message :{}",msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);
    }

    public void sendTopic(Object message) {
        String msg = CommonConvert.beanToString(message);
        log.info("send topic message:"+msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
    }

    public void sendFanout(Object message) {
		String msg = CommonConvert.beanToString(message);
		log.info("send fanout message:"+msg);
		amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
	}

	public void sendHeader(Object message) {
		String msg = CommonConvert.beanToString(message);
		log.info("send fanout message:"+msg);
		MessageProperties properties = new MessageProperties();
		properties.setHeader("header1", "value1");
		properties.setHeader("header2", "value2");
		Message obj = new Message(msg.getBytes(), properties);
		amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
	}

    /**
     * 秒杀消息入队
     * @param message
     */
	public void sendMiaoshaMessage(Object message){
        String msg = CommonConvert.beanToString(message);
        log.info("sendMiaoshaMessage message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_MESSAGE,msg);
    }
}
