package com.zew.demo.iot.service;

import com.alibaba.fastjson.JSONObject;
import com.zew.demo.iot.model.Weather;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Service
public class MessageListenerService {
    @Value("${broker.ip:10.0.4.214}")
    private String brokerIp;
    @Value("${subscribe.topic:/#}")
    private String topic;
    @Autowired
    private WeatherService weatherService;

    @PostConstruct
    public void ini() {
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            MqttClient client = new MqttClient("tcp://" + brokerIp + ":1883", host + System.currentTimeMillis(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(15);
            options.setUserName("userName");
            options.setPassword("userName".toCharArray());
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    log.warn("连接中断", cause);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    log.debug("消息送达{}，{}", topic, message);
                    byte[] payload = message.getPayload();
                    JSONObject jsonObject = JSONObject.parseObject(new String(payload));
                    Weather weather=new Weather();
                    weather.setTemperature(jsonObject.getInteger("temperature"));
                    weather.setHumidity(jsonObject.getFloat("humidity"));
                    weatherService.save(weather);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    log.debug("消息完成{}", token);
                }
            });

            client.connect(options);
            client.subscribe(topic,1);
        } catch (UnknownHostException | MqttException e) {
            log.error("mqtt连接broker错误", e);
        }

    }
}
