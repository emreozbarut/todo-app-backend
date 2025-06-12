package com.todo.app.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.auditing.EnableCouchbaseAuditing;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@EnableCouchbaseAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableCouchbaseRepositories(basePackages = "com.todo.app.backend.repository")
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${couchbase.connection-string}")
    private String connectionString;

    @Value("${couchbase.username}")
    private String userName;

    @Value("${couchbase.password}")
    private String password;

    @Value("${couchbase.bucket.name}")
    private String bucketName;

    @Override
    public String getConnectionString() {
        return connectionString;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now());
    }
}
