CREATE Table Poste(
    id serial NOT NULL PRIMARY KEY,
    nom varchar(256)
);

CREATE Table Personne(
    idPersonne serial NOT NULL PRIMARY KEY,
    nom varchar(256) NOT NULL,
    prenom varchar(256) NOT NULL,
    dateNaissance date NOT NULL,
    sexe varchar(100) NOT NULL,
    adresse varchar(256),
    ville varchar(256),
    cin integer NOT NULL,
    contact integer NOT NULL,
    email varchar(256),
    cv varchar(256),
    idposte integer NOT NULL references Poste(idPoste)
);

CREATE Table Admin(
    idAdmin serial NOT NULL PRIMARY KEY,
    login varchar(256) NOT NULL,
    mdp varchar(256) NOT NULL
);

CREATE Table DemandeEmploye(
    idDemande serial NOT NULL PRIMARY KEY,
    idPersonne integer references Personne(idPersonne),
    estValider boolean NOT NULL
);

CREATE Table Contrat(
    idContrat serial NOT NULL PRIMARY KEY,
    typeContrat varchar(255) NOT NULL,
    duree varchar(255) NOT NULL
);

CREATE Table Employe(
    idEmploye serial NOT NULL PRIMARY KEY,
    idDemandeEmploye integer references demandeEmploye(idDemande),
    idContrat integer NOT NULL references Contrat(idContrat),
    dateDebut date NOT NULL,
    dateFin date NOT NULL
);

---Employe to poste

CREATE VIEW PersPost as
SELECT
    p.idPersonne,
    p.nom,
    p.prenom,
    p.dateNaissance,
    p.sexe,
    p.adresse,
    p.ville,
    p.cin,
    p.contact,
    p.email,
    p.cv,
    poste.id idPoste,
    poste.nom nomPoste,
	d.idDemande,
	d.estValider
from
    Personne p
    join Poste poste on p.idPoste = poste.id
    join  DemandeEmploye d on p.idPersonne = d.idPersonne;



CREATE VIEW Employes as
SELECT
    emp.idDemandeEmploye idDemandeEmployeE,
    emp.idContrat idContratE,
    emp.dateDebut,
    emp.dateFin,
    p.*,
    c.idContrat,
    c.typeContrat,
    c.duree
from
    Employe emp
    join PersPost p on emp.idDemande = p.idDemandeEmploye
    join Contrat c on emp.idContrat = c.idContrat;