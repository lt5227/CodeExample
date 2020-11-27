package com.stackstone.study.elasticsearch.high;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * ClientInitialization
 * The high-level client will internally create the low-level client used to perform requests based on the provided
 * builder. That low-level client maintains a pool of connections and starts some threads so you should close
 * the high-level client when you are well and truly done with it and it will in turn close the internal
 * low-level client to free those resources.
 *
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.x/java-rest-high-getting-started-initialization.html">
 * 官方文档
 * </a>
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/11/5 13:41
 * @since 1.0.0
 */
public class ClientInitialization {

    @Test
    public void restHighLevelClientInit() throws IOException {
       RestHighLevelClient client =  getRestHighLevelClient();
       client.close();
    }

    public static RestHighLevelClient getRestHighLevelClient() {
        HttpHost hosts1 = new HttpHost("localhost", 9200, "http");
        HttpHost hosts2 = new HttpHost("localhost", 9201, "http");
        RestClientBuilder restClientBuilder = RestClient.builder(hosts1, hosts2);
        return new RestHighLevelClient(restClientBuilder);
    }
}
