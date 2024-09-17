package com.running.tracker.ctrl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.running.tracker.model.Runs;
import com.running.tracker.reps.RunsRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
public class RunsController {

    @Autowired
    private RunsRepository runsRepository;

    @CrossOrigin
    @GetMapping("runs")
    public Collection<Runs> getAllRuns() {
        return runsRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(path = "runs/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Runs> deleteRun(@PathVariable ("id") Integer id) {
        runsRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("runs")
    public ResponseEntity<Runs> startRun(@RequestBody Runs run) {
        Optional<Runs> checkRun = runsRepository.findStarterRuns(run.getUser_id().getId());
        // must finish the started run to add a new one
        if(!checkRun.isPresent()){
            Runs run1 = new Runs(run);
            runsRepository.save(run1);
        }
        return new ResponseEntity<Runs> (run ,HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(value = "runs", consumes="application/json")
    public ResponseEntity<Runs> finishRun(@RequestBody Runs changed) {
        Optional<Runs> run = runsRepository.findStarterRuns(changed.getUser_id().getId());
        // if (runId != run.get().getId()) {
        //     return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        // }
        if(changed.getFinish_datetime().isBefore(run.get().getStart_datetime())){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
        run.get().setFinish_lat(changed.getFinish_lat());
        run.get().setFinish_lon(changed.getFinish_lon());
        run.get().setFinish_datetime(changed.getFinish_datetime());
        // It would be a lot easier and faster to user a PostGIS (Postgres geospatial extension) and instead of lat and lon fields to have geometry field,
        // so the distance could be calculated in the database
        if(changed.getDistance() != null){
            run.get().setDistance(changed.getDistance());
        } else {
            run.get().calculateDistance();
        }
        runsRepository.save(run.get());
        return new ResponseEntity<> (HttpStatus.OK);
    }
    
    
    

}
