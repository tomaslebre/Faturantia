package pt.iade.sebastiaorusu.myapplication.models;

import java.io.Serializable;
import java.util.Random;

public class UserItem implements Serializable {
    private int id;
    private String email;
    private String password;

    public UserItem() {
        this(0, "", "");
    }

    public UserItem(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public void save() {
        // TODO: Send the object's data to our web server and update the database there.

        if (id == 0) {
            // This is a brand new object and must be a INSERT in the database.

            // Simulate doing an insert and getting an ID back from the web server.
            id = new Random().nextInt(1000) + 1;
        } else {
            // This is an update to an existing object and must use UPDATE in the database.
        }
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
}
