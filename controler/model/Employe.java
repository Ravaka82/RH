package model;

import java.util.Date;

public class Employe extends Inserable{
    int idEmploye;
    int idDemandeEmploye;
    int idContrat;
    Date dateDebut;
    Date dateFin;

    public int getIdemploye() {
        return idEmploye;
    }
    public void setIdemploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }
    public int getIddemandeemploye() {
        return idDemandeEmploye;
    }
    public void setIddemandeemploye(int idDemandeEmploye) {
        this.idDemandeEmploye = idDemandeEmploye;
    }
    public int getIdcontrat() {
        return idContrat;
    }
    public void setIdcontrat(int idContrat) {
        this.idContrat = idContrat;
    }
    public Date getDatedebut() {
        return dateDebut;
    }
    public void setDatedebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    public Date getDatefin() {
        return dateFin;
    }
    public void setDatefin(Date dateFin) {
        this.dateFin = dateFin;
    }

    
    
}
