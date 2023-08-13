package com.example.bookstoredemo.repository;

import com.example.bookstoredemo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);

    List<Book> findByPriceBetween(Double min, Double max);

    List<Book> findByPriceLessThanEqual(Double ceiling);

    List<Book> findByPriceGreaterThanEqual(Double floor);

    List<Book> findByStockGreaterThanEqual(int floor);

    List<Book> findByStockLessThanEqual(int ceiling);

    List<Book> findByStockBetween(int min, int max);

}
