package com.example.demo.repository;

import com.example.demo.domain.ExerciseSet;
import com.example.demo.domain.Routine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoutineRepository extends CrudRepository<Routine, Long> {


    List<Routine> findAllByOrderByDateDesc();

}
