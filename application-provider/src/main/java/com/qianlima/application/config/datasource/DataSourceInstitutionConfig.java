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
@MapperScan(basePackages = "com.qianlima.application.mapper.institution", sqlSessionTemplateRef = "institutionSqlSessionTemplate")
public class DataSourceInstitutionConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.institution")
    public DataSource dataSourceInstitution() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "institutionSqlSessionTemplate")
    public SqlSessionTemplate qianlimaSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(qianlimaSqlSessionFactory());
    }

    @Bean(name = "institutionSqlSessionFactory")
    public SqlSessionFactory qianlimaSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSourceInstitution());
        bean.setMapperLocations(MapperLocationUtil.resolveMapperLocations(new String[]{"classpath:mapper/institution/*.xml"}));
        bean.setTypeAliasesPackage("com.qianlima.application.domain.institution");
        return bean.getObject();
    }

}
