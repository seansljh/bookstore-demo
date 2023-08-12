package com.example.bookstoredemo.service;

import com.example.bookstoredemo.entities.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    Book addBook (Book book);

    List<Book> fetchAllBooks();

    Book getBookById(Long book_isbn);

    Book updateBookStockById(Long book_isbn, int stock);

    Object getBookStockById(Long book_isbn);

    String removeBook(Long book_isbn);

    List<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);
}