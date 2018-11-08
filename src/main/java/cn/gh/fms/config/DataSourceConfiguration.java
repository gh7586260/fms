package cn.gh.fms.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "cn.gh.fms.mapper")
//开启事物管理
@EnableTransactionManagement
@PropertySource(value = {"classpath:propertity/gh-fms.properties"},ignoreResourceNotFound = true,encoding = "utf-8")
public class DataSourceConfiguration {

    @Primary
    @Bean(name = "dataSourceCore")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource4Core() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "sqlSessionFactoryMain")
    public SqlSessionFactory sqlSessionFactory4Core(@Qualifier("dataSourceCore") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:sql/configuration.xml"));
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:sql/mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("cn.gh.fms.DO");
        return sqlSessionFactoryBean.getObject();
    }
}
