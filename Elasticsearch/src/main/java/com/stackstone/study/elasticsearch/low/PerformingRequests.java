package com.stackstone.study.elasticsearch.low;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.stackstone.study.elasticsearch.util.MyEntityUtil;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * PerformingRequests
 * Once the RestClient has been created, requests can be sent by calling either performRequest or performRequestAsync.
 * performRequest is synchronous and will block the calling thread and return the Response when the request
 * is successful or throw an exception if it fails. performRequestAsync is asynchronous and accepts a ResponseListener
 * argument that it calls with a Response when the request is successful or with an Exception if it fails.
 *
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/11/5 11:22
 * @since 1.0.0
 */
public class PerformingRequests {
    /**
     * This is synchronous
     */
    @Test
    public void synchronousMethod() throws IOException {
        /*
            1. The HTTP method (GET, POST, HEAD, etc)
            2. The endpoint on the server
         */
        Request request = new Request("GET", "/");
        Response response = ClientInitialization.getRestClient().performRequest(request);
        JSONObject jsonObject = MyEntityUtil.parseHttpEntity(response.getEntity());
        String result = jsonObject.toString(SerializerFeature.PrettyFormat);
        System.out.println(result);
    }

    /**
     * This is asynchronous
     */
    @Test
    public void asynchronousMethod() throws InterruptedException {
        /*
            1. The HTTP method (GET, POST, HEAD, etc)
            2. The endpoint on the server
         */
        Request request = new Request("GET", "/");
        ClientInitialization.getRestClient().performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                // Handle the response
                JSONObject jsonObject = MyEntityUtil.parseHttpEntity(response.getEntity());
                String result = jsonObject.toString(SerializerFeature.PrettyFormat);
                System.out.println(result);
            }

            @Override
            public void onFailure(Exception e) {
                // Handle the failure
                e.printStackTrace();
            }
        });
        // 设置等待时间
        Thread.sleep(5000L);
    }
}
