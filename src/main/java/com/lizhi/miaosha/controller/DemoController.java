package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.rabbitmq.MQSender;
import com.lizhi.miaosha.util.ResultUtil;
import com.lizhi.miaosha.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试用
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/6/11
 */
@Slf4j
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private MQSender sender;


    @ResponseBody
    @GetMapping("testMQ")
    public ResultVO<String> testMQ() {
//        sender.send("hello,imooc");
//        sender.sendTopic("hello,imooc徐立志");
        sender.sendHeader("hello,imooc徐立志");
        return ResultUtil.success("Hello，world");
    }

}
