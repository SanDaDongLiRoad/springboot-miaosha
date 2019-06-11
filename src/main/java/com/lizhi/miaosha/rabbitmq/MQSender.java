package com.lizhi.miaosha.rabbitmq;

import com.lizhi.miaosha.convert.CommonConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
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
}
