package com.hacettepe.usermicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/model")
@RequiredArgsConstructor
public class ModelController {

    @PostMapping("/upload-model")
    public ResponseEntity<?> uploadModel() {
        throw new RuntimeException();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getModels() {
        throw new RuntimeException();
    }

    @DeleteMapping("remove-model")
    public ResponseEntity<?> removeModel() {
        throw new RuntimeException();
    }

    @GetMapping("/train-model")
    public ResponseEntity<?> trainModel() {
        throw new RuntimeException();
    }

    @GetMapping("/run-model")
    public ResponseEntity<?> runModel() {
        throw new RuntimeException();
    }

}
