package fr.istic.m2il.mmm.fetescience.models;

/**
 * Created by ismael on 03/01/18.
 */

public class Field {

    private String nom_du_lieu;
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
    private String organisateur;
    private String apercu;

    public String getNom_du_lieu() {
        return nom_du_lieu;
    }

    public void setNom_du_lieu(String nom_du_lieu) {
        this.nom_du_lieu = nom_du_lieu;
    }

    public String getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(String organisateur) {
        this.organisateur = organisateur;
    }

    public String getApercu() {
        return apercu;
    }

    public void setApercu(String apercu) {
        this.apercu = apercu;
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
}
