package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.repository.IModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService implements IModelService {
    private final IModelRepository modelRepository;

}

