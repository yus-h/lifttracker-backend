package com.example.demo.controllers;

import com.example.demo.domain.Exercise;
import com.example.demo.domain.ExerciseSet;
import com.example.demo.domain.MuscleGroup;
import com.example.demo.domain.Routine;
import com.example.demo.formmodels.FormSubmission;
import com.example.demo.repository.RoutineRepository;
import com.example.demo.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    RoutineService routineService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String home() {
        return "TEST aaeer1122";
    }


    @RequestMapping(
            value = "/exercises",
            method = RequestMethod.GET)
    public Page<Exercise> getExercises(@RequestParam("page") int page,
                                       @RequestParam("size") int size,
                                       @RequestParam("filters") String filters[]) {
        ///  TODO is there a way  to return a "LITE" version of this for the side menu.
        return routineService.getAllExercises(page, size, filters);
    }


    @RequestMapping(value = "/exercises/{id}", method = RequestMethod.GET)
    public Exercise getExercise(@PathVariable(value="id") Long id) {
        return routineService.getExercise(id);
    }


    @RequestMapping(value = "/exercises/{id}", method = RequestMethod.PUT)
    public Exercise updateExercise(@RequestBody Exercise exercise) {
        return routineService.updateExercise(exercise);
    }

    @RequestMapping(value = "/exercises/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteExercise(@RequestBody Exercise exercise) {
        routineService.deleteExercise(exercise);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/exercises", method = RequestMethod.POST)
    public ResponseEntity<?> saveExercise(@RequestBody Exercise exercise) {
        Exercise savedExercise = routineService.saveExercise(exercise);
        return new ResponseEntity<>(savedExercise, HttpStatus.CREATED);
    }



    @RequestMapping(value = "/musclegroups", method = RequestMethod.GET)
    public List<MuscleGroup> getMuscleGroups() {
        return routineService.getAllMuscleGroups();
    }


    // TODO should we change the endpoint name for form submission?
    @RequestMapping(value = "/routines", method = RequestMethod.POST)
    public ResponseEntity<?>  saveRoutine(@RequestBody FormSubmission formRoutine) {
        long routineId = routineService.saveFormRoutine(formRoutine, null);
        return new ResponseEntity<>(routineId, HttpStatus.CREATED);
    }

    /**
     * To populate Routine form during view/edit mode.
     * @param id routine id
     * @return
     */
    @RequestMapping(value = "/forms/routines/{id}", method = RequestMethod.GET)
    public FormSubmission getRoutineFormSubmission(@PathVariable(value="id") Long id) {
        // TODO return based on exercise set order too.
        return routineService.getRoutineFormSubmission(id);
    }

    /**
     * To update Routine form during view/edit mode.
     * @param id routine id
     * @return
     */
    @RequestMapping(value = "/forms/routines/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?>  updateRoutineFormSubmission(@RequestBody FormSubmission formRoutine) {
        long routineId = routineService.updateRoutineFormSubmission(formRoutine);
        return new ResponseEntity<>(routineId, HttpStatus.OK);

    }



    /**
     * For the side panel
     * @return
     */
   @RequestMapping(value = "/routines", method = RequestMethod.GET)
    public List<Routine> getRoutines() {
        return routineService.getAllRoutines();
    }

    /**
    @RequestMapping(value = "/routines/{id}", method = RequestMethod.GET)
    public Routine getRoutine(@PathVariable(value="id") Long id) {
        return routineService.getRoutine(id);
    }

    @RequestMapping(value = "/routines/{id}/exercises", method = RequestMethod.GET)
    public List<ExerciseSet> getRoutineExerciseSets(@PathVariable(value="id") Long id) {
        return routineService.getAllExerciseSetsForRoutineId(id);
    }**/


    // TODO test save exercise + exercise set + routine.
    // When getting list of routines - do we actually want to return all exercise sets within a routine as well??

    //TODO for now leave it as 1 way binding - need to rethink structure.




}
