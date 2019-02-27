package com.example.demo.service;

import com.example.demo.domain.ApplicationUser;
import com.example.demo.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {


    @Autowired
    private ApplicationUserRepository userRepository;


    /**
     * Spring Security doesn't come with a concrete implementation of UserDetailsService
     *  When a user tries to authenticate, this method receives the username
     *  searches the database for a record containing it,
     *  and (if found) returns an instance of ApplicationUser.
     *  The properties of this instance (username and password)
     *  are then checked against the credentials passed by the user in the login request.
     *  This last process is executed outside this class, by the Spring Security framework.
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
}