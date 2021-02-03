package com.vertagelab.test.repository;

import com.vertagelab.test.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Integer> {
}
