package pt.iade.sebastiaorusu.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import pt.iade.sebastiaorusu.myapplication.R;
import pt.iade.sebastiaorusu.myapplication.models.FatItem;

public class FatItemRowAdapter extends RecyclerView.Adapter<FatItemRowAdapter.ViewHolder>{
    private ArrayList<FatItem> items;
    private LayoutInflater inflater;
    private TodoItemRowAdapter.ItemClickListener clickListener;

    public FatItemRowAdapter(Context context, ArrayList<FatItem> items) {
        inflater = LayoutInflater.from(context);
        this.items = items;
        clickListener = null;
    }

    public void setOnClickListener(TodoItemRowAdapter.ItemClickListener listener) {
        clickListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_fat_item, parent, false);
        return new ViewHolder(view);
    }
    public void onBindViewHolder( ViewHolder holder, int position) {
        FatItem item = items.get(position);

        holder.titleLabel.setText(item.getTitle());
        holder.storeLabel.setText(item.getStore());
        holder.storeLocationLabel.setText(item.getStoreLocation());
        holder.datePurchaseLabel.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.getDateofpurchaseCalendar().getTime()));
    }
    
    public int getItemCount() {

        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView titleLabel;
        public TextView storeLabel;
        public TextView storeLocationLabel;
        public TextView datePurchaseLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            titleLabel = itemView.findViewById(R.id.title_label);
            storeLabel = itemView.findViewById(R.id.store_label);
            storeLocationLabel = itemView.findViewById(R.id.storelocal_label);
            datePurchaseLabel = itemView.findViewById(R.id.date_label);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
