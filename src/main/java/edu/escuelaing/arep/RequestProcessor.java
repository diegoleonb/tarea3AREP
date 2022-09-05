package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestProcessor implements Runnable {

    private Socket clientSocket;

    public RequestProcessor(Socket clienSocket) {
        this.clientSocket = clienSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            if (true) {
                EchoServer.respuesta(EchoServer.request(in), clientSocket);
            }
            in.close();
            clientSocket.close();
            System.out.println(Thread.currentThread());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
