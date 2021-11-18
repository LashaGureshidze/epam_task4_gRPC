package com.epam.task4;

import com.epam.task4.service.PingServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingServer {

    private static final int SERVER_PORT = 8080;
    private static final Logger log = Logger.getLogger(PingServer.class.getName());
    private Server server;

    public void startServer() {
        try {
            server = ServerBuilder.forPort(SERVER_PORT)
                    .addService(new PingServiceImpl())
                    .build()
                    .start();

            log.log(Level.INFO, "gRPC server is up");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    PingServer.this.shutdownServer();
                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, "error shutting down gRPC server", e);
                }
            }));
        } catch (IOException e) {
            log.log(Level.SEVERE, "Could not start gRPC server", e);
        }
    }

    public void shutdownServer() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(10, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PingServer pingServer = new PingServer();

        pingServer.startServer();
        pingServer.blockUntilShutdown();  //to not kill main thread
    }
}
