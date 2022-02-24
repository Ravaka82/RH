package metier;

import model.Inserable;

public class LoginService {
    public boolean isCorrectIdentifier(Inserable b){
        if(b.read(b).length>0)
            return true;

        return false;        
    }
}
