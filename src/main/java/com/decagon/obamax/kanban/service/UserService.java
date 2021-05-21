package com.decagon.obamax.kanban.service;

import com.decagon.obamax.kanban.exception.RecordNotFoundException;
import com.decagon.obamax.kanban.model.User;
import com.decagon.obamax.kanban.payload.UserResponse;
import com.decagon.obamax.kanban.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse addUser(User user) {
        UserResponse response = new UserResponse();

        try {
            Optional<User> userDb = userRepository.findByEmail(user.getEmail());

            if (userDb.isPresent()) {
                throw new RecordNotFoundException("SigUp Failed!!! Email has been taken");
            }

            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setTeam(user.getTeam());

            User addedUser = userRepository.save(newUser);
            if (addedUser == null) {
                throw new RecordNotFoundException("Something went wrong");
            }
            response.setUser(addedUser);
            response.setSuccessful(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccessful(false);
        }
        return response;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse logIn(User user) {

        UserResponse response = new UserResponse();
        try {
            User userDb1 = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

            if (userDb1 == null) {
                throw new RecordNotFoundException("Invalid Password or Email");
            }
            response.setSuccessful(true);
            response.setUser(userDb1);
        } catch (Exception e) {
            response.setSuccessful(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public User findById(Long id){
        return userRepository.findByUserId(id);
    }
}