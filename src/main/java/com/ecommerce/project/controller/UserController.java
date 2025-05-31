package com.ecommerce.project.controller;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.User;
import com.ecommerce.project.request.CreateUserRequest;
import com.ecommerce.project.request.UpdateUserRequest;
import com.ecommerce.project.response.APIResponse;
import com.ecommerce.project.sevice.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user/{Id}")
    public ResponseEntity<APIResponse> getUserById(@PathVariable Long Id) {
        try {
            User user = userService.getUserById(Id);
            return  ResponseEntity.ok(new APIResponse("Success!!" , user));
        } catch (ResourceNotFoundException e) {
              return  ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null ));
        }
    }

    @PostMapping("/user/add")
    public ResponseEntity<APIResponse> createUser(@RequestBody CreateUserRequest userRequest) {
        try {
            User user = userService.createUser(userRequest);
            return  ResponseEntity.ok(new APIResponse("Create User Success!!" , user));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(),null ));
        }
    }

    @PutMapping("/user/update/{Id}")
    public ResponseEntity<APIResponse> updateUser(@RequestBody UpdateUserRequest userRequest, @PathVariable Long Id) {
        try {
            User user = userService.updateUser(userRequest, Id);
            return  ResponseEntity.ok(new APIResponse("Update User Success!!" , user));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(),null ));
        }
    }

    @DeleteMapping("/user/delete/{Id}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long Id) {
        try {
            userService.deleteUser(Id);
            return  ResponseEntity.ok(new APIResponse(" User Deleted Successfully!!" , null));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null ));
        }
    }
}
