package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.ModelDTO;
import com.hacettepe.usermicroservice.dto.ModelQueryDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModelService implements IModelService {

    private final IUserRepository userRepository;
    private final IModelRepository modelRepository;
    private final IDevelopersModelRepository developersModelRepository;
    private final S3Service s3Service;

    private String username;

    public String getUsername() {
        if (username == null) {
            // Retrieve the currently authenticated principal
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                username = authentication.getName();
            }
        }
        return username;
    }

    @Override
    public List<Model> listModels() {
        return modelRepository.findAll();
    }

    public List<Model> listModelsByDev() {
        User user = userRepository.findByEmail(getUsername()).get();
        var y = user.getUsername();
        var x = developersModelRepository.findByUser(user.getUsername());
        List<Model> result = new ArrayList<>();

        for (DevelopersModel developersModel : x) {
            result.add(developersModel.getModel());
        }

        return result;
    }

    public Model getModelById(long modelId) {
        return modelRepository.findById(modelId).get();
    }

    public List<Model> getBoughtModels() {
        User user = userRepository.findByEmail(getUsername()).get();
        return modelRepository.findBoughtModels(user.getUsername());
    }

    public Model uploadModel(ModelDTO modelDto) {
        Model newModel = Model.builder()
                .name(modelDto.getName())
                .modelLink(modelDto.getModelLink())  //TODO bunu modelDto dan almayıp ataberkin fonkunu çağır
                .price(modelDto.getPrice())
                .description(modelDto.getDescription())
                .createdAt(LocalDate.now())
                .availability(false)
                .build();

        return modelRepository.save(newModel);
    }

    public void deleteIds(List<Long> ids) {
        List<Model> models = modelRepository.findAllById(ids);

        for (Model model : models) {
            model.setAvailability(false);
            modelRepository.save(model);
        }
    }
}
