package com.example.bookstoredemo.controller;

import com.example.bookstoredemo.entities.Book;
import com.example.bookstoredemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/search/all")
    public Page<Book> fetchAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return bookService.fetchAllBooks(PageRequest.of(page,size));
    }

    @GetMapping("/search/isbn")
    public Book getBookById(@RequestParam("isbn") Long book_isbn) {
        return bookService.getBookById(book_isbn);
    }

    @GetMapping("/search/author")
    public Page<Book> getBookByAuthor(@RequestParam("author") String author, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable paged = PageRequest.of(page, size);
        return bookService.findByAuthor(author, paged);
    }

    @GetMapping("/search/title")
    public Page<Book> getBookByTitle(@RequestParam("title") String title, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable paged = PageRequest.of(page, size);
        return bookService.findByTitle(title, paged);
    }

    @GetMapping("/search/stock")
    public Page<Book> getBookByStock(@RequestParam("min") Optional<Integer> min, @RequestParam("max") Optional<Integer> max, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable paged = PageRequest.of(page, size);
        if (min.isPresent() && max.isPresent()) {
            return bookService.findByStockBetween(min.get(), max.get(), paged);
        } else if (min.isPresent()) {
            return bookService.findByStockGreaterThanEqual(min.get(), paged);
        } else if (max.isPresent()) {
            return bookService.findByStockLessThanEqual(max.get(), paged);
        }
        return null;
    }

    @GetMapping("/search/price")
    public Page<Book> getBookByPrice(@RequestParam("min") Optional<Double> min, @RequestParam("max") Optional<Double> max, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable paged = PageRequest.of(page, size);
        if (min.isPresent() && max.isPresent()) {
            return bookService.findByPriceBetween(min.get(), max.get(), paged);
        } else if (min.isPresent()) {
            return bookService.findByPriceGreaterThanEqual(min.get(), paged);
        } else if (max.isPresent()) {
            return bookService.findByPriceLessThanEqual(max.get(), paged);
        }
        return null;
    }

    @GetMapping("/search/{book_isbn}/stock")
    public Object getBookStockById(@PathVariable("book_isbn") Long book_isbn) {
        return bookService.getBookStockById(book_isbn);
    }
    @PutMapping("/update/{book_isbn}")
    public Book updateBookStockById(@PathVariable("book_isbn") Long book_isbn, @RequestParam("stock") int stock) {
        return bookService.updateBookStockById(book_isbn, stock);
    }

    @DeleteMapping("/book/{book_isbn}")
    public String removeBook(@PathVariable("book_isbn") Long book_isbn) {
        return bookService.removeBook(book_isbn);
    }


}
