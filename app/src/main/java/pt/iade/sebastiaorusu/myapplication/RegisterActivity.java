package pt.iade.sebastiaorusu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URL;

import pt.iade.sebastiaorusu.myapplication.models.UserItem;
import pt.iade.sebastiaorusu.myapplication.utilities.WebRequest;

public class RegisterActivity extends AppCompatActivity {
    protected Button cancelButtonReg;
    protected Button signUpButtonReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        signUpButtonReg = findViewById(R.id.register_butt);
        signUpButtonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input from EditText fields
                String email = ((EditText) findViewById(R.id.register_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.register_pass)).getText().toString();
                String name = ((EditText) findViewById(R.id.register_name)).getText().toString();
                String location = ((EditText) findViewById(R.id.register_location)).getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            UserItem user = new UserItem(0, email, password, name, location);
                            WebRequest req = new WebRequest(new URL(WebRequest.LOCALHOST + "/api/users/register"));
                            String response = req.performPostRequest(user);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Handle the response from the server
                                    // If registration is successful, navigate to LoginActivity
                                    // If registration fails, show error message
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Handle exception
                        }
                    }
                }).start();
            }
        });



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