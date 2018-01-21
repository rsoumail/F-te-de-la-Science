package fr.istic.m2il.mmm.fetescience.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ismael on 29/12/17.
 */


@DatabaseTable(tableName = "event")
public class Event {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String apercu;

    @DatabaseField
    private String description_fr;

    @DatabaseField
    private String titre_fr;

    @DatabaseField
    private String thematiques;


    public Event() {}

    public Event(String apercu, String description_fr, String titre_fr, String thematiques) {
        this.apercu = apercu;
        this.description_fr = description_fr;
        this.titre_fr = titre_fr;
        this.thematiques = thematiques;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApercu() {
        return apercu;
    }

    public void setApercu(String apercu) {
        this.apercu = apercu;
    }

    public String getDescription_fr() {
        return description_fr;
    }

    public void setDescription_fr(String description_fr) {
        this.description_fr = description_fr;
    }

    public String getTitre_fr() {
        return titre_fr;
    }

    public void setTitre_fr(String titre_fr) {
        this.titre_fr = titre_fr;
    }

    public String getThematiques() {
        return thematiques;
    }

    public void setThematiques(String thematiques) {
        this.thematiques = thematiques;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", titre='" + titre_fr +
                ", thematiques='" + thematiques +
                ", description='" + description_fr + '\'' +
                '}';
    }

}
