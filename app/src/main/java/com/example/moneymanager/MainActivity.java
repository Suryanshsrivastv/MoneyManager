package com.example.moneymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.adapters.ExpenseAdapter;
import com.example.moneymanager.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView welcomeText;
    private ListView expenseListView;
    private List<Expense> expenseList;
    private ExpenseAdapter expenseAdapter;
    private UserDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeText = findViewById(R.id.welcomeText);
        expenseListView = findViewById(R.id.expenseListView);
        Button addExpenseButton = findViewById(R.id.addExpenseButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        dbHelper = new UserDbHelper(this);

        // Get logged-in username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        if (username != null) {
            welcomeText.setText("Welcome, " + username);

            // Fetch expenses from SQLite
            expenseList = fetchExpenses();
            expenseAdapter = new ExpenseAdapter(this, expenseList);
            expenseListView.setAdapter(expenseAdapter);

            // Add Expense Button
            addExpenseButton.setOnClickListener(v -> {
                // Redirect to AddExpenseActivity to add a new expense
                startActivity(new Intent(MainActivity.this, AddExpenseActivity.class));
            });

            // Logout Button
            logoutButton.setOnClickListener(v -> {
                // Clear SharedPreferences (logout)
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.apply();

                // Redirect to LoginActivity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            });
        } else {
            // If no user is logged in, redirect to login
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    // Fetch expenses from SQLite database
    private List<Expense> fetchExpenses() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Expense> expenses = new ArrayList<>();

        // Query the database to get all expenses
        Cursor cursor = db.query("expenses", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String category = cursor.getString(cursor.getColumnIndex("category"));
                double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                expenses.add(new Expense(category, amount, date));
            }
            cursor.close();
        }

        return expenses;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to MainActivity
        expenseList.clear();
        expenseList.addAll(fetchExpenses());
        expenseAdapter.notifyDataSetChanged();
    }
}