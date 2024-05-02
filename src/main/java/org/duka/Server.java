package org.duka;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
//    STEP 1 - CONNECT TO SERVER
//returns a connection to the server
    static int serverPort = 8080;

//    create a server

    public static HttpServer getServer ()  {
        HttpServer myServer = null;
        try {
            myServer = HttpServer.create(new InetSocketAddress(serverPort), 0);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return myServer;

    }




}
