package org.duka;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import static org.duka.DatabaseConnection.createConnection;

public class PostProducts {
    //STEP 3 - POST  API
//runs the sql query and returns the results in json

    static HttpServer serverConnection;
    PostProducts( HttpServer serverConnection){
        this.serverConnection = serverConnection;

    }
    public static void processQuery (String values){

        JSONParser parser = new JSONParser();
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

    //sends the results to the endpoint
    public static void request (){
//        HttpServer serverConnection;
        final String result;

        {
            //                serverConnection = Server.getServer();

            serverConnection.createContext("/products", (exchange -> {
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
            }));
            serverConnection.setExecutor(null); // creates a default executor
            serverConnection.start();
        }



    }



}
