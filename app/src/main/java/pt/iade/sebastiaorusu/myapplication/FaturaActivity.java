package pt.iade.sebastiaorusu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import pt.iade.sebastiaorusu.myapplication.models.FatItem;
import pt.iade.sebastiaorusu.myapplication.models.TodoItem;

public class FaturaActivity extends AppCompatActivity {
    protected EditText titleEdit;
    protected EditText storeEdit;
    protected EditText storelocalEdit;
    protected EditText datePurchaseEdit;
    protected FatItem item;

    private static final int EDITOR_ACTIVITY_RETURN_ID = 1;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    protected EditText purchaseDateEdit;
    protected CalendarView purchaseDateCalendar;
    protected Button nextButton;

    protected Button cancelButtonFat;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_view);

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
                    Toast.makeText(FaturaActivity.this, "Home ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FaturaActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Toast.makeText(FaturaActivity.this, " ", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Toast.makeText(FaturaActivity.this, " ", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(FaturaActivity.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FaturaActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(FaturaActivity.this, "Login Page ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FaturaActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });


        setupCalendar();
        setupComponents();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Must be called always and before everything.
        super.onActivityResult(requestCode, resultCode, data);

        // Check which activity returned to us.
        if (requestCode == EDITOR_ACTIVITY_RETURN_ID) {
            // Check if the activity was successful.
            if (resultCode == AppCompatActivity.RESULT_OK) {
                // Get extras returned to us.
                int position = data.getIntExtra("position", -1);
                TodoItem updatedItem = (TodoItem) data.getSerializableExtra("item");

                Intent returnIntent = new Intent();
                returnIntent.putExtra("position", position);
                returnIntent.putExtra("item", updatedItem);
                setResult(AppCompatActivity.RESULT_OK, returnIntent);

                finish();
            }

        }
    }


    private void setupCalendar() {
        purchaseDateEdit = findViewById(R.id.purchase_date_edit);
        purchaseDateCalendar = findViewById(R.id.purchase_date_calendar);
        purchaseDateCalendar.setVisibility(View.GONE);
        purchaseDateEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // Quando o EditText recebe foco, esconde o teclado e mostra o CalendarView
                purchaseDateCalendar.setVisibility(View.VISIBLE);
            } else {
                purchaseDateCalendar.setVisibility(View.GONE);
            }
        });
        purchaseDateEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null && purchaseDateEdit.hasSelection()) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        purchaseDateCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Quando a data é alterada no CalendarView, atualiza o EditText
            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
            purchaseDateEdit.setText(date);
            // Quando escolhe a data, esconde o teclado e o CalendarView
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(purchaseDateEdit.getWindowToken(), 0);
            purchaseDateCalendar.setVisibility(View.GONE);
        });

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaturaActivity.this, guarantee_activity.class);
                intent.putExtra("position", -1);
                intent.putExtra("item", new TodoItem());

                startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);

            }


        });

        cancelButtonFat = findViewById(R.id.exit_button);
        cancelButtonFat.setOnClickListener(v -> {
            Intent cancelIntent = new Intent(FaturaActivity.this, MainPageActivity.class);
            startActivity(cancelIntent);

        });
    }

    private void setupComponents() {
        titleEdit= (EditText) findViewById(R.id.bill_name);
        storeEdit= (EditText) findViewById(R.id.edit_nome_loja);
        storelocalEdit= (EditText) findViewById(R.id.loc_view);
        datePurchaseEdit= (EditText) findViewById(R.id.purchase_date_edit);
    }
    protected void populateView() {
        titleEdit.setText(item.getTitle());
        storeEdit.setText(item.getStore());
        storelocalEdit.setText(item.getStoreLocation());
        datePurchaseEdit.setText(item.getDatePurchase());
    }
    protected void commitView() {
        item.setTitle(titleEdit.getText().toString());
        item.setStore(storeEdit.getText().toString());
        item.setStoreLocation(storelocalEdit.getText().toString());
        item.setDatePurchase(datePurchaseEdit.getText().toString());
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


//