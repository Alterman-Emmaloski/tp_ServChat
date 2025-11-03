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
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author atand
 */
public class Customer {

    private static Set<ManagerCustomer> customers = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        String host = "localhost";
        int port = 1020;

        try (Socket socket = new Socket(host, port); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); Scanner scanner = new Scanner(System.in)) {

            new Thread(() -> {
                String response;
                try {
                    while ((response = in.readLine()) != null) {
                        System.out.println(">> " + response);
                    }
                } catch (IOException e) {
                    System.out.println("Connexion fermée.");
                }
            }).start();

            while (true) {
                String message = scanner.nextLine();
                out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void remove(ManagerCustomer aThis) {
        synchronized (customers) {
            customers.remove(aThis);
            System.out.println("Client supprimé. Nombre de clients restants : " + customers.size());
        }
    }

}
