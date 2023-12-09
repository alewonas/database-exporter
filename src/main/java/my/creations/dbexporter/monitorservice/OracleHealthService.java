package my.creations.dbexporter.monitorservice;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class OracleHealthService {

    @Autowired
    public OracleHealthService(MeterRegistry meterRegistry, DataSource dataSource) {
        Gauge.builder("oracle.database.health", this, value -> isDatabaseUp(dataSource))
                .description("Oracle Database Health")
                .register(meterRegistry);
    }

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
