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
    private static Set<ManagerCustomer> customers = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("Serveur démarré sur le port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ManagerCustomer customer = new ManagerCustomer(socket);
                customers.add(customer);
                new Thread((Runnable) customer).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getClientCount() {
        synchronized (customers) {
            return customers.size();
        }
    }

    static void broadcast(String message, ManagerCustomer sender) {
        synchronized (customers) {
            for (ManagerCustomer customer : customers) {
                if (customer != sender) {
                    customer.sendMessage(message);
                }
            }
        }
    }

}
