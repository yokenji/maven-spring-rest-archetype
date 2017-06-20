package ${package}.conf;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * @author Bossuyt Fabrice <fabrice.bossuyt@gmail.com>, Original Author
 *
 */
//@Configuration
public class QuartzSchedulerConfig {

  private static final Logger logger = LogManager.getLogger(QuartzSchedulerConfig.class);

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private Environment environment;

  @PostConstruct
  public void init() {
    logger.debug("QuartzConfig initialized.");
  }

  @Bean
  public SpringBeanJobFactory springBeanJobFactory() {
      AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
      logger.debug("Configuring Job factory");

      jobFactory.setApplicationContext(applicationContext);
      return jobFactory;
  }

  @Bean
  public SchedulerFactoryBean scheduler(Trigger[] triggers) {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setConfigLocation(new ClassPathResource("quartz.properties"));
    factory.setJobFactory(springBeanJobFactory());
    factory.setTriggers(triggers);
    return factory;
  }

  /**
   * Create a new JobDetail
   * 
   * @param Class jobClass
   * @return JobDetailFactoryBean
   */
  private static JobDetailFactoryBean createJobDetail(Class<?> jobClass) {
    JobDetailFactoryBean bean = new JobDetailFactoryBean();
    bean.setJobClass(jobClass);
    bean.setDurability(true);
    return bean;
  }

  /**
   * Create a new JobTrigger.
   * 
   * @param JobDetail jobDetail
   * @param String expression
   * @return CronTriggerFactoryBean
   */
  private static CronTriggerFactoryBean createTrigger(JobDetail jobDetail, String expression) {
    CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
    bean.setJobDetail(jobDetail);
    bean.setCronExpression(expression);
    return bean;
  }
}
