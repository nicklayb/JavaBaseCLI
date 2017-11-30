package com.nboisvert.cli.Core.Database;

import com.nboisvert.cli.Core.Database.QueryBuilder.Query;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Connection string interface
 */
abstract public class ConnectionString
{
    /**
     * Gets the connection string correctly formatted
     *
     * @return String
     */
    abstract public String getConnectionString();

    /**
     * Gets the username
     *
     * @return String
     */
    abstract public String getUsername();

    /**
     * Gets the password
     * @return String
     */
    abstract public String getPassword();

    /**
     * Gets the connection string driver
     *
     * @return String the driver name
     */
    abstract protected String getDriver();

    /**
     * Gets the connection from the connection string
     *
     * @return Connection
     * @throws SQLException if an error occurred
     */
    public Connection getConnection() throws SQLException, DriverNotFoundException
    {
        if (!Database.hasLoadedDriver(this.getDriver())) {
            if (!Database.loadDriver(this.getDriver())) {
                throw new DriverNotFoundException(this.getDriver());
            }
        }
        return DriverManager.getConnection(this.getConnectionString(), this.getUsername(), this.getPassword());
    }

    /**
     * Gets a prepared statement from a query
     *
     * @param query Query to prepare
     * @return Prepared statement of the query
     * @throws SQLException if an error occured
     */
    public PreparedStatement prepare(Query query) throws SQLException, DriverNotFoundException
    {
        return this.getConnection().prepareStatement(query.getQueryString());
    }
}
