package org.justjava.gymcore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GymcoreApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() {
        // Context loads successfully
    }

    @Test
    void verifyDataSourceUrl() throws Exception {
        try (var connection = dataSource.getConnection()) {
            var url = connection.getMetaData().getURL();
            System.out.println("Datasource URL: " + url);
            assertThat(url).contains("jdbc:h2:mem:");
        }
    }

    @Test
    void fail() throws Exception {
        throw new Exception("Test failed");
    }

}
