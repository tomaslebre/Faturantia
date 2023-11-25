package pt.iade.sebastiaorusu.myapplication.models;

import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TodoItem {
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
