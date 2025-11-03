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

/**
 *
 * @author atand
 */
public class CustomerTest2 {
     public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1020);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("Bonjour, je suis etudiant !");
            out.println("Bonjour, je suis codeur !");
            out.println("Bonjour, je suis administrateur reseau !");

            String response = in.readLine();
            System.out.println("Réponse reçue : " + response);

        } catch (IOException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }

}
