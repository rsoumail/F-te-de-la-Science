package fr.istic.m2il.mmm.fetescience.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

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
    private String image;

    @DatabaseField
    private String ville;

    @DatabaseField
    private String identifiant;

    @DatabaseField
    private String description_fr;

    @DatabaseField
    private String titre_fr;

    @DatabaseField
    private String thematiques;

    @DatabaseField
    private String region;

    @DatabaseField
    private String description_longue_fr;

    @DatabaseField
    private String type_d_animation;

    @DatabaseField
    private String horaires_detailles_fr;

    @DatabaseField
    private String date_debut;

    @DatabaseField
    private String date_fin;

    @DatabaseField
    private String nom_du_lieu;

    @DatabaseField
    private String inscription_necessaire;

    @DatabaseField
    private String adresse;

    @DatabaseField
    private String organisateur;

    @DatabaseField
    private String publics_concernes;

    @DatabaseField
    private String statut;

    @DatabaseField
    private String lien;

    @DatabaseField
    private String dates;

    @DatabaseField
    private String mots_cles_fr;

    @DatabaseField
    private String accessibilite_fr;

    @DatabaseField(dataType=DataType.SERIALIZABLE)
    private ArrayList<Double> geolocalisation ;

    @DatabaseField(dataType=DataType.SERIALIZABLE)
    private Integer nb_evenements;


    private Boolean checked = false;

    private Integer votantsNumber;

    private Integer fillingRate;

    private Double rating;

    public Integer getVotantsNumber() {
        return votantsNumber;
    }

    public void setVotantsNumber(Integer votantsNumber) {
        this.votantsNumber = votantsNumber;
    }

    public Integer getFillingRate() {
        return fillingRate;
    }

    public void setFillingRate(Integer fillingRate) {
        this.fillingRate = fillingRate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription_longue_fr() {
        return description_longue_fr;
    }

    public void setDescription_longue_fr(String description_longue_fr) {
        this.description_longue_fr = description_longue_fr;
    }

    public String getType_d_animation() {
        return type_d_animation;
    }

    public void setType_d_animation(String type_d_animation) {
        this.type_d_animation = type_d_animation;
    }

    public String getHoraires_detailles_fr() {
        return horaires_detailles_fr;
    }

    public void setHoraires_detailles_fr(String horaires_detailles_fr) {
        this.horaires_detailles_fr = horaires_detailles_fr;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getNom_du_lieu() {
        return nom_du_lieu;
    }

    public void setNom_du_lieu(String nom_du_lieu) {
        this.nom_du_lieu = nom_du_lieu;
    }

    public String getInscription_necessaire() {
        return inscription_necessaire;
    }

    public void setInscription_necessaire(String inscription_necessaire) {
        this.inscription_necessaire = inscription_necessaire;
    }

    public ArrayList<Double> getGeolocalisation() {
        return geolocalisation;
    }

    public void setGeolocalisation(ArrayList<Double> geolocalisation) {
        this.geolocalisation = geolocalisation;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(String organisateur) {
        this.organisateur = organisateur;
    }

    public String getPublics_concernes() {
        return publics_concernes;
    }

    public void setPublics_concernes(String publics_concernes) {
        this.publics_concernes = publics_concernes;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getAccessibilite_fr() {
        return accessibilite_fr;
    }

    public void setAccessibilite_fr(String accessibilite_fr) {
        this.accessibilite_fr = accessibilite_fr;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Integer getNb_evenements() {
        return nb_evenements;
    }

    public void setNb_evenements(Integer nb_evenements) {
        this.nb_evenements = nb_evenements;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getMots_cles_fr() {
        return mots_cles_fr;
    }

    public void setMots_cles_fr(String mots_cles_fr) {
        this.mots_cles_fr = mots_cles_fr;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", titre=" + titre_fr +
                ", thematiques=" + thematiques +
                ", nom du lieu=" + nom_du_lieu +
                ", description='" + description_fr + '\'' +
                '}';
    }
}
