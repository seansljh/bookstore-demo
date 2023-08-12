package com.example.bookstoredemo.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.bookstoredemo.entities.Book;
import com.example.bookstoredemo.repository.BookRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final Logger logger;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(Logger logger){
        this.logger = logger;
    }

    @Override
    public Book addBook (Book book){
        logger.info(String.format("Adding book %s into the Inventory", book.getIsbn()));
        return bookRepository.save(book);
    }

    @Override
    public List<Book> fetchAllBooks() {
        logger.info("Fetching all books in the Inventory");
        return bookRepository.findAll(); // need to do a query on stock
    }
    @Override
    public Book getBookById(Long book_isbn) {
        logger.info(String.format("Querying DB for book_isbn %s", book_isbn));
        Optional<Book> book = bookRepository.findById(book_isbn);
        if (book.isPresent()) {
            logger.info(String.format("Book with book_isbn %s retrieved successfully", book_isbn));
            return book.get();
        }
        logger.error(String.format("No book found in inventory for book_isbn %s", book_isbn));
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No book found in inventory for given isbn"
        );
    }
    @Override
    public Book updateBookStockById(Long book_isbn, int stock) {
        if (stock < 0) {
            logger.info(String.format("Stock is negative %s, abort update", stock));
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Given stock number is negative!"
            );
        }
        logger.info(String.format("Attempting to update the stock of %s to %s", book_isbn, stock));
        Optional<Book> book1 = bookRepository.findById(book_isbn);
        if (book1.isPresent()) {
            logger.info(String.format("Book with book_isbn %s retrieved successfully", book_isbn));
            Book curBook = book1.get();

            logger.info(String.format("Attempting to update stock to %s", stock));
            curBook.setStock(stock);
            return bookRepository.save(curBook);
        } else {
            logger.error(String.format("No book found in inventory for book_isbn %s", book_isbn));
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No book found in inventory for given isbn"
            );
        }
    }

    @Override
    public Integer getBookStockById(Long book_isbn) {
        logger.info(String.format("Attempting to get the stock of %s", book_isbn));
        Optional<Book> book1 = bookRepository.findById(book_isbn);
        if (book1.isPresent()) {
            logger.info(String.format("Book with book_isbn %s retrieved successfully", book_isbn));
            Book curBook = book1.get();
            return curBook.getStock();
        } else {
            logger.error(String.format("No book found in inventory for book_isbn %s", book_isbn));
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No book found in inventory for given isbn"
            );
        }
    }

    @Override
    public String removeBook(Long book_isbn) {
        if (bookRepository.findById(book_isbn).isPresent()) {
            logger.info(String.format("Attempting to delete %s", book_isbn));
            bookRepository.deleteById(book_isbn);
            return "Book deleted successfully";
        } else {
            logger.error(String.format("No book found in inventory for book_isbn %s", book_isbn));
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No book found in inventory for given isbn"
            );
        }
    }

    @Override
    public List<Book> findByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
}
