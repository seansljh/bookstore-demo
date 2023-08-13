package com.example.bookstoredemo.repository;

import com.example.bookstoredemo.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByAuthor(String author, Pageable page);

    Page<Book> findByTitle(String title, Pageable page);

    Page<Book> findByPriceBetween(Double min, Double max, Pageable page);

    Page<Book> findByPriceLessThanEqual(Double ceiling, Pageable page);

    Page<Book> findByPriceGreaterThanEqual(Double floor, Pageable page);

    Page<Book> findByStockGreaterThanEqual(int floor, Pageable page);

    Page<Book> findByStockLessThanEqual(int ceiling, Pageable page);

    Page<Book> findByStockBetween(int min, int max, Pageable page);

}
