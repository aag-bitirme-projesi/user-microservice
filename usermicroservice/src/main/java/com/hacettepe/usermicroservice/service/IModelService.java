package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.ModelDTO;
import com.hacettepe.usermicroservice.exception.ModelNotFoundException;
import com.hacettepe.usermicroservice.exception.SomethingWentWrongException;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.model.Model;

import java.io.IOException;
import java.util.List;

public interface IModelService {

    List<Model> listModels();

    Model getModelById(long modelId);

    List<Model> getBoughtModels();

}
