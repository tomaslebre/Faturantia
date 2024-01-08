package pt.iade.sebastiaorusu.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import pt.iade.sebastiaorusu.myapplication.models.UserItem;

public class InfPersonalActivity extends AppCompatActivity {
    protected Button imageButtonProfile;
    protected ImageView imageViewProfile;

    protected Button SavePersonalInfoButton;
    protected Button BackPersonalInfoButton;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    protected static final int CAMERA_REQUEST = 123456789;

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

        imageViewProfile = findViewById(R.id.imageViewProfilw);
        imageButtonProfile = findViewById(R.id.profile_pic);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera, CAMERA_REQUEST);
            }
        });

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
                    Intent intent = new Intent(InfPersonalActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Intent intent = new Intent(InfPersonalActivity.this, ImptGuarantee.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Intent intent = new Intent(InfPersonalActivity.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Intent intent = new Intent(InfPersonalActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Intent intent = new Intent(InfPersonalActivity.this, LoginActivity.class);
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
            EditText nameEdit = findViewById(R.id.name_edit);
            EditText locationEdit = findViewById(R.id.local_edit);
            nameEdit.setText(user.getName());
            locationEdit.setText(user.getLocation());
        } else {
            Toast.makeText(this, "Failed to load user details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        // Must be called always and before everything.
        super.onActivityResult(requestCode, resultCode, data);

        // TODO: Check if returning from CAMERA_REQUEST.

        Bitmap photo = (Bitmap) data.getExtras().get("data");
        imageViewProfile.setImageBitmap(photo);
        //imageViewProfile.setBackgroundResource();
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
            EditText nameEdit = findViewById(R.id.name_edit);
            EditText locationEdit = findViewById(R.id.local_edit);

            String newName = nameEdit.getText().toString();
            String newLocation = locationEdit.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("UserID", -1);
            if (userId != -1) {
                UserItem.getById(userId, user -> {
                    if (user != null && (!user.getName().equals(newName) || !user.getLocation().equals(newLocation))) {
                        user.setName(newName);
                        user.setLocation(newLocation);
                        user.updateUser(this, () -> {
                            Toast.makeText(InfPersonalActivity.this, "User info updated", Toast.LENGTH_SHORT).show();
                            // Redirecionar para outra atividade se necessário
                        }, () -> {
                            Toast.makeText(InfPersonalActivity.this, "Failed to update user info", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            } else {
                Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            }
        });

        BackPersonalInfoButton = findViewById(R.id.back_profile);
        BackPersonalInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(InfPersonalActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

}