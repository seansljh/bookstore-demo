package com.example.bookstoredemo.controller;

import com.example.bookstoredemo.entities.Book;
import com.example.bookstoredemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/query/all")
    public List<Book> fetchAllBooks() {
        return bookService.fetchAllBooks();
    }

    @GetMapping("/query/isbn")
    public Book getBookById(@RequestParam("isbn") Long book_isbn) {
        return bookService.getBookById(book_isbn);
    }

    @GetMapping("/query/author")
    public List<Book> getBookByAuthor(@RequestParam("author") String author) {
        return bookService.findByAuthor(author);
    }

    @GetMapping("/query/title")
    public List<Book> getBookByTitle(@RequestParam("title") String title) {
        return bookService.findByTitle(title);
    }

    @PutMapping("/update/{book_isbn}")
    public Book updateBookStockById(@PathVariable("book_isbn") Long book_isbn, @RequestParam("stock") int stock) {
        return bookService.updateBookStockById(book_isbn, stock);
    }

    @GetMapping("/book/{book_isbn}/stock")
    public Object getBookStockById(@PathVariable("book_isbn") Long book_isbn) {
        return bookService.getBookStockById(book_isbn);
    }

    @DeleteMapping("/book/{book_isbn}")
    public String removeBook(@PathVariable("book_isbn") Long book_isbn) {
        return bookService.removeBook(book_isbn);
    }

}
