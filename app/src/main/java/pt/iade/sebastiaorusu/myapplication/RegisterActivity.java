package pt.iade.sebastiaorusu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {
    protected Button cancelButtonReg;
    protected Button signUpButtonReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.register);
        super.onCreate(savedInstanceState);
        setupComponents();
    }

    private void setupComponents() {
        cancelButtonReg = findViewById(R.id.cancel_butt_reg);
        cancelButtonReg.setOnClickListener(v -> {
            Intent cancelIntent = new Intent(this, LoginActivity.class);
            startActivity(cancelIntent);

        });

        signUpButtonReg = findViewById(R.id.register_butt);
        signUpButtonReg.setOnClickListener(v -> {
            Intent regIntent = new Intent(this, MainPageActivity.class);
            startActivity(regIntent);
        });



    }


}