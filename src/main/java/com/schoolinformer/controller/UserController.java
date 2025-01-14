package com.schoolinformer.controller;

import com.schoolinformer.repository.PUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final PUserRepository PUserRepository;

    public UserController(final PUserRepository PUserRepository) {
        this.PUserRepository = PUserRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(PUserRepository.findAll());
    }
}
