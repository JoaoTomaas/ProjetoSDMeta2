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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
