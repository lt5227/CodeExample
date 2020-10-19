package com.stackstone.study.springboot.rabbitmq;

import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.OrderRecordEntity;
import com.stackstone.study.springboot.rabbitmq.db.mysql.repository.OrderRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RabbitmqApplicationTests {
	@Autowired
	private OrderRecordRepository orderRecordRepository;

	@Test
	void contextLoads() {
		List<OrderRecordEntity> orderRecordEntities = orderRecordRepository.findAll();
		System.out.println(orderRecordEntities);
	}

}
