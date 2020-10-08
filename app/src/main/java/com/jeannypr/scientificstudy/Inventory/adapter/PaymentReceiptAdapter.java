package com.jeannypr.scientificstudy.Inventory.adapter;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeannypr.scientificstudy.Inventory.model.PaymentReceiptSummaryModel;
import com.jeannypr.scientificstudy.R;

import java.util.List;

public class PaymentReceiptAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<PaymentReceiptSummaryModel> purchases;
    PaymentReceiptAdapter.TransactionDetail listner;

    public PaymentReceiptAdapter(Context context, List<PaymentReceiptSummaryModel> collections, PaymentReceiptAdapter.TransactionDetail listner) {
        super();
        mContext = context;
        purchases = collections;
        this.listner = listner;
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_purchase_summary, parent, false);
        return new MyViewHolder(view);
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PaymentReceiptSummaryModel message = (PaymentReceiptSummaryModel) purchases.get(position);
        ((MyViewHolder) holder).bind(message, listner);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtPurchase, txtTotalAmount, txtPurchaseDate;
        ConstraintLayout summaryRow;

        MyViewHolder(View itemView) {
            super(itemView);

            summaryRow = itemView.findViewById(R.id.summaryRow);
            txtPurchase = itemView.findViewById(R.id.purchase);
            txtTotalAmount = itemView.findViewById(R.id.totalAmount);
            txtPurchaseDate = itemView.findViewById(R.id.purchaseDate);
        }

        void bind(final PaymentReceiptSummaryModel purchase, final PaymentReceiptAdapter.TransactionDetail listner) {
            summaryRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.GetTransactionDetail(purchase);
                }
            });
            txtPurchase.setText(purchase.getName());
            txtTotalAmount.setText(String.valueOf(purchase.Amount));
            txtPurchaseDate.setText(purchase.getPaymentDate());
        }
    }


    public interface TransactionDetail {
        void GetTransactionDetail(PaymentReceiptSummaryModel purchase);
    }
}


