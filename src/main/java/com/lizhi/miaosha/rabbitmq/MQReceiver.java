package com.lizhi.miaosha.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 消息接收者
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/6/7
 */
@Slf4j
@Service
public class MQReceiver {

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        log.info("receive message:"+message);
    }
}