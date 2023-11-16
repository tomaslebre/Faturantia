package pt.iade.sebastiaorusu.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

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
        purchaseDateCalendar = findViewById(R.id.purchase_date_calendar);

        purchaseDateEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // Quando o EditText recebe foco, esconde o teclado e mostra o CalendarView
                hideKeyboard();
                purchaseDateCalendar.setVisibility(View.VISIBLE);

            } else {
                purchaseDateCalendar.setVisibility(View.GONE);
            }
        });

        purchaseDateCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Quando a data é alterada no CalendarView, atualiza o EditText
            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
            purchaseDateEdit.setText(date);
        });
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

}
