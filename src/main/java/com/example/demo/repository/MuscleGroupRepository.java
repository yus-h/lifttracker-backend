package com.example.demo.repository;

import com.example.demo.domain.Exercise;
import com.example.demo.domain.MuscleGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MuscleGroupRepository extends CrudRepository<MuscleGroup, Long> {

    List<MuscleGroup> findAllByOrderByNameAsc();


}
