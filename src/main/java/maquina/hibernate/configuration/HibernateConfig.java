package maquina.hibernate.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Clase destinada a la configuración de Spring
 * <p>
 * Tiene implementado filtro de paquetes por regexp mas información en el enlace
 * <p>
 * <a href=
 * "https://www.logicbig.com/how-to/code-snippets/jcode-spring-framework-componentscan-filter.html">Documentación
 * ComponentScan por regExp y mas</a>
 * 
 * @author MaQuiNa1995
 *
 */
@EnableTransactionManagement
@ComponentScan(basePackages = "maquina.hibernate.",
        useDefaultFilters = true,
        includeFilters = @Filter(type = FilterType.REGEX,
                pattern = "(repository)$"))
public class HibernateConfig {

	private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "maquina.hibernate.dominio";

	/**
	 * Bean que representa la conexión a la base de datos
	 * 
	 * @return {@link DataSource} bean que permite la conexión a la base de datos
	 */
	@Bean
	public DataSource dataSource() {

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
		hikariConfig.setMaximumPoolSize(5);
		hikariConfig.setPoolName("MaQuina1995-HikariCP");

		hikariConfig.setJdbcUrl("jdbc:hsqldb:mem:maquina1995");
		hikariConfig.setUsername("sa");
		hikariConfig.setPassword("");

		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
		hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public Configuration createHibernateConfig(CustomPhysicalNamingStrategy customPhysicalNamingStrategy) {

		Configuration hibernateConfiguration = new Configuration();
		hibernateConfiguration.setPhysicalNamingStrategy(customPhysicalNamingStrategy);

		return hibernateConfiguration;

	}

	/**
	 * Bean que representa la clase encargada de las transacciones en la base de
	 * datos
	 * 
	 * @param dataSource {@link DataSource} bean que representa la conexión a la
	 *                   base de datos
	 * 
	 * @return {@link JpaTransactionManager} encargado de las transaciones en la
	 *         base de datos
	 */
	@Bean
	public JpaTransactionManager jpaTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return transactionManager;
	}

	/**
	 * Bean encargado de la persistencia usando el dominio
	 * 
	 * @return {@link LocalContainerEntityManagerFactoryBean} objeto encargado de la
	 *         persistencia en base de datos
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setJpaVendorAdapter(this.jpaProperties());
		entityManagerFactoryBean.setJpaProperties(this.createAditionalProperties());
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceUnitName("MaQuiNaPersistenceUnit");
		// Clase encargada de la persistencia
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		// Paquetes donde se van a buscar las entidades
		entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
		return entityManagerFactoryBean;
	}

	private Properties createAditionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.physical_naming_strategy",
		        "maquina.hibernate.configuration.CustomPhysicalNamingStrategy");
		properties.setProperty("hibernate.jdbc.batch_size", "50");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		return properties;
	}

	/**
	 * Bean encargado de propiedades de configuracion adicionales de hibernate y jpa
	 * 
	 * @return {@link HibernateJpaVendorAdapter} Objeto que contiene las propieades
	 *         adicionales que podemos usar en jpa/hibernate
	 */
	@Bean
	public HibernateJpaVendorAdapter jpaProperties() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.HSQL);
		return vendorAdapter;
	}

}
