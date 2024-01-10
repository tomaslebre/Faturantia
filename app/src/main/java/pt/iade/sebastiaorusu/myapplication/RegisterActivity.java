package pt.iade.sebastiaorusu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;
import java.net.URLEncoder;
import java.util.function.Consumer;
import android.os.Handler;
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




        setupComponents();
    }

    public void checkEmailExists(String email, Consumer<Boolean> callback) {
        new Thread(() -> {
            try {
                WebRequest request = new WebRequest(new URL(WebRequest.LOCALHOST + "/api/users/check-email?email=" + URLEncoder.encode(email, "UTF-8")));
                String response = request.performGetRequest();
                boolean exists = Boolean.parseBoolean(response);
                new Handler(Looper.getMainLooper()).post(() -> callback.accept(exists));
            } catch (Exception e) {
                Log.e("CheckEmailExists", "Error checking email", e);
                new Handler(Looper.getMainLooper()).post(() -> callback.accept(false));
            }
        }).start();
    }

    private void setupComponents() {
        cancelButtonReg = findViewById(R.id.cancel_butt_reg);
        cancelButtonReg.setOnClickListener(v -> {
            Intent cancelIntent = new Intent(this, LoginActivity.class);
            startActivity(cancelIntent);

        });

        signUpButtonReg = findViewById(R.id.register_butt);
        signUpButtonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input from EditText fields
                String email = ((EditText) findViewById(R.id.register_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.register_pass)).getText().toString();
                String name = ((EditText) findViewById(R.id.register_name)).getText().toString();
                String location = ((EditText) findViewById(R.id.register_location)).getText().toString();

                // First, check if the email already exists
                checkEmailExists(email, exists -> {
                    if (exists) {
                        // Email already exists, show an error message
                        runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Email already in use. Please use a different email.", Toast.LENGTH_SHORT).show());
                    } else {
                        // Email does not exist, proceed with registration
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    UserItem user = new UserItem(0, email, password, name, location);
                                    WebRequest req = new WebRequest(new URL(WebRequest.LOCALHOST + "/api/users/register"));
                                    String response = req.performPostRequest(user);

                                    runOnUiThread(() -> {
                                        // Handle the response from the server
                                        // If registration is successful, navigate to LoginActivity
                                        if(response.equals("false")) {
                                            Toast.makeText(RegisterActivity.this, "Failed to register. Please try again.", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(loginIntent);
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    // If there's an exception, show a general error message
                                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Failed to register. Please try again.", Toast.LENGTH_SHORT).show());
                                }
                            }
                        }).start();
                    }
                });
            }
        });





    }


}