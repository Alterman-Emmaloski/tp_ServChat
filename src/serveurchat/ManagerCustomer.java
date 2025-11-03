/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serveurchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author atand
 */
public class ManagerCustomer implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private static Set<ManagerCustomer> clients = Collections.synchronizedSet(new HashSet<>());

    public ManagerCustomer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Nouveau client connecté. Total : " + Server.getClientCount());

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message reçu : " + message);
                Server.broadcast(message, this);
                System.out.println("Clients connectés actuellement : " + Server.getClientCount());
            }
        } catch (IOException e) {
            System.out.println("Client déconnecté.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }

            Customer.remove(this);
            System.out.println("Client supprimé. Total : " + Server.getClientCount());
        }
    }

    void sendMessage(String message) {
        out.println(message);
    }

}
