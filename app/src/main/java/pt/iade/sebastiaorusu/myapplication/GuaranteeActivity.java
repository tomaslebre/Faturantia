package pt.iade.sebastiaorusu.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import com.google.gson.Gson;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import pt.iade.sebastiaorusu.myapplication.models.FatItem;
import pt.iade.sebastiaorusu.myapplication.models.GuarItem;
import pt.iade.sebastiaorusu.myapplication.utilities.WebRequest;

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
                    Intent intent = new Intent(GuaranteeActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Intent intent = new Intent(GuaranteeActivity.this, ImptGuarantee.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Intent intent = new Intent(GuaranteeActivity.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Intent intent = new Intent(GuaranteeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Intent intent = new Intent(GuaranteeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        Log.d("GuarItemDebug", "GuarItem ID: " + item.getId());

        Intent intent = getIntent();
        int faturaId = intent.getIntExtra("faturaId", -1); // Default to -1 if not found
        listPosition = intent.getIntExtra("position", -1);
        item = (GuarItem) intent.getSerializableExtra("item");
        Log.d("GuarItemDebug", "After Intent: ID = " + item.getId());
        saveButton = findViewById(R.id.save_guar_butt);
        saveButton.setOnClickListener(v -> {
            commitView(); // Update item object with UI data
            if(faturaId != -1){
                item.add(this, faturaId, (success, savedItem) -> {
                    handleSaveResponse(success, savedItem);
                });
            }else{
                    item.update(this, (success, savedItem) -> {
                    handleSaveResponse(success, savedItem);
                });
            }



        });

        setupComponents();
        setupCalendar();
    }
    private void handleSaveResponse(boolean success, GuarItem savedItem) {
        if (success) {
            if (savedItem == null) {
                Log.e("GuaranteeActivity", "Saved item is null");
            } else {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("savedItem", savedItem);
                returnIntent.putExtra("position", listPosition);
                setResult(AppCompatActivity.RESULT_OK, returnIntent);
                finish();
            }
        } else {
            Toast.makeText(GuaranteeActivity.this, "Error saving guarantee", Toast.LENGTH_SHORT).show();
        }
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