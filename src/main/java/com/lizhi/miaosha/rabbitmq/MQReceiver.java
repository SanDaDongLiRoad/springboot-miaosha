package com.lizhi.miaosha.rabbitmq;

import com.lizhi.miaosha.convert.CommonConvert;
import com.lizhi.miaosha.domain.MiaoshaOrder;
import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.redis.JedisService;
import com.lizhi.miaosha.redis.MiaoshaKey;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.service.MiaoshaOrderService;
import com.lizhi.miaosha.service.MiaoshaService;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 消息接收者
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/6/7
 */
@Slf4j
@Service
public class MQReceiver {

    @Autowired
    private JedisService jedisService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private MiaoshaGoodsService miaoshaGoodsService;

    @Autowired
    private MiaoshaOrderService miaoshaOrderService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        log.info("receive message:"+message);
    }

    @RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:"+message);
    }

    @RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:"+message);
    }

    @RabbitListener(queues=MQConfig.HEADER_QUEUE)
    public void receiveHeaderQueue(byte[] message) {
        log.info(" header  queue message:"+new String(message));
    }

    @RabbitListener(queues = MQConfig.MIAOSHA_MESSAGE)
    public void receiveMiaoShaMessage(String message){
        log.info("receiveMiaoShaMessage:"+message);
        MiaoshaMessage miaoshaMessage  = CommonConvert.stringToBean(message, MiaoshaMessage.class);
        long goodsId = miaoshaMessage.getGoodsId();
        MiaoshaUser miaoshaUser = miaoshaMessage.getMiaoshaUser();
        MiaoshaGoodsVO miaoshaGoodsVO = miaoshaGoodsService.queryMiaoshaGoodsVOById(goodsId);
        //判断库存是否充足
        if(Objects.equals(null,miaoshaGoodsVO) || Objects.equals(0,miaoshaGoodsVO.getStockCount())){
            jedisService.set(MiaoshaKey.isGoodsOver, ""+goodsId, true);
            throw new GlobalException(ResultEnum.MIAOSHA_OVER);
        }

        //判断用户是否已经秒杀过
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.queryByUserIdAndGoodsId(miaoshaUser.getId(),goodsId);
        if(!Objects.equals(null,miaoshaOrder)){
            throw new GlobalException(ResultEnum.REPEATE_MIAOSHA);
        }
        miaoshaService.miaosha(miaoshaMessage.getMiaoshaUser(),miaoshaGoodsVO);
    }
}
