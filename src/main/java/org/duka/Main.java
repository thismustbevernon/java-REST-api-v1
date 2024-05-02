package org.duka;

import com.sun.net.httpserver.HttpServer;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

//        DatabaseConnection.populateDatabase();
        DatabaseConnection.createConnection();

        HttpServer myServer = Server.getServer();

        GetProducts getProducts = new GetProducts(myServer);
        PostProducts postProducts = new PostProducts(myServer);
//        getProducts.processQuery();
        getProducts.request();
        postProducts.request();



//        NOTES -
//        It is possible that connection is being created multiple times causing errors
//        server connection - try to organize the files inoto one file where server is initially created


    }
}