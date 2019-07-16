package com.tikalk.chaostoolkitrunner.controller;

import com.tikalk.chaostoolkitrunner.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "gr7/experiment", produces = "application/json")
public class ExperimentController {

    private ExperimentService experimentService;

    @Autowired
    public ExperimentController(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @PostMapping
    public ResponseEntity<String> runExperiment() {
        try {
            return new ResponseEntity<>(experimentService.launch(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getResult(@PathVariable String id) {
        return new ResponseEntity<>(experimentService.getJournal(id), HttpStatus.OK);
    }
}
