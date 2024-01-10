package sebastiao.tomas.faturantia.models;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;

@Entity
@Table(name="garantia")
public class Guarantee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    

    @Column(name="gar_id")
    private int id;

    @Column(name="gar_name")
    private String title;

    @Column(name="gar_expDate")    
    private String expDateEdit;

    @Column(name="gar_imp")
    private boolean importantCheck;

    @Column(name="gar_remDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Calendar remDateCalendar;
    
    @Column(name = "gar_expDateCalendar")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Calendar expDateCalendar;

    @Column(name="gar_notes")
    private String notes;

    @OneToOne(mappedBy = "guarantee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("garantia-fatura")
    private Fatura fatura;

    public Guarantee(){
        
    }


    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
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

    public Calendar getExpDateCalendar() {
        return expDateCalendar;
    }

    public void setExpDateCalendar(Calendar expDateCalendar) {
        this.expDateCalendar = expDateCalendar;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
