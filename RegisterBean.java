package hey.model;

import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import hey.rmiserver.RMIServer_I;

public class RegisterBean {

    private RMIServer_I server;
    private String username; // username and password supplied by the user
    private String password;

    private String texto;
    //private String search_type = "music", word = "feels like summer";
    private String search_type, word;

    public RegisterBean() {
        try {
            server = (RMIServer_I) Naming.lookup("PRIMARY");
        }
        catch(NotBoundException|MalformedURLException|RemoteException e) {
            e.printStackTrace(); // what happens *after* we reach this line?
        }
    }

    public boolean registo() throws RemoteException{
        return server.registo(this.username,this.password);
    }

    /*START NEW METHODS*/

    public String getSearchInfo() throws RemoteException {
        texto = server.ver_dados(this.search_type, this.word);
        return texto;
    }

    public void setSearch_type(String search_type){
        this.search_type = search_type;
    }

    public void setWord(String word){
        this.word = word;
    }

    /*END NEW METHODS*/

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
