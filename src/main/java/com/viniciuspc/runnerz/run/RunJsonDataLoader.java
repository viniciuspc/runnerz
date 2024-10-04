package com.viniciuspc.runnerz.run;

import java.io.InputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RunJsonDataLoader implements CommandLineRunner{
  private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);
  private final JdbcClientRunRepository runRepository;
  private final ObjectMapper objectMapper;

  public RunJsonDataLoader(JdbcClientRunRepository runRepository, ObjectMapper objectMapper) {
    this.runRepository = runRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public void run(String... args) throws Exception {
    if(runRepository.count() == 0){
      try (InputStream inputStream = getClass().getResourceAsStream("/data/runs.json")){
        Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
        runRepository.saveAll(allRuns.runs());
        log.info("Loaded {} runs", allRuns.runs().size());
      } catch (IOException e) {
        log.error("Failed to load runs", e);
      }
    } else {
      log.info("Not loading runs, data already loaded");
    }
  }

}
