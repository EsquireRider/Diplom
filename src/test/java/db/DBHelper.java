package db;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static final String DB_URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/aqa_shop");
    private static final String DB_USER = System.getProperty("db.user", "root");
    private static final String DB_PASSWORD = System.getProperty("db.password", "root");

    private static QueryRunner runner = new QueryRunner();

    private DBHelper() {}

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (Connection conn = getConn()) {
            runner.update(conn, "DELETE FROM payment_entity");
            runner.update(conn, "DELETE FROM credit_request_entity");
            runner.update(conn, "DELETE FROM order_entity"); // Если есть зависимость
        }
    }

    @SneakyThrows
    public static PaymentEntity getLatestPayment() {
        String sql = "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (Connection conn = getConn()) {
            return runner.query(conn, sql, new BeanHandler<>(PaymentEntity.class));
        }
    }

    @SneakyThrows
    public static CreditRequestEntity getLatestCreditRequest() {
        String sql = "SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (Connection conn = getConn()) {
            return runner.query(conn, sql, new BeanHandler<>(CreditRequestEntity.class));
        }
    }

    @SneakyThrows
    public static long getPaymentCount() {
        String sql = "SELECT COUNT(*) FROM payment_entity";
        try (Connection conn = getConn()) {
            return runner.query(conn, sql, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static long getCreditRequestCount() {
        String sql = "SELECT COUNT(*) FROM credit_request_entity";
        try (Connection conn = getConn()) {
            return runner.query(conn, sql, new ScalarHandler<>());
        }
    }

    // Классы-POJO для сущностей БД
    @Data
    public static class PaymentEntity {
        private String id;
        private String amount;
        private String created; // Или Timestamp
        private String status;
        private String transaction_id; // id из OrderEntity
    }

    @Data
    public static class CreditRequestEntity {
        private String id;
        private String bank_id; // id из OrderEntity
        private String created;
        private String status;
    }
}
