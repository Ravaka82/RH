package model;

import java.util.Date;

public class Personne extends Inserable{
    int idPersonne;
    String nom;
    String prenom;
    Date dateNaissance;
    String sexe; 
    String addresse;
    String ville;
    int cin;
    int contact;
    String email;
    String cv;
    String idPoste;

    public int getIdpersonne() {
        return idPersonne;
    }
    public void setIdpersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Date getDatenaissance() {
        return dateNaissance;
    }
    public void setDatenaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public String getSexe() {
        return sexe;
    }
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    public String getAddresse() {
        return addresse;
    }
    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public int getCin() {
        return cin;
    }
    public void setCin(int cin) {
        this.cin = cin;
    }
    public int getContact() {
        return contact;
    }
    public void setContact(int contact) {
        this.contact = contact;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCv() {
        return cv;
    }
    public void setCv(String cv) {
        this.cv = cv;
    }
    public String getIdposte() {
        return idPoste;
    }
    public void setIdposte(String idPoste) {
        this.idPoste = idPoste;
    }

    

    
}
