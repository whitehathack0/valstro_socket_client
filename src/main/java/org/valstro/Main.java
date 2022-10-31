package org.valstro;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Socket socket = IO.socket("http://localhost:3000");
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connected to server!");
            }
        });

        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Disconnected from server..");
            }
        });

        socket.on("search", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject res = (JSONObject) args[0];
                if ((int) res.get("page") != -1) {
                    System.out.println("(" + (res.get("page")) + "/" + (res.get("resultCount")) + ") " + res.get("name") + " - " + res.get("films"));
                }
                else {
                    System.out.println(res.getString("error"));
                }
            }
        });

        socket.connect();

        while (true) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter search string (type '-c' to exit program) : ");
            String input = in.readLine();
            if (input.equals("-c")) {
                break;
            }
            else {
                socket.emit("search", new JSONObject().put("query", input));
            }
        }

        socket.disconnect();


    }
}