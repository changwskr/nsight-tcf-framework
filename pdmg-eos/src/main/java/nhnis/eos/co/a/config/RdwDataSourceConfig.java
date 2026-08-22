package nhnis.eos.co.a.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import nhnis.fw.mybatis.MybatisStatementTimeoutInterceptor;
import nhnis.fw.tcf.timeout.OnlineTimeoutProperties;

@Configuration
@MapperScan(basePackages = "nhnis.eos.co.a.persistence.dao", sqlSessionTemplateRef = "rdwSqlSessionTemplate")
public class RdwDataSourceConfig {

    private static final String MAPPER_LOCATION_PATTERN = "classpath*:rdw.*/*.xml";

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.rdw")
    public DataSource rdwDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public SqlSessionFactory rdwSqlSessionFactory(DataSource rdwDataSource,
            MybatisLogInterceptor mybatisLogInterceptor,
            OnlineTimeoutProperties timeoutProperties,
            ObjectProvider<MybatisStatementTimeoutInterceptor> timeoutInterceptorProvider) throws Exception {
        org.apache.ibatis.session.Configuration mybatisConfig = new org.apache.ibatis.session.Configuration();
        mybatisConfig.setJdbcTypeForNull(JdbcType.NULL);
        mybatisConfig.setCallSettersOnNulls(true);
        mybatisConfig.setDefaultStatementTimeout(timeoutProperties.getSqlSafetyTimeoutSeconds());

        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(rdwDataSource);
        factory.setConfiguration(mybatisConfig);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION_PATTERN));

        List<Interceptor> plugins = new ArrayList<>();
        plugins.add(mybatisLogInterceptor);
        timeoutInterceptorProvider.ifAvailable(plugins::add);
        factory.setPlugins(plugins.toArray(Interceptor[]::new));
        return factory.getObject();
    }

    @Bean
    public SqlSessionTemplate rdwSqlSessionTemplate(SqlSessionFactory rdwSqlSessionFactory) {
        return new SqlSessionTemplate(rdwSqlSessionFactory);
    }

    @Bean
    @Primary
    public PlatformTransactionManager rdwTransactionManager(DataSource rdwDataSource) {
        return new DataSourceTransactionManager(rdwDataSource);
    }
}
