package org.justjava.gymcore.config;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@RequiredArgsConstructor
@Configuration
@Profile("dev")
public class DevDatabaseInitializer implements CommandLineRunner {

    private final Flyway flyway;

    @Override
    public void run(String... args) {
        // Clean the database: drops all objects in the configured schema
        flyway.clean();
        // Re-run all migrations to set up a fresh database with test data
        flyway.migrate();
    }
}
