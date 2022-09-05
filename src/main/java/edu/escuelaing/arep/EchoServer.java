package edu.escuelaing.arep;

import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class EchoServer {
    private static boolean esPath = true;
    private static EchoServer instance = new EchoServer();

    public static EchoServer getInstance() {
        return instance;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = iniciarServidor();
        String flag = "";
        ExecutorService pool = Executors.newFixedThreadPool(7);
        while (flag != "exit") {
            Socket clientSocket = activarSocket(serverSocket);
            RequestProcessor processor = new RequestProcessor(clientSocket);
            pool.execute(processor);

        }
        serverSocket.close();

    }

    private ServerSocket iniciarServidor() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        return serverSocket;
    }

    private Socket activarSocket(ServerSocket socket) {
        Socket clientSocket = null;
        try {
            System.out.println("Activado");
            clientSocket = socket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        return clientSocket;
    }

    public static void respuesta(String url, Socket socket) throws IOException {
        String tipo = url.substring(url.indexOf(".") + 1);
        File file = new File(url);
        PrintWriter out;
        if (file.exists()) {
            tipoArchivo(tipo, socket, file);
        } else if (!url.contains(".")) {
            out = new PrintWriter(socket.getOutputStream(), true);
            try {
                String outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html  \r\n"
                        + "\r\n"
                        + Spring.getMethod("/" + url);
                out.println(outputLine);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(error(404));
            out.close();
        }

    }

    private static void tipoArchivo(String tipo, Socket clientSocket, File archivo) throws IOException {

        if (tipo.equals("png") || tipo.equals("jpg") || tipo.equals("gif")
                || tipo.equals("jpeg")) {
            binario(tipo, clientSocket, archivo);
        } else if (tipo.equals("html") || tipo.equals("js")) {
            archivos(tipo, clientSocket, archivo);
        }
    }

    private static String binario(String tipo, Socket clientSocket, File archivo) throws IOException {
        String outputLine = "";
        byte[] imagen = leerImagen(archivo);
        DataOutputStream binario = new DataOutputStream(clientSocket.getOutputStream());

        outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: image/" + tipo + "\r\n"
                + "Content-Length: " + imagen.length + "\r\n"
                + "\r\n";
        binario.writeBytes(outputLine);
        binario.write(imagen);
        binario.close();
        binario.close();
        return outputLine;
    }

    private static String archivos(String tipo, Socket clientSocket, File archivo) throws IOException {
        String outputLine = "";
        PrintWriter out;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/" + tipo + "\r\n"
                + "\r\n"
                + leerArchivo(archivo);
        out.println(outputLine);
        out.close();
        return outputLine;
    }

    private static String leerArchivo(File archivo) throws FileNotFoundException {
        try (Scanner scanne = new Scanner(archivo)) {
            String lista = new String();
            while (scanne.hasNextLine()) {
                lista += (scanne.nextLine());
            }
            return (lista);
        } catch (NumberFormatException e) {
            String lista = new String();
            return lista;
        }
    }

    private static byte[] leerImagen(File archivo) throws IOException {
        try {
            FileInputStream inputImage = new FileInputStream(archivo);
            byte[] bytes = new byte[(int) archivo.length()];
            inputImage.read(bytes);
            inputImage.close();
            return bytes;
        } catch (Exception e) {
            byte[] bytes = new byte[0];
            return bytes;
        }

    }

    public static String request(BufferedReader in) throws IOException {
        String path = "";
        String inputLine = "";
        while ((inputLine = in.readLine()) != null) {
            esPath = true;
            if (esPath && inputLine.startsWith("GET")) {
                System.out.println("Path: " + inputLine.split(" ")[1].substring(1));
                path = inputLine.split(" ")[1].substring(1);
                esPath = false;
            }
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }
        return path;
    }

    private static String error(int tipo) {
        String respuesta = "";
        if (tipo == 404) {
            respuesta = "HTTP/1.1 404 Not Found\r\n\r\n"
                    + "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<title>Home</title>"
                    + "</head>"
                    + "<body style='background-color:black'>"
                    + "<h1 style='color:white'>File not found</h1>"
                    + "<img src='https://www.trecebits.com/wp-content/uploads/2020/11/Error-404.jpg'>"
                    + "</body>"
                    + "</html>";
        }
        return respuesta;
    }

    private int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }

}
