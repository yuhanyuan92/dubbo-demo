package com.qianlima.application.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/*@Configuration
@MapperScan(basePackages = "com.qianlima.application.mapper.cusdata", sqlSessionTemplateRef = "cusdataSqlSessionTemplate")
public class DataSourceCusDataConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.cusdata")
    public DataSource dataSourceCusData() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "cusdataSqlSessionTemplate")
    public SqlSessionTemplate cusdataSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(cusdataSqlSessionFactory());
    }

    @Bean(name = "cusdataSqlSessionFactory")
    public SqlSessionFactory cusdataSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSourceCusData());
        bean.setTypeAliasesPackage("com.qianlima.application.domain.cusdata");
        bean.setMapperLocations(MapperLocationUtil.resolveMapperLocations(new String[]{"classpath:mapper/cusdata/*.xml"}));
        return bean.getObject();
    }


}*/
