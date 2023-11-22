package pt.iade.sebastiaorusu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.google.android.material.navigation.NavigationView;


public class MenuActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private boolean isMenuVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        navigationView = findViewById(R.id.nav_view);
    }

    public void toggleMenu(View view) {
        if (isMenuVisible) {
            navigationView.setVisibility(View.GONE);
        } else {
            navigationView.setVisibility(View.VISIBLE);
        }
        isMenuVisible = !isMenuVisible;
    }
}
