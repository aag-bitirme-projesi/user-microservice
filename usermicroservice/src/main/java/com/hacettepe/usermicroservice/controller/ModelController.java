package com.hacettepe.usermicroservice.controller;

import com.hacettepe.usermicroservice.dto.ExecModelDTO;
import com.hacettepe.usermicroservice.dto.ModelDTO;
import com.hacettepe.usermicroservice.exception.ModelNotFoundException;
import com.hacettepe.usermicroservice.exception.SomethingWentWrongException;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/model")
@RequiredArgsConstructor
public class ModelController {

    @Autowired
    private ModelService modelService;

    @PostMapping("/upload-model")
    public ResponseEntity<?> uploadModel(@ModelAttribute ModelDTO modelDTO) {
        modelService.uploadModel(modelDTO);
        return ResponseEntity.ok("Model Uploaded Successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getModels() {
        return ResponseEntity.ok(modelService.listModels());
    }

    @DeleteMapping("remove-model")
    public ResponseEntity<?> removeModel(@ModelAttribute ModelDTO modelDTO) {
        try {
            modelService.removeModel(modelDTO);
            return ResponseEntity.ok("Model Removed Successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("Developer Not Found");
        } catch (ModelNotFoundException e) {
            return ResponseEntity.badRequest().body("Model Not Found");
        } catch (SomethingWentWrongException e) {
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    @PostMapping("/train-model")
    public ResponseEntity<?> trainModel() {
        throw new RuntimeException();
    }

    @PostMapping("/run-model")
    public ResponseEntity<?> runModel(@ModelAttribute ExecModelDTO execModelDTO) {
        try {
            modelService.runModel(execModelDTO);
            return ResponseEntity.ok("Model Run Successfully");
        } catch (ModelNotFoundException e) {
            return ResponseEntity.badRequest().body("Model Not Found");
        } catch (InterruptedException e) {
            return ResponseEntity.badRequest().body("Docker Error Encountered During Model Execution");
        }
    }

}
