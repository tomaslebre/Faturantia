package pt.iade.sebastiaorusu.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import pt.iade.sebastiaorusu.myapplication.adapters.FatItemRowAdapter;
import pt.iade.sebastiaorusu.myapplication.models.FatItem;


public class FatSaved extends AppCompatActivity {
    private static final int EDITOR_ACTIVITY_RETURN_ID = 1;
    protected RecyclerView itemsListView;
    protected FatItemRowAdapter itemsRowAdapter;
    protected ArrayList<FatItem> itemsList;
    protected int listPosition;
    protected FatItem item;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Must be called always and before everything.
        super.onActivityResult(requestCode, resultCode, data);

        // Check which activity returned to us.
        if (requestCode == EDITOR_ACTIVITY_RETURN_ID) {
            // Check if the activity was successful.
            if (resultCode == AppCompatActivity.RESULT_OK) {
                // Get extras returned to us.
                int position = data.getIntExtra("position", -1);
                FatItem updatedItem = (FatItem) data.getSerializableExtra("item");

                if (position == -1) {
                    // Add the item to the list it was created new.
                    itemsList.add(updatedItem);
                    itemsRowAdapter.notifyItemInserted(itemsList.size() - 1);
                } else {
                    // Updates an existing item on the list.
                    itemsList.set(position, updatedItem);
                    itemsRowAdapter.notifyItemChanged(position);
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_view);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_leave) {

                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(item.getItemId() == R.id.home) {
                    Intent intent = new Intent(FatSaved.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Intent intent = new Intent(FatSaved.this, ImptGuarantee.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Intent intent = new Intent(FatSaved.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Intent intent = new Intent(FatSaved.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Intent intent = new Intent(FatSaved.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        Intent intent = getIntent();
        listPosition = intent.getIntExtra("position", -1);
        item = (FatItem) intent.getSerializableExtra("item");
        setupComponents();


   }
    private void setupComponents() {


        // Set up the items recycler view.
        itemsListView = (RecyclerView) findViewById(R.id.recyclerView_Fat_saved);
        itemsListView.setLayoutManager(new LinearLayoutManager(this));
        itemsList = new ArrayList<>();
        itemsRowAdapter = new FatItemRowAdapter(this, itemsList);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("UserID", -1);

        if (userId != -1) {
            // User ID is available, fetch items for this user
            fetchItemsFromServer(userId);
        } else {
            //
        }
    }

    private void fetchItemsFromServer(int userId) {
        FatItem.List(userId, new FatItem.ListResponse() {
            @Override
            public void response(ArrayList<FatItem> items) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemsList.clear();
                        itemsList.addAll(items);
                        itemsListView.setAdapter(itemsRowAdapter); // Set the adapter here
                        itemsRowAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
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