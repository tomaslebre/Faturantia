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
        setContentView(R.layout.activity_main);
        setupComponents();
        loginButton = (Button) findViewById(R.id.login_button);
        loginTxt = (EditText) findViewById(R.id.email_log_txt);
        passwordTxt = (EditText) findViewById(R.id.pass_log_txt);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginTxt.getText().toString().equals("admin") && passwordTxt.getText().toString().equals("admin")) {
                    Intent noBackLogin = new Intent(LoginActivity.this, MainPageActivity.class);
                    noBackLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(noBackLogin);
                } else {
                    counter--;
                    Log.d("LoginActivity", "Login failed");
                    if (counter == 2) {
                        loginTxt.setError("E-mail or password wrong, only 2 attempts left");
                    } else if (counter == 1) {
                        loginTxt.setError("E-mail or password wrong, only 1 attempts left");
                    } else if (counter == 0) {
                        loginTxt.setError("E-mail or password wrong, no attempts left, try again later");
                        loginButton.setEnabled(false);
                        iniciarContagemRegressiva();
                    }
                }
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

