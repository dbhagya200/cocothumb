package lk.ijse.cocothumb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
private static dbConnection dbConnection1;

private Connection connection;

private dbConnection() throws SQLException {
    connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/cocothumb",
            "root",
            "Ijse@1234"
    );

}
public static dbConnection getInstance() throws SQLException {
    if (dbConnection1 == null) {
        dbConnection1 = new dbConnection();
    }
    return dbConnection1;
}

public Connection getConnection(){
    return connection;
}

}
