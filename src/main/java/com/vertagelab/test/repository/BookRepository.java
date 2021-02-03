package com.vertagelab.test.repository;

import com.vertagelab.test.model.BookModel;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookModel, Integer> {

}
