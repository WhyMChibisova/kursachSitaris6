package com.example.kursach.services;

import com.example.kursach.models.Book;
import com.example.kursach.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        ArrayList<Book> result = new ArrayList<>();
        book.ifPresent(result::add);
        return result;
    }

    public void editBook(int id, String name, String author, String language, String level, String description) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setName(name);
        book.setAuthor(author);
        book.setLanguage(language);
        book.setLevel(level);
        book.setDescription(description);
        bookRepository.save(book);
    }

    public void deleteBook(int id) {
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
    }

    public boolean existsById(int id) {
        return bookRepository.existsById(id);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }
}
