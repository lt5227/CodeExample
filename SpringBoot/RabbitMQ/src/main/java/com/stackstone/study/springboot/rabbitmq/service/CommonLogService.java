package com.stackstone.study.springboot.rabbitmq.service;

import com.stackstone.study.springboot.rabbitmq.core.dto.LogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * CommonLogService
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 19:08
 * @since 1.0.0
 */
@Service
@Slf4j
public class CommonLogService {


    /**
     * 通用的写日志服务逻辑
     * Insert log.
     *
     * @param logDTO the log
     */
    public void insertLog(LogDTO logDTO) {
        log.info("通用的写日志服务逻辑 数据: {}", logDTO);
    }
}
