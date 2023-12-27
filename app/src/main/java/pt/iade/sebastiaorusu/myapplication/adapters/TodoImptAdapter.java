package pt.iade.sebastiaorusu.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import pt.iade.sebastiaorusu.myapplication.R;
import pt.iade.sebastiaorusu.myapplication.models.TodoItem;

public class TodoImptAdapter extends RecyclerView.Adapter<TodoImptAdapter.ViewHolder>{
    private ArrayList<TodoItem> items;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public TodoImptAdapter(Context context, ArrayList<TodoItem> items) {
        inflater = LayoutInflater.from(context);
        this.items = items;
        clickListener = null;
    }


    public void setOnClickListener(ItemClickListener listener) {
        clickListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_todo_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder( ViewHolder holder, int position) {
        TodoItem item = items.get(position);

        holder.titleLabel.setText(item.getTitle());
        holder.notesLabel.setText(item.getNotes());
        holder.dateLabel.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.getExp_date().getTime()));
        holder.importantCheck.setChecked(item.isImportant());
    }

    public int getItemCount() {

        return items.size();
    }

    public void setItems(ArrayList<TodoItem> importantItems) {
        this.items = importantItems;
        notifyDataSetChanged();
    }

    //get important items
    public ArrayList<TodoItem> getImportantItems() {
        ArrayList<TodoItem> importantItems = new ArrayList<TodoItem>();

        for (TodoItem item : items) {
            if (item.isImportant()) {
                importantItems.add(item);
            }
        }

        return importantItems;
    }
    //only show important items

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView titleLabel;
        public TextView notesLabel;
        public TextView dateLabel;
        public CheckBox importantCheck;

        public ViewHolder(View itemView) {
            super(itemView);

            titleLabel = itemView.findViewById(R.id.title_label);
            notesLabel = itemView.findViewById(R.id.notes_label);
            dateLabel = itemView.findViewById(R.id.date_label);
            importantCheck = itemView.findViewById(R.id.important_check);

            importantCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            importantCheck.setOnTouchListener((v, event) -> {
                return true;
            });

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

    //only show important items
    public void showImportantItems(View view) {
        ArrayList<TodoItem> importantItems = new ArrayList<TodoItem>();

        for (TodoItem item : items) {
            if (item.isImportant()) {
                importantItems.add(item);
            }
        }
    }
}

