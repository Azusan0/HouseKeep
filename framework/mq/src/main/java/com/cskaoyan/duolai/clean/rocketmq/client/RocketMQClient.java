package com.cskaoyan.duolai.clean.rocketmq.client;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.duolai.clean.common.expcetions.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.nio.charset.Charset;

@Component
@Slf4j
public class RocketMQClient {

    @Value("${rocketmq.namesrv.address}")
    String namesrvAddr;

    @Value("${rocketmq.producer.group}")
    String producerGroup;

    DefaultMQProducer mqProducer;

    @PostConstruct
    public void init(){

        mqProducer = new DefaultMQProducer(producerGroup);

        mqProducer.setNamesrvAddr(namesrvAddr);

        try {
            mqProducer.start();
            log.info("mqProducer inited successed...namesrcAddr:{}, producerGroup:{}", namesrvAddr,producerGroup);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param topicName 主题名称
     * @param messageBody 消息内容
     * @return: java.lang.Boolean
     * 功能描述: 发送普通消息
     */
    public Boolean sendMessage(String topicName, Object messageBody) {
        try {
            String jsonMessage = JSON.toJSONString(messageBody);
            log.info("准备发送消息，topic:{}, message:{}", topicName, jsonMessage);

            Message message = new Message(topicName, jsonMessage.getBytes(Charset.forName("utf-8")));

            SendResult sendResult = mqProducer.send(message);

            if (sendResult == null
                    || sendResult.getSendStatus() == null
                    || !SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
                log.info("消息发送失败，topic:{}, message:{}", topicName,jsonMessage);
                return false;
            }

            log.info("消息发送成功，topic:{}, message:{}", topicName,jsonMessage);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("消息发送异常，topic:{}, message:{}",topicName,messageBody);
            throw new CommonException( topicName + "消息发送异常");
        }
    }
    /**
     * @param topicName 主题名称
     * @param messageBody 消息内容
     * @return: java.lang.Boolean
     * 功能描述: 发送延迟消息
     */
    public Boolean sendDelayMessage(String topicName, Object messageBody, int delayLevel) {

        try {
            String jsonMessage = JSON.toJSONString(messageBody);
            log.info("准备发送延迟消息，topic:{}, message:{}", topicName, jsonMessage);

            Message message = new Message(topicName, jsonMessage.getBytes(Charset.forName("utf-8")));

            // 设置消息延迟级别
            if (delayLevel <= 0) throw new RuntimeException("发送延迟消息，延迟级别应该大于0， 当前，delayLevel:" + delayLevel);

            message.setDelayTimeLevel(delayLevel);
            SendResult sendResult = mqProducer.send(message);

            if (sendResult == null
                    || sendResult.getSendStatus() == null
                    || !SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
                log.info("延迟消息发送失败，topic:{}, message:{}", topicName,jsonMessage);
                return false;
            }

            // 消息发送成功
            log.info("延迟消息发送成功，topic:{}, message:{}", topicName, jsonMessage);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("延迟消息发送异常，topic:{}, message:{}",topicName,messageBody);
        }
        return false;

    }



}
