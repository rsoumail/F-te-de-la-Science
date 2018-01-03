package fr.istic.m2il.mmm.fetescience.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ismael on 29/12/17.
 */

public class Event implements Parcelable{

    private String lieu;
    private String date_debut;
    private String date_fin;
    private String identifiant;
    private String titre_fr;
    private String thematiques;
    private String lien;
    private String mots_cles_fr;
    private String description_fr;
    private String adresse;
    private String inscription_necessaire;
    private String statut;

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
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

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getMots_cles_fr() {
        return mots_cles_fr;
    }

    public void setMots_cles_fr(String mots_cles_fr) {
        this.mots_cles_fr = mots_cles_fr;
    }

    public String getDescription_fr() {
        return description_fr;
    }

    public void setDescription_fr(String description_fr) {
        this.description_fr = description_fr;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getInscription_necessaire() {
        return inscription_necessaire;
    }

    public void setInscription_necessaire(String inscription_necessaire) {
        this.inscription_necessaire = inscription_necessaire;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.identifiant);
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
        dest.writeString(this.thematiques);

    }

    public Event() {
    }

    public Event(Parcel in){
        this.identifiant = in.readString();
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
        this.thematiques = in.readString();
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
