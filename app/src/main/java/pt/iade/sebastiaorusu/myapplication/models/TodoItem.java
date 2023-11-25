package pt.iade.sebastiaorusu.myapplication.models;

import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TodoItem implements Serializable {
    private int id;
    private String title;
    private Calendar expDateCalendar;
    private EditText expDateEdit;
    private boolean importantCheck;
    private Calendar remDateCalendar;
    private String notes;

    public TodoItem() {
        this(0, "", Calendar.getInstance(), false, Calendar.getInstance(), "");
    }

    public TodoItem(int id, String title, Calendar exp_date, boolean important, Calendar rem_date, String notes) {
        this.id = id;
        this.title = title;
        this.expDateCalendar = exp_date;
        this.importantCheck = important;
        this.remDateCalendar = rem_date;
        this.notes = notes;
    }


    public static ArrayList<TodoItem> List(){
        ArrayList<TodoItem> items = new ArrayList<TodoItem>();
        items.add(new TodoItem(1, "Item 1", new GregorianCalendar(2022, 4, 24), true, new GregorianCalendar(2022, 3, 24), "Item Notes 1"));
        items.add(new TodoItem(2, "Item 2", new GregorianCalendar(2021, 11, 23), true, new GregorianCalendar(2021, 10, 23), "Item Notes 2"));
        items.add(new TodoItem(3, "Item 3", new GregorianCalendar(2019, 1, 12), false, new GregorianCalendar(2022, 12, 12), "Item Notes 3"));
        items.add(new TodoItem(4, "Item 4", new GregorianCalendar(2021, 7, 11), false, new GregorianCalendar(2021, 6, 11), "Item Notes 4"));

        return items;
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

    public Calendar getExp_date() {
        return expDateCalendar;
    }

    public void setExp_date(Calendar exp_date) {
        this.expDateCalendar = exp_date;
    }

    public boolean isImportant() {
        return importantCheck;
    }

    public void setImportant(boolean important) {
        this.importantCheck = important;
    }

    public Calendar getRem_date() {
        return remDateCalendar;
    }

    public void setRem_date(Calendar rem_date) {
        this.remDateCalendar = rem_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getExpDateCalendar() {
        return expDateCalendar;
    }

    public void setExpDateCalendar(Calendar expDateCalendar) {
        this.expDateCalendar = expDateCalendar;
    }

    public EditText getExpDateEdit() {
        return expDateEdit;
    }

    public void setExpDateEdit(EditText expDateEdit) {
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
}
