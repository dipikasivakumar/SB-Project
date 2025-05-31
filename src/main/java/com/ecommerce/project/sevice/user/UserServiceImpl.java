package com.ecommerce.project.sevice.user;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.request.CreateUserRequest;
import com.ecommerce.project.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public User getUserById(Long Id) {
        return userRepository.findById(Id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public User createUser(CreateUserRequest userRequest) {
        return Optional.of(userRequest).map(req -> {
            User user = new User();
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User Creation Failed"));
    }

    @Override
    public User updateUser(UpdateUserRequest userRequest, Long Id) {
        return userRepository.findById(Id).map(existingUser -> {
            existingUser.setFirstName(userRequest.getFirstName());
            existingUser.setLastName(userRequest.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow( () -> new ResourceNotFoundException("User Updation Failed"));
    }

    @Override
    public void deleteUser(Long Id) {
        userRepository.findById(Id).ifPresentOrElse(userRepository :: delete, () -> {
           throw new ResourceNotFoundException("User Not Found");});
    }
}
