package book;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
    private String id;
    private String title;
    private String author;
    private String category;
    private int stock;
    private LocalDate dueDate;

    public Book(String id, String title, String author, String category, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.stock = stock;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setLoanDuration(int loanDuration) {
        this.dueDate = LocalDate.now().plusDays(loanDuration);
    }
}
