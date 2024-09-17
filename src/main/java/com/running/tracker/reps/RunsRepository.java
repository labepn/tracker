package com.running.tracker.reps;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.running.tracker.model.Runs;

public interface RunsRepository extends JpaRepository<Runs, Integer> {

    @Query("SELECT r from Runs r WHERE"
            +":userId = user_id AND finish_datetime is NULL")
    Optional<Runs> findStarterRuns(@Param("userId") Integer userId);

}
