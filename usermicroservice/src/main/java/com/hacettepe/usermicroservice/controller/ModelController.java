package com.hacettepe.usermicroservice.controller;

import com.hacettepe.usermicroservice.dto.ModelDTO;
import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.service.IModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/model")
@RequiredArgsConstructor
public class ModelController {
    private final IModelService modelService;

//    @GetMapping("/all")
//    public ResponseEntity<List<Model>> getModels() {
//        return ResponseEntity.ok(modelService.listModels());
//    }
//
    @GetMapping("/my-models")
    public ResponseEntity<List<Model>> getModelsByDev() {
        return ResponseEntity.ok(modelService.listModelsByDev());
    }

    @GetMapping("/details")
    public ResponseEntity<Model> getModelDetails(@RequestBody Long modelId) {
        return ResponseEntity.ok(modelService.getModelById(modelId));
    }

    @GetMapping("/bought-models")
    public ResponseEntity<List<Model>> getBoughtModel() {
        return ResponseEntity.ok(modelService.getBoughtModels());
    }

    @PostMapping("/upload-model")
    public ResponseEntity<Model> uploadModel(@RequestBody ModelDTO modelDto) {
        return ResponseEntity.ok(modelService.uploadModel(modelDto));
    }
}
