package Hadgehogs.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBuilder {
    private String serverName="Localhost";
    private Integer port=5432;
    private String basename;
    private String username;
    private String password;

    public ConnectionBuilder(String basename, String username, String password) {
        this.basename = basename;
        this.username = username;
        this.password = password;
    }

    public ConnectionBuilder setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public ConnectionBuilder setPort(Integer port) {
        this.port = port;
        return this;
    }

    public Connection build() throws SQLException {
        String url=String.format("jdbc:postgresql://%s:%s/%s",serverName,String.valueOf(port),basename); // Драйвер пока фиксированный
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }


}

