package com.qianlima.application.config.datasource;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.qianlima.application.mapper.qlmservice", sqlSessionTemplateRef = "qlmserviceSqlSessionTemplate")
public class DataSourceQLMServiceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.qlmservice")
    public DataSource dataSourceQlmservice() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "qlmserviceSqlSessionTemplate")
    public SqlSessionTemplate qianlimaSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(qianlimaSqlSessionFactory());
    }

    @Bean(name = "qlmserviceSqlSessionFactory")
    public SqlSessionFactory qianlimaSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSourceQlmservice());
        bean.setMapperLocations(MapperLocationUtil.resolveMapperLocations(new String[]{"classpath:mapper/qlmservice/*.xml"}));
        bean.setTypeAliasesPackage("com.qianlima.application.domain.qlmservice");
        bean.setPlugins(pageInterceptor());
        return bean.getObject();
    }

    private PageInterceptor pageInterceptor(){
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "pageNum=pageNo;pageSize=pageSize;");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
