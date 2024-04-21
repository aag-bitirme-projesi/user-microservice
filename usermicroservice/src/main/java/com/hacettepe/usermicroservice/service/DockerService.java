package com.hacettepe.usermicroservice.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.command.RemoveContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.stereotype.Service;

@Service
public class DockerService implements IDockerService{
    private final DockerClient dockerClient;

    public DockerService() {
        this.dockerClient = DockerClientBuilder.getInstance().build();
    }

    @Override
    public String runContainer(String imageName, int hostPort) throws InterruptedException {
        dockerClient.pullImageCmd(imageName).exec(new PullImageResultCallback()).awaitCompletion();

        ExposedPort tcp5000 = ExposedPort.tcp(5000);
        PortBinding portBinding = new PortBinding(PortBinding.parse(hostPort + "").getBinding(), tcp5000);
        Ports portBindings = new Ports();
        portBindings.bind(tcp5000, portBinding.getBinding());

        CreateContainerResponse container = dockerClient.createContainerCmd(imageName)
                .withExposedPorts(tcp5000)
                .withPortBindings(portBindings)
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();
        return container.getId();
    }

    @Override
    public void stopContainer(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();

        RemoveContainerCmd removeContainerCmd = dockerClient.removeContainerCmd(containerId).withForce(true);
        removeContainerCmd.exec();
    }

}
