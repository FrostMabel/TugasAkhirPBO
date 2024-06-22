package com.tugasgui.main.tugasgui;

import book.Book;
import data.Student;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibrarySystemGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library System");

        LibrarySystem.initializeData();

        // Main menu
        VBox mainMenu = new VBox(10);
        mainMenu.setPadding(new Insets(10));

        Label mainLabel = new Label("Welcome, Please Login");
        Button studentLoginButton = new Button("Login as Student");
        Button adminLoginButton = new Button("Login as Admin");
        Button exitButton = new Button("Exit");

        mainMenu.getChildren().addAll(mainLabel, studentLoginButton, adminLoginButton, exitButton);

        Scene mainScene = new Scene(mainMenu, 1000, 500);
        primaryStage.setScene(mainScene);

        // Student login
        studentLoginButton.setOnAction(e -> showStudentLogin(primaryStage));

        // Admin login
        adminLoginButton.setOnAction(e -> showAdminLogin(primaryStage));

        // Exit application
        exitButton.setOnAction(e -> {
            LibrarySystem.saveData();
            primaryStage.close();
        });

        primaryStage.show();
    }

    private void showStudentLogin(Stage primaryStage) {
        VBox studentLogin = new VBox(10);
        studentLogin.setPadding(new Insets(10));

        Label nimLabel = new Label("Enter your NIM:");
        TextField nimField = new TextField();
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        studentLogin.getChildren().addAll(nimLabel, nimField, loginButton, backButton);

        Scene studentLoginScene = new Scene(studentLogin, 300, 200);
        primaryStage.setScene(studentLoginScene);

        loginButton.setOnAction(e -> {
            String nim = nimField.getText();
            if (LibrarySystem.checkNim(nim)) {
                Student student = LibrarySystem.getStudentByNim(nim);
                showStudentMenu(primaryStage, student);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid NIM. Please try again.");
            }
        });

        backButton.setOnAction(e -> primaryStage.setScene(primaryStage.getScene()));
    }

    private void showAdminLogin(Stage primaryStage) {
        VBox adminLogin = new VBox(10);
        adminLogin.setPadding(new Insets(10));

        Label userLabel = new Label("Enter admin username:");
        TextField userField = new TextField();
        Label passLabel = new Label("Enter admin password:");
        PasswordField passField = new PasswordField();
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        adminLogin.getChildren().addAll(userLabel, userField, passLabel, passField, loginButton, backButton);

        Scene adminLoginScene = new Scene(adminLogin, 300, 200);
        primaryStage.setScene(adminLoginScene);

        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();
            if (LibrarySystem.authenticateAdmin(username, password)) {
                showAdminMenu(primaryStage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials. Please try again.");
            }
        });

        backButton.setOnAction(e -> primaryStage.setScene(primaryStage.getScene()));

        backButton.setOnAction(actionEvent -> {
            primaryStage.close();
            start(primaryStage);
        });
    }

    private void showStudentMenu(Stage primaryStage, Student student) {
        VBox studentMenu = new VBox(10);
        studentMenu.setPadding(new Insets(10));

        Label menuLabel = new Label("=== Student Menu ===");
        Button viewBorrowedButton = new Button("View Borrowed Books");
        Button borrowBookButton = new Button("Borrow Book");
        Button returnBookButton = new Button("Return Book");
        Button logoutButton = new Button("Logout");

        studentMenu.getChildren().addAll(menuLabel, viewBorrowedButton, borrowBookButton, returnBookButton, logoutButton);

        Scene studentMenuScene = new Scene(studentMenu, 300, 200);
        primaryStage.setScene(studentMenuScene);

        viewBorrowedButton.setOnAction(e -> showBorrowedBooks(primaryStage, student));

        borrowBookButton.setOnAction(e -> borrowBook(primaryStage, student));

        returnBookButton.setOnAction(e -> returnBook(primaryStage, student));

        logoutButton.setOnAction(e -> primaryStage.setScene(primaryStage.getScene()));

        logoutButton.setOnAction(actionEvent -> {
            primaryStage.close();
            start(primaryStage);
        });
    }

    private void showAdminMenu(Stage primaryStage) {
        VBox adminMenu = new VBox(10);
        adminMenu.setPadding(new Insets(10));

        Label menuLabel = new Label("Admin Menu");
        Button addStudentButton = new Button("Add Student");
        Button viewStudentsButton = new Button("View Students");
        Button addBookButton = new Button("Add Book");
        Button viewBooksButton = new Button("View Books");
        Button logoutButton = new Button("Logout");

        adminMenu.getChildren().addAll(menuLabel, addStudentButton, viewStudentsButton, addBookButton, viewBooksButton, logoutButton);

        Scene adminMenuScene = new Scene(adminMenu, 300, 200);
        primaryStage.setScene(adminMenuScene);

        addStudentButton.setOnAction(e -> addStudent(primaryStage));

        viewStudentsButton.setOnAction(e -> viewStudents(primaryStage));

        addBookButton.setOnAction(e -> addBook(primaryStage));

        viewBooksButton.setOnAction(e -> viewBooks(primaryStage));

        logoutButton.setOnAction(e -> primaryStage.setScene(primaryStage.getScene()));

        logoutButton.setOnAction(actionEvent -> {
            primaryStage.close();
            start(primaryStage);
        });

    }

    private void showBorrowedBooks(Stage primaryStage, Student student) {
        VBox borrowedBooksView = new VBox(10);
        borrowedBooksView.setPadding(new Insets(10));

        Label borrowedBooksLabel = new Label("Borrowed Books:");
        ListView<String> borrowedBooksList = new ListView<>();
        for (Book book : student.getBorrowedBooks()) {
            borrowedBooksList.getItems().add(book.getTitle());
        }

        Button backButton = new Button("Back");

        borrowedBooksView.getChildren().addAll(borrowedBooksLabel, borrowedBooksList, backButton);

        Scene borrowedBooksScene = new Scene(borrowedBooksView, 300, 200);
        primaryStage.setScene(borrowedBooksScene);

        backButton.setOnAction(e -> showStudentMenu(primaryStage, student));
    }

    private void borrowBook(Stage primaryStage, Student student) {
        VBox borrowBookView = new VBox(10);
        borrowBookView.setPadding(new Insets(10));

        Label booksLabel = new Label("Available Books:");
        ListView<String> booksList = new ListView<>();
        for (Book book : LibrarySystem.getAvailableBooks()) {
            booksList.getItems().add(book.getTitle());
        }

        TextField loanDurationField = new TextField();
        loanDurationField.setPromptText("Loan duration (days)");

        Button borrowButton = new Button("Borrow");
        Button backButton = new Button("Back");

        borrowBookView.getChildren().addAll(booksLabel, booksList, loanDurationField, borrowButton, backButton);

        Scene borrowBookScene = new Scene(borrowBookView, 300, 300);
        primaryStage.setScene(borrowBookScene);

        borrowButton.setOnAction(e -> {
            int selectedIndex = booksList.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1 && !loanDurationField.getText().isEmpty()) {
                Book selectedBook = LibrarySystem.getAvailableBooks().get(selectedIndex);
                int loanDuration = Integer.parseInt(loanDurationField.getText());
                selectedBook.setLoanDuration(loanDuration);
                student.borrowBook(selectedBook);
                selectedBook.setStock(selectedBook.getStock() - 1);
                LibrarySystem.saveData(); // Save data
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book borrowed successfully!");
                showStudentMenu(primaryStage, student);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a book and enter a loan duration.");
            }
        });

        backButton.setOnAction(e -> showStudentMenu(primaryStage, student));
    }

    private void returnBook(Stage primaryStage, Student student) {
        VBox returnBookView = new VBox(10);
        returnBookView.setPadding(new Insets(10));

        Label borrowedBooksLabel = new Label("Borrowed Books:");
        ListView<String> borrowedBooksList = new ListView<>();
        for (Book book : student.getBorrowedBooks()) {
            borrowedBooksList.getItems().add(book.getTitle());
        }

        Button returnButton = new Button("Return");
        Button backButton = new Button("Back");

        returnBookView.getChildren().addAll(borrowedBooksLabel, borrowedBooksList, returnButton, backButton);

        Scene returnBookScene = new Scene(returnBookView, 300, 300);
        primaryStage.setScene(returnBookScene);

        returnButton.setOnAction(e -> {
            int selectedIndex = borrowedBooksList.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                Book selectedBook = student.getBorrowedBooks().get(selectedIndex);
                student.returnBook(selectedBook);
                LibrarySystem.saveData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book returned successfully!");
                showStudentMenu(primaryStage, student);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a book to return.");
            }
        });

        backButton.setOnAction(e -> showStudentMenu(primaryStage, student));
    }

    private void addStudent(Stage primaryStage) {
        VBox addStudentView = new VBox(10);
        addStudentView.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField nimField = new TextField();
        nimField.setPromptText("NIM");
        TextField facultyField = new TextField();
        facultyField.setPromptText("Faculty");
        TextField programField = new TextField();
        programField.setPromptText("Program");

        Button addButton = new Button("Add");
        Button backButton = new Button("Back");

        addStudentView.getChildren().addAll(nameField, nimField, facultyField, programField, addButton, backButton);

        Scene addStudentScene = new Scene(addStudentView, 300, 300);
        primaryStage.setScene(addStudentScene);

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String nim = nimField.getText();
            String faculty = facultyField.getText();
            String program = programField.getText();

            if (!name.isEmpty() && !nim.isEmpty() && !faculty.isEmpty() && !program.isEmpty()) {
                LibrarySystem.admin.addStudent(name, nim, faculty, program);
                LibrarySystem.saveData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully!");
                showAdminMenu(primaryStage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill out all fields.");
            }
        });

        backButton.setOnAction(e -> showAdminMenu(primaryStage));
    }

    private void viewStudents(Stage primaryStage) {
        VBox viewStudentsView = new VBox(10);
        viewStudentsView.setPadding(new Insets(10));

        Label studentsLabel = new Label("Students:");
        ListView<String> studentsList = new ListView<>();
        for (Student student : LibrarySystem.studentList) {
            studentsList.getItems().add(student.getName() + " (" + student.getNim() + ")");
        }

        Button backButton = new Button("Back");

        viewStudentsView.getChildren().addAll(studentsLabel, studentsList, backButton);

        Scene viewStudentsScene = new Scene(viewStudentsView, 300, 300);
        primaryStage.setScene(viewStudentsScene);

        backButton.setOnAction(e -> showAdminMenu(primaryStage));
    }

    private void addBook(Stage primaryStage) {
        VBox addBookView = new VBox(10);
        addBookView.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");
        TextField stockField = new TextField();
        stockField.setPromptText("Stock");

        Button addButton = new Button("Add");
        Button backButton = new Button("Back");

        addBookView.getChildren().addAll(idField, titleField, authorField, categoryField, stockField, addButton, backButton);

        Scene addBookScene = new Scene(addBookView, 300, 300);
        primaryStage.setScene(addBookScene);

        addButton.setOnAction(e -> {
            String id = idField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();
            int stock;
            try {
                stock = Integer.parseInt(stockField.getText());
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid stock number.");
                return;
            }

            if (!id.isEmpty() && !title.isEmpty() && !author.isEmpty() && !category.isEmpty()) {
                LibrarySystem.admin.addBook(id, title, author, category, stock);
                LibrarySystem.saveData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
                showAdminMenu(primaryStage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill out all fields.");
            }
        });

        backButton.setOnAction(e -> showAdminMenu(primaryStage));
    }

    private void viewBooks(Stage primaryStage) {
        VBox viewBooksView = new VBox(10);
        viewBooksView.setPadding(new Insets(10));

        Label booksLabel = new Label("Books:");
        ListView<String> booksList = new ListView<>();
        for (Book book : LibrarySystem.bookList) {
            booksList.getItems().add(book.getTitle() + " (" + book.getStock() + " available)");
        }

        Button backButton = new Button("Back");

        viewBooksView.getChildren().addAll(booksLabel, booksList, backButton);

        Scene viewBooksScene = new Scene(viewBooksView, 300, 300);
        primaryStage.setScene(viewBooksScene);

        backButton.setOnAction(e -> showAdminMenu(primaryStage));
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
