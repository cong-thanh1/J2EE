package com.example.lab02.Service;

import com.example.lab02.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    // Lấy tất cả sách
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    // Lấy sách theo ID
    public Optional<Book> getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    // Thêm sách mới
    public Book addBook(Book book) {
        book.setId(idGenerator.getAndIncrement());
        books.add(book);
        return book;
    }

    // Cập nhật sách
    public Optional<Book> updateBook(int id, Book updatedBook) {
        Optional<Book> existingBook = getBookById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            return Optional.of(book);
        }
        return Optional.empty();
    }

    // Xóa sách
    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }
}

