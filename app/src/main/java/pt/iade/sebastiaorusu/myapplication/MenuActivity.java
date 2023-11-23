package pt.iade.sebastiaorusu.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity {
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
        setContentView(R.layout.mainpage);

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
                    Toast.makeText(MenuActivity.this, "Home ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MenuActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Toast.makeText(MenuActivity.this, " ", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Toast.makeText(MenuActivity.this, " ", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(MenuActivity.this, "Login Page ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

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
