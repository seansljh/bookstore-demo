package com.example.bookstoredemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bookstoredemo.entities.Book;
import com.example.bookstoredemo.repository.BookRepository;

import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book addBook (Book book){
        return bookRepository.save(book);
    }

    @Override
    public List<Book> fetchAllBooks() {
        return bookRepository.findAll(); // need to do a query on stock
    }
    @Override
    public Book getBookById(Long book_isbn) {
        Optional<Book> book = bookRepository.findById(book_isbn);
        if (book.isPresent()) {
            return book.get();
        }
        return null;
    }
    @Override
    public Book updateBookStockById(Long book_isbn, int stock) {
        Optional<Book> book1 = bookRepository.findById(book_isbn);
        if (book1.isPresent()) {
            Book curBook = book1.get();

            if (stock >= 0) {
                curBook.setStock(stock);
            }
            return bookRepository.save(curBook);
        }
        return null;
    }

    @Override
    public Object getBookStockById(Long book_isbn) {
        Optional<Book> book1 = bookRepository.findById(book_isbn);
        if (book1.isPresent()) {
            Book curBook = book1.get();
            return curBook.getStock();
        }
        return "Book is not in inventory!";
    }

    @Override
    public String removeBook(Long book_isbn) {
        if (bookRepository.findById(book_isbn).isPresent()) {
            bookRepository.deleteById(book_isbn);
            return "Book deleted successfully";
        }
        return "No such book in the inventory";
    }

}
