/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.checkit.rest.java;

/**
 *
 * @author BEDAAR
 */


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;



//Vorlage mithilfe von Daniel Semmling

public class Main {

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        System.out.println("(1) Alle User");
        System.out.println("(2) Spezifischer User");
        System.out.println("(3) Alle Produkte");
        System.out.println("(9) Ende");
        System.out.println("Auswahl: ");

        String url1 = "http://localhost:8443/checkIT/";
        String url2 = "";

        String basicAuth = null;
        BufferedReader br;

        try {

            br = new BufferedReader(new InputStreamReader(System.in));
            String auswahl = br.readLine();

            switch (auswahl) {
                case "1":
                    url2 = "api/User/All";
                    break;
                case "2":
                    url2 = "api/User/" + id();
                    break;
                case "3":
                    url2 = "api/Projects/";
                    break;
                case "9":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Ungültige Eingabe!");
                    menu();
            }

            System.out.println("Benutzername: ");
            String user = br.readLine();

            System.out.println("Password: ");
            String password = br.readLine();

            basicAuth = Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));

        } catch (IOException ex) {
            System.out.println("Fehler beim Lesen der Eingabe.");
       }

        URL url = null;
        try {
            url = new URL(url1 + url2);
        } catch (MalformedURLException ex) {
            System.out.println("Fehler in URL: " + url);
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + basicAuth);

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String read = "";
            StringBuilder sb = new StringBuilder();
            while ((read = br.readLine()) != null) {
                sb.append(read);
            }

            System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson((new JsonParser().parse(sb.toString()))));

            br.close();
            br = null;
        } catch (ConnectException ex) {
            System.out.println("Verbindung zu " + url.toString() + " nicht möglich.");
        } catch (IOException ex) {
            System.out.println("Verbindung zu " + url.toString() + " nicht möglich.");
        } finally {
            menu();
        }

    }

    private static String id() throws IOException {
        System.out.println("Bitte ID eingeben: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
}