package com.norbert.librarymanagement.controller;

import com.norbert.librarymanagement.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // In-memory storage for books (using ArrayList)
    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    // Constructor - initialize with 3 sample books
    public BookController() {
        books.add(new Book(nextId++, "Clean Code", "Robert Martin", "978-0132350884", 2008));
        books.add(new Book(nextId++, "Effective Java", "Joshua Bloch", "978-0134685991", 2017));
        books.add(new Book(nextId++, "Spring in Action", "Craig Walls", "978-1617294945", 2018));
    }

    // GET /api/books - Return all books (200 OK)
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // GET /api/books/{id} - Return a specific book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        
        if (book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // GET /api/books/search?title={title} - Search books by title
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> foundBooks = new ArrayList<>();
        
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        
        if (foundBooks.isEmpty()) {
            return new ResponseEntity<>(foundBooks, HttpStatus.NOT_FOUND); // 404 Not Found
        }
        
        return new ResponseEntity<>(foundBooks, HttpStatus.OK); // 200 OK
    }

    // POST /api/books - Add a new book (201 Created)
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        book.setId(nextId++); // Auto-generate ID
        books.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED); // 201 Created
    }

    // DELETE /api/books/{id} - Delete a book by ID (204 No Content or 404 Not Found)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }
}
