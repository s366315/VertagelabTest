package com.vertagelab.test.controller;

import com.vertagelab.test.exception.RequestException;
import com.vertagelab.test.model.BookModel;
import com.vertagelab.test.model.ErrorResponseModel;
import com.vertagelab.test.model.SuccessResponseModel;
import com.vertagelab.test.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<?> list() {
        SuccessResponseModel response = getSuccessResponse();
        response.setResult(bookRepository.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getBook(@PathVariable int id) {
        Optional<BookModel> opt = bookRepository.findById(id);
        SuccessResponseModel response = getSuccessResponse();
        if (opt.isPresent()) {
            response.setResult(opt.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new RequestException(getErrorResponse().toString());
        }
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody BookModel model) {
        bookRepository.save(model);
        return new ResponseEntity<>(getSuccessResponse(), HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> editBook(@PathVariable int id, @RequestBody BookModel model) {
        Optional<BookModel> opt = bookRepository.findById(id);
        if (opt.isPresent()) {
            model.setId(opt.get().getId());
            bookRepository.save(model);
            return new ResponseEntity<>(getSuccessResponse(), HttpStatus.OK);
        } else {
            ErrorResponseModel response = getErrorResponse();
            response.setError("book not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        Optional<BookModel> opt = bookRepository.findById(id);
        if (opt.isPresent()) {
            BookModel bookModel = opt.get();
            bookRepository.delete(bookModel);
            return new ResponseEntity<>(getSuccessResponse(), HttpStatus.OK);
        } else {
            ErrorResponseModel response = getErrorResponse();
            response.setError("book not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    private static SuccessResponseModel getSuccessResponse() {
        SuccessResponseModel response = new SuccessResponseModel();
        response.setSuccess(true);
        return response;
    }

    private static ErrorResponseModel getErrorResponse() {
        ErrorResponseModel response = new ErrorResponseModel();
        response.setSuccess(false);
        return response;
    }
}
