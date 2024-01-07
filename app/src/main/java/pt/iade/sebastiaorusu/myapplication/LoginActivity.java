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

import java.net.URL;
import java.util.HashMap;

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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            WebRequest req = new WebRequest(new URL(WebRequest.LOCALHOST + "/api/users/login"));
                            HashMap<String, String> params = new HashMap<>();
                            params.put("email", email);
                            params.put("password", password);
                            String response = req.performPostRequest(params);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Handle the response from the server
                                    // If login is successful, navigate to MainPageActivity
                                    // If login fails, show error message
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


    private void setupComponents() {
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            Intent noBackLogin = new Intent(this, MainPageActivity.class);
            noBackLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(noBackLogin);
        });

        signUpButton = findViewById(R.id.sign_up);
        signUpButton.setOnClickListener(v -> {
            Intent signUpIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(signUpIntent);
        });
    }

}

