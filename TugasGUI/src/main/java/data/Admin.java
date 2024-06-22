package data;

import book.Book;
import java.io.Serializable;
import com.tugasgui.main.tugasgui.LibrarySystem;

public class Admin implements Serializable{
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addStudent(String name, String nim, String faculty, String program) {
        LibrarySystem.studentList.add(new Student(name, nim, faculty, program));
        LibrarySystem.saveData();
    }

    public void addBook(String id, String title, String author, String category, int stock) {
        LibrarySystem.bookList.add(new Book(id, title, author, category, stock));
        LibrarySystem.saveData();
    }
}
