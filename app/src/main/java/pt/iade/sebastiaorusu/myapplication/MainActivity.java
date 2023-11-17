package pt.iade.sebastiaorusu.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    protected Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComponents();
    }

    private void setupComponents() {
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, FaturaActivity.class);
            startActivity(intent);
        });
    }
}
