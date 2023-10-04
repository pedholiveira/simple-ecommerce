package com.tv.demo.simpleecommerce.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
public abstract class PostgresContainerTest {

  static final PostgreSQLContainer<?> database;

  static {
    database = createDatabase();
    database.start();
  }

  @DynamicPropertySource
  static void datasourceConfig(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", database::getJdbcUrl);
    registry.add("spring.datasource.password", database::getPassword);
    registry.add("spring.datasource.username", database::getUsername);
  }

  private static PostgreSQLContainer<?> createDatabase() {
    return new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"))
        .withDatabaseName("testdb")
        .withUsername("postgres")
        .withPassword("postgres")
        .withReuse(true);
  }
}
