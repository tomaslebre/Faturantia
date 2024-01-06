package pt.iade.sebastiaorusu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

import pt.iade.sebastiaorusu.myapplication.adapters.TodoImptAdapter;
import pt.iade.sebastiaorusu.myapplication.models.GuarItem;

public class ImptGuarantee extends AppCompatActivity {
    private static final int EDITOR_ACTIVITY_RETURN_ID = 1;
    protected RecyclerView itemsListView;
    protected TodoImptAdapter itemsRowAdapter;
    protected ArrayList<GuarItem> itemsList;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;


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

        // Verifica se o resultado veio da atividade correta e se foi bem-sucedido
        if (requestCode == EDITOR_ACTIVITY_RETURN_ID && resultCode == RESULT_OK) {
            // Obtém os dados extras retornados (o item atualizado ou novo)
            int position = data.getIntExtra("position", -1);
            GuarItem updatedItem = (GuarItem) data.getSerializableExtra("item");

            if (position == -1) {
                // Se position é -1, um novo item foi adicionado
                if (updatedItem.isImportantCheck()) {
                    // Se o novo item é importante, adiciona na lista e notifica o adapter
                    itemsList.add(updatedItem);
                    itemsRowAdapter.notifyItemInserted(itemsList.size() - 1);
                }
            } else {
                // Se position não é -1, um item existente foi atualizado
                itemsList.set(position, updatedItem);

                // Aqui, precisamos verificar se o item atualizado ainda é importante
                if (updatedItem.isImportantCheck()) {
                    // Se ainda é importante, atualiza o item no adapter
                    itemsRowAdapter.notifyItemChanged(position);
                } else {
                    // Se não é mais importante, remove da lista e notifica o adapter
                    itemsList.remove(position);
                    itemsRowAdapter.notifyItemRemoved(position);
                }
            }

            // Re-filtrar a lista para mostrar apenas os itens importantes
            filterAndDisplayImportantItems();
        }
    }

    private void filterAndDisplayImportantItems() {
        ArrayList<GuarItem> importantItems = new ArrayList<>();
        for (GuarItem item : itemsList) {
            if (item.isImportantCheck()) {
                importantItems.add(item);
            }
        }
        itemsRowAdapter.setItems(importantItems);
        itemsRowAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impt_guarantee);

        itemsList = GuarItem.List();

        ArrayList<GuarItem> importantItems = new ArrayList<>();
        for (GuarItem item : itemsList) {
            if (item.isImportantCheck()) {
                importantItems.add(item);
            }
        }

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
                    Toast.makeText(ImptGuarantee.this, "Home", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ImptGuarantee.this, MainPageActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_important_guarantee) {
                    Toast.makeText(ImptGuarantee.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ImptGuarantee.this, ImptGuarantee.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_support) {
                    Toast.makeText(ImptGuarantee.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ImptGuarantee.this, SupportActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(ImptGuarantee.this, " ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ImptGuarantee.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else if(item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(ImptGuarantee.this, "Login Page", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ImptGuarantee.this, LoginActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        // Get the items from the web server.
        itemsList = GuarItem.List();

        setupComponents(importantItems);

    }

    private void setupComponents(ArrayList<GuarItem> importantItems) {
        // Setup the ActionBar.

        // Set up row adapter with our items list.
        itemsRowAdapter = new TodoImptAdapter(this, importantItems);
        itemsRowAdapter.setOnClickListener(new TodoImptAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Place our clicked item object in the intent to send to the other activity.
                Intent intent = new Intent(ImptGuarantee.this, GuaranteeActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("item", itemsList.get(position));

                startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);
            }
        });

        // Set up the items recycler view.
        itemsListView = (RecyclerView) findViewById(R.id.recyclerView_imptGua);
        itemsListView.setLayoutManager(new LinearLayoutManager(this));
        itemsListView.setAdapter(itemsRowAdapter);

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
