/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Disha
 */
public class ChatServer {
   private static final int PORT = 1234;
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Chat Server started...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                clientHandler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast message to all clients
    static void broadcast(String message, ClientHandler excludeUser) {
        for (ClientHandler client : clients) {
            if (client != excludeUser) {
                client.sendMessage(message);
            }
        }
    }

    // Remove client when disconnected
    static void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("Client disconnected");
    }
}
