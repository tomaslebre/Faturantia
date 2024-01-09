package pt.iade.sebastiaorusu.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

import pt.iade.sebastiaorusu.myapplication.adapters.GuarItemRowAdapter;
import pt.iade.sebastiaorusu.myapplication.models.FatItem;
import pt.iade.sebastiaorusu.myapplication.models.GuarItem;

public class MainPageActivity extends AppCompatActivity {
    private static final int EDITOR_ACTIVITY_RETURN_ID = 1;
    protected RecyclerView itemsListView;
    protected GuarItemRowAdapter itemsRowAdapter;
    protected ArrayList<GuarItem> itemsList;
    protected Button viewBill;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    protected ImageButton addGuarantee;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDITOR_ACTIVITY_RETURN_ID && resultCode == RESULT_OK && data != null) {
            GuarItem updatedItem = (GuarItem) data.getSerializableExtra("savedItem");
            int position = data.getIntExtra("position", -1);

            if (updatedItem != null) {
                if (position == -1) {
                    itemsList.add(updatedItem);
                    itemsRowAdapter.notifyItemInserted(itemsList.size() - 1);
                } else {
                    itemsList.set(position, updatedItem);
                    itemsRowAdapter.notifyItemChanged(position);
                }
            } else {
                Log.e("MainPageActivity", "Updated item is null");
            }
        }
    }

    private void setupComponents() {
        itemsListView = findViewById(R.id.recyclerView);
        itemsListView.setLayoutManager(new LinearLayoutManager(this));
        itemsList = new ArrayList<>(); // Initialize your list
        itemsRowAdapter = new GuarItemRowAdapter(this, itemsList);
        itemsListView.setAdapter(itemsRowAdapter);

// When you create or fetch GuarItems, populate this map with the corresponding faturaId
// For example: guarToFaturaMap.put(guarItemId, faturaId);

        itemsRowAdapter.setOnClickListener(new GuarItemRowAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GuarItem item = itemsList.get(position);

                Intent intent = new Intent(MainPageActivity.this, GuaranteeActivity.class);
                intent.putExtra("position", position);
                Log.d("GuarItemDebug", "Before Intent: ID = " + item.getId());
                intent.putExtra("item", item);
                startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);
            }
        });

        // Call the method to fetch items from the server.
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("UserID", -1);

        if (userId != -1) {
            // User ID is available, fetch items for this user
            fetchItemsFromServer(userId);
        } else {
            // Handle the case where user ID is not available
            // This might be a good place to redirect to the login screen
        }
    }

    private void fetchItemsFromServer(int userId) {
        GuarItem.List(userId, new GuarItem.ListResponse() {
            @Override
            public void response(ArrayList<GuarItem> items) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemsList.clear();
                        itemsList.addAll(items);
                        itemsRowAdapter.notifyDataSetChanged(); // A lista já está ordenada pelo servidor
                    }
                });
            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);


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
                    Intent intent = new Intent(MainPageActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Intent intent = new Intent(MainPageActivity.this, ImptGuarantee.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Intent intent = new Intent(MainPageActivity.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Intent intent = new Intent(MainPageActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }

        });

        viewBill = (Button) findViewById(R.id.butt_view_bill);
        viewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, FatSaved.class);
                intent.putExtra("position", -1);
                intent.putExtra("item", new FatItem());

                startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);

            }

        });


        addGuarantee = findViewById(R.id.add_butt_guarantee);
        addGuarantee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, FaturaActivity.class);
                intent.putExtra("position", -1);
                intent.putExtra("item", new FatItem());

                startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);
            }
        });

        // Get the items from the web server.
        setupComponents();


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
