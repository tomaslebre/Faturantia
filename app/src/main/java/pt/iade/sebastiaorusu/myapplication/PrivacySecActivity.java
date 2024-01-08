package pt.iade.sebastiaorusu.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import pt.iade.sebastiaorusu.myapplication.models.UserItem;

public class PrivacySecActivity extends AppCompatActivity {
    protected Button SaveSecurity;
    protected Button BackSecurity;
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
        setContentView(R.layout.activity_privacy_sec);

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
                    Intent intent = new Intent(PrivacySecActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Intent intent = new Intent(PrivacySecActivity.this, ImptGuarantee.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Intent intent = new Intent(PrivacySecActivity.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Intent intent = new Intent(PrivacySecActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Intent intent = new Intent(PrivacySecActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("UserID", -1);
        if (userId != -1) {
            UserItem.getById(userId, this::populateUserData);
        }


// Method to populate user data
        setupComponents();
    }
    private void populateUserData(UserItem user) {
        if (user != null) {
            EditText emailEdit = findViewById(R.id.security_email_edit);
            EditText passwordEdit = findViewById(R.id.security_pass_edit);
            emailEdit.setText(user.getEmail());
            passwordEdit.setText(user.getPassword());
            // Do not display the actual password for security reasons
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
        SaveSecurity = findViewById(R.id.save_security);
        SaveSecurity.setOnClickListener(v -> {
            // Recupera os dados atuais do formulário
            EditText emailEdit = findViewById(R.id.security_email_edit);
            EditText newPassEdit = findViewById(R.id.security_change_pass_edit);
            EditText confirmPassEdit = findViewById(R.id.security_pass_confirm_edit);

            String email = emailEdit.getText().toString();
            String newPassword = newPassEdit.getText().toString();
            String confirmPassword = confirmPassEdit.getText().toString();

            // Verifica se a confirmação da senha está correta
            if (!newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
                Toast.makeText(PrivacySecActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("UserID", -1);

            if (userId != -1) {
                UserItem.getById(userId, user -> {
                    if (user != null) {
                        boolean isChanged = false;
                        if (!email.isEmpty() && !email.equals(user.getEmail())) {
                            user.setEmail(email);
                            isChanged = true;
                        }

                        if (!newPassword.isEmpty()) {
                            user.setPassword(newPassword);
                            isChanged = true;
                        }

                        if (isChanged) {
                            user.updateUser(PrivacySecActivity.this, () -> {
                                Toast.makeText(PrivacySecActivity.this, "User updated", Toast.LENGTH_SHORT).show();
                            }, () -> {
                                Toast.makeText(PrivacySecActivity.this, "Error updating user", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            Toast.makeText(PrivacySecActivity.this, "No changes made", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            }
        });

        BackSecurity = findViewById(R.id.back_security);
        BackSecurity.setOnClickListener(v -> {
            Intent intent = new Intent(PrivacySecActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

}