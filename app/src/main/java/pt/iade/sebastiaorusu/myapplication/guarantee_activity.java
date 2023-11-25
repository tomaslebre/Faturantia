package pt.iade.sebastiaorusu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import pt.iade.sebastiaorusu.myapplication.models.TodoItem;

public class guarantee_activity extends AppCompatActivity {
    protected TodoItem item;
    protected EditText titleEdit;
    protected CheckBox importantCheck;
    protected EditText notes;
    protected EditText expDateEdit;
    protected CalendarView expDateCalendar;
    protected CalendarView remDateCalendar;
    protected Button saveButton;
    protected ImageButton expandButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarantee);

        Calendar chosenDate = Calendar.getInstance();
        chosenDate.set(2022, Calendar.MARCH, 15);

        item = new TodoItem(1, "Titlo Teste", chosenDate, true, Calendar.getInstance(), "Notas teste");

        setupComponents();
        setupCalendar();
    }

    private void setupCalendar() {

        //Configuração do calendar view para a data de expiração
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


        //Configuração do calendar view para a data de Lembrete

        // Configuração do ImageButton e do CalendarView para a data de Lembrete
        expandButton = findViewById(R.id.expand_butt);
        remDateCalendar = findViewById(R.id.rem_date_calendar);
        remDateCalendar.setVisibility(View.GONE);

        expandButton.setOnClickListener(v -> {
            // Se o CalendarView estiver visível, esconde-o; caso contrário, mostra-o
            if (remDateCalendar.getVisibility() == View.VISIBLE) {
                remDateCalendar.setVisibility(View.GONE);
            } else {
                remDateCalendar.setVisibility(View.VISIBLE);
            }
        });

        remDateCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Quando escolhe a data, esconde o teclado e o CalendarView
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(expandButton.getWindowToken(), 0);
            remDateCalendar.setVisibility(View.GONE);
        });

// Certifique-se de esconder o CalendarView quando o ImageButton não tem foco
        expandButton.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                remDateCalendar.setVisibility(View.GONE);
            }
        });










        // Save button garantias
        saveButton = findViewById(R.id.save_guar_butt);
        saveButton.setOnClickListener(v -> {
            Intent saveIntent = new Intent(guarantee_activity.this, MainPageActivity.class);
            startActivity(saveIntent);

        });

    }
    private void setupComponents(){
        setSupportActionBar(findViewById(R.id.toolbar));

        titleEdit = (EditText) findViewById(R.id.prod_name_txt);
        expDateCalendar = (CalendarView) findViewById(R.id.exp_date_calendar);
        expDateEdit = (EditText) findViewById(R.id.exp_date_txt);
        importantCheck = (CheckBox) findViewById(R.id.check_important);
        remDateCalendar = (CalendarView) findViewById(R.id.rem_date_calendar);
        notes = (EditText) findViewById(R.id.notes_edit);

        populateView();

    }
    protected void populateView(){
        titleEdit.setText(item.getTitle());
        expDateCalendar.setDate(item.getExp_date().getTimeInMillis());
        expDateEdit.setText(new SimpleDateFormat("dd/MM/yyyy").format(item.getExp_date().getTime()));
        importantCheck.setChecked(item.isImportant());
        remDateCalendar.setDate(item.getRem_date().getTimeInMillis());
        notes.setText(item.getNotes());
    }
}