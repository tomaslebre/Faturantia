package pt.iade.sebastiaorusu.myapplication.models;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import android.os.Handler;

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


    public void save(Context context) {
        new Thread(() -> {
            try {
                WebRequest req;
                String endpoint;

                if (id == 0) {
                    // This is a new GuarItem, so use the 'add' endpoint
                    endpoint = "/api/guarantee/add";
                } else {
                    // This is an existing GuarItem, so use the 'update' endpoint
                    endpoint = "/api/guarantee/update/" + id;
                }

                // Prepare and send the request
                req = new WebRequest(new URL(WebRequest.LOCALHOST + endpoint));
                String jsonBody = new Gson().toJson(this);
                String response = req.performPostRequest(null, jsonBody, "application/json");

                // Process the response
                if (id == 0) {
                    GuarItem respItem = new Gson().fromJson(response, GuarItem.class);
                    id = respItem.getId(); // Update the id if it's a new item
                }

                // Update UI on success
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, "Guarantee saved successfully", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                // Update UI on error
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, "Failed to save guarantee: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("GuarItem", "Save failed", e);
                });
            }
        }).start();
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
