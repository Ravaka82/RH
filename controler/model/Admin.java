package model;

public class Admin extends Inserable{
    int idAdmin;
    String login;
    String mdp;

    public int getIdadmin() {
        return idAdmin;
    }
    public void setIdadmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getMdp() {
        return mdp;
    }
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    
}
