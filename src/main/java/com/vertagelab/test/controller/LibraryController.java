package com.vertagelab.test.controller;

import com.vertagelab.test.model.BookModel;
import com.vertagelab.test.model.LibraryModel;
import com.vertagelab.test.model.SuccessResponseModel;
import com.vertagelab.test.model.UserModel;
import com.vertagelab.test.repository.BookRepository;
import com.vertagelab.test.repository.LibraryRepository;
import com.vertagelab.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

import static com.vertagelab.test.statics.Statics.getErrorResponse;
import static com.vertagelab.test.statics.Statics.getSuccessResponse;

@RestController
@RequestMapping("/api/library")
public class LibraryController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping("/takeBook")
    public ResponseEntity<?> takeBook(@RequestParam int bookId, @RequestParam int userId) {
        if (!isBookPresent(bookId)) {
            return getBookNotFoundException();
        }
        if (!isUserPresent(userId)) {
            return getUserNotFoundException();
        }

        Optional<LibraryModel> optHandledBook = libraryRepository.findLibraryModelByBookId(bookId);
        if (optHandledBook.isPresent()) {
            return getBookAlreadyTakenException();
        }

        LibraryModel libraryModel = new LibraryModel();
        libraryModel.setBookId(bookId);
        libraryModel.setUserId(userId);
        libraryRepository.save(libraryModel);

        return new ResponseEntity<>(getSuccessResponse(), HttpStatus.OK);
    }

    @GetMapping("/returnBook")
    public ResponseEntity<?> returnBook(@RequestParam int bookId) {
        if (!isBookPresent(bookId)) {
            return getBookNotFoundException();
        }

        Optional<LibraryModel> optHandledBook = libraryRepository.findLibraryModelByBookId(bookId);
        if (optHandledBook.isPresent()) {
            libraryRepository.delete(optHandledBook.get());
            return new ResponseEntity<>(getSuccessResponse(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(getErrorResponse("book already free"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/listByUser")
    public ResponseEntity<?> listByUser(@RequestParam int userId) {
        if (!isUserPresent(userId)) {
            return getUserNotFoundException();
        }

        ArrayList<BookModel> books = new ArrayList<>();
        for (LibraryModel libraryModel : libraryRepository.findLibraryModelsByUserId(userId)) {
            Optional<BookModel> book = bookRepository.findById(libraryModel.getBookId());
            books.add(book.get());
        }

        SuccessResponseModel response = getSuccessResponse();
        response.setResult(books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/findBookHandler")
    public ResponseEntity<?> findBookHandler(@RequestParam int bookId) {
        if (!isBookPresent(bookId)) {
            return getBookNotFoundException();
        }

        SuccessResponseModel response = getSuccessResponse();
        Optional<LibraryModel> libraryModel = libraryRepository.findLibraryModelByBookId(bookId);
        if (libraryModel.isPresent()) {
            Optional<UserModel> user = userRepository.findById(libraryModel.get().getUserId());
            response.setResult(user);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean isBookPresent(int bookId) {
        return bookRepository.findById(bookId).isPresent();
    }

    private boolean isUserPresent(int userId) {
        return userRepository.findById(userId).isPresent();
    }

    private ResponseEntity<?> getBookNotFoundException() {
        return new ResponseEntity<>(getErrorResponse("book not found"), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<?> getUserNotFoundException() {
        return new ResponseEntity<>(getErrorResponse("user not found"), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<?> getBookAlreadyTakenException() {
        return new ResponseEntity<>(getErrorResponse("book already taken"), HttpStatus.NOT_FOUND);
    }
}
