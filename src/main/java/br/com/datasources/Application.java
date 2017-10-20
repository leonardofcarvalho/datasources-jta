package br.com.datasources;

import javax.annotation.Resource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

//@ComponentScan
//@EnableAutoConfiguration
//@EnableJpaRepositories
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@EnableTransactionManagement
// @SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Resource(mappedName = "java:jboss/TransactionManager")
	private TransactionManager transactionManager;

	@Resource(mappedName = "java:jboss/UserTransaction")
	private UserTransaction userTransaction;

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() throws Throwable {
		return new JtaTransactionManager(userTransaction, transactionManager);
	}

	private static Class<Application> applicationClass = Application.class;
}
