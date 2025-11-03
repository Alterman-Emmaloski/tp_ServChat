/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serveurchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author atand
 */
public class Server {

    private static final int PORT = 1020;
    private static Set<ManagerCustomer> clients = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("Serveur démarré sur le port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ManagerCustomer client = new ManagerCustomer(socket);
                clients.add(client);
                new Thread((Runnable) client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getClientCount() {
        synchronized (clients) {
            return clients.size();
        }
    }

    static void broadcast(String message, ManagerCustomer sender) {
        synchronized (clients) {
            for (ManagerCustomer client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

}
