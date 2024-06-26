package com.hacettepe.usermicroservice.service;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
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

    public Model getModelById(long modelId) {
        return modelRepository.findById(modelId);
    }

    public List<Model> getBoughtModels() {
        return modelRepository.findBoughtModels(getUsername());
    }
}
