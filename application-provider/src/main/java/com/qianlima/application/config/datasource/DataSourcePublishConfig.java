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

@Configuration
@MapperScan(basePackages = "com.qianlima.application.mapper.publish", sqlSessionTemplateRef = "publishSqlSessionTemplate")
public class DataSourcePublishConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.publish")
    public DataSource dataSourcePublish() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "publishSqlSessionTemplate")
    public SqlSessionTemplate qianlimaSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(qianlimaSqlSessionFactory());
    }

    @Bean(name = "publishSqlSessionFactory")
    public SqlSessionFactory qianlimaSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSourcePublish());
        bean.setMapperLocations(MapperLocationUtil.resolveMapperLocations(new String[]{"classpath:mapper/publish/*.xml"}));
        bean.setTypeAliasesPackage("com.qianlima.application.domain.publish");
        return bean.getObject();
    }

}
