package com.running.tracker.ctrl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import com.running.tracker.model.User;
import com.running.tracker.reps.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @CrossOrigin
    @RequestMapping(path = "user", method=RequestMethod.GET)
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @CrossOrigin
    @RequestMapping(value = "user/{id}", method=RequestMethod.GET)
    public Optional<User> getUserById (@PathVariable ("id") Integer id) {
        return userRepository.findById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "user/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser (@PathVariable ("id") Integer id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("user")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User changed) {
        if(!userRepository.existsById(changed.getId()))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        if(!id.equals(changed.getId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userRepository.save(changed);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    

}
