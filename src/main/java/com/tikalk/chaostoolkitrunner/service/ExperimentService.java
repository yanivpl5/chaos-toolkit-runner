package com.tikalk.chaostoolkitrunner.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Chaos engineering experiments service
 */
@Service
public class ExperimentService {
    private Logger logger = LoggerFactory.getLogger(ExperimentService.class);

    @Value("${chaos.runtime}")
    private String chaosRuntime;
    @Value("${experiments.path}")
    private  static final String JOURNAL_JSON_FILE = "journal.json";

    @Value("${experiments.path}")
    private String experimentPathStr;

    private Path experimentPath;
    private Map<String, String> experiments = new HashMap();

    @PostConstruct
    public void init() {
        experimentPath = Paths.get(experimentPathStr);
    }

    /**
     * Launch the defined experiment
     * @return Run-id
     */
    public String launch() {
        UUID uuid = UUID.randomUUID();

        String id = uuid.toString();
        Path experimentDir = getExperimentPath(id);
        try {
            Files.createDirectory(experimentDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String experimentFileName = "/Users/yaniv/chaos-toolkit/experiment.json";
        try {
            Process process = Runtime.getRuntime().exec(chaosRuntime + " run " + experimentFileName,
                    new String[] {"LC_CTYPE=UTF-8"},
                    experimentDir.toFile());
            logger.info("Launch experiment:{}, pid: {}", experimentFileName, process);
            String output = IOUtils.toString(process.getInputStream());
            String error = IOUtils.toString(process.getErrorStream());
            if (error != null && error.length() > 0) {
                logger.error(error);
            } else {
                if (output.contains("Experiment ended with status")) {
                    experiments.put("id", "active");
                }
            }
            process.waitFor();
            experiments.put("id", "ended");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        } else {
            List<String> journalLines = Files.readAllLines(journalPath);
            return Optional.of(String.join(" ", journalLines));
        }
    }
//    public String getJournal(String id) {
//        String experiment = experiments.get(id);
//        if (experiment.equals("active")){
//            return "active";
//        }
//        String fileContent = "error";
//        try {
//            fileContent = FileUtils.readFileToString(new File(experimentPath.toString() + "/" + id + "journal.json") , "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return fileContent;
//    }
}
