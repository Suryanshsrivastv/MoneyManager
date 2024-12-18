package com.example.moneymanager.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;

public class ExpenseViewHolder extends RecyclerView.ViewHolder {

    TextView categoryText, amountText, dateText;

    public ExpenseViewHolder(View itemView) {
        super(itemView);

        // Initialize the TextViews correctly
        categoryText = itemView.findViewById(R.id.categoryText);
        amountText = itemView.findViewById(R.id.amountText);
        dateText = itemView.findViewById(R.id.dateText);
    }
}
