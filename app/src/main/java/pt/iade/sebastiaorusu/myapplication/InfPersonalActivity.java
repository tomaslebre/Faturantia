package pt.iade.sebastiaorusu.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class InfPersonalActivity extends AppCompatActivity {

    protected Button SavePersonalInfoButton;
    protected Button BackPersonalInfoButton;

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
        setContentView(R.layout.activity_inf_personal);

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
                    Toast.makeText(InfPersonalActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InfPersonalActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Toast.makeText(InfPersonalActivity.this, " ", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Toast.makeText(InfPersonalActivity.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InfPersonalActivity.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(InfPersonalActivity.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InfPersonalActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(InfPersonalActivity.this, "Login Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InfPersonalActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        setupComponents();
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

    private void setupComponents() {
        SavePersonalInfoButton = findViewById(R.id.save_profile);
        SavePersonalInfoButton.setOnClickListener(v -> {
            Toast.makeText(InfPersonalActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(InfPersonalActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        BackPersonalInfoButton = findViewById(R.id.back_profile);
        BackPersonalInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(InfPersonalActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

}