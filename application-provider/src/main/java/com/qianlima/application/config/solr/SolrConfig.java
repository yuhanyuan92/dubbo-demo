package com.qianlima.application.config.solr;

import com.qianlima.application.repo.SolrUtile;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {

    @ConfigurationProperties("data.sjsolr")
    @Bean("solr1")
    public SolrUtile getSolrBean2(){
        return new SolrUtile();
    }

    @ConfigurationProperties("data.qysolr")
    @Bean("solr2")
    public SolrUtile getSolrBean3(){
        return new SolrUtile();
    }
}
