package es.maquina1995.hsqldb.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@ComponentScan({ "es.maquina1995.hsqldb.service", "es.maquina1995.hsqldb.repository" })
public class ConfigurationSpring {
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "org.hibernate.dialect.HSQLDialect";
    private static final String PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH = "hibernate.max_fetch_depth";
    private static final String PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE = "hibernate.jdbc.fetch_size";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "es.maquina1995.hsqldb.dominio";

    @Bean
    public DataSource dataSource() {
	BasicDataSource dataSource = new BasicDataSource();
	dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
	dataSource.setUrl("jdbc:hsqldb:mem:maquina1995");
	dataSource.setUsername("sa");
	dataSource.setPassword("");
	dataSource.setInitialSize(5);
	dataSource.setMaxActive(10);
	dataSource.setPoolPreparedStatements(Boolean.TRUE);
	dataSource.setMaxOpenPreparedStatements(10);
	return dataSource;

    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
	JpaTransactionManager transactionManager = new JpaTransactionManager();
	transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
	return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

	LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
	entityManagerFactoryBean.setDataSource(dataSource());
	entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
	entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
	entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());

	return entityManagerFactoryBean;
    }

    private HibernateJpaVendorAdapter vendorAdaptor() {
	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	vendorAdapter.setShowSql(true);
	return vendorAdapter;
    }

    private Properties jpaHibernateProperties() {

	Properties properties = new Properties();

	properties.put(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH, 10);
	properties.put(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE, 2);
	properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, true);
	properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.MySQLDialect");
	return properties;
    }

}
