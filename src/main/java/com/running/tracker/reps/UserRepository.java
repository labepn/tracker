package com.running.tracker.reps;

import org.springframework.data.jpa.repository.JpaRepository;

import com.running.tracker.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    

}
