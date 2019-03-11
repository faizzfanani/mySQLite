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

import com.kontrakanelite.tugas1sqlite.dbHelper.NasabahHelper;

import java.util.ArrayList;
public class NasabahAdapter extends RecyclerView.Adapter<NasabahAdapter.CustomViewHolder> {

//private ArrayList<String> values;


    private LayoutInflater mInflater;
    private ArrayList<NasabahModel> nasabah;
    private Context context;
    private NasabahHelper nasabahHelper;


    public NasabahAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        nasabahHelper = new NasabahHelper(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                               int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.row_nasabah, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String nama = nasabah.get(position).getName();
        final String noRek = nasabah.get(position).getNoRek();
        final int tabungan = nasabah.get(position).getTabungan();
        holder.editNama.setText(nama);
        holder.editNoRek.setText(noRek);
        holder.editTabungan.setText(String.valueOf(tabungan));

        holder.btnupdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                nasabah.get(position).setName(holder.editNama.getText().toString());
                nasabah.get(position).setNoRek(holder.editNoRek.getText().toString());
                nasabah.get(position).setTabungan(Integer.valueOf(holder.editTabungan.getText().toString()));

                nasabahHelper.open();
                nasabahHelper.update(nasabah.get(position));
                nasabahHelper.close();
                Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        holder.btndelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteitem(nasabah.get(position).getId());
                nasabah.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    private void deleteitem(int id) {

        nasabahHelper.open();
        nasabahHelper.delete(id);
        nasabahHelper.close();

        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return nasabah.size();
    }

    public void addItem(ArrayList<NasabahModel> mData) {
        this.nasabah = mData;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private EditText editNama, editNoRek, editTabungan;
        private Button btnupdate, btndelete;
        private CardView cv;

        public CustomViewHolder(View itemView) {
            super(itemView);

            editNama = (EditText) itemView.findViewById(R.id.edit_nama);
            editNoRek = (EditText) itemView.findViewById(R.id.edit_noRek);
            editTabungan = itemView.findViewById(R.id.edit_tabungan);
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
                    nasabahHelper.open();
                    nasabah = nasabahHelper.getAllData();
                    nasabahHelper.close();
                } else {
                    ArrayList<NasabahModel> filteredList = new ArrayList<>();
                    nasabahHelper.open();
                    for (NasabahModel row : nasabahHelper.getAllData()) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNoRek().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    nasabah = filteredList;
                    nasabahHelper.close();
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = nasabah;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                nasabah = (ArrayList<NasabahModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
