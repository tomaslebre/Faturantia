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

        itemsList = FatItem.List();

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
                    Toast.makeText(FatSaved.this, "Home", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FatSaved.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Toast.makeText(FatSaved.this, " ", Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Toast.makeText(FatSaved.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FatSaved.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(FatSaved.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FatSaved.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(FatSaved.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FatSaved.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        itemsList = FatItem.List();

        setupComponents();


   }
    private void setupComponents() {
        // Setup the ActionBar.

        // Set up row adapter with our items list.
        itemsRowAdapter = new FatItemRowAdapter(this, itemsList);
        itemsRowAdapter.setOnClickListener(new FatItemRowAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Place our clicked item object in the intent to send to the other activity.
                Intent intent = new Intent(FatSaved.this, FaturaActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("item", itemsList.get(position));

                startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);
            }
        });

        // Set up the items recycler view.
        itemsListView = (RecyclerView) findViewById(R.id.recyclerView_Fat_saved);
        itemsListView.setLayoutManager(new LinearLayoutManager(this));
        itemsListView.setAdapter(itemsRowAdapter);

        //Set up the View bill button
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