package hey.rmiserver;

import java.rmi.*;
import java.util.HashMap;

public interface RMIServer_I extends Remote {
    public void print_on_server(String s) throws RemoteException;

    //adicionei isto
    public boolean registo(/*RMIClient_I c,*/ String username, String password) throws RemoteException;
    public boolean login(/*RMIClient_I c, */String username, String password) throws RemoteException;
    public String[] sendMulticast(String parameter, String idMulticast, String pack_ID) throws RemoteException;
    public void initialize_server() throws RemoteException;
    public void ping() throws RemoteException;
    public void removeIDArrayList(String s)throws RemoteException;
    public void atualizarIDArrayList(String s)throws RemoteException;
    public String ver_dados(RMIClient_I c, String searchType, String msg) throws RemoteException;
    public String  searchMusic(RMIClient_I c, String searchType, String chave) throws RemoteException;
    public void logOut(RMIClient_I c, String u) throws RemoteException;
    public void addCliente(String u, RMIClient_I c) throws RemoteException;
    public boolean makeEditor(RMIClient_I c, String username, String newEditor) throws RemoteException;
    public void pingCliente(RMIClient_I c, String username) throws RemoteException;
    public HashMap<String, RMIClient_I> getClientes()throws  RemoteException;
    public void setClientes(HashMap<String, RMIClient_I> clientes) throws RemoteException;
    public boolean albumReview(RMIClient_I c, String username, String nomeAlbum, String rating, String texto) throws RemoteException;
    public boolean manageData(RMIClient_I c, String use, String tipoS, String nome, String tipoO, String tipoI, String msg) throws RemoteException;
    public void resolveTudo(RMIClient_I c, String[] pares)throws RemoteException;

    //------------------
    //novos


    public boolean userMatchesPassword(String username, String password) throws RemoteException;







}