# Expense Tracker Application - Project Report

**Student:** Suryadeep Kundu  
**Course:** PCCCS495 – ADVANCE PROGRAMMING LAB Term II Project  
**Date:** 29th March 2026

---

## Table of Contents
1. [Executive Summary](#executive-summary)
2. [Project Title](#project-title)
3. [Problem Statement](#problem-statement)
4. [Target Users](#target-users)
5. [Core Features](#core-features)
6. [Object-Oriented Programming Concepts](#object-oriented-programming-concepts)
7. [Architecture Description](#architecture-description)
8. [User Guide](#user-guide)
9. [Technical Implementation](#technical-implementation)
10. [Testing and Validation](#testing-and-validation)
11. [Conclusion](#conclusion)

---

## Executive Summary

The **Expense Tracker Application** is a desktop-based solution designed to help individuals manage and track their personal expenses efficiently. Built using Java Swing, the application provides an intuitive graphical user interface (GUI) that enables users to add, edit, delete, and monitor their spending habits. The application demonstrates strong adherence to Object-Oriented Programming (OOP) principles and includes persistence functionality to save expense records to disk.

---

## Project Title

**💰 Expense Tracker Application - Personal Finance Management Tool**

---

## Problem Statement

Managing personal finances and tracking expenses manually using pen and paper or spreadsheets is inefficient, error-prone, and difficult to maintain. Users often struggle to:
- Keep accurate records of all expenditures
- Calculate total spending across multiple transactions
- Organize expenses in a structured manner
- Retrieve historical expense data reliably

This project addresses these issues by providing a user-friendly desktop application that centralizes expense management, automates calculations, and persists data for future reference. The application eliminates the need for manual record-keeping and provides instant access to financial summaries.

---

## Target Users

The Expense Tracker Application targets the following user groups:

1. **Personal Finance Enthusiasts** - Individuals who want to monitor their daily spending and budget management
2. **Students** - College and university students managing limited budgets and tracking educational expenses
3. **Freelancers/Professionals** - Self-employed individuals tracking business and personal expenses
4. **Budget-Conscious Consumers** - Anyone seeking to understand and control their spending patterns
5. **Small Business Owners** - Entrepreneurs managing petty cash and miscellaneous expenses

The application requires:
- Basic computer literacy
- Java Runtime Environment (JRE) installed
- No technical expertise for operation

---

## Core Features

### 1. **Add Expense**
   - Users can input expense amount (in currency) and a descriptive label
   - Real-time validation ensures valid numeric input for amounts
   - Added expenses are immediately displayed in the expense list

### 2. **Edit Existing Expenses**
   - Users can select an expense from the list and modify its amount or description
   - Updates are reflected immediately in the table display
   - Prevents invalid input with error handling

### 3. **Delete Expenses**
   - Users can remove unwanted expense entries from the list
   - Confirmation dialog prevents accidental deletions
   - Deleted expenses are immediately removed from the display

### 4. **View Expense Summary**
   - Displays total sum of all expenses in a modal dialog
   - Provides quick financial overview for users
   - Formatted currency display for readability ($X.XX format)

### 5. **Persistent Storage**
   - Automatically saves all expenses to a serialized file (`expenses.ser`)
   - Loads previously saved expenses on application startup
   - Ensures no data loss between sessions

### 6. **Professional User Interface**
   - Modern, responsive Swing-based GUI with color-coded buttons
   - Alternating row colors in expense table for better readability
   - Organized input panel with labeled fields
   - Helpful tooltips for each action button

---

## Object-Oriented Programming Concepts

### 1. **Abstraction**
   - The `Expense` class abstracts expense data into a simple, focused model
   - The `ExpenseManager` class abstracts complex collection and file operations
   - Users interact with simple methods (addExpense, removeExpense) without knowing implementation details
   - File I/O complexities are hidden behind `saveToFile()` and `loadFromFile()` methods

### 2. **Encapsulation**
   - Private instance variables in `Expense` class (`amount`, `description`)
   - Public getter and setter methods provide controlled access to expense data
   - `ExpenseManager` maintains private `expenses` list with public interface methods
   - Data validation occurs within class methods, protecting data integrity

### 3. **Single Responsibility Principle**
   - `Expense` class: Represents a single expense record with getters/setters
   - `ExpenseManager` class: Handles business logic (add, edit, delete, calculate totals)
   - `ExpenseTrackerGUI` class: Manages user interface and user interactions
   - Clear separation of concerns among the three main classes

### 4. **Exception Handling**
   - NumberFormatException caught when parsing user input for amount validation
   - FileNotFoundException handled gracefully when loading from file (creates new list if file doesn't exist)
   - IOException and ClassNotFoundException handled during deserialization
   - Error dialogs inform users of issues without crashing the application

### 5. **Collections**
   - `ArrayList<Expense>` used for dynamic storage of expense records
   - Efficient collection operations: add, remove, update, iteration
   - Lambda expressions and streams used for calculating total expenses:
     ```java
     expenses.stream()
             .mapToDouble(Expense::getAmount)
             .sum();
     ```

### 6. **Serialization**
   - `Expense` implements `Serializable` interface
   - `ObjectOutputStream` and `ObjectInputStream` for persistence
   - Entire `ArrayList` of expenses serialized to binary file
   - Enables data preservation across application sessions

### 7. **Inheritance and Composition**
   - `ExpenseTrackerGUI` extends `JFrame` for window functionality
   - Composition used throughout: GUI contains `ExpenseManager` and `JTable` components
   - Styled button creation through helper methods demonstrating code reuse

---

## Architecture Description

### System Architecture

The application follows a **three-tier architecture pattern**:

```
┌─────────────────────────────────────────┐
│   Presentation Layer (GUI)              │
│   ├─ ExpenseTrackerGUI                  │
│   ├─ UI Components (JTable, JButtons)   │
│   └─ User Interaction Handling          │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│   Business Logic Layer                  │
│   ├─ ExpenseManager                     │
│   ├─ Collection Management              │
│   ├─ CRUD Operations                    │
│   └─ Total Calculation Logic            │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│   Data Layer                            │
│   ├─ Expense (Model/Entity)             │
│   ├─ File Persistence (expenses.ser)    │
│   └─ Serialization                      │
└─────────────────────────────────────────┘
```

### Component Relationships

| Component | Responsibility | Dependencies |
|-----------|-----------------|--------------|
| **Expense** | Data model for individual expense | Serializable interface |
| **ExpenseManager** | Manages expense collection, I/O | List, Expense, I/O streams |
| **ExpenseTrackerGUI** | Main window, user interactions | Swing, ExpenseManager, Expense |

### Data Flow

1. **User Input** → GUI captures amount and description
2. **Validation** → GUI validates input format
3. **Processing** → ExpenseManager adds/edits/deletes expense
4. **Display** → GUI refreshes table with updated data
5. **Persistence** → ExpenseManager saves to `expenses.ser`
6. **Startup** → Application loads saved expenses from file

---

## User Guide

### Installation and Launch

1. Ensure Java Development Kit (JDK) or Java Runtime Environment (JRE) is installed
2. Navigate to project directory: `term-ii-project-submission-suryadeep-kundu\src`
3. Execute on Windows: `compile.bat` to compile the project
4. Execute: `run.bat` to launch the application

### Using the Application

#### **Adding an Expense**
1. Enter the expense amount (numeric value) in the "Amount ($)" field
2. Enter a descriptive label in the "Description" field (e.g., "Grocery Shopping", "Gas")
3. Click the "Add" button
4. Expense appears in the list below
5. Fields clear automatically for next entry

#### **Editing an Expense**
1. Click on the expense row in the table to select it
2. Modify the amount and/or description in the input fields
3. Click the "Edit" button
4. Updated expense is reflected immediately in the table

#### **Deleting an Expense**
1. Select the expense row you wish to remove
2. Click the "Delete" button
3. Confirm deletion in the popup dialog
4. Expense is permanently removed from the list

#### **Viewing Summary**
1. Click the "Summary" button
2. A dialog appears showing total expenses formatted as currency
3. Close the dialog to continue

#### **Data Persistence**
- All changes are automatically saved to `expenses.ser`
- On next application launch, previous expenses are automatically loaded
- No manual save action required

---

## Technical Implementation

### Technologies Used

- **Language:** Java (JDK 8+)
- **GUI Framework:** Java Swing
- **Collections:** Java ArrayList and Streams API
- **Persistence:** Java Serialization (ObjectInputStream/ObjectOutputStream)
- **Design Patterns:** Model-View-Controller (MVC), Data Access Object (DAO)

### Key Code Implementations

#### Expense Model
```java
public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private double amount;
    private String description;
    // Getters and setters for encapsulation
}
```

#### Total Calculation (Functional Programming)
```java
public double getTotalExpenses() {
    return expenses.stream()
            .mapToDouble(Expense::getAmount)
            .sum();
}
```

#### File Persistence
```java
public void saveToFile(String filename) {
    try (ObjectOutputStream oos = new ObjectOutputStream(
             new FileOutputStream(filename))) {
        oos.writeObject(expenses);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

#### GUI Styling Features
- Color schemes for visual appeal (Steel Blue headers, alternating row colors)
- Hover effects on buttons for better UX
- Bordered panels with titled borders for organization
- Monospace layout using GridBagLayout for responsive design
- Tooltip text for user guidance

---

## Testing and Validation

### Functional Testing Scenarios

| Test Case | Input | Expected Output | Status |
|-----------|-------|-----------------|--------|
| Add valid expense | Amount: 50.00, Description: "Lunch" | Expense added to table | ✓ Pass |
| Add with invalid amount | Amount: "abc", Description: "Test" | Error dialog, no add | ✓ Pass |
| Add with empty field | Amount: "", Description: "Test" | Error dialog | ✓ Pass |
| Edit selected expense | Row selected, new data entered | Expense updated in table | ✓ Pass |
| Edit without selection | No row selected | Error dialog | ✓ Pass |
| Delete with confirmation | Row selected, confirm delete | Expense removed | ✓ Pass |
| Delete without confirmation | Row selected, cancel delete | Expense retained | ✓ Pass |
| View summary | Click Summary button | Total displayed correctly | ✓ Pass |
| Save data | Add expense and close app | Expense loads on restart | ✓ Pass |
| Load existing data | Restart app with saved file | Previous expenses displayed | ✓ Pass |

### Data Validation

- **Amount Field:** Accepts positive numeric values (decimals allowed) with 2 decimal place precision
- **Description Field:** Accepts alphanumeric text with special characters
- **Empty Field Validation:** Prevents incomplete expense entries
- **Index Validation:** Prevents deletion/edit of non-existent indices

### User Experience Testing

- **Responsiveness:** Application responds immediately to user actions
- **Visual Feedback:** Success/error messages clearly communicate operation results
- **Error Recovery:** Application continues functioning after invalid input
- **Data Integrity:** No data loss observed across multiple test sessions

---

## Conclusion

The **Expense Tracker Application** successfully demonstrates the integration of Object-Oriented Programming concepts with practical software design through a fully functional personal finance management tool. The application effectively addresses the problem of manual expense tracking through an intuitive interface, robust business logic, and reliable data persistence.

### Key Achievements

✓ Implemented all core CRUD (Create, Read, Update, Delete) operations  
✓ Demonstrated proper encapsulation and abstraction principles  
✓ Built professional GUI with Swing framework  
✓ Implemented reliable data persistence using serialization  
✓ Applied exception handling throughout the application  
✓ Used modern Java features (Streams API, Lambda expressions)  
✓ Maintained clean separation of concerns across three architectural layers  

### Future Enhancement Opportunities

1. **Expense Categories:** Organize expenses by categories (Food, Transport, Entertainment)
2. **Date Tracking:** Add transaction dates for better expense history
3. **Expense Filtering:** Filter expenses by date range or category
4. **Data Export:** Export expense records to CSV or PDF formats
5. **Charts and Graphs:** Visualize spending patterns with pie/bar charts
6. **Multi-user Support:** Database backend for multiple user profiles
7. **Search Functionality:** Search expenses by keywords
8. **Budget Alerts:** Notify users when spending exceeds budget limits

### Project Statistics

- **Total Lines of Code:** ~400+ (excluding comments and blank lines)
- **Main Classes:** 3 (`Expense`, `ExpenseManager`, `ExpenseTrackerGUI`)
- **OOP Concepts Demonstrated:** 7 (Encapsulation, Abstraction, Collections, Serialization, Exception Handling, Inheritance, Single Responsibility)
- **UI Components:** 10+ (JFrame, JPanel, JTable, JButton, JTextField, JLabel, etc.)
- **Git Commits:** Minimum 10 meaningful commits required

---

**End of Report**
