package Rest;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

//Vorlage mithilfe von Daniel Semmling
public class RestTest {

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        System.out.println("(1) Alle User");
        System.out.println("(2) Spezifischer User");
        System.out.println("(3) Alle Produkte");
        System.out.println("(4) Alle Order des Users");
        System.out.println("(9) Ende");
        System.out.println("Auswahl: ");

        String url1 = "https://localhost:8443/";
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
                    url2 = "api/Products/";
                    break;
                case "4":
                    url2 = "api/Order/" + id();
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
            url = new URL("https://localhost:8443/checkIT/api/Projects/");
        } catch (MalformedURLException ex) {
            System.out.println("Fehler in URL: " + url);
        }

        try {
            trustAllHosts();
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
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            menu();
        }

    }

    private static String id() throws IOException {
        System.out.println("Bitte ID eingeben: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    private static void trustAllHosts() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

// Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
        }
    }
}