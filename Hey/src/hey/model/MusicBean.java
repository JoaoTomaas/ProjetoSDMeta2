package hey.model;

import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import hey.rmiserver.RMIServer_I;

public class MusicBean {

    private RMIServer_I server;
    private String username= null;
    private String newEditor = null;
    //devemos ter aqui uma variavel newEditor?
    //e uma variavel por todas as cenas que vao ser precisas ou só deve estar aqui o que deve ser guardado na session... n sei


    public MusicBean() {
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



    public boolean makeEditor() throws RemoteException{
        return server.makeEditor(this.username,this.newEditor);
    }


    public void setNewEditor(String newEditor) {
        this.newEditor = newEditor;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
/*
    public String getNewEditor(){
        return this.newEditor;
    }
*/





}
