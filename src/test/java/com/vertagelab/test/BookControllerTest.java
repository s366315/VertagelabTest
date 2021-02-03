package com.vertagelab.test;

import com.vertagelab.test.controller.BookController;
import com.vertagelab.test.model.BookModel;
import com.vertagelab.test.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookControllerTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    BookController bookController;

    @Test
    void getExistBook() {
        Optional<BookModel> user = bookRepository.findById(1);

        assertThat(user).isPresent();
    }

    @Test
    void getUnExistBook() {
        Optional<BookModel> user = bookRepository.findById(5);

        assertThat(user).isEmpty();
    }
}
