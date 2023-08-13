package com.example.bookstoredemo.service;

import com.example.bookstoredemo.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public interface BookService {

    Book addBook (Book book);

    Page<Book> fetchAllBooks(Pageable page);

    Book getBookById(Long book_isbn);

    Book updateBookStockById(Long book_isbn, int stock);

    Object getBookStockById(Long book_isbn);

    String removeBook(Long book_isbn);

    Page<Book> findByAuthor(String author, Pageable page);

    Page<Book> findByTitle(String title, Pageable page);

    Page<Book> findByPriceBetween(Double min, Double max, Pageable page);

    Page<Book> findByPriceLessThanEqual(Double ceiling, Pageable page);

    Page<Book> findByPriceGreaterThanEqual(Double floor, Pageable page);

    Page<Book> findByStockGreaterThanEqual(int floor, Pageable page);

    Page<Book> findByStockLessThanEqual(int floor, Pageable page);

    Page<Book> findByStockBetween(int min, int max, Pageable page);
}
