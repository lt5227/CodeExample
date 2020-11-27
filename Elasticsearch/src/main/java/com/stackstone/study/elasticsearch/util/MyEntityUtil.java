package com.stackstone.study.elasticsearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * MyEntityUtil
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/11/5 11:34
 * @since 1.0.0
 */
public class MyEntityUtil {

    private MyEntityUtil() {

    }

    public static JSONObject parseHttpEntity(HttpEntity httpEntity) {
        String result = null;
        try {
            result = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(result);
    }
}
