package pt.iade.sebastiaorusu.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Define a ActionBar para a MenuActivity
        setSupportActionBar(findViewById(R.id.toolbar)); // Substitua "R.id.toolbar" pelo ID correto da sua Toolbar

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: // Refere-se ao item com ID home
                        Toast.makeText(MenuActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_important_guarantee: // Refere-se ao item com ID nav_important_guarantee
                        Toast.makeText(MenuActivity.this, "Important Guarantee", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_support: // Refere-se ao item com ID nav_support
                        Toast.makeText(MenuActivity.this, "Support", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout: // Refere-se ao item com ID nav_logout
                        Toast.makeText(MenuActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

    }
}
