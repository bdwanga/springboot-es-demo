package com.wbd.config;

import com.wbd.utils.EsClientFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.elasticsearch.client.*;

@Configuration
public class EsConfig
{
    @Value("${elasticsearch.cluster-nodes}")
    private String nodes;

    @Bean(initMethod = "init", destroyMethod = "close")
    public EsClientFactory getFactory() {
        return EsClientFactory.getEsClientFactory(this.nodes);
    }

    @Bean
    @Scope("singleton")
    public RestClient getRestClient() {
        return this.getFactory().getClient();
    }

    @Bean
    @Scope("singleton")
    public RestHighLevelClient getRestHighLevelClient() {
        return this.getFactory().getRestHighLevelClient();
    }
}
