package com.example.moneymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.models.Expense;

import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private Context context;
    private List<Expense> expenseList;

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
        this.context = context;
        this.expenseList = expenses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
        }

        // Get the data item for this position
        Expense expense = getItem(position);

        // Lookup the view for the category, amount, and date
        TextView categoryText = convertView.findViewById(R.id.categoryText);
        TextView amountText = convertView.findViewById(R.id.amountText);
        TextView dateText = convertView.findViewById(R.id.dateText);

        // Populate the data into the template view using the data object
        categoryText.setText(expense.getCategory());
        amountText.setText(String.valueOf(expense.getAmount()));
        dateText.setText(expense.getDate());

        // Return the completed view to render on screen
        return convertView;
    }
}