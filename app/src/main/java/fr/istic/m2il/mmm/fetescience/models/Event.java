package fr.istic.m2il.mmm.fetescience.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ismael on 29/12/17.
 */

public class Event implements Parcelable{

    private Geometry geometry;
    private Field fields;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Field getFields() {
        return fields;
    }

    public void setFields(Field fields) {
        this.fields = fields;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /*dest.writeString(this.identifiant);
        dest.writeString(this.titre_fr);
        dest.writeString(this.adresse);
        dest.writeString(this.date_debut);
        dest.writeString(this.date_fin);
        dest.writeString(this.description_fr);
        dest.writeString(this.inscription_necessaire);
        dest.writeString(this.lien);
        dest.writeString(this.lieu);
        dest.writeString(this.mots_cles_fr);
        dest.writeString(this.statut);
        dest.writeString(this.thematiques);*/

    }

    public Event() {
    }

    public Event(Parcel in){
       /* this.identifiant = in.readString();
        this.titre_fr = in.readString();
        this.adresse = in.readString();
        this.date_debut = in.readString();
        this.date_fin = in.readString();
        this.description_fr = in.readString();
        this.inscription_necessaire = in.readString();
        this.lien = in.readString();
        this.lieu = in.readString();
        this.mots_cles_fr = in.readString();
        this.statut = in.readString();
        this.thematiques = in.readString();*/
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

}
