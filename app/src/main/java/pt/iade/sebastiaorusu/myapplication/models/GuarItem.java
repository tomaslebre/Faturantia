package pt.iade.sebastiaorusu.myapplication.models;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import java.util.Calendar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import android.os.Handler;

import org.json.JSONObject;

import pt.iade.sebastiaorusu.myapplication.utilities.CalendarJsonAdapter;
import pt.iade.sebastiaorusu.myapplication.utilities.WebRequest;

public class GuarItem implements Serializable {
    private int id;
    private String title;
    @JsonAdapter(CalendarJsonAdapter.class)
    private Calendar expDateCalendar;
    private String expDateEdit;
    private boolean importantCheck;
    @JsonAdapter(CalendarJsonAdapter.class)
    private Calendar remDateCalendar;
    private String notes;


    public GuarItem() {
        this(0, "", Calendar.getInstance(), " ", false, Calendar.getInstance(), "");
    }

    public GuarItem(int id, String title, Calendar exp_date,String exp_date_edit, boolean important, Calendar rem_date, String notes) {
        this.id = id;
        this.title = title;
        this.expDateCalendar = exp_date;
        this.expDateEdit = exp_date_edit;
        this.importantCheck = important;
        this.remDateCalendar = rem_date;
        this.notes = notes;
    }
    public static void List(int userId, ListResponse response) {
        new Thread(() -> {
            try {
                WebRequest req = new WebRequest(new URL(
                        WebRequest.LOCALHOST + "/api/guarantee/" + userId + "/list"));
                String resp = req.performGetRequest();

                // Assume the root of the response is a JsonArray.
                JsonArray arr = new Gson().fromJson(resp, JsonArray.class);
                ArrayList<GuarItem> items = new ArrayList<>();
                for (JsonElement elem : arr) {
                    items.add(new Gson().fromJson(elem, GuarItem.class));
                }

                // Switch to main thread to update UI components.
                new Handler(Looper.getMainLooper()).post(() -> {
                    response.response(items);
                });

            } catch (Exception e) {
                // Handle exceptions and make sure to switch to main thread if updating UI
                Log.e("GuarItem", "Web request failed: " + e.toString());
            }
        }).start();
    }


    public void add(Context context, int faturaId, SaveResponse response) {
        new Thread(() -> {
            try {
                WebRequest req;
                String endpoint = "/api/guarantee/add/" + faturaId;
                String jsonBody = new Gson().toJson(this);

                req = new WebRequest(new URL(WebRequest.LOCALHOST + endpoint));
                String responseString = req.performPostRequest(null, jsonBody, "application/json");

                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(responseString);
                        // Aqui você deverá extrair o ID da resposta e atualizar o ID do GuarItem
                        this.id = jsonResponse.getInt("id"); // Ajuste este código conforme o formato da sua resposta
                        response.response(true, this); // Sucesso
                    } catch (Exception e) {
                        Log.e("GuarItem", "Error parsing response", e);
                        response.response(false, null); // Erro
                    }
                });
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, "Failed to add guarantee: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("GuarItem", "Add failed", e);
                    response.response(false, null);
                });
            }
        }).start();
    }

    public void update(Context context, SaveResponse response) {
        new Thread(() -> {
            try {
                String endpoint = "/api/guarantee/update/" + this.id;
                String jsonBody = new Gson().toJson(this);

                WebRequest req = new WebRequest(new URL(WebRequest.LOCALHOST + endpoint));
                String responseString = req.performPutRequest(null, jsonBody, "application/json");

                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(responseString);
                        // Aqui você pode adicionar mais tratamento, se necessário
                        response.response(true, this); // Sucesso
                    } catch (Exception e) {
                        Log.e("GuarItem", "Error parsing response", e);
                        response.response(false, null); // Erro
                    }
                });
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, "Failed to update guarantee: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("GuarItem", "Update failed", e);
                    response.response(false, null);
                });
            }
        }).start();
    }




    // Interface for the callback of the save method
    public interface SaveResponse {
        void response(boolean success, GuarItem savedItem);
    }

    public static void ImptList(int userId, ListResponse response) {
        new Thread(() -> {
            try {
                WebRequest req = new WebRequest(new URL(
                        WebRequest.LOCALHOST + "/api/guarantee/" + userId + "/list/imp"));
                String resp = req.performGetRequest();

                // Assume the root of the response is a JsonArray.
                JsonArray arr = new Gson().fromJson(resp, JsonArray.class);
                ArrayList<GuarItem> items = new ArrayList<>();
                for (JsonElement elem : arr) {
                    items.add(new Gson().fromJson(elem, GuarItem.class));
                }

                // Switch to main thread to update UI components.
                new Handler(Looper.getMainLooper()).post(() -> {
                    response.response(items);
                });

            } catch (Exception e) {
                // Handle exceptions and make sure to switch to main thread if updating UI
                Log.e("GuarItem", "Web request failed: " + e.toString());
            }
        }).start();
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getExpDateCalendar() {
        return expDateCalendar;
    }

    public void setExpDateCalendar(Calendar expDateCalendar) {
        this.expDateCalendar = expDateCalendar;
    }

    public String getExpDateEdit() {
        return expDateEdit;
    }

    public void setExpDateEdit(String expDateEdit) {
        this.expDateEdit = expDateEdit;
    }

    public boolean isImportantCheck() {
        return importantCheck;
    }

    public void setImportantCheck(boolean importantCheck) {
        this.importantCheck = importantCheck;
    }

    public Calendar getRemDateCalendar() {
        return remDateCalendar;
    }

    public void setRemDateCalendar(Calendar remDateCalendar) {
        this.remDateCalendar = remDateCalendar;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public interface ListResponse {
        public void response(ArrayList<GuarItem> items);
    }

    public interface GetByIdResponse {
        public void response(GuarItem item);
    }
}
