package com.xkq.gmall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: xkq
 * @date: 2023/3/25 11:24
 * @description:
 * 1、导入依赖
 * 2、编写配置，给容器注入一个RestHighLevelClient
 * 3、参照api
 */
@Configuration
public class GmallElasticSearchConfig {
    public static final RequestOptions COMMON_OPTIONS;
    static {
        //通用设置项
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        // builder.addHeader("Authorization", "Bearer " + TOKEN);
        // builder.setHttpAsyncResponseConsumerFactory(
        //         new HttpAsyncResponseConsumerFactory
        //                 .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esRestHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.188.100", 9200, "http")));
    }
}
