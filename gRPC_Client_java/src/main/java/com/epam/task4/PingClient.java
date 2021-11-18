package com.epam.task4;

import com.epam.task4.stubs.PingRequest;
import com.epam.task4.stubs.PingServiceGrpc;
import com.epam.task4.stubs.PongResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingClient {

    private static final String gRPC_TARGET = "localhost:8080";
    private static final Logger log = Logger.getLogger(PingClient.class.getName());

    public static void main(String[] args) throws InterruptedException {
        //construct channel
        ManagedChannel channel = ManagedChannelBuilder.forTarget(gRPC_TARGET)
                .usePlaintext()
                .build();

        // create blocking stub
        PingServiceGrpc.PingServiceBlockingStub pingServiceBlockingStub = PingServiceGrpc.newBlockingStub(channel);

        //call gRPC method and print response
        PongResponse response = pingServiceBlockingStub.ping(PingRequest.newBuilder().setMessage("Hey").build());
        log.log(Level.INFO, "got response from gRPC, message = (" + response.getMessage() + ")");

        //shutdown channel
        channel.shutdown().awaitTermination(10, TimeUnit.SECONDS);

    }
}
