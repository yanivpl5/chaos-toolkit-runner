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
        return new ResponseEntity<>(experimentService.launch(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getResult(@PathVariable String id) {
        String journal = experimentService.getJournal(id);
        if (journal.equals("active")) {
            return new ResponseEntity<>("active", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(journal, HttpStatus.OK);
    }
}
