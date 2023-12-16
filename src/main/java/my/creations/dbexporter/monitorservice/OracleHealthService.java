package my.creations.dbexporter.monitorservice;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * A service class for monitoring the health of an Oracle database.
 */
@Component
public class OracleHealthService {

    /**
     * Constructs an {@code OracleHealthService} with the specified dependencies.
     *
     * @param meterRegistry The Micrometer registry used for registering metrics.
     * @param dataSource    The DataSource representing the Oracle database connection.
     */
    @Autowired
    public OracleHealthService(MeterRegistry meterRegistry, DataSource dataSource) {
        // Register a Gauge metric for monitoring Oracle database health
        Gauge.builder("oracle.database.health", this, value -> isDatabaseUp(dataSource))
                .description("Oracle Database Health")
                .register(meterRegistry);
    }

    /**
     * Checks the health of the Oracle database by attempting to establish a connection.
     *
     * @param dataSource The DataSource representing the Oracle database connection.
     * @return 1.0 if the database is up and the connection is established, 0.0 otherwise.
     */
    private double isDatabaseUp(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            // If the connection is established, consider the database as healthy
            return 1.0;
        } catch (SQLException e) {
            // Database is down or an error occurred
            return 0.0;
        }
    }

}
