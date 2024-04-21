package com.hacettepe.usermicroservice.service;

public interface IDockerService {
    String runContainer(String imageName, int hostPort) throws InterruptedException;

    void stopContainer(String containerId);
}
