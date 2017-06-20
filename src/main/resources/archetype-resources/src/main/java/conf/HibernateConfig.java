package ${package}.conf;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"${groupId}.config"})
public class HibernateConfig {

  @Autowired
  private Environment environment;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
    dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
    dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
    dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource());
    emf.setJpaVendorAdapter(vendor());
    emf.setPackagesToScan(new String[] {"${groupId}.model"});
    emf.setJpaProperties(hibernateProperties());
    return emf;
  }

  private HibernateJpaVendorAdapter vendor() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    return vendorAdapter;
  }

  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
    properties.put("hibernate.max_fetch_depth", environment.getRequiredProperty("hibernate.max_fetch_depth"));
    properties.put("hibernate.jdbc.fetch_size", environment.getRequiredProperty("hibernate.jdbc.fetch_size"));
    properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
    properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
    properties.put("hibernate.show.sql", environment.getRequiredProperty("hibernate.show_sql"));
    return properties;
  }

  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }

  @Bean
  public DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource());
    dataSourceInitializer.setDatabasePopulator(databasePopulator());
    dataSourceInitializer.setEnabled(Boolean.parseBoolean(environment.getRequiredProperty("hibernate.run.script")));
    return dataSourceInitializer;
  }

  private ResourceDatabasePopulator databasePopulator() {
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    databasePopulator.addScript(new ClassPathResource("init-data.sql"));
    return databasePopulator;
  }

}
