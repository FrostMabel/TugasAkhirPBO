package data;

import book.Book;
import com.tugasgui.main.tugasgui.LibrarySystem;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
    private String name;
    private String nim;
    private String faculty;
    private String program;
    private ArrayList<Book> borrowedBooks;

    public Student(String name, String nim, String faculty, String program) {
        this.name = name;
        this.nim = nim;
        this.faculty = faculty;
        this.program = program;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getNim() {
        return nim;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
        LibrarySystem.saveData();
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.setStock(book.getStock() + 1);
        LibrarySystem.saveData();
    }
}
