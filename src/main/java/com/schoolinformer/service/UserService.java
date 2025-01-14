package com.schoolinformer.service;

import com.schoolinformer.model.entity.PUser;
import com.schoolinformer.repository.PUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final PUserRepository PUserRepository;

    public UserService(final PUserRepository PUserRepository) {
        this.PUserRepository = PUserRepository;
    }

    public List<PUser> getAllUsers() {
        return PUserRepository.findAll();
    }
}
