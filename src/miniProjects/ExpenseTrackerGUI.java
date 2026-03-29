package miniProjects;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.*;
import java.util.List;

public class ExpenseTrackerGUI extends JFrame {
    private JTextField txtAmount;
    private JTextField txtDescription;
    private DefaultTableModel tableModel;
    private JTable expenseTable;
    private ExpenseManager expenseManager;

    public ExpenseTrackerGUI() {
        expenseManager = new ExpenseManager();
        expenseManager.loadFromFile("expenses.ser"); // Load saved expenses if exists

        setTitle("💰 Expense Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Create main panel with better layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 245, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Expense Tracker Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create input panel with GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Add or Edit Expense",
                3, 2,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 130, 180)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Amount label and field
        JLabel amountLabel = new JLabel("Amount ($):");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(amountLabel, gbc);

        txtAmount = new JTextField(12);
        txtAmount.setFont(new Font("Arial", Font.PLAIN, 12));
        txtAmount.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        gbc.gridx = 1;
        gbc.weightx = 0.2;
        inputPanel.add(txtAmount, gbc);

        // Description label and field
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 2;
        gbc.weightx = 0;
        inputPanel.add(descriptionLabel, gbc);

        txtDescription = new JTextField(18);
        txtDescription.setFont(new Font("Arial", Font.PLAIN, 12));
        txtDescription.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        gbc.gridx = 3;
        gbc.weightx = 0.3;
        inputPanel.add(txtDescription, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        buttonPanel.setBackground(new Color(255, 255, 255));

        JButton btnAdd = createStyledButton("Add", new Color(76, 175, 80));
        JButton btnEdit = createStyledButton("Edit", new Color(255, 193, 7));
        JButton btnDelete = createStyledButton("Delete", new Color(244, 67, 54));
        JButton btnSummary = createStyledButton("Summary", new Color(33, 150, 243));

        btnAdd.setToolTipText("Add a new expense to the tracker");
        btnEdit.setToolTipText("Edit the selected expense");
        btnDelete.setToolTipText("Delete the selected expense");
        btnSummary.setToolTipText("View total expenses summary");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSummary);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1;
        inputPanel.add(buttonPanel, gbc);

        // Create table with styling
        tableModel = new DefaultTableModel(new Object[]{"Amount", "Description"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        expenseTable = new JTable(tableModel);
        expenseTable.setFont(new Font("Arial", Font.PLAIN, 12));
        expenseTable.setRowHeight(25);
        expenseTable.setGridColor(new Color(0, 0, 0));

        // Style table header
        expenseTable.getTableHeader().setBackground(new Color(70, 130, 180));
        expenseTable.getTableHeader().setForeground(Color.WHITE);
        expenseTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Add alternating row colors
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(240, 248, 255));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                } else {
                    c.setBackground(new Color(100, 150, 200));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        };

        for (int i = 0; i < expenseTable.getColumnCount(); i++) {
            expenseTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane tableScroll = new JScrollPane(expenseTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Expense List",
                3, 2,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 130, 180)));

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(tableScroll, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            addExpense();
            expenseManager.saveToFile("expenses.ser"); // Save after add
        });
        btnEdit.addActionListener(e -> {
            editExpense();
            expenseManager.saveToFile("expenses.ser"); // Save after edit
        });
        btnDelete.addActionListener(e -> {
            deleteExpense();
            expenseManager.saveToFile("expenses.ser"); // Save after delete
        });
        btnSummary.addActionListener(e -> showSummary());

        refreshTable();
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(85, 32));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(darkenColor(bgColor));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    private Color darkenColor(Color color) {
        return new Color(
                Math.max(0, color.getRed() - 30),
                Math.max(0, color.getGreen() - 30),
                Math.max(0, color.getBlue() - 30)
        );
    }

    private void addExpense() {
        String amountStr = txtAmount.getText().trim();
        String description = txtDescription.getText().trim();

        if (amountStr.isEmpty() || description.isEmpty()) {
            showErrorDialog("Please provide both amount and description.",
                    "Input Error");
            return;
        }
        try {
            double amount = Double.parseDouble(amountStr);
            expenseManager.addExpense(new Expense(amount, description));
            refreshTable();
            txtAmount.setText("");
            txtDescription.setText("");
            showSuccessMessage("Expense added successfully!");
        } catch (NumberFormatException ex) {
            showErrorDialog("Please enter a valid amount (number).",
                    "Input Error");
        }
    }


    private void editExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorDialog("Please select an expense row to edit.",
                    "Edit Error");
            return;
        }
        String amountStr = txtAmount.getText().trim();
        String description = txtDescription.getText().trim();

        if (amountStr.isEmpty() || description.isEmpty()) {
            showErrorDialog("Please provide both amount and description.",
                    "Input Error");
            return;
        }
        try {
            double amount = Double.parseDouble(amountStr);
            expenseManager.updateExpense(selectedRow, new Expense(amount, description));
            refreshTable();
            txtAmount.setText("");
            txtDescription.setText("");
            showSuccessMessage("Expense updated successfully!");
        } catch (NumberFormatException ex) {
            showErrorDialog("Please enter a valid amount (number).",
                    "Input Error");
        }
    }

    private void deleteExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorDialog("Please select an expense row to delete.",
                    "Delete Error");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this expense?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            expenseManager.removeExpense(selectedRow);
            refreshTable();
            showSuccessMessage("Expense deleted successfully!");
        }
    }

    private void showSummary() {
        double totalExpenses = expenseManager.getTotalExpenses();
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBackground(new Color(240, 248, 255));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Expense Summary");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel totalLabel = new JLabel("Total Expenses: $" + String.format("%.2f", totalExpenses));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(244, 67, 54));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        summaryPanel.add(titleLabel);
        summaryPanel.add(Box.createVerticalStrut(10));
        summaryPanel.add(totalLabel);

        JOptionPane.showMessageDialog(this, summaryPanel, "Summary",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Expense> expenses = expenseManager.getExpenses();
        for (Expense expense : expenses) {
            tableModel.addRow(new Object[]{expense.getAmount(), expense.getDescription()});
        }
    }

    private void showErrorDialog(String message, String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel label = new JLabel("<html><b>\u26a0 " + message + "</b></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(new Color(200, 50, 50));
        panel.add(label);

        JOptionPane.showMessageDialog(this, panel, title, JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 255, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel label = new JLabel("<html><b>\u2713 " + message + "</b></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(new Color(76, 175, 80));
        panel.add(label);

        JOptionPane.showMessageDialog(this, panel, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            // fallback to default look and feel
        }

        SwingUtilities.invokeLater(ExpenseTrackerGUI::new);
    }

}