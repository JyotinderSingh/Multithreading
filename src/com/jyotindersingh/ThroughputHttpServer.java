package com.jyotindersingh;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThroughputHttpServer {
    public static final String INPUT_FILE = "resources//war_and_peace.txt";
    public static final int NUMBER_OF_THREADS = 4;

    public static void main(String[] args) throws IOException {
        // Load the book into a string, the book is really large, so searching it is gonna be expensive
        String text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
        startServer(text);
    }

    public static void startServer(String text) throws IOException {
        // Create an HTTP server, that listens on port 8000,
        // and backlog size = 0, which defines the size of the queue for the HTTP server  requests.
        // We keep it at 0 since all the requests should end up in the Thread Pools Queue instead
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // We now create a context which basically assigns a handler object to a particular HTTP route
        // This handler is gonna handle each incoming HTTP req and send back an HTTP response
        server.createContext("/search", new WordCountHandler(text));

        // The scheduler schedules each incoming HTTP request to a pool of worker threads
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        server.setExecutor(executor);
        server.start();
    }

    public static class WordCountHandler implements HttpHandler {
        private String text;

        public WordCountHandler(String text) {
            this.text = text;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            // Extract the query from the URI
            String query = httpExchange.getRequestURI().getQuery();
            String[] keyValue = query.split("=");
            String action = keyValue[0];
            String word = keyValue[1];

            // In case something goes wrong
            if (!action.equals(("word"))) {
                httpExchange.sendResponseHeaders(400, 0);
                return;
            }

            // count the occurrences
            long count = countWord(word);

            // Convert the count into a byte array that we can serialize and send over the wire
            byte[] response = Long.toString(count).getBytes();
            httpExchange.sendResponseHeaders(200, response.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response);
            outputStream.close();
        }

        private long countWord(String word) {
            long count = 0;
            int index = 0;
            while (index >= 0) {
                index = text.indexOf(word, index);

                if (index >= 0) {
                    count++;
                    index++;
                }
            }
            return count;
        }

    }
}
