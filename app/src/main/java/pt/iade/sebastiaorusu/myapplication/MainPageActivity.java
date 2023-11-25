package pt.iade.sebastiaorusu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;

import pt.iade.sebastiaorusu.myapplication.adapters.TodoItemRowAdapter;
import pt.iade.sebastiaorusu.myapplication.models.TodoItem;

public class MainPageActivity extends AppCompatActivity {
    protected RecyclerView itemsListView;
    protected TodoItemRowAdapter itemsRowAdapter;
    protected ArrayList<TodoItem> itemsList;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    protected ImageButton addGuarantee;



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
        setContentView(R.layout.mainpage);

        itemsList = TodoItem.List();

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
                    Toast.makeText(MainPageActivity.this, "Home ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainPageActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Toast.makeText(MainPageActivity.this, " ", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Toast.makeText(MainPageActivity.this, " ", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(MainPageActivity.this, "Login Page ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        addGuarantee = findViewById(R.id.add_butt_guarantee);
        addGuarantee.setOnClickListener(v -> {
            Intent intent = new Intent(MainPageActivity.this, FaturaActivity.class);
            startActivity(intent);

        });
        setupComponents();


    }

    private void setupComponents() {
        itemsRowAdapter = new TodoItemRowAdapter(this, itemsList);

        itemsListView = (RecyclerView) findViewById(R.id.recyclerView);
        itemsListView.setLayoutManager(new LinearLayoutManager(this));
        itemsListView.setAdapter(itemsRowAdapter);
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
