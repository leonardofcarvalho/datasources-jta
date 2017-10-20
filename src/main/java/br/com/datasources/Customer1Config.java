package br.com.datasources;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@PropertySource({ "classpath:jndi.properties" })
@EnableJpaRepositories(basePackages = {
		"br.com.datasources.repository.customer1" }, entityManagerFactoryRef = "customer1EntityManagerFactory")
public class Customer1Config {
	@Autowired
	private Environment env;

	// @Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean customer1EntityManagerFactory() throws NamingException {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setJtaDataSource(customer1DataSource());
		// em.setPackagesToScan(new String[] { "org.baeldung.persistence.model" });
		em.setPackagesToScan("br.com.datasources.repository.customer1");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaProperties(additionalProperties());
		return em;
	}

	// @Primary
	public DataSource customer1DataSource() throws NamingException {
		return (DataSource) new JndiTemplate().lookup(env.getProperty("spring.datasource.jndi-name1"));
	}

	// // @Primary
	// @Bean
	// public PlatformTransactionManager customer1TransactionManager() throws
	// NamingException {
	// final JpaTransactionManager transactionManager = new
	// JpaTransactionManager(customer1EntityManagerFactory().getObject());
	// return transactionManager;
	// }
	//
	// @Bean
	// public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	// return new PersistenceExceptionTranslationPostProcessor();
	// }

	final Properties additionalProperties() {
		final Properties hibernateProperties = new Properties();
		// hibernateProperties.setProperty("hibernate.hbm2ddl.auto",
		// env.getProperty("hibernate.hbm2ddl.auto"));
		hibernateProperties.put("hibernate.transaction.jta.platform",
				"org.hibernate.service.jta.platform.internal.JBossStandAloneJtaPlatform");
		hibernateProperties.put("javax.persistence.transactionType", "JTA");
		hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		return hibernateProperties;
	}
}
