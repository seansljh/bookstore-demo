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
    public Book getBookById(Long isbn) {
        logger.info(String.format("Querying DB for book_isbn %s", isbn));
        Optional<Book> book = bookRepository.findById(isbn);
        if (book.isPresent()) {
            logger.info(String.format("Book with book_isbn %s retrieved successfully", isbn));
            return book.get();
        }
        logger.error(String.format("No book found in inventory for book_isbn %s", isbn));
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No book found in inventory for given isbn"
        );
    }
    @Override
    public Book updateBookStockById(Long isbn, int stock) {
        if (stock < 0) {
            logger.info(String.format("Stock is negative %s, abort update", stock));
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Given stock number is negative!"
            );
        }
        logger.info(String.format("Attempting to update the stock of %s to %s", isbn, stock));
        Optional<Book> book1 = bookRepository.findById(isbn);
        if (book1.isPresent()) {
            logger.info(String.format("Book with book_isbn %s retrieved successfully", isbn));
            Book curBook = book1.get();

            logger.info(String.format("Attempting to update stock to %s", stock));
            curBook.setStock(stock);
            return bookRepository.save(curBook);
        } else {
            logger.error(String.format("No book found in inventory for book_isbn %s", isbn));
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No book found in inventory for given isbn"
            );
        }
    }

    @Override
    public Integer getBookStockById(Long isbn) {
        logger.info(String.format("Attempting to get the stock of %s", isbn));
        Optional<Book> book1 = bookRepository.findById(isbn);
        if (book1.isPresent()) {
            logger.info(String.format("Book with book_isbn %s retrieved successfully", isbn));
            Book curBook = book1.get();
            return curBook.getStock();
        } else {
            logger.error(String.format("No book found in inventory for book_isbn %s", isbn));
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No book found in inventory for given isbn"
            );
        }
    }

    @Override
    public String removeBook(Long isbn) {
        if (bookRepository.findById(isbn).isPresent()) {
            logger.info(String.format("Attempting to delete %s", isbn));
            bookRepository.deleteById(isbn);
            return "Book deleted successfully";
        } else {
            logger.error(String.format("No book found in inventory for book_isbn %s", isbn));
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No book found in inventory for given isbn"
            );
        }
    }

    @Override
    public List<Book> findByAuthor(String author){
        logger.info(String.format("Querying books written by %s", author));
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findByTitle(String title) {
        logger.info(String.format("Querying books with title %s", title));
        return bookRepository.findByTitle(title);
    }
    @Override
    public List<Book> findByPriceBetween(Double min, Double max){
        logger.info(String.format("Querying books with prices between %s and %s", min, max));
        return bookRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Book> findByPriceLessThanEqual(Double ceiling){
        logger.info(String.format("Querying books with prices less than %s", ceiling));
        return bookRepository.findByPriceLessThanEqual(ceiling);
    }

    @Override
    public List<Book> findByPriceGreaterThanEqual(Double floor){
        logger.info(String.format("Querying books with prices more than %s", floor));
        return bookRepository.findByPriceGreaterThanEqual(floor);
    }

    @Override
    public List<Book> findByStockGreaterThanEqual(int floor){
        logger.info(String.format("Querying books with stock more than %s", floor));
        return bookRepository.findByStockGreaterThanEqual(floor);
    }

    @Override
    public List<Book> findByStockLessThanEqual(int ceiling){
        logger.info(String.format("Querying books with stock less than %s", ceiling));
        return bookRepository.findByStockLessThanEqual(ceiling);
    }

    @Override
    public List<Book> findByStockBetween(int min, int max){
        logger.info(String.format("Querying books with stock between %s and %s", min, max));
        return bookRepository.findByStockBetween(min, max);
    }
}
