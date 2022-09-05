package edu.escuelaing.arep;

public class EchoServerController {
    
    public static void main(String[] args) {
        EchoServer httpServer = EchoServer.getInstance();
        try {
            Spring.run();
            httpServer.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
