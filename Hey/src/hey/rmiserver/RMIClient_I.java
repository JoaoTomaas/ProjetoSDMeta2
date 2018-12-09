package hey.rmiserver;

import hey.rmiserver.RMIServer_I;

import java.rmi.*;

public interface RMIClient_I extends Remote{
    public void print_on_client(String s) throws RemoteException;
    //adicionei isto
    public String read_on_client() throws RemoteException;
    public void menu_inicial(RMIServer_I h)throws RemoteException;
    public void updateUsername(String u) throws RemoteException;
    //public void menu(hey.rmiserver.RMIClient_I c)throws RemoteException;
    public void menu(RMIServer_I h)throws RemoteException;
    public void initializeClient(RMIServer_I h) throws RemoteException;
    public RMIServer_I ServerChooser() throws RemoteException;
}