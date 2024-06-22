package com.tugasgui.main.tugasgui;

import book.Book;
import data.Admin;
import data.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibrarySystem {
    public static List<Student> studentList = new ArrayList<>();
    public static List<Book> bookList = new ArrayList<>();
    public static Admin admin;

    public static void initializeData() {
        // Initialize some dummy data for testing purposes
        admin = new Admin("admin", "admin123");

        // Load saved data if available
        loadData();

        // If there's no saved data, add sample data
        if (studentList.isEmpty() && bookList.isEmpty()) {
            // Add sample students
            studentList.add(new Student("Althaf", "202310370311062", "Engineering", "Computer Science"));
            studentList.add(new Student("Frost", "1", "Arts", "History"));

            // Add sample books
            bookList.add(new Book("1", "Effective Java", "Joshua Bloch", "Programming", 10));
            bookList.add(new Book("2", "Clean Code", "Robert C. Martin", "Programming", 8));
            bookList.add(new Book("3", "Design Patterns", "Erich Gamma", "Software Engineering", 5));
        }
    }

    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library_data.dat"))) {
            oos.writeObject(studentList);
            oos.writeObject(bookList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library_data.dat"))) {
            studentList = (List<Student>) ois.readObject();
            bookList = (List<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If file not found or other error, we ignore and use default data
            e.printStackTrace();
        }
    }

    public static boolean checkNim(String nim) {
        for (Student student : studentList) {
            if (student.getNim().equals(nim)) {
                return true;
            }
        }
        return false;
    }

    public static Student getStudentByNim(String nim) {
        for (Student student : studentList) {
            if (student.getNim().equals(nim)) {
                return student;
            }
        }
        return null;
    }

    public static boolean authenticateAdmin(String username, String password) {
        return admin.getUsername().equals(username) && admin.getPassword().equals(password);
    }

    public static List<Book> getAvailableBooks() {
        return bookList;
    }
}
