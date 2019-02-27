package com.example.demo.service;

import com.example.demo.domain.Exercise;
import com.example.demo.domain.ExerciseSet;
import com.example.demo.domain.MuscleGroup;
import com.example.demo.domain.Routine;
import com.example.demo.formmodels.FormExercise;
import com.example.demo.formmodels.FormSubmission;
import com.example.demo.formmodels.FormWeightAndReps;
import com.example.demo.repository.ExerciseRepository;
import com.example.demo.repository.ExerciseSetRepository;
import com.example.demo.repository.MuscleGroupRepository;
import com.example.demo.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class RoutineService {

    @Autowired
    RoutineRepository routineRepository;

    @Autowired
    ExerciseSetRepository exerciseSetRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    MuscleGroupRepository muscleGroupRepository;

    public Exercise getExercise(long id) {
        return exerciseRepository.findOne(id);
    }

    public Exercise updateExercise(Exercise exercise) {
        Exercise foundExercise = exerciseRepository.findOne(exercise.getId());
        foundExercise.setName(exercise.getName());
        return exerciseRepository.save(foundExercise);
    }

    public void deleteExercise(Exercise exercise) {
        Exercise foundExercise = exerciseRepository.findOne(exercise.getId());
        exerciseRepository.delete(foundExercise);
        // TODO better error handling
    }


    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Page<Exercise> getAllExercises(int page, int size) {
        return exerciseRepository.findAllByOrderByNameAsc(new PageRequest(page, size));
    }

    public Page<Exercise> getAllExercises(int page, int size, String[] filters) {
        if (filters.length == 0) {
            return getAllExercises(page, size);
        } else {
//            Long f = Long.parseLong(filters[0]);
//            Page<Exercise> exercises =  exerciseRepository.findByMuscleGroupIdOrderByNameAsc(f, new PageRequest(page, size));
//            long[] muscleGroupIDs = Arrays.stream(filters).mapToLong(i -> i).toArray();

            List<Long> intList = Arrays.stream(filters)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            Page<Exercise> exercises =  exerciseRepository.findByMuscleGroupIdInOrderByNameAsc(intList, new PageRequest(page, size));
            return exercises;
        }
    }

    public List<Routine> getAllRoutines() {
        return routineRepository.findAllByOrderByDateDesc();
    }

    public Routine getRoutine(long id) {
        return routineRepository.findOne(id);
    }

    public void saveRoutine(Routine r) {
        routineRepository.save(r);
    }


    public List<ExerciseSet> getAllExerciseSetsForRoutineId(Long id) {
        return exerciseSetRepository.findAllByRoutineId(id);
    }

    // TODO how to pass in routine
    public void saveExerciseSetToRoutine(ExerciseSet exerciseSet, Long routineID) {
        Routine r = routineRepository.findOne(routineID);
        exerciseSet.setRoutine(r);
        exerciseSetRepository.save(exerciseSet);
    }

    public List<MuscleGroup> getAllMuscleGroups() {
        return muscleGroupRepository.findAllByOrderByNameAsc();
    }



    /**
     * Takes an angular json form object and saves the corresponding domain objects to the DB
     * @param formSubmission
     */
    public long saveFormRoutine(FormSubmission formSubmission, Routine routine) {

        Routine savedRoutine;
        if (routine == null) {
            routine = new Routine();
            routine.setNotes(formSubmission.getNotes());

            String dateYYYYMMDD = formSubmission.getDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = new Date();
            try {
                inputDate = dateFormat.parse(dateYYYYMMDD);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            routine.setDate(inputDate);

            savedRoutine = routineRepository.save(routine);
        } else {
            savedRoutine = routine;
        }





        for (FormExercise formExercise : formSubmission.getExercises()) {
            Exercise exercise = exerciseRepository.findOne(formExercise.getExerciseId());

            //Want to save X amount of Exercise sets for each set
            int i = 1;
            for (FormWeightAndReps formWeightAndReps : formExercise.getWeightAndReps()) {

                // Construct domain object
                ExerciseSet exerciseSet = new ExerciseSet();
                exerciseSet.setRoutine(savedRoutine);
                exerciseSet.setExercise(exercise);
                exerciseSet.setWeight(formWeightAndReps.getWeight());
                exerciseSet.setReps(formWeightAndReps.getReps());
                exerciseSet.setSetOrder(i);
                exerciseSetRepository.save(exerciseSet);
                i++;
            }
        }

        return routine.getId();
    }



    /**
     * Create a FormSubmission object to return
     * Construct it based on doing the DB queries for domain Routine and ExerciseSet
     *
     * Example of expected JSON see below
     * @param id
     * @return
     */
    //    {
    //        "notes": "wadawd",
    //            "exercises": [
    //        {
    //            "exerciseId": 1,
    //                "exerciseName": "Bicep curl346666",
    //                "weightAndReps": [
    //            {
    //                "weight": "123",
    //                 "reps": "23"
    //            },
    //            {
    //                "weight": "43",
    //                 "reps": "32"
    //            }
    //      ]
    //        },
    //        {
    //            "exerciseId": 3,
    //                "exerciseName": "Deadlift",
    //                "weightAndReps": [
    //            {
    //                "weight": "11",
    //                 "reps": "22"
    //            }
    //      ]
    //        }
    //  ]
    //    }
    public FormSubmission getRoutineFormSubmission(Long id) {
        FormSubmission formSubmission = new FormSubmission();

        Routine r = routineRepository.findOne(id);
        formSubmission.setNotes(r.getNotes());
        formSubmission.setRoutineId(r.getId());

        Date date = r.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputDate = dateFormat.format(date);
        formSubmission.setDate(inputDate);



        List<ExerciseSet> exerciseSets = exerciseSetRepository.findAllByRoutineId(id);

        // Group Exercise Sets based on Exercise
        Map<Exercise, List<ExerciseSet>> exercisesGroups = exerciseSets.stream()
                .collect(groupingBy(ExerciseSet::getExercise));

        List<FormExercise> formExercises = new ArrayList<>();
        for (Exercise key : exercisesGroups.keySet()){

            FormExercise formExercise = new FormExercise();
            formExercise.setExerciseId(key.getId());
            formExercise.setExerciseName(key.getName());

            List<FormWeightAndReps> weightAndReps = new ArrayList<>();
            List<ExerciseSet> sets = exercisesGroups.get(key);
            for (ExerciseSet exerciseSet : sets) {
                FormWeightAndReps formWeightAndReps = new FormWeightAndReps();
                formWeightAndReps.setWeight(exerciseSet.getWeight());
                formWeightAndReps.setReps(exerciseSet.getReps());
                weightAndReps.add(formWeightAndReps);
            }

            formExercise.setWeightAndReps(weightAndReps);
            formExercises.add(formExercise);
        }

        formSubmission.setExercises(formExercises);

        return formSubmission;
    }

    /**
     *  For now this deletes and re adds all the sets.
     *  Need a better way to detect if exercise/set has been deleted added.
     *  e.g. a diff between the 2 formsubmissions
     * @param formSubmission
     */
    public long updateRoutineFormSubmission(FormSubmission formSubmission) {
        Routine routine = routineRepository.findOne(formSubmission.getRoutineId());
        routine.setNotes(formSubmission.getNotes());

        String dateYYYYMMDD = formSubmission.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = new Date();
        try {
            inputDate = dateFormat.parse(dateYYYYMMDD);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        routine.setDate(inputDate);

        Routine savedRoutine = routineRepository.save(routine);
        List<ExerciseSet> exerciseSets = exerciseSetRepository.findAllByRoutineId(savedRoutine.getId());
        for (ExerciseSet exerciseSet : exerciseSets) {
            exerciseSetRepository.delete(exerciseSet);
        }

        return saveFormRoutine(formSubmission, savedRoutine);

    }

   /** public void updateRoutineFormSubmission(FormSubmission formSubmission) {

        Routine r = new Routine();
        r.setNotes(formSubmission.getNotes());
        Routine savedRoutine = routineRepository.findOne(formSubmission.getRoutineId()); // TODO hold id in formsubmission?


        for (FormExercise formExercise : formSubmission.getExercises()) {

            // Find by exercise and routine id
            List<ExerciseSet> exerciseSets = exerciseSetRepository.findAllByRoutineIdAndExerciseId(savedRoutine.getId(), formExercise.getExerciseId());


            Exercise exercise = exerciseRepository.findOne(formExercise.getExerciseId());

            //Want to save X amount of Exercise sets for each set
            for (FormWeightAndReps formWeightAndReps : formExercise.getWeightAndReps()) {

                formWeightAndReps.getReps();
                formWeightAndReps.getWeight();


                // Construct domain object
                ExerciseSet exerciseSet = new ExerciseSet();
                exerciseSet.setRoutine(savedRoutine);
                exerciseSet.setExercise(exercise);
                exerciseSet.setWeight(formWeightAndReps.getWeight());
                exerciseSet.setReps(formWeightAndReps.getReps());
                exerciseSetRepository.save(exerciseSet);
            }
        }
    }**/
}
