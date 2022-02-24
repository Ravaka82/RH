package metier;

import model.Inserable;

public class ServiceUpdate {
    public void modify(Inserable objetFiltre,String primaryKey) throws Exception{
        objetFiltre.update(objetFiltre, primaryKey);
    }
}
