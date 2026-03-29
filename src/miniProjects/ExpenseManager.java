package miniProjects;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void removeExpense(int index) {
        if (isValidIndex(index)) {
            expenses.remove(index);
        }
    }

    public void updateExpense(int index, Expense expense) {
        if (isValidIndex(index)) {
            expenses.set(index, expense);
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < expenses.size();
    }

    public List<Expense> getExpenses() {
        return new ArrayList<>(expenses);
    }

    public double getTotalExpenses() {
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            expenses = (List<Expense>) ois.readObject();
        } catch (FileNotFoundException e) {
            // No existing file, start fresh
            expenses = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            expenses = new ArrayList<>();
        }
    }
}