package com.tikalk.chaostoolkitrunner.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


/**
 * Chaos engineering experiments service
 */
@Service
public class ExperimentService {

    @Value("experiments.path")
    private String experimentPathStr;

    private Path experimentPath;

    @PostConstruct
    public void init() {
        experimentPath = Paths.get(experimentPathStr);
    }

    /**
     * Launch the defined experiment
     * @return Run-id
     */
    public String launch() throws IOException {
        UUID uuid = UUID.randomUUID();

        String id = uuid.toString();
        Path experimentDir = Paths.get(experimentPathStr, id);
        Files.createDirectory(experimentDir);

        return id;
    }

    /**
     * Get the journal with the results of an experiment run
     * @param id Run-id
     * @return Journal JSon
     */
    public String getJournal(String id) {
        return null;
    }
}
