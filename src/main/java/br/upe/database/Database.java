package br.upe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class Database {
    private static final String baseURL = "jdbc:derby:prog3";
    private static final String connectionURL = "jdbc:derby:prog3";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL);
    }

    public static void shutDown() throws SQLException {
        try {
            String shutdownURL = baseURL + ";shutdown=true";
            DriverManager.getConnection(shutdownURL);
        } catch (SQLException error) {
            if (!error.getSQLState().equals("XJ015")) {
                throw error;
            }
        }
    }

    public static void initializeDatabase() throws SQLException {
        String createURL = baseURL + ";create=true";
        try (Connection connection = DriverManager.getConnection(createURL)) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            if (!doesTableExist(connection, "user")) {
                statement.executeUpdate("""
                    CREATE TABLE user (
                        id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(32) FOR BIT DATA NOT NULL
                    )
                """);
            }

            if (!doesTableExist(connection, "event")) {
                statement.executeUpdate("""
                    CREATE TABLE event (
                        id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                        name VARCHAR(255) NOT NULL,
                        description VARCHAR(1000) NOT NULL,
                        start_date DATE NOT NULL,
                        end_date DATE NOT NULL
                    )
                """);

                statement.addbatch("""
                    INSERT INTO event (name, description, start_date, end_date)
                        VALUES (
                            'Evento UFPE',
                            'Evento organizado pela Universidade Federal de Pernambuco',
                            DATE('2024-10-20'),
                            DATE('2024-10-25')
                        )
                """);
                statement.addbatch("""
                    INSERT INTO event (name, description, start_date, end_date)
                        VALUES (
                            'Evento UPE',
                            'Evento organizado pela Universidade de Pernambuco',
                            DATE('2024-11-01'),
                            DATE('2024-11-10')
                        )
                """);
                statement.addbatch("""
                    INSERT INTO event (name, description, start_date, end_date)
                        VALUES (
                            'Evento UFRJ',
                            'Evento organizado pela Universidade Federal do Rio de Janeiro',
                            DATE('2024-11-15'),
                            DATE('2024-11-25')
                        )
                """);
                statement.addbatch("""
                    INSERT INTO event (name, description, start_date, end_date)
                        VALUES (
                            'Evento UFMG', 'Evento organizado pela Universidade Federal de Minas Gerais',
                            DATE('2024-12-05'),
                            DATE('2024-12-09')
                        )
                """);
                statement.addbatch("""
                    INSERT INTO event (name, description, start_date, end_date)
                        VALUES (
                            'Evento Unifesp',
                            'Evento organizado pela Universidade Federal de São Paulo',
                            DATE('2024-12-22'),
                            DATE('2024-12-27')
                        )
                """);
                statement.executeBatch();
                statement.clearBatch();
            }

            if (!doesTableExist(connection, "session")) {
                statement.executeUpdate("""
                    CREATE TABLE session (
                        event_id BIGINT REFERENCES event (id) ON DELETE CASCADE,
                        session_number BIGINT,
                        name VARCHAR(255) NOT NULL,
                        date DATE NOT NULL,
                        start_time TIME NOT NULL,
                        end_time TIME NOT NULL,
                        PRIMARY KEY (event_id, session_number)
                    )
                """);

                statement.addBatch("""
                    INSERT INTO session (event_id, session_number, name, date, start_time, end_time)
                        VALUES (1, 1, 'Sessão 1', DATE('2024-10-21'), TIME('15:00:00'), TIME('16:00:00'))
                """);
                statement.addBatch("""
                    INSERT INTO session (event_id, session_number, name, date, start_time, end_time)
                        VALUES (1, 2, 'Sessão 2', DATE('2024-10-23'), TIME('12:00:00'), TIME('14:00:00'))
                """);
                statement.addBatch("""
                    INSERT INTO session (event_id, session_number, name, date, start_time, end_time)
                        VALUES (2, 1, 'Sessão 1', DATE('2024-11-01'), TIME('09:00:00'), TIME('11:00:00'))
                """);
                statement.addBatch("""
                    INSERT INTO session (event_id, session_number, name, date, start_time, end_time)
                        VALUES (2, 2, 'Sessão 2', DATE('2024-11-07'), TIME('07:30:00'), TIME('08:30:00'))
                """);
                statement.addBatch("""
                    INSERT INTO session (event_id, session_number, name, date, start_time, end_time)
                        VALUES (3, 1, 'Sessão 1', DATE('2024-11-17'), TIME('18:00:00'), TIME('20:00:00'))
                """);
                statement.addBatch("""
                    INSERT INTO session (event_id, session_number, name, date, start_time, end_time)
                        VALUES (3, 2, 'Sessão 2', DATE('2024-11-24'), TIME('14:00:00'), TIME('16:00:00'))
                """);
                statement.addBatch("""
                    INSERT INTO session (event_id, session_number, name, date, start_time, end_time)
                        VALUES (4, 1, 'Sessão 1', DATE('2024-12-05'), TIME('10:00:00'), TIME('13:00:00'))
                """);
                statement.addBatch("""
                    INSERT INTO session (event_id, session_number, name, date, start_time, end_time)
                        VALUES (4, 2, 'Sessão 2', DATE('2024-12-09'), TIME('15:00:00'), TIME('17:00:00'))
                """);
                statement.addBatch("""
                    INSERT INTO session (event_id, session_number, name, date, start_time, end_time)
                        VALUES (5, 1, 'Sessão 1', DATE('2024-12-26'), TIME('09:00:00'), TIME('11:30:00'))
                """);
                statement.addBatch("""
                    INSERT INTO session (event_id, session_number, name, date, start_time, end_time)
                        VALUES (5, 2, 'Sessão 2', DATE('2024-12-27'), TIME('14:30:00'), TIME('16:00:00'))
                """);
                statement.executeBatch();
                statement.clearBatch();
            }

            if (!doesTableExist(connection, "subscription")) {
                statement.executeUpdate("""
                    CREATE TABLE subscription (
                        user_id BIGINT REFERENCES user (id) ON DELETE CASCADE,
                        event_id BIGINT,
                        session_number BIGINT,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (user_id, event_id, session_number),
                        FOREIGN KEY (event_id, session_number) REFERENCES session (event_id, session_number) ON DELETE CASCADE
                    )
                """);
            }

            if (!doesTableExist(connection, "submission")) {
                statement.executeUpdate("""
                    CREATE TABLE submission (
                        user_id BIGINT REFERENCES user (id) ON DELETE CASCADE,
                        event_id BIGINT REFERENCES event (id) ON DELETE CASCADE,
                        article_name VARCHAR(500) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (user_id, event_id)
                    )
                """);
            }

            connection.commit();
        }
        catch (SQLException error) {
            connection.rollback();
            throw error;
        }
    }

    private static boolean doesTableExist(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, tableName.toUpperCase(), null);
        return tables.next();
    }
}
