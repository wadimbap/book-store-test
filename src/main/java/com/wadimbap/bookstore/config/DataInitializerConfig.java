package com.wadimbap.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DataInitializerConfig {

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        return new DataSourceInitializer() {
            {
                setDataSource(dataSource);
                setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
            }
        };
    }
}

