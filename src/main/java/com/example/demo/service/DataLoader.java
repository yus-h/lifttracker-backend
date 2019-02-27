package com.example.demo.service;

import com.example.demo.domain.Exercise;
import com.example.demo.domain.ExerciseSet;
import com.example.demo.domain.MuscleGroup;
import com.example.demo.domain.Routine;
import com.example.demo.repository.ExerciseRepository;
import com.example.demo.repository.ExerciseSetRepository;
import com.example.demo.repository.MuscleGroupRepository;
import com.example.demo.repository.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataLoader {


    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    ExerciseSetRepository exerciseSetRepository;

    @Autowired
    RoutineRepository routineRepository;

    @Autowired
    MuscleGroupRepository muscleGroupRepository;

    public void clearData() {
        exerciseSetRepository.deleteAll();
        routineRepository.deleteAll();
        exerciseRepository.deleteAll();
        muscleGroupRepository.deleteAll();
    }

    public void test() {

        Iterable<Routine> routines = routineRepository.findAll();
        for (Routine r : routines) {

            long id = r.getId();
            System.out.println("FOUND ROUTINE WITH ID: " + id);
            List<ExerciseSet> exerciseSets = exerciseSetRepository.findAllByRoutineId(id);
            for (ExerciseSet exerciseSet : exerciseSets) {
                System.out.println("With exercise:");
                System.out.println(exerciseSet);
            }
        }

    }


    public void loadData(){

        MuscleGroup muscleGroup1 = new MuscleGroup("Biceps");
        muscleGroupRepository.save(muscleGroup1);

        MuscleGroup muscleGroup2 = new MuscleGroup("Chest");
        muscleGroupRepository.save(muscleGroup2);

        MuscleGroup muscleGroup3 = new MuscleGroup("Back");
        muscleGroupRepository.save(muscleGroup3);

        Exercise e = new Exercise("Bicep curl");
        e.setMuscleGroup(muscleGroup1);
        exerciseRepository.save(e);

        Exercise e2 = new Exercise("Bench Press");
        e2.setMuscleGroup(muscleGroup2);
        exerciseRepository.save(e2);

        Exercise e3 = new Exercise("Deadlift");
        e3.setMuscleGroup(muscleGroup3);
        exerciseRepository.save(e3);

        Routine r = new Routine();
        r.setNotes("My new routine");
        r.setDate(new Date());
        routineRepository.save(r);

        ExerciseSet eSet = new ExerciseSet();
        eSet.setExercise(e);
        eSet.setReps(5);
        eSet.setWeight(10);
        eSet.setNotes("Notes here");
        eSet.setRoutine(r);
        eSet.setSetOrder(1);
        exerciseSetRepository.save(eSet);

        ExerciseSet eSet2 = new ExerciseSet();
        eSet2.setExercise(e2);
        eSet2.setReps(8);
        eSet2.setWeight(80);
        eSet2.setNotes("Notes here1");
        eSet2.setRoutine(r);
        eSet2.setSetOrder(1);
        exerciseSetRepository.save(eSet2);

        ExerciseSet eSet3 = new ExerciseSet();
        eSet3.setExercise(e2);
        eSet3.setReps(6);
        eSet3.setWeight(80);
        eSet3.setNotes("Notes here2");
        eSet3.setRoutine(r);
        eSet3.setSetOrder(2);
        exerciseSetRepository.save(eSet3);

        ExerciseSet eSet4 = new ExerciseSet();
        eSet4.setExercise(e2);
        eSet4.setReps(4);
        eSet4.setWeight(80);
        eSet4.setNotes("Notes here3");
        eSet4.setRoutine(r);
        eSet4.setSetOrder(3);
        exerciseSetRepository.save(eSet4);

        ExerciseSet eSet5 = new ExerciseSet();
        eSet5.setExercise(e3);
        eSet5.setReps(5);
        eSet5.setWeight(120);
        eSet5.setNotes("Notes here5");
        eSet5.setRoutine(r);
        eSet5.setSetOrder(1);
        exerciseSetRepository.save(eSet5);

        System.out.println("DATA LOADED ~~~~~~~~~~~~~~~");
        Routine rNew = routineRepository.findOne(r.getId());

    }


}
