package com.stackstone.study.elasticsearch.high;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.stackstone.study.elasticsearch.high.ClientInitialization.getRestHighLevelClient;

/**
 * IndexApiExample
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/11/5 13:29
 * @since 1.0.0
 */
public class IndexApiExample {

    @Test
    public void indexRequestExample1() throws IOException {
        IndexRequest request = new IndexRequest("posts");
        request.id("1");
        String jsonString = "{" +
                "\"user\":\"Lee\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        IndexResponse response = ClientInitialization.getRestHighLevelClient()
                .index(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Test
    public void indexRequestExample2() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>(3);
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("1").source(jsonMap);
        IndexResponse response = ClientInitialization.getRestHighLevelClient()
                .index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }
}
