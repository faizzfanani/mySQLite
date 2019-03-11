package com.kontrakanelite.tugas1sqlite;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Toast;

import com.kontrakanelite.tugas1sqlite.dbHelper.TransactionHelper;

import java.util.ArrayList;
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.CustomViewHolder>{
    //private ArrayList<String> values;

    private LayoutInflater mInflater;
    private ArrayList<TransactionModel> transaction;
    private Context context;
    private TransactionHelper transactionHelper;

    public TransactionAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        transactionHelper = new TransactionHelper(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                               int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.row_transaction, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String rekening = transaction.get(position).getNoRek();
        final int nominal = transaction.get(position).getNominal();
        final String keterangan = transaction.get(position).getKeterangan();

        holder.editRekening.setText(rekening);
        holder.editNominal.setText(String.valueOf(nominal));
        holder.editKeterangan.setText(keterangan);

        holder.btnupdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                transaction.get(position).setNoRek(holder.editRekening.getText().toString());
                transaction.get(position).setNominal(Integer.valueOf(holder.editNominal.getText().toString()));
                transaction.get(position).setKeterangan(holder.editKeterangan.getText().toString());

                transactionHelper.open();
                transactionHelper.update(transaction.get(position));
                transactionHelper.close();
                Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        holder.btndelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteitem(transaction.get(position).getId());
                transaction.remove(position);
                notifyDataSetChanged();

            }
        });
    }
    private void deleteitem(int id) {

        transactionHelper.open();
        transactionHelper.delete(id);
        transactionHelper.close();

        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return transaction.size();
    }

    public void addItem(ArrayList<TransactionModel> mData) {
        this.transaction = mData;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private EditText editRekening, editNominal, editKeterangan;
        private Button btnupdate, btndelete;
        private CardView cv;

        public CustomViewHolder(View itemView) {
            super(itemView);

            editRekening = (EditText) itemView.findViewById(R.id.edit_rekening);
            editNominal = (EditText) itemView.findViewById(R.id.edit_nominal);
            editKeterangan = itemView.findViewById(R.id.edit_keterangan);
            btnupdate = (Button) itemView.findViewById(R.id.btn_update);
            btndelete = (Button) itemView.findViewById(R.id.btn_delete);
            cv = (CardView) itemView.findViewById(R.id.cv);


        }

    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    transactionHelper.open();
                    transaction = transactionHelper.getAllData();
                    transactionHelper.close();
                } else {
                    ArrayList<TransactionModel> filteredList = new ArrayList<>();
                    transactionHelper.open();
                    for (TransactionModel row : transactionHelper.getAllData()) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNoRek().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    transaction = filteredList;
                    transactionHelper.close();
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = transaction;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                transaction = (ArrayList<TransactionModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
