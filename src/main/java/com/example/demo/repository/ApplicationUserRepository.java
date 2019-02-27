package com.example.demo.repository;

import com.example.demo.domain.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);

}
