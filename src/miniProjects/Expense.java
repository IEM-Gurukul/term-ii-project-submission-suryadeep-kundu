package miniProjects;

import java.io.Serializable;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;

    private double amount;
    private String description;

    public Expense(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() { return amount; }
    public String getDescription() { return description; }

    public void setAmount(double amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
}