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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.qianlima.application.mapper.qianlima", sqlSessionTemplateRef = "qianlimaSqlSessionTemplate")
public class DataSourceQianLiMaConfig implements TransactionManagementConfigurer {
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return null;
    }

    @Bean(name = "qianlimaTransactionManager")
    public PlatformTransactionManager qianlimaTransactionManager() {
        return new DataSourceTransactionManager(dataSourceQianLiMa());
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.qianlima")
    public DataSource dataSourceQianLiMa() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "qianlimaSqlSessionTemplate")
    public SqlSessionTemplate qianlimaSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(qianlimaSqlSessionFactory());
    }

    @Bean(name = "qianlimaSqlSessionFactory")
    @Primary
    public SqlSessionFactory qianlimaSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSourceQianLiMa());
        bean.setMapperLocations(MapperLocationUtil.resolveMapperLocations(new String[]{"classpath:mapper/qianlima/*.xml"}));
        bean.setTypeAliasesPackage("com.qianlima.application.domain.qianlima");
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
