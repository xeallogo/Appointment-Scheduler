/*
 * Program: DatabaseQuery.java
 * Author: Alex Gool
 * Description: DatabaseQuery class used to execute SQL statements
 */

package Utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL statements are executed in this class.
 *
 * @author Alex Gool
 */
public class DatabaseQuery {

    // reference to statement
    private static Statement statement;

    /**
     * SQL statement object is created.
     *
     * @param conn Connection statement to database.
     * @throws SQLException If SQL exception occurred.
     */
    public static void  setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    /**
     * Returns SQL statement object.
     *
     * @return A statement object.
     */
    public static Statement getStatement(){
        return statement;
    }
}