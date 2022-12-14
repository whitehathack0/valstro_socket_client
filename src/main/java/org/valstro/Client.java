package org.valstro;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import static java.lang.Thread.sleep;

public class Client {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        Socket socket = IO.socket("http://localhost:3000");

        socket.on(Socket.EVENT_CONNECT, args12 -> System.out.println("Connected to server!"));

        socket.on(Socket.EVENT_CONNECT_ERROR, args1 -> System.out.println("Could not connect to server.."));

        socket.on(Socket.EVENT_DISCONNECT, args13 -> System.out.println("Disconnected from server.."));

        /*  This is the 'search' event name method that will
            Sys.out when client receives 'search' event from server
        */
        socket.on("search", args14 -> {
            JSONObject res = (JSONObject) args14[0];

            try {
                if ((int) res.get("page") != -1) {
                    System.out.println("(" + (res.get("page")) + "/" + (res.get("resultCount")) + ") " + res.get("name") + " - " + "[" + res.get("films") + "]");
                    if ((int) res.get("page") == (int) res.get("resultCount")) {
                        System.out.println();
                        System.out.print("Enter search string (type '-c' to exit program) : ");
                    }
                }
                else {
                    System.out.println(res.get("error"));
                    System.out.print("Enter search string ('-c' to exit program) : ");
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });

        // Initialize a connection to server on localhost:3000
        socket.connect();

        // Sleep for 500ms after connecting to socket
        sleep(1000);

        System.out.print("Enter search string (type '-c' to exit program) : ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        /*  Continuously ask user for input and emit 'search' events */
        while (true) {
            String input = in.readLine();

            // '-c' will break out of this while loop, ending the program
            if (input.equals("-c")) {
                break;
            }
            else {
                System.out.println("Searching for " + input + "...");

                try {
                    // EMIT message to server in format e.g. {'query' : 'Dar'} represented here as JSONObject
                    socket.emit("search", new JSONObject().put("query", input));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        // Disconnect from server once program ends
        socket.disconnect();
        in.close();

    }
}