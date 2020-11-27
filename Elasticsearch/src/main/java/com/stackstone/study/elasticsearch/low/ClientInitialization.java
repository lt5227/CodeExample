package com.stackstone.study.elasticsearch.low;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

/**
 * ClientInitialization
 * A RestClient instance can be built through the corresponding RestClientBuilder class,
 * created via RestClient#builder(HttpHost...) static method.
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.9/java-rest-low-usage-initialization.html#java-rest-low-usage-initialization">
 * 官方文档地址
 * </a>
 *
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/11/5 11:07
 * @since 1.0.0
 */
public class ClientInitialization {
    public static void main(String[] args) throws IOException {
        RestClient restClient = getRestClient();
        restClient.close();
    }

    public static RestClient getRestClient() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9600, "http")
        );
        /*
            Set the default headers that need to be sent with each request,
            to prevent having to specify them with each single request
        */
        Header[] defaultHeaders = new Header[]{new BasicHeader("header", "value")};
        builder.setDefaultHeaders(defaultHeaders);
        /*
            Set a listener that gets notified every time a node fails, in case actions need to be taken.
            Used internally when sniffing on failure is enabled.
         */
        builder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(Node node) {
                System.out.println("连接错误通知处理");
            }
        });
        /*
            Set the node selector to be used to filter the nodes the client will send requests
            to among the ones that are set to the client itself. This is useful for instance to
            prevent sending requests to dedicated master nodes when sniffing is enabled.
            By default the client sends requests to every configured node.
         */
        builder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setSocketTimeout(10000));

        /*
            Set a callback that allows to modify the http client configuration
            (e.g. encrypted communication over ssl, or anything that the
            org.apache.http.impl.nio.client.HttpAsyncClientBuilder allows to set)
         */
        /*
        builder.setHttpClientConfigCallback(
                httpAsyncClientBuilder ->
                        httpAsyncClientBuilder.setProxy(new HttpHost("proxy", 9000, "http"))
        );
        */

        return builder.build();
    }
}
