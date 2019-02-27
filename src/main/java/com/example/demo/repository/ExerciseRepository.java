package com.example.demo.repository;

import com.example.demo.domain.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {

    Page<Exercise> findAllByOrderByNameAsc(Pageable pageable);

    /**
     * Find by ID in a child property
     * https://stackoverflow.com/questions/24441411/spring-data-jpa-find-by-embedded-object-property
     */
    Page<Exercise> findByMuscleGroupId(Long id, Pageable pageable);

    Page<Exercise> findByMuscleGroupIdOrderByNameAsc(Long id, Pageable pageable);

    /**
     * Find In
     * https://stackoverflow.com/questions/32796419/crudrepository-findby-method-signature-with-multiple-in-operators
     */
    Page<Exercise> findByMuscleGroupIdInOrderByNameAsc(List<Long> id, Pageable pageable);


}
