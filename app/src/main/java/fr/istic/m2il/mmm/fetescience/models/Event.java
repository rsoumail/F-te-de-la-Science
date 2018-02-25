package fr.istic.m2il.mmm.fetescience.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ramadan Soumaila.
 */


@DatabaseTable(tableName = "event")
public class Event implements Parcelable {

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

    private Integer votersNumber = new Integer(0);

    private Float fillingRate;

    private Float rating = new Float(0);

    private Integer fillPlaces = new Integer(0);

    private Integer maxAvailablePlaces = new Integer(0);

    protected Event(Parcel in) {
        id = in.readInt();
        apercu = in.readString();
        image = in.readString();
        ville = in.readString();
        identifiant = in.readString();
        description_fr = in.readString();
        titre_fr = in.readString();
        thematiques = in.readString();
        region = in.readString();
        description_longue_fr = in.readString();
        type_d_animation = in.readString();
        horaires_detailles_fr = in.readString();
        date_debut = in.readString();
        date_fin = in.readString();
        nom_du_lieu = in.readString();
        inscription_necessaire = in.readString();
        adresse = in.readString();
        organisateur = in.readString();
        publics_concernes = in.readString();
        statut = in.readString();
        lien = in.readString();
        dates = in.readString();
        mots_cles_fr = in.readString();
        accessibilite_fr = in.readString();
        nb_evenements = in.readInt();
        ArrayList<Double> arr = new ArrayList<Double>();
        arr.add(in.readDouble());
        arr.add(in.readDouble());
        geolocalisation = arr;
        votersNumber = in.readInt();
        fillingRate = in.readFloat();
        rating = in.readFloat();
        fillPlaces = in.readInt();
        maxAvailablePlaces = in.readInt();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public Integer getVotersNumber() {
        return votersNumber;
    }

    public void setVotersNumber(Integer votersNumber) {
        this.votersNumber = votersNumber;
    }

    public Float getFillingRate() {
        return fillingRate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getFillPlaces(){
        return fillPlaces;
    }

    public void setFillPlaces(Integer fillPlaces){
        this.fillPlaces = fillPlaces;
    }

    public Integer getMaxAvailablePlaces(){
        return maxAvailablePlaces;
    }

    public void setMaxAvailablePlaces(Integer maxAvailablePlaces){
        this.maxAvailablePlaces = maxAvailablePlaces;
    }

    public Event() {}

    public Event(String apercu, String description_fr, String titre_fr, String thematiques, ArrayList<Double> geolocalisation) {
        this.apercu = apercu;
        this.description_fr = description_fr;
        this.titre_fr = titre_fr;
        this.thematiques = thematiques;
        this.geolocalisation = geolocalisation;
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

    public Map<String, Object> mapToFireBaseEvent(){
        Map<String, Object> fireBaseEvent = new HashMap<>();
        fireBaseEvent.put("id", this.id);
        fireBaseEvent.put("rating", this.rating);
        fireBaseEvent.put("fillingRate", (this.fillPlaces * 1.0 / this.maxAvailablePlaces));
        fireBaseEvent.put("votersNumber", this.votersNumber);
        fireBaseEvent.put("fillPlaces", this.fillPlaces);
        fireBaseEvent.put("maxAvailablePlaces", this.maxAvailablePlaces);

        return fireBaseEvent;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);

        if(apercu != null){
            dest.writeString(apercu);
        }
        else {
            dest.writeString("nothing");
        }

        if(image != null){
            dest.writeString(image);
        }
        else {
            dest.writeString("nothing");
        }

        if(ville != null){
            dest.writeString(ville);
        }
        else {
            dest.writeString("nothing");
        }

        if(identifiant != null){
            dest.writeString(identifiant);
        }
        else {
            dest.writeString("nothing");
        }

        if(description_fr != null){
            dest.writeString(description_fr);
        }
        else {
            dest.writeString("nothing");
        }

        if(titre_fr != null){
            dest.writeString(titre_fr);
        }
        else {
            dest.writeString("nothing");
        }

        if(thematiques != null){
            dest.writeString(thematiques);
        }
        else {
            dest.writeString("nothing");
        }

        if(region != null){
            dest.writeString(region);
        }
        else {
            dest.writeString("nothing");
        }

        if(description_longue_fr != null){
            dest.writeString(description_longue_fr);
        }
        else {
            dest.writeString("nothing");
        }

        if(type_d_animation != null){
            dest.writeString(type_d_animation);
        }
        else {
            dest.writeString("nothing");
        }

        if(horaires_detailles_fr != null){
            dest.writeString(horaires_detailles_fr);
        }
        else {
            dest.writeString("nothing");
        }

        if(date_debut != null){
            dest.writeString(date_debut);
        }
        else {
            dest.writeString("nothing");
        }

        if(date_fin != null){
            dest.writeString(date_fin);
        }
        else {
            dest.writeString("nothing");
        }

        if(nom_du_lieu != null){
            dest.writeString(nom_du_lieu);
        }
        else {
            dest.writeString("nothing");
        }

        if(inscription_necessaire != null){
            dest.writeString(inscription_necessaire);
        }
        else {
            dest.writeString("nothing");
        }

        if(adresse != null){
            dest.writeString(adresse);
        }
        else {
            dest.writeString("nothing");
        }

        if(organisateur != null){
            dest.writeString(organisateur);
        }
        else {
            dest.writeString("nothing");
        }

        if(publics_concernes != null){
            dest.writeString(publics_concernes);
        }
        else {
            dest.writeString("nothing");
        }

        if(statut != null){
            dest.writeString(statut);
        }
        else {
            dest.writeString("nothing");
        }

        if(lien != null){
            dest.writeString(lien);
        }
        else {
            dest.writeString("nothing");
        }

        if(dates != null){
            dest.writeString(dates);
        }
        else {
            dest.writeString("nothing");
        }

        if(mots_cles_fr != null){
            dest.writeString(mots_cles_fr);
        }
        else {
            dest.writeString("nothing");
        }

        if(accessibilite_fr != null){
            dest.writeString(accessibilite_fr);
        }
        else {
            dest.writeString("nothing");
        }

        if(nb_evenements != null){
            dest.writeInt(nb_evenements);
        }
        else {
            dest.writeInt(0);
        }

        if(geolocalisation != null){
            dest.writeDouble(geolocalisation.get(0));
            dest.writeDouble(geolocalisation.get(1));
        }
        else {
            dest.writeDouble(0);
            dest.writeDouble(0);
        }

        if(votersNumber != null){
            dest.writeInt(votersNumber);
        }
        else {
            dest.writeInt(-1);
        }

        if(fillingRate != null){
            dest.writeFloat(fillingRate);
        }
        else {
            dest.writeInt(-1);
        }

        if(rating != null){
            dest.writeFloat(rating);
        }
        else {
            dest.writeFloat(-1);
        }

        if(fillPlaces != null){
            dest.writeInt(fillPlaces);
        }
        else {
            dest.writeInt(-1);
        }

        if(maxAvailablePlaces != null){
            dest.writeInt(maxAvailablePlaces);
        }
        else {
            dest.writeInt(-1);
        }
    }
}
