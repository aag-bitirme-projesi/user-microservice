package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.ExecModelDTO;
import com.hacettepe.usermicroservice.dto.ModelDTO;
import com.hacettepe.usermicroservice.exception.ModelNotFoundException;
import com.hacettepe.usermicroservice.exception.SomethingWentWrongException;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.model.Model;

import java.util.List;

public interface IModelService {

    public Model uploadModel(ModelDTO model);

    public List<Model> listModels();

    public void removeModel(ModelDTO modelDTO) throws UserNotFoundException, ModelNotFoundException, SomethingWentWrongException;

    public void trainModel();

    void runModel(ExecModelDTO execModelDTO) throws ModelNotFoundException, InterruptedException;
}
