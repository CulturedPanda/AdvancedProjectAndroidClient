package com.example.advancedprojectandroidclient.server_communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerCommunication {

    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final String baseUrl = "https://192.168.1.163:7031/";

    public static Future<Boolean> login(String username, String password){
        return executor.submit(() -> {
            try {
                URL url = new URL(baseUrl + "/RegisteredUsers");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                String payload = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);
                byte[] out = payload.getBytes(StandardCharsets.UTF_8);
                OutputStream stream = con.getOutputStream();
                stream.write(out);
                System.out.println(con.getResponseCode() + " " + con.getResponseMessage());
//                BufferedReader br = new BufferedReader(
//                        new InputStreamReader(con.getInputStream(), "utf-8"));
//                    StringBuilder response = new StringBuilder();
//                    String responseLine = null;
//                    while ((responseLine = br.readLine()) != null) {
//                        response.append(responseLine.trim());
//                    }
//                    System.out.println(response.toString());
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        });
    }
}
