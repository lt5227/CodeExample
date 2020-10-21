package com.stackstone.study.springboot.rabbitmq.service;

import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.ProductEntity;
import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.ProductRobbingRecordEntity;
import com.stackstone.study.springboot.rabbitmq.db.mysql.repository.ProductRepository;
import com.stackstone.study.springboot.rabbitmq.db.mysql.repository.ProductRobbingRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ConcurrencyService
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 15:32
 * @since 1.0.0
 */
@Service
@Slf4j
public class ConcurrencyService {

    private static final String PRODUCT_NO = "product_10010";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductRobbingRecordRepository productRobbingRecordRepository;

    public void manageRobbing(String mobile) {
        try {
            ProductEntity product = productRepository.findByProductNo(PRODUCT_NO);
            if (product != null && product.getTotal() > 0) {
                int result = productRepository.updateTotal(PRODUCT_NO);
                if (result > 0) {
                    // 类似乐观锁的概念，限制库存被扣减成负数，保证用户抢单的正确性
                    log.info("当前手机号：{} 恭喜您抢到单了", mobile);
                    ProductRobbingRecordEntity robbingRecordEntity = new ProductRobbingRecordEntity();
                    robbingRecordEntity.setMobile(mobile);
                    robbingRecordEntity.setProductId(product.getId());
                    productRobbingRecordRepository.save(robbingRecordEntity);
                }
            } else {
                log.error("当前手机号:{} 抢不到单!", mobile);
            }
        } catch (Exception e) {
            log.error("处理抢单发生异常： mobile={}", mobile, e.fillInStackTrace());
        }
    }
}
