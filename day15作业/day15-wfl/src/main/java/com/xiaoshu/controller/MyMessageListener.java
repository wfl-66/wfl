package com.xiaoshu.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.entity.Person;

public class MyMessageListener implements MessageListener{
	
	

@Autowired
	private RedisTemplate redistTemplate;
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			redistTemplate.boundHashOps("perlist").put("per1", text);
			Person person = JSONObject.parseObject(text,Person.class);
			System.out.println("-----------------"+person);
			redistTemplate.boundHashOps("perlist").put("per", person);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
