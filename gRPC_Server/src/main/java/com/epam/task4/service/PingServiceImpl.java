package com.epam.task4.service;

import com.epam.task4.stubs.PingRequest;
import com.epam.task4.stubs.PingServiceGrpc;
import com.epam.task4.stubs.PongResponse;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PingServiceImpl extends PingServiceGrpc.PingServiceImplBase {

    private static final Logger log = Logger.getLogger(PingServiceImpl.class.getName());

    @Override
    public void ping(PingRequest request, StreamObserver<PongResponse> responseObserver) {
        log.log(Level.INFO, "received message=(" + request.getMessage() + ")");

        //create response
        PongResponse response = PongResponse.newBuilder()
                .setMessage("pong")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
