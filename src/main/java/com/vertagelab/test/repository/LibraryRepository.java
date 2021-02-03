package com.vertagelab.test.repository;

import com.vertagelab.test.model.LibraryModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LibraryRepository extends CrudRepository<LibraryModel, Integer> {
    Optional<LibraryModel> findLibraryModelByBookId(int bookId);

    Iterable<LibraryModel> findLibraryModelsByUserId(int userId);
}
