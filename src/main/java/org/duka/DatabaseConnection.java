package org.duka;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class DatabaseConnection {
//    STEP 2 - CONNECT TO POSTGRESQL
    private static String url = "jdbc:postgresql://localhost/postgres";
    public static Connection createConnection() {

//        modify this function to return only the connection then use the connection in other files to perform
//        the necessary tasks

        {
            Connection conn;
            try {
                conn = DriverManager.getConnection(url, "vernon", "");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            try {
                System.out.println(conn.getMetaData().getDatabaseProductVersion());
            } catch (SQLException e) {
                System.out.println("Error connecting to database " + Arrays.toString(e.getStackTrace()));
                throw new RuntimeException(e);
            }


            return conn;
        }



    }

//    Populates the database with initial values;
    public static void populateDatabase() {
        Connection currentConn = createConnection();
        Statement st;

        {
            try {
                st = currentConn.createStatement();
//                st.execute("DROP TABLE products ");
                st.execute("CREATE TABLE IF NOT EXISTS products (id SERIAL PRIMARY KEY, name VARCHAR(255), price DECIMAL(10,2) NOT NULL)");
                st.execute("INSERT INTO products (name, price) VALUES ('milk', 35.3)");
                st.execute("INSERT INTO products (name, price) VALUES ('bread', 50)");
                st.execute("INSERT INTO products (name, price) VALUES ('milk', 35.3)");
                st.execute("INSERT INTO products (name, price)  VALUES ('meat', 105.3)");
                st.close();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }


    }



}
