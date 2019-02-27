package com.example.demo.repository;

import com.example.demo.domain.Exercise;
import com.example.demo.domain.ExerciseSet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExerciseSetRepository extends CrudRepository<ExerciseSet, Long> {

    List<ExerciseSet> findAllByRoutineId(Long id);

    List<ExerciseSet> findAllByRoutineIdAndExerciseId(Long routineId, Long exerciseId);


}
