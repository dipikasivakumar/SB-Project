package com.ecommerce.project.sevice.user;

import com.ecommerce.project.model.User;
import com.ecommerce.project.request.CreateUserRequest;
import com.ecommerce.project.request.UpdateUserRequest;

public interface IUserService {

    User getUserById(Long Id);
    User createUser(CreateUserRequest userRequest);
    User updateUser(UpdateUserRequest userRequest, Long Id);
    void deleteUser(Long Id);
}
