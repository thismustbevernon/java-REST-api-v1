package org.duka;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {


    //    STEP 1 - CONNECT TO SERVER
//returns a connection to the server
    static int serverPort = 8000;



    public static void main(String[] args) {

////        DatabaseConnection.populateDatabase();
        DatabaseConnection.createConnection();
//
//        HttpServer myServer = Server.getServer();
//
//        GetProducts getProducts = new GetProducts(myServer);
//        PostProducts postProducts = new PostProducts(myServer);
////        getProducts.processQuery();
//        getProducts.request();
//        postProducts.request();

        try {
            HttpServer myServer = HttpServer.create(new InetSocketAddress(serverPort), 0);
//            using two separate endpoints for products because context allows only one per
//            next step - change logic to have them on a single ProductHandler()
            myServer.createContext("/getProducts", new GetHandler());
            myServer.createContext("/postProducts", new PostHandler());

            myServer.setExecutor(null);
            myServer.start();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }



//        NOTES -
//        It is possible that connection is being created multiple times causing errors
//        server connection - try to organize the files inoto one file where server is initially created


    }
}