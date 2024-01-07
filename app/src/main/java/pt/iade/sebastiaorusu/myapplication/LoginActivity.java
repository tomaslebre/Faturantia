package pt.iade.sebastiaorusu.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

import pt.iade.sebastiaorusu.myapplication.models.UserItem;
import pt.iade.sebastiaorusu.myapplication.utilities.WebRequest;

// ACTIVITY DO LOGIN //

public class LoginActivity extends AppCompatActivity {
    protected Button loginButton;

    protected Button signUpButton;
    protected EditText loginTxt;
    protected EditText passwordTxt;
    private int counter = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupComponents();
        loginButton = (Button) findViewById(R.id.login_button);
        loginTxt = (EditText) findViewById(R.id.email_log_txt);
        passwordTxt = (EditText) findViewById(R.id.pass_log_txt);


    }
        private void iniciarContagemRegressiva() {  
            new CountDownTimer(5000, 1000) { // 5000 milissegundos (5 segundos), intervalo de 1000 milissegundos (1 segundo)
                public void onTick(long millisUntilFinished) {
                    // A cada tick (1 segundo no exemplo), você pode realizar alguma ação se necessário
                }

                public void onFinish() {
                    // A contagem regressiva terminou, reativa o botão
                    loginButton.setEnabled(true);
                    counter = 3; // Restaura o contador para o número máximo de tentativas
                }
            }.start();
        }

    private boolean isLoginSuccessful(String response) {
        try {
            // Assuming the response is in JSON format and contains a field indicating success
            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getBoolean("success");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setupComponents() {

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            String email = ((EditText) findViewById(R.id.email_log_txt)).getText().toString();
            String password = ((EditText) findViewById(R.id.pass_log_txt)).getText().toString();

            new Thread(() -> {
                try {
                    UserItem user = new UserItem(0, email, password, null, null);
                    WebRequest req = new WebRequest(new URL(WebRequest.LOCALHOST + "/api/users/login"));
                    String response = req.performPostRequest(user);

                    Log.d("LoginActivity", "Server Response: " + response);

                    runOnUiThread(() -> {
                        if (!response.contains("Invalid credentials")) {
                            // Login successful
                            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Login failed
                            Toast.makeText(LoginActivity.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        });

        signUpButton = findViewById(R.id.sign_up);
        signUpButton.setOnClickListener(v -> {
            Intent signUpIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(signUpIntent);
        });
    }

}

