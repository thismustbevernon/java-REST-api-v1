package org.duka;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.duka.DatabaseConnection.createConnection;

public class PostHandler implements HttpHandler {


    public PostHandler() {
        // No need for explicit initialization here if no parameters are required
    }

    public void handle(HttpExchange exchange) throws IOException {
        // Your handling logic goes here
        if ("POST".equals(exchange.getRequestMethod())){
//                        String dummyText = processQuery ();
//                        exchange.sendResponseHeaders(200, dummyText.getBytes().length);
            InputStream input = exchange.getRequestBody();
//                      the input stream returns some bytes

            int currentByte;

//                       we want to create a string

            StringBuilder stringBuilder = new StringBuilder();
            while ((currentByte = input.read()) != -1) {
                stringBuilder.append((char)currentByte);
            }

            // Print the content of the input stream
            System.out.println("Received POST data: " + stringBuilder.toString());
            processQuery (stringBuilder.toString());
            input.close();

            exchange.close();

        }else{
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed

        }
        exchange.close();


    }


    public static void processQuery (String values){


        JsonArray array = JsonParser.parseString(values)
                .getAsJsonArray();

        // crete DB connection
        // Iterate jsonArray using for loop
        for (int i = 0; i < array.size(); i++) {
            // get field value from JSONObject using get() method
            JsonObject currObject = array.get(i).getAsJsonObject();
            String name = currObject.get("name").getAsString();
            double price = currObject.get("price").getAsDouble();

            System.out.println(name);
            System.out.println(price);

            Connection conn = createConnection();
            Statement st;



            {
                try {
                    st = conn.createStatement();
//                need to have a statement that inserts values into the DB
                    String insertQuery = "INSERT INTO products (name, price) VALUES ('" + name + "', " + price + ")";
                    System.out.println("down here");
                    ResultSet rs = st.executeQuery(insertQuery);

                    rs.close();
                    st.close();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

        }




    }


}
