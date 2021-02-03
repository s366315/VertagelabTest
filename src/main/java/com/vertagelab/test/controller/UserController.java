package com.vertagelab.test.controller;

import com.vertagelab.test.model.SuccessResponseModel;
import com.vertagelab.test.model.UserModel;
import com.vertagelab.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.vertagelab.test.statics.Statics.getErrorResponse;
import static com.vertagelab.test.statics.Statics.getSuccessResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> list() {
        SuccessResponseModel response = getSuccessResponse();
        response.setResult(userRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        Optional<UserModel> opt = userRepository.findById(id);
        SuccessResponseModel response = getSuccessResponse();
        if (opt.isPresent()) {
            response.setResult(opt.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(getErrorResponse(null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserModel model) {
        userRepository.save(model);
        return new ResponseEntity<>(getSuccessResponse(), HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> editUser(@PathVariable int id, @RequestBody UserModel model) {
        Optional<UserModel> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            model.setId(opt.get().getId());
            userRepository.save(model);
            return new ResponseEntity<>(getSuccessResponse(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(getErrorResponse("user not found"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        Optional<UserModel> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            UserModel userModel = opt.get();
            userRepository.delete(userModel);
            return new ResponseEntity<>(getSuccessResponse(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(getErrorResponse("user not found"), HttpStatus.NOT_FOUND);
        }
    }
}
