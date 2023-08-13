package com.example.bookstoredemo.controller;

import com.example.bookstoredemo.entities.Book;
import com.example.bookstoredemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<Book> fetchAllBooks() {
        return bookService.fetchAllBooks();
    }

    @GetMapping("/search/isbn")
    public Book getBookById(@RequestParam("isbn") Long book_isbn) {
        return bookService.getBookById(book_isbn);
    }

    @GetMapping("/search/author")
    public List<Book> getBookByAuthor(@RequestParam("author") String author) {
        return bookService.findByAuthor(author);
    }

    @GetMapping("/search/title")
    public List<Book> getBookByTitle(@RequestParam("title") String title) {
        return bookService.findByTitle(title);
    }

    @GetMapping("/search/stock")
    public Object getBookByStock(@RequestParam("min") Optional<Integer> min, @RequestParam("max") Optional<Integer> max) {
        if (min.isPresent() && max.isPresent()) {
            return bookService.findByStockBetween(min.get(), max.get());
        } else if (min.isPresent()) {
            return bookService.findByStockGreaterThanEqual(min.get());
        } else if (max.isPresent()) {
            return bookService.findByStockLessThanEqual(max.get());
        }
        return null;
    }

    @GetMapping("/search/price")
    public Object getBookByPrice(@RequestParam("min") Optional<Double> min, @RequestParam("max") Optional<Double> max) {
        if (min.isPresent() && max.isPresent()) {
            return bookService.findByPriceBetween(min.get(), max.get());
        } else if (min.isPresent()) {
            return bookService.findByPriceGreaterThanEqual(min.get());
        } else if (max.isPresent()) {
            return bookService.findByPriceLessThanEqual(max.get());
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
