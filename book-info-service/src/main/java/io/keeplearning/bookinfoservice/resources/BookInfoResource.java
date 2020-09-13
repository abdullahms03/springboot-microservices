package io.keeplearning.bookinfoservice.resources;

import io.keeplearning.bookinfoservice.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookInfoResource {

    @GetMapping("/{bookId}")
    public Book getBookInfo(@PathVariable("bookId") String bookId) {
        return new Book(bookId, "Cracking the " +  bookId + " interview");
    }
}
