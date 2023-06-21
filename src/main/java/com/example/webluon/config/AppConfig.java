package com.example.webluon.config;

import java.io.IOException;
import com.maxmind.geoip2.DatabaseReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class AppConfig {
    @Bean
    public DatabaseReader databaseReader(ResourceLoader resourceLoader) throws IOException {
        return new DatabaseReader.Builder(resourceLoader.getResource("classpath:static/city/GeoLite2-City.mmdb").getInputStream()).build();
    }
}