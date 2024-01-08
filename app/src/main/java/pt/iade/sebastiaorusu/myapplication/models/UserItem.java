package pt.iade.sebastiaorusu.myapplication.models;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;

import pt.iade.sebastiaorusu.myapplication.utilities.WebRequest;

public class UserItem implements Serializable {
    private int id;
    private String email;
    private String password;
    private String name;

    private String location;

    public UserItem() {
        this(0, "", "", "", "");
    }

    public UserItem(int id, String email, String password, String name, String location) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.location = location;
    }

    public static void getById(int userId, Consumer<UserItem> callback) {
        new Thread(() -> {
            try {
                WebRequest request = new WebRequest(new URL(WebRequest.LOCALHOST + "/api/users/" + userId));
                String response = request.performGetRequest();
                UserItem user = new Gson().fromJson(response, UserItem.class);
                new Handler(Looper.getMainLooper()).post(() -> callback.accept(user));
            } catch (Exception e) {
                Log.e("UserItem", "Error fetching user data", e);
                new Handler(Looper.getMainLooper()).post(() -> callback.accept(null));
            }
        }).start();
    }

    // Inside UserItem class
    public void updatePassword(String newPassword, Runnable onSuccess, Runnable onError) {
        new Thread(() -> {
            try {
                WebRequest request = new WebRequest(new URL(WebRequest.LOCALHOST + "/api/users/update-password/" + this.id));

                // Assuming the server expects the new password in plain text in the request body
                String response = request.performPutRequest(null, newPassword, "text/plain");

                if (response.equals("Password updated successfully")) {
                    new Handler(Looper.getMainLooper()).post(onSuccess);
                } else {
                    new Handler(Looper.getMainLooper()).post(onError);
                }
            } catch (Exception e) {
                Log.e("UserItem", "Error updating password", e);
                new Handler(Looper.getMainLooper()).post(onError);
            }
        }).start();
    }



    public void updateUser(Context context, Runnable onSuccess, Runnable onError) {
        new Thread(() -> {
            try {
                WebRequest req = new WebRequest(new URL(WebRequest.LOCALHOST + "/api/users/update/" + this.id));
                String jsonBody = new Gson().toJson(this);
                String responseString = req.performPutRequest(null, jsonBody, "application/json");

                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        new JSONObject(responseString);
                        onSuccess.run(); // On success
                    } catch (Exception e) {
                        Log.e("UserItem", "Error parsing response", e);
                        onError.run(); // On error
                    }
                });
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, "Failed to update user profile: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("UserItem", "Update failed", e);
                    onError.run();
                });
            }
        }).start();
    }


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
