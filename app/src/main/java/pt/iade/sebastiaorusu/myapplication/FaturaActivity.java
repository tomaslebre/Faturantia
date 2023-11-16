package pt.iade.sebastiaorusu.myapplication;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FaturaActivity extends AppCompatActivity {
    protected EditText purchaseDateEdit;
    protected CalendarView purchaseDateCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fat_view);

        setupComponents();
    }

    private void setupComponents() {
        purchaseDateEdit = findViewById(R.id.purchase_date_edit);
        purchaseDateEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                purchaseDateCalendar.setVisibility(CalendarView.VISIBLE);
            } else {
                purchaseDateCalendar.setVisibility(CalendarView.GONE);
            }
        });

        purchaseDateCalendar = findViewById(R.id.purchase_date_calendar);
        purchaseDateCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
            purchaseDateEdit.setText(date);
        });
    }
}
