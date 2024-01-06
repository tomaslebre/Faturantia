package pt.iade.sebastiaorusu.myapplication.models;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import pt.iade.sebastiaorusu.myapplication.utilities.WebRequest;

public class GuarItem implements Serializable {
    private int id;
    private String title;
    private Calendar expDateCalendar;
    private String expDateEdit;
    private boolean importantCheck;
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


    /*public static ArrayList<GuarItem> List(){
        ArrayList<GuarItem> items = new ArrayList<GuarItem>();
        items.add(new GuarItem(1, "Item 1", new GregorianCalendar(2022, 4, 24), " ", true, new GregorianCalendar(2022, 3, 24), "Item Notes 1"));
        items.add(new GuarItem(2, "Item 2", new GregorianCalendar(2022, 4, 24), " ", false, new GregorianCalendar(2022, 3, 24), "Item Notes 2"));

        return items;
    }*/

    public static void List(ListResponse response) {
        ArrayList<GuarItem> items = new ArrayList<>();


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WebRequest request = new WebRequest(new URL(WebRequest.LOCALHOST + "/api/guarantee/list"));

                    //WebRequest requestState =   new WebRequest(new URL(WebRequest.LOCALHOST + "/api/userChallenge/completed/user/" + user.getId() ));

                    String resp = request.performGetRequest();

                    //requestState.performGetRequest();

                    Gson gson = new Gson();

                    GuarItem[] array = gson.fromJson(resp, GuarItem[].class);

                    //JsonObject json = new Gson().fromJson(resp,JsonObject.class);
                    //JsonArray array = json.getAsJsonArray("items");


                    ArrayList<GuarItem> items = new ArrayList<>();


                    for (GuarItem elem : array) {
                        items.add(elem);
                    }

                    response.response(items);

                } catch (Exception e) {
                    Log.e("permissions", e.toString());
                }
            }
        });
        thread.start();
    }


    public void save() {
        // TODO: Send the object's data to our web server and update the database there.

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        if (id == 0) {
                            // This is a brand new object and must be a INSERT in the database.
                            WebRequest req = new WebRequest(new URL(
                                    WebRequest.LOCALHOST + "/api/guarantee/add"));
                            String response = req.performPostRequest(GuarItem.this);

                            // Get the new ID from the server's response.
                            GuarItem respItem = new Gson().fromJson(response, GuarItem.class);
                            id = respItem.getId();
                        } else {
                            // This is an update to an existing object and must use UPDATE in the database.
                            WebRequest req = new WebRequest(new URL(
                                    WebRequest.LOCALHOST + "/api/guarantee/update/" + id));
                            req.performPostRequest(GuarItem.this);
                        }
                    } catch (Exception e) {
                        Toast.makeText(null, "Web request failed: " + e.toString(),
                                Toast.LENGTH_LONG).show();
                        Log.e("GuarItem", e.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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
