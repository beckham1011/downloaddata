package com.ge.predix.ccm.boot;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;


@Configuration
public class AppConfiguration{
	@Autowired
    public Environment env;
	
	
	@Bean(name = "dataSource")
	@Qualifier(value = "dataSource")
	@Primary
	@ConfigurationProperties(prefix = "c3p0")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
	}
		
}


