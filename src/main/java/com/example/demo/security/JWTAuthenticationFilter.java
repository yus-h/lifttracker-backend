package com.example.demo.security;

import com.example.demo.domain.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.demo.security.SecurityConstants.*;

/**
 * Authentication filter to issue JWTS to users sending credentials
 */
//https://docs.spring.io/spring-security/site/docs/4.1.2.RELEASE/apidocs/org/springframework/security/web/authentication/UsernamePasswordAuthenticationFilter.html
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // todo google this
    private AuthenticationManager authenticationManager;



    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
            // todo DEBUG - where does request object come from.
            ApplicationUser applicationUser = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            applicationUser.getUsername(),
                            applicationUser.getPassword(),
                            new ArrayList<>())
            );

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    /**
     * Send token back to client
     * @param request
     * @param response
     * @param chain
     * @param auth
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws IOException {

        // TODO need to test the expiration_time.
        String token = Jwts.builder()
                .setSubject(((User)auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.addHeader("TEST", "TEST123");

        TokenResponse tokenResponse = new TokenResponse(token, ((User) auth.getPrincipal()).getUsername());
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(tokenResponse);
        response.getWriter().write(jsonInString);
        System.out.println("TOKEN: " + token);

    }

    private class TokenResponse {

        private String jwtToken;
        private String username;

        public TokenResponse(String jwtToken, String username) {
            this.jwtToken = jwtToken;
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getJwtToken() {
            return jwtToken;
        }

        public void setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
        }
    }
}