package org.duka;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.duka.DatabaseConnection.createConnection;

public class GetHandler implements HttpHandler {


    public GetHandler() {
        // No need for explicit initialization here if no parameters are required
    }

    public void handle(HttpExchange exchange) throws IOException {
        // Your handling logic goes here

        if ("GET".equals(exchange.getRequestMethod())){
            String dummyText = processQuery ();
            exchange.sendResponseHeaders(200, dummyText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(dummyText.getBytes());
            output.flush();
            exchange.close();

        }else{
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed

        }
        exchange.close();
    }


    public static String processQuery (){
        String result = "";
        Connection conn = createConnection();
        Statement st;

        {
            try {
                st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM products");

                System.out.println(rs);


                JSONArray array = new JSONArray();


                while (rs.next()) {
                    JSONObject obj=new JSONObject();
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    obj.put("id", id);
                    obj.put("name", name);
                    obj.put("price",price);

                    array.add(obj);
                }

                // Convert to string
                result = array.toString();

                rs.close();
                st.close();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }

        return result;


    }
}
