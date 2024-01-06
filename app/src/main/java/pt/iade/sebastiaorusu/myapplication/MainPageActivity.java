package pt.iade.sebastiaorusu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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

import pt.iade.sebastiaorusu.myapplication.adapters.TodoItemRowAdapter;
import pt.iade.sebastiaorusu.myapplication.models.GuarItem;

public class MainPageActivity extends AppCompatActivity {
    private static final int EDITOR_ACTIVITY_RETURN_ID = 1;
    protected RecyclerView itemsListView;
    protected TodoItemRowAdapter itemsRowAdapter;
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

        if (item.getItemId() == R.id.add_butt_guarantee) {
            // ActionBar "Add" button.
            Intent intent = new Intent(MainPageActivity.this, GuaranteeActivity.class);
            intent.putExtra("position", -1);
            intent.putExtra("item", new GuarItem());

            startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);

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
                GuarItem updatedItem = (GuarItem) data.getSerializableExtra("item");

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
        setContentView(R.layout.activity_mainpage);

        //itemsList = GuarItem.List();

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
                    Toast.makeText(MainPageActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainPageActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Toast.makeText(MainPageActivity.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainPageActivity.this, ImptGuarantee.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Toast.makeText(MainPageActivity.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainPageActivity.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(MainPageActivity.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainPageActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(MainPageActivity.this, "Login Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        addGuarantee = findViewById(R.id.add_butt_guarantee);
        addGuarantee.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, FaturaActivity.class);
                intent.putExtra("position", -1);
                intent.putExtra("item", new GuarItem());

                startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);
            }
        });

        // Get the items from the web server.
        //itemsList = GuarItem.List();

        setupComponents();
        fetchDataFromServer();


    }
    private void fetchDataFromServer(){
        GuarItem.List(new GuarItem.ListResponse() {
            @Override
            public void response(ArrayList<GuarItem> items) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemsList.clear();
                        itemsList.addAll(items);
                        itemsRowAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    private void setupComponents() {
        // Setup the ActionBar.

        // Set up row adapter with our items list.
        /*itemsRowAdapter = new TodoItemRowAdapter(this, itemsList);
        itemsRowAdapter.setOnClickListener(new TodoItemRowAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Place our clicked item object in the intent to send to the other activity.
                Intent intent = new Intent(MainPageActivity.this, GuaranteeActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("item", itemsList.get(position));

                startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);
            }
        });

        // Set up the items recycler view.
        itemsListView = (RecyclerView) findViewById(R.id.recyclerView);
        itemsListView.setLayoutManager(new LinearLayoutManager(this));
        itemsListView.setAdapter(itemsRowAdapter);*/

        GuarItem.List(new GuarItem.ListResponse() {
            @Override
            public void response(ArrayList<GuarItem> items) {
                // Set our items list.
                itemsList = items;

                // Set up row adapter with our items list.
                itemsRowAdapter = new TodoItemRowAdapter(MainPageActivity.this, itemsList);
                itemsRowAdapter.setOnClickListener(new TodoItemRowAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // Place our clicked item object in the intent to send to the other activity.
                        Intent intent = new Intent(MainPageActivity.this, GuaranteeActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("item", itemsList.get(position));

                        startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);
                    }
                });

                // Set up the items recycler view.
                itemsListView = (RecyclerView) findViewById(R.id.recyclerView);
                itemsListView.setLayoutManager(new LinearLayoutManager(MainPageActivity.this));
                itemsListView.setAdapter(itemsRowAdapter);
            }
        });


        //Set up the View bill button
         viewBill = (Button) findViewById(R.id.butt_view_bill);
            viewBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainPageActivity.this, FatSaved.class);
                    startActivity(intent);
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
