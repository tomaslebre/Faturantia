package pt.iade.sebastiaorusu.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import pt.iade.sebastiaorusu.myapplication.models.FatItem;
import pt.iade.sebastiaorusu.myapplication.models.GuarItem;

public class GuaranteeActivity extends AppCompatActivity {
    protected EditText titleEdit;
    protected CheckBox importantCheck;
    protected EditText notes;
    protected EditText expDateEdit;
    protected CalendarView expDateCalendar;
    protected CalendarView remDateCalendar;


    protected Button saveButton;
    protected ImageButton expandButton;

    protected GuarItem item;
    protected FatItem fatitem;
    protected int listPosition;
    protected Button cancelButton;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarantee);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner categorySpinner = findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Configurar um listener para quando um item é selecionado
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Faça algo com a categoria selecionada
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Opcional: Faça algo quando nenhum item está selecionado
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_leave) {

                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(item.getItemId() == R.id.home) {
                    Toast.makeText(GuaranteeActivity.this, "Home ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GuaranteeActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Toast.makeText(GuaranteeActivity.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GuaranteeActivity.this, ImptGuarantee.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Toast.makeText(GuaranteeActivity.this, " ", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(GuaranteeActivity.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GuaranteeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(GuaranteeActivity.this, "Login Page ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GuaranteeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });


        // Get the item passed from the previous activity.
        Intent intent = getIntent();
        listPosition = intent.getIntExtra("position", -1);
        item = (GuarItem) intent.getSerializableExtra("item");

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
            // ActionBar "Save" button.
            commitView();
            this.item.save();

            // Setup the data to be sent back to the previous activity.
            Intent returnIntent = new Intent();
            returnIntent.putExtra("position", this.listPosition);
            returnIntent.putExtra("item", this.item);
            setResult(AppCompatActivity.RESULT_OK, returnIntent);

            finish();
        });

        // Cancel button garantias
        cancelButton = findViewById(R.id.exit_button);
        cancelButton.setOnClickListener(v -> {
            Intent cancelIntent = new Intent(GuaranteeActivity.this, MainPageActivity.class);
            startActivity(cancelIntent);

        });


    }

    private void setupComponents(){

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
        expDateCalendar.setDate(item.getExpDateCalendar().getTimeInMillis());
        expDateEdit.setText(item.getExpDateEdit());
        importantCheck.setChecked(item.isImportantCheck());
        remDateCalendar.setDate(item.getRemDateCalendar().getTimeInMillis());
        notes.setText(item.getNotes());
    }


    protected void commitView() {
        item.setTitle(titleEdit.getText().toString());
        Calendar expDateCalendar = Calendar.getInstance();
        expDateCalendar.setTimeInMillis(this.expDateCalendar.getDate());
        item.setExpDateCalendar(expDateCalendar);
        item.setExpDateEdit(expDateEdit.getText().toString());
        item.setImportantCheck(importantCheck.isChecked());

        Calendar remDateCalendar = Calendar.getInstance();
        remDateCalendar.setTimeInMillis(this.remDateCalendar.getDate());
        item.setRemDateCalendar(remDateCalendar);
        item.setNotes(notes.getText().toString());

    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}