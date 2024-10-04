package com.viniciuspc.runnerz.run;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryRunRepositoryTest {
  InMemoryRunRepository repository;

  @BeforeEach
  void setUp() {
    // The @PostConstruct method is not called because we are not involving 
    // Spring here. We need to call the method manually.
    repository = new InMemoryRunRepository();
    repository.create(new Run(1, 
        "Monday Morning Run", 
        LocalDateTime.now(), 
        LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 
        3,
        Location.INDOOR,
        null));

    repository.create(new Run(2, 
        "Wednesday Evening Run", 
        LocalDateTime.now(), 
        LocalDateTime.now().plus(60, ChronoUnit.MINUTES), 
        6,
        Location.OUTDOOR,
        null));
  }

  @Test
  void shouldFindAllRuns() {
    List<Run> runs = repository.findAll();
    assertEquals(2, runs.size(), "Should have returned 2 runs");
  }

  @Test
  void shouldFindRunWithValidId() {
    Run run = repository.findById(1).get();
    assertEquals("Monday Morning Run", run.title());
    assertEquals(3, run.miles());
  }

  @Test
    void shouldCreateNewRun() {
        repository.create(new Run(3,
                "Friday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                3,
                Location.INDOOR, null));
        List<Run> runs = repository.findAll();
        assertEquals(3, runs.size());
    }

    @Test
    void shouldUpdateRun() {
        repository.update(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                5,
                Location.OUTDOOR, null), 1);
        var run = repository.findById(1).get();
        assertEquals("Monday Morning Run", run.title());
        assertEquals(5, run.miles());
        assertEquals(Location.OUTDOOR, run.location());
    }

    @Test
    void shouldDeleteRun() {
        repository.delete(1);
        List<Run> runs = repository.findAll();
        assertEquals(1, runs.size());
    }

}
