package pt.iade.sebastiaorusu.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pt.iade.sebastiaorusu.myapplication.models.FatItem;
import pt.iade.sebastiaorusu.myapplication.models.GuarItem;

public class FaturaActivity extends AppCompatActivity {
    protected Button imageButton;
    protected ImageView imageView;
    protected Button pdfButton;
    protected EditText titleEdit;
    protected EditText storeEdit;
    protected EditText storelocalEdit;
    protected int listPosition;

    protected FatItem item;

    protected static final int CAMERA_REQUEST = 123456789;
    private static final int EDITOR_ACTIVITY_RETURN_ID = 1;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    protected EditText purchaseDateEdit;
    protected CalendarView purchaseDateCalendar;
    protected Button nextButton;

    protected Button cancelButtonFat;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_create);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        imageView = findViewById(R.id.add_img_view);
        imageButton = findViewById(R.id.add_img_but);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera, CAMERA_REQUEST);
            }
        });



        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_leave) {

                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(item.getItemId() == R.id.home) {
                    Intent intent = new Intent(FaturaActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Intent intent = new Intent(FaturaActivity.this, ImptGuarantee.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Intent intent = new Intent(FaturaActivity.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(FaturaActivity.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FaturaActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(FaturaActivity.this, "Login Page ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FaturaActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });


        // Get the item passed from the previous activity.

        Intent intent = getIntent();

        // Verificar se há extras na Intent
        if (intent.hasExtra("item")) {
            // Obter o objeto FatItem passado pela Intent
            item = (FatItem) intent.getSerializableExtra("item");
        } else {
            // Se não houver um objeto FatItem, inicialize um novo
            item = new FatItem();
        }

        listPosition = intent.getIntExtra("position", -1);

        setupCalendar();
        setupComponents();


        //nextbutton to guarantee
        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitView();
                saveFaturaAndContinue();

            }
        });
    }

    private void saveFaturaAndContinue() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("UserID", -1);

        if (userId != -1) {
            item.save(this, userId, new FatItem.SaveResponse() {
                @Override
                public void response(boolean success) {
                    if (success) {
                        // Se a fatura foi salva com sucesso, inicie a GuaranteeActivity
                        Intent intent = new Intent(FaturaActivity.this, GuaranteeActivity.class);
                        intent.putExtra("position", -1); // ou a posição apropriada, se necessário
                        intent.putExtra("item", new GuarItem()); // um novo GuarItem ou o GuarItem associado, se necessário
                        startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);
                    } else {
                        // Tratamento de erro se a fatura não for salva
                        runOnUiThread(() -> Toast.makeText(FaturaActivity.this, "Erro ao salvar fatura", Toast.LENGTH_SHORT).show());
                    }
                }
            });
        } else {
            // Tratamento de erro se o userId não estiver disponível
        }

    }


    public void buttonCreateFile(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
            intent.setType("*/*");
            this.startActivity(intent);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check which activity returned to us.
        if (requestCode == EDITOR_ACTIVITY_RETURN_ID) {
            // Check if the activity was successful.
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                // Get extras returned to us.
                int position = data.getIntExtra("position", -1);
                GuarItem updatedItem = (GuarItem) data.getSerializableExtra("item");

                // Return the data back to the previous activity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("position", position);
                returnIntent.putExtra("item", updatedItem);
                setResult(AppCompatActivity.RESULT_OK, returnIntent);

                // End this activity and return to the previous one
                finish();
            }
        }

        // Add additional handling for other request codes if needed
    }


    private void setupCalendar() {
        purchaseDateEdit = findViewById(R.id.purchase_date_edit);
        purchaseDateCalendar = findViewById(R.id.purchase_date_calendar);
        purchaseDateCalendar.setVisibility(View.GONE);
        purchaseDateEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // Quando o EditText recebe foco, esconde o teclado e mostra o CalendarView
                purchaseDateCalendar.setVisibility(View.VISIBLE);
            } else {
                purchaseDateCalendar.setVisibility(View.GONE);
            }
        });
        purchaseDateEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null && purchaseDateEdit.hasSelection()) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        purchaseDateCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Quando a data é alterada no CalendarView, atualiza o EditText
            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
            purchaseDateEdit.setText(date);
            // Quando escolhe a data, esconde o teclado e o CalendarView
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(purchaseDateEdit.getWindowToken(), 0);
            purchaseDateCalendar.setVisibility(View.GONE);
        });


        cancelButtonFat = findViewById(R.id.exit_button);
        cancelButtonFat.setOnClickListener(v -> {
            Intent cancelIntent = new Intent(FaturaActivity.this, MainPageActivity.class);
            startActivity(cancelIntent);

        });

    }

    private void setupComponents() {
        titleEdit= (EditText) findViewById(R.id.bill_name);
        storeEdit= (EditText) findViewById(R.id.edit_nome_loja);
        storelocalEdit= (EditText) findViewById(R.id.loc_view);
        purchaseDateEdit= (EditText) findViewById(R.id.purchase_date_edit);
        purchaseDateCalendar= (CalendarView) findViewById(R.id.purchase_date_calendar);

        purchaseDateCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            item.setDateofpurchaseCalendar(new GregorianCalendar(year, month, dayOfMonth));
        });
        populateView();
    }
    protected void populateView() {
        if (item != null) {
            titleEdit.setText(item.getTitle());
            storeEdit.setText(item.getStore());
            storelocalEdit.setText(item.getStoreLocation());
            purchaseDateEdit.setText(item.getDatePurchase());
            purchaseDateCalendar.setDate(item.getDateofpurchaseCalendar().getTimeInMillis());
        } else {
            // Inicialize item aqui ou trate o caso de item ser nulo
            item = new FatItem();
        }
    }
    protected void commitView() {
        item.setDatePurchase(purchaseDateEdit.getText().toString());
        Calendar purchaseDateCalendar = Calendar.getInstance();
        purchaseDateCalendar.setTimeInMillis(this.purchaseDateCalendar.getDate());
        item.setDateofpurchaseCalendar(purchaseDateCalendar);
        item.setTitle(titleEdit.getText().toString());
        item.setStore(storeEdit.getText().toString());
        item.setStoreLocation(storelocalEdit.getText().toString());
        // Continue com o restante do seu código...
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


}


//