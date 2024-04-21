package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.ExecModelDTO;
import com.hacettepe.usermicroservice.dto.ModelDTO;
import com.hacettepe.usermicroservice.exception.ModelNotFoundException;
import com.hacettepe.usermicroservice.exception.SomethingWentWrongException;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.model.DevelopersModel;
import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.repository.IDevelopersModelRepository;
import com.hacettepe.usermicroservice.repository.IModelRepository;
import com.hacettepe.usermicroservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService implements IModelService {

    private final IUserRepository userRepository;
    private final IModelRepository modelRepository;
    private final IDevelopersModelRepository developersModelRepository;
    private final S3Service s3Service;
    private final IDockerService dockerService;

    @Override
    public Model uploadModel(ModelDTO modelDTO) {
        String username = modelDTO.getUsername();
        String modelName = modelDTO.getName();
        String dockerImage = modelDTO.getDockerImage();

        String keyname = username + "/" + modelName;

        Model tempModel = Model.builder()
                .name(keyname).modelLink(dockerImage).build();
        Model model = modelRepository.save(tempModel);

        User user = userRepository.findById(username).orElse(null);
        DevelopersModel developersModel = DevelopersModel.builder()
                .user(user).model(model).build();
        developersModelRepository.save(developersModel);

        return model;
    }

    @Override
    public List<Model> listModels() {
        return modelRepository.findAll();
    }

    @Override
    @ExceptionHandler({UserNotFoundException.class, ModelNotFoundException.class, SomethingWentWrongException.class})
    public void removeModel(ModelDTO modelDTO) throws UserNotFoundException, ModelNotFoundException, SomethingWentWrongException {
        String username = modelDTO.getUsername();
        String modelName = modelDTO.getName();
        String keyname = username + "/" + modelName;

        User user = userRepository.findById(username).orElse(null);
        if(user == null)
            throw new UserNotFoundException("User with username " + username + " not found");

        Model model = modelRepository.findByName(keyname).orElse(null);
        if (model == null)
            throw new ModelNotFoundException("Model with key " + keyname + " not found.");

        DevelopersModel developersModel = developersModelRepository.findByUserAndModel(user, model).orElse(null);
        if (developersModel == null)
            throw new SomethingWentWrongException();
        developersModelRepository.delete(developersModel);
        modelRepository.delete(model);
    }


    @Override
    public void trainModel() {

    }

    @Override
    public void runModel(ExecModelDTO execModelDTO) throws ModelNotFoundException, InterruptedException {
        String username = execModelDTO.getUsername();
        String modelName = execModelDTO.getName();
        String keyname = username + "/" + modelName;

        Model model = modelRepository.findByName(keyname).orElse(null);
        if (model == null)
            throw new ModelNotFoundException("Model with key " + keyname + " not found.");
        String dockerImage = model.getModelLink();

        String id = dockerService.runContainer(dockerImage, 5000);
        dockerService.stopContainer(id);
    }


}
