package pt.iade.sebastiaorusu.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.navigation.NavigationView;

import pt.iade.sebastiaorusu.myapplication.models.UserItem;


public class ProfileActivity extends AppCompatActivity {
    protected Button PersonalInfoButton;
    protected Button SecurityButton;


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
        setContentView(R.layout.activity_profile);

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
                    Intent intent = new Intent(ProfileActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Intent intent = new Intent(ProfileActivity.this, ImptGuarantee.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Intent intent = new Intent(ProfileActivity.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("UserID", -1);
        if (userId != -1) {
            UserItem.getById(userId, this::populateUserDetails);
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            // Redirecionar para a tela de login ou outra apropriada
        }
        setupComponents();
    }
    private void populateUserDetails(UserItem user) {
        if (user != null) {
            TextView nameEdit = findViewById(R.id.user_name);
            nameEdit.setText(user.getName());
        } else {
            Toast.makeText(this, "Failed to load user details", Toast.LENGTH_SHORT).show();
        }
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
        PersonalInfoButton = findViewById(R.id.inf_button);
        PersonalInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, InfPersonalActivity.class);
            startActivity(intent);
        });


        SecurityButton = findViewById(R.id.security_button);
        SecurityButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PrivacySecActivity.class);
            startActivity(intent);
        });


    }

}