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
@MapperScan(basePackages = "com.qianlima.application.mapper.qlmlog", sqlSessionTemplateRef = "qlmlogSqlSessionTemplate")
public class DataSourceQlmlogConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.qlmlog")
    public DataSource dataSourceQlmlog() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "qlmlogSqlSessionTemplate")
    public SqlSessionTemplate qianlimaSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(qianlimaSqlSessionFactory());
    }

    @Bean(name = "qlmlogSqlSessionFactory")
    public SqlSessionFactory qianlimaSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSourceQlmlog());
        bean.setMapperLocations(MapperLocationUtil.resolveMapperLocations(new String[]{"classpath:mapper/qlmlog/*.xml"}));
        bean.setTypeAliasesPackage("com.qianlima.application.domain.qlmlog");
        return bean.getObject();
    }

}
