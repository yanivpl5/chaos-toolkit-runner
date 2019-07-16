package com.tikalk.chaostoolkitrunner.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Chaos engineering experiments service
 */
@Service
public class ExperimentService {

    private  static final String JOURNAL_JSON_FILE = "journal.json";

    @Value("experiments.path")
    private String experimentPathStr;

    /**
     * Launch the defined experiment
     * @return Run-id
     */
    public String launch() throws IOException {
        UUID uuid = UUID.randomUUID();

        String id = uuid.toString();
        Path experimentDir = getExperimentPath(id);
        Files.createDirectory(experimentDir);

        return id;
    }

    private Path getExperimentPath(String id) {
        return Paths.get(experimentPathStr, id);
    }

    /**
     * Get the journal with the results of an experiment run
     * @param id Run-id
     * @return Journal JSon
     */
    public Optional<String> getJournal(String id) throws IOException {
        Path experimentPath = getExperimentPath(id);

        Path journalPath = Paths.get(experimentPath.toString(), JOURNAL_JSON_FILE);

        if (Files.notExists(experimentPath))
            return Optional.empty();
        else if (Files.notExists(journalPath)) {
                return Optional.empty();
        }
        else {
            List<String> journalLines = Files.readAllLines(journalPath);
            return Optional.of(String.join(" ", journalLines));
        }
    }
}
