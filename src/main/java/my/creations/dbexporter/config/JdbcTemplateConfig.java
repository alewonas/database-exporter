package my.creations.dbexporter.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration class for setting up a Spring JdbcTemplate.
 */
@Configuration
public class JdbcTemplateConfig {

    private final DataSource dataSource;

    /**
     * Constructor for JdbcTemplateConfig.
     *
     * @param dataSource The DataSource to be used by the JdbcTemplate.
     */
    @Autowired
    public JdbcTemplateConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Creates and configures a JdbcTemplate bean using the provided DataSource.
     *
     * @return A configured JdbcTemplate bean.
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
}
