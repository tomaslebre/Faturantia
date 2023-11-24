package pt.iade.sebastiaorusu.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

// ACTIVITY DO LOGIN //

public class LoginActivity extends AppCompatActivity {
    protected Button loginButton;

    protected Button signUpButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComponents();
    }

    private void setupComponents() {
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            Intent noBackLogin = new Intent(this, MainPageActivity.class);
            noBackLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(noBackLogin);
        });

            signUpButton = findViewById(R.id.sign_up);
            signUpButton.setOnClickListener(v -> {
                Intent noBackLogin = new Intent(this, RegisterActivity.class);
                noBackLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(noBackLogin);
            });
        }

}

