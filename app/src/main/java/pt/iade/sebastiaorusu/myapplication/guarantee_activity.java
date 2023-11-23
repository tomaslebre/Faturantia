package pt.iade.sebastiaorusu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;

public class guarantee_activity extends AppCompatActivity {
    protected EditText expDateEdit;
    protected CalendarView expDateCalendar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarantee);
        setupCalendar();
    }

    private void setupCalendar() {
        expDateEdit = findViewById(R.id.exp_date_txt);
        expDateCalendar = findViewById(R.id.exp_date_calendar);
        expDateCalendar.setVisibility(View.GONE);
        expDateEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // Quando o EditText recebe foco, esconde o teclado e mostra o CalendarView
                expDateCalendar.setVisibility(View.VISIBLE);
            } else {
                expDateCalendar.setVisibility(View.GONE);
            }
        });
        expDateCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Quando a data é alterada no CalendarView, atualiza o EditText
            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
            expDateEdit.setText(date);
            // Quando escolhe a data, esconde o teclado e o CalendarView
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(expDateEdit.getWindowToken(), 0);
            expDateCalendar.setVisibility(View.GONE);
        });

    }

}