package com.example.bookstoredemo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Book() {

    }

    public Book(String book_title, String author, Long isbn, Double price, int stock) {
        this.book_title = book_title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
    }

    @Id
    private long isbn;

    private String book_title;

    private String author;

    private Double price;

    private int stock;

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", book_title='" + book_title + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                ", stock='" + stock + '\'' +
                '}';
    }
}
