package hey.model;
import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import hey.rmiserver.RMIServer_I;

public class LoginBean {

    private RMIServer_I server;
    private String username; // username and password supplied by the user
    private String password;

    public LoginBean() {
        try {
            server = (RMIServer_I) Naming.lookup("PRIMARY");
        }
        catch(NotBoundException|MalformedURLException|RemoteException e) {
            e.printStackTrace(); // what happens *after* we reach this line?
        }
    }

    /*public ArrayList<String> getAllUsers() throws RemoteException {
        return server.getAllUsers(); // are you going to throw all exceptions?
    }*/

    public boolean login() throws RemoteException {
        return server.login(this.username, this.password);
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}