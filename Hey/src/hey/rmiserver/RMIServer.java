package hey.rmiserver;

import java.rmi.*;
import java.rmi.server.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;


class MulticastReceiver implements Runnable {

    private RMIServer_I h;
    private String MULTICAST_ADDRESS = "224.3.2.1";

    public MulticastReceiver(RMIServer_I h) {
        this.h = h;
    }

    private int PORT1 = 4444;

    public void run() {

        MessageParsing parsing = new MessageParsing();

        //receber
        MulticastSocket socket1 = null;
        try {
            socket1 = new MulticastSocket(PORT1);  // create socket and bind it
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket1.joinGroup(group);
            String idPackRecebido;
            String[] pares = new String[]{};


            while (true) {

                byte[] buffer1 = new byte[256];
                DatagramPacket packet1 = new DatagramPacket(buffer1, buffer1.length);
                socket1.receive(packet1);
               // System.out.println("Received packet from " + packet1.getAddress().getHostAddress() + ":" + packet1.getPort() + " with message:");
                String message1 = new String(packet1.getData(), 0, packet1.getLength());
               // System.out.println("mensagem que esta a estrebuchar" + message1);
                pares = parsing.MessageParsing(message1);
                idPackRecebido = pares[0];
               // System.out.println("pares que esta a estrebuchar" + pares[1]);
                if (Integer.parseInt(idPackRecebido) == 0) {
                    h.atualizarIDArrayList(pares[1]);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket1.close();
        }

    }
}

class Check extends Thread{

    public void run() {

        System.out.println("the call is coming from inside the thread");
        int count = 0;
        while (count<=5) {

            try {
                RMIServer_I h = (RMIServer_I) Naming.lookup("PRIMARY");
                RMIServer_I h1 = (RMIServer_I) Naming.lookup("SECONDARY");
                try {
                    System.out.println("check " + count);
                    //dorme 5 segundos
                    sleep(5000);
                    h.ping();
                    //se funcionar continua
                    if (count > 0) {


                        System.out.println("Original Server now Secondary.");

                        Naming.rebind("SECONDARY", h);
                        Naming.rebind("PRIMARY", h1);


                        System.out.println("Primary Server ready.");

                        return;
                    }

                } catch (Exception r) {
                    count++;
                }
                //---------------
            } catch (Exception e) {
                System.out.println("something wrong!");
            }

        }

        try{
            RMIServer_I hNew = (RMIServer_I) Naming.lookup("SECONDARY");
            System.out.println("Not Found.");
            Naming.rebind("PRIMARY", hNew);
            Naming.unbind("SECONDARY");
            System.out.println("Primary Server ready.");
            return;}
        catch(Exception x){
            System.out.println("deu mal");
        }
    }
}


class MessageParsingRMI {

    public String [] MessageParsing (String message){

        //Separar por ";" e saber o número de pares
        String [] pares  = message.split(";");
        String  [] campos = new String[pares.length] ;

        if (pares.length >= 3){ //NAO SEI SE PRECISO DESTA CONDICAO OU NAO, TENHO QUE TER ATENCAO POR CAUSA DA EXCEPTION
            for (int i = 0; i < pares.length; i++){
                String [] aux = pares[i].split("\\|");
                campos[i] = aux[1];
            }
        }


        return campos;
    }

}

public class RMIServer extends UnicastRemoteObject implements RMIServer_I {


    //se isto for static nao é partilhado por todas as instances?
    private HashMap<String, RMIClient_I> clientes = new HashMap<>();

    private CopyOnWriteArrayList<String> idMulticastArray = new CopyOnWriteArrayList<>();

    private int idCounter = 1;


    public void addCliente(String u, RMIClient_I c) throws RemoteException{
        this.clientes.put(u,c);
    }

    public HashMap<String, RMIClient_I> getClientes()throws  RemoteException{
        return clientes;
    }

    public void setClientes(HashMap<String, RMIClient_I> cHash) throws RemoteException {
        for(Map.Entry<String, RMIClient_I> entry : cHash.entrySet() ){
            //se o cliente nao tiver a chave, posso pôr
            if(! clientes.containsKey(entry.getKey())  )
                clientes.put( entry.getKey(),entry.getValue() );
        }
    }

    public void atualizarIDArrayList(String s) throws RemoteException {

        if (!idMulticastArray.contains(s)){
            System.out.println("adicionei "+s);
            idMulticastArray.add(s);

        }

    }

    public void removeIDArrayList(String s) throws RemoteException {
        idMulticastArray.remove(s);
    }

    private String randomIDMulticast() throws RemoteException {
        while(idMulticastArray.isEmpty()){
            System.out.println("à procura de hey.multicast");
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return idMulticastArray.get(new Random().nextInt(idMulticastArray.size()));
    }

    private synchronized int newIDCounter(){
        incrementIDCounter();
        return getIDCounter();
    }

    private int getIDCounter() {
        return this.idCounter;
    }

    private void incrementIDCounter(){
        this.idCounter++;
    }

    private RMIServer() throws RemoteException {
        super();
    }

    public void resolveTudo(RMIClient_I c, String[] pares) throws RemoteException {


        String opcao = pares[2];
        System.out.println("ja tou no resolve " + pares[2]);


        //TIPO DE MENSAGEM A PROCESSAR
        System.out.println("JOOAO " + pares[3]);
        switch (opcao) {
            case "login":
                //on
                if (pares[3].equals("on")) {
                    System.out.println("Entrei no on");

                    c.updateUsername(pares[4]);
                    c.menu(this);
                    notificar(pares[4], pares[5]);
                    //COMO COLOCAR ISTO NA HASHMAP
                    clientes.put(pares[4], c);
                }
                //off
                else {
                    System.out.println("correu mal");
                    c.print_on_client("\nErro no Login\n");
                    c.menu_inicial(this);
                }
                break;
            case "register":

                //successful
                if (pares[4].equals("successful")) {
                    c.print_on_client("Registo com sucesso. Faça login!");
                    c.menu_inicial(this);

                }
                //unsuccessful
                else {
                    c.print_on_client("\nErro no Register\n");
                    c.menu_inicial(this);
                }
                break;
            case "make editor":

                //successful
                if (pares[4].equals("successful")) {
                    c.print_on_client("\nEdicao efetuada com sucesso");
                    c.menu(this);
                    notificar(pares[3], "Noticação Recebida: É agora editor");

                }
                //unsuccessful
                else {


                    c.print_on_client("\nErro em fornecer editoriedade\n");
                    c.menu(this);

                }


                break;
            case "search music":

                //successful
                if (pares[3].equals("successful")) {
                    c.print_on_client(pares[4]);
                    c.menu(this);
                }
                //unsuccessful
                else {
                    c.print_on_client("Erro no Search Music");
                    c.menu(this);
                }

                break;
            case "review":

                //successful
                if (pares[3].equals("successful")) {
                    c.print_on_client("Review efetuada com sucesso");
                    c.menu(this);
                }
                //unsuccessful
                else {

                    c.print_on_client("Erro na review");
                    c.menu(this);

                }
                break;
            case "add":
                //successful
                if (pares[3].equals("successful")) {
                    c.print_on_client("Notificação guardada com sucesso");
                    c.menu(this);
                }
                //unsuccessful
                else {

                    c.print_on_client("Erro na notificação");
                    c.menu(this);

                }
                break;


        }


    }

    public void ping() throws RemoteException {
        System.out.println("Yo someone trynna ping u");
    }

    public void pingCliente(RMIClient_I c, String username) throws RemoteException{

        System.out.println("Cliente a dar-me ping");

        if(username != null)  addCliente(username,c);


    }

    public void logOut(RMIClient_I c, String u) throws RemoteException {

        clientes.remove(u);
        c.print_on_client("Goodbye");


    }

    public String[] sendMulticast(String parameter, String idMulticast, String pack_ID) throws RemoteException {

        System.out.println("entrou B");
        String MULTICAST_ADDRESS = "224.3.2.1";
        int PORT = 4321;
        int PORT1 = 4444;
        //enviar
        MulticastSocket socket = null;

        String[] pares = new String[]{};

        String sendParameter = "pack_id|"    +   pack_ID    +   ";server_id|"   +   idMulticast    +   parameter;
        System.out.println(sendParameter);


        try {

            socket = new MulticastSocket();  // create socket without binding it (only for sending)
            System.out.println("ESTOU AQUIIIIII AQUI PARA TE VER");
            byte[] buffer = sendParameter.getBytes();
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }

        System.out.println("passa a receber");


        //receber
        MulticastSocket socket1 = null;
        try {
            socket1 = new MulticastSocket(PORT1);  // create socket and bind it
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket1.joinGroup(group);

            byte[] buffer1 = new byte[256];
            DatagramPacket packet1 = new DatagramPacket(buffer1, buffer1.length);

            String idPackRecebido = "-1";
            //enquanto o id recebido nao for igual ao meu id, continuo a dar receive

            socket1.setSoTimeout(15000); //timeout de 15 segundos


            MessageParsing parsing = new MessageParsing();


            long start = System.currentTimeMillis();
            long end = start + 15*1000;
            while(System.currentTimeMillis() < end) {

                try {
                    socket1.receive(packet1);
                    System.out.println("Received packet from " + packet1.getAddress().getHostAddress() + ":" + packet1.getPort() + " with message:");
                    String message1 = new String(packet1.getData(), 0, packet1.getLength());
                    System.out.println(message1);
                    //FAZER PARSE DA MENSAGEM e agir de acordo
                    pares = parsing.MessageParsing(message1);
                    idPackRecebido = pares[0];
                    if( idPackRecebido.equals(pack_ID) )
                        return pares;

                } catch (SocketTimeoutException e) {
                    // timeout exception.
                    System.out.println("Timeout reached!!! " + e);
                    socket1.close();
                    //removo o que nao consigo aceder da lista
                    removeIDArrayList(idMulticast);
                    //aqui tenho de escolher um id aleatório do array
                    String novoID = randomIDMulticast();

                    //send to newID
                    return sendMulticast(parameter, novoID, pack_ID);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket1.close();
        }

        return pares;
    }

    private void notificar(String username, String notificacao) throws RemoteException{
        //e no caso do cliente nao estar no hashmap?
        RMIClient_I c = clientes.get(username);

        if( ! notificacao.equals("nulo")) {
            System.out.println("vou tentar imprimir");
            try {
                c.print_on_client(notificacao);
            } catch (Exception e) {
                System.out.println("OFFLINE");
                clientes.remove(username);
                //Nao consigo aceder ao cliente, quer dizer que esta offline
                String[] pares = sendMulticast(";type|add;username|" + username + ";msg|" + notificacao, randomIDMulticast(), String.valueOf(newIDCounter()));
            }

        }

    }

    public void initialize_server() throws RemoteException {
        try {
            try {
                System.out.println("Searching for main server...");
                //isto vai mandar exception se o Primário nao existir
                System.out.println("primario existe?");
                Naming.lookup("PRIMARY");
                System.out.println("primario existe");

                //----------------- continua se já existir Primário (quer esteja on ou off)


                try{
                    System.out.println("secundario existe?");
                    //se já existir secundario, quer dizer que este é o server primario a tentar conectar-se ( server original estava off)
                    Naming.lookup("SECONDARY");
                    System.out.println("secundario existe");
                    Naming.rebind("PRIMARY",this);

                }
                catch(Exception e) {

                    System.out.println("secundario nao existe lol");
                    //se existir primario e nao existir secundario, vai-se ligar como se fosse secundario como normalmente
                    Naming.rebind("SECONDARY", this);
                    System.out.println("Secondary Server ready.");
                    //vai agora para uma thread que tem um while em que a cada 5 segundos vê se o servidor principal está online
                    Check check = new Check();
                    check.start();

                }




            } catch (Exception NotBoundException) {
                System.out.println("Not Found.");
                Naming.rebind("PRIMARY", this);
                System.out.println("Primary Server ready.");





            }
        } catch (Exception re) {
            System.out.println("Exception in HelloImpl.main: " + re);
        }



        MulticastReceiver mr = new MulticastReceiver(this);
        new Thread(mr).start();

    }

    public void print_on_server(String s) throws RemoteException {
        System.out.println("> " + s);
    }

    public boolean registo(/*RMIClient_I c,*/ String username, String password) throws RemoteException {

        int idMulticast = 1;

        String [] pares = sendMulticast(";type|register;username|"+username+";password|"+password, String.valueOf(idMulticast),  String.valueOf(newIDCounter()) );

        if (pares[3].equals("successful"))
            return true;
        else
            return false;
    }

    public boolean userMatchesPassword(String username, String password){
        System.out.println("opa provavelmente sei la");
        return true;
    }

    public boolean login(/*RMIClient_I c,*/ String username, String password) throws RemoteException {

        System.out.println("ola");
        String [] pares = sendMulticast(";type|login;username|"+username+";password|"+password,   randomIDMulticast(),  String.valueOf(newIDCounter()) );

        if (pares[4].equals("on")) {
            //c tem de dar update do username
            //c tem de dar print do menu
            System.out.println("ola");
            //clientes.put(pares[3], c);
            notificar(pares[3], pares[5]);
            return true;
        }
        //off
        else {
            System.out.println("correu mal");
            return false;
        }

    }

    public boolean makeEditor(RMIClient_I c, String username, String newEditor) throws RemoteException {

        String [] pares = sendMulticast(";type|make editor;username|" + username+";new_editor|"+newEditor,   randomIDMulticast(),  String.valueOf(newIDCounter()) );

        if (pares[4].equals("successful")) {
            notificar(newEditor,"Welcome to The Editors");
            return true;
        }
        //off
        else {
            System.out.println("correu mal");
            return false;
        }



    }

    public String searchMusic(RMIClient_I c, String searchType, String chave) throws RemoteException {


        String [] pares = sendMulticast(";type|search music;search_type|" + searchType + ";msg|" + chave,   randomIDMulticast(),  String.valueOf(newIDCounter()) );

        if (pares[3].equals("successful")) {
            return pares[5];
        }
        //off
        else {
            System.out.println("correu mal");
            return "unsuccessful";
        }





    }

    public boolean albumReview(RMIClient_I c, String username , String nomeAlbum, String rating, String texto) throws RemoteException {



        String [] pares = sendMulticast( ";type|review;username|"+username+";album|"+nomeAlbum+";rating|"+rating+";text|"+texto,   randomIDMulticast(),  String.valueOf(newIDCounter()) );


        if (pares[3].equals("successful")) {

            return true;
        }
        //off
        else {
            return false;
        }




    }

    public boolean manageData(RMIClient_I c, String use, String tipoS, String nome, String tipoO, String tipoI, String msg) throws RemoteException{


        String [] pares = sendMulticast(";type|edit info;username|"+use+";search_type|"+tipoS+";search_name|"+nome+";op_type|"+tipoO+";field_type|"+tipoI+";msg|"+msg,randomIDMulticast(),  String.valueOf(newIDCounter()) );


        if(tipoO.equals("biography")||tipoO.equals("description")){
            String[] users = pares[5].split("-");
            for(String u : users)
                notificar(u,"Editaram-lhe uma descrição ou biografia");
        }


        if (pares[3].equals("successful")) {
            return true;
        }
        //off
        else {
            return false;
        }



    }

    public String ver_dados(RMIClient_I c, String searchType, String msg) throws RemoteException {

        String [] pares = sendMulticast(";type|search info;search_type|" + searchType + ";msg|" + msg,randomIDMulticast() , String.valueOf(newIDCounter()) );

        System.out.println(Arrays.toString(pares));

        if (pares[3].equals("successful")) {
            return pares[4];
        }
        //off
        else {
            System.out.println("correu mal");
            return "unsuccessful";
        }


    }

    public static void main(String args[]) {


        //System.getProperties().put("java.security.policy", "policy.all");
        // System.setSecurityManager(new RMISecurityManager());

        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        try {

            RMIServer h = new RMIServer();
            System.out.println("oula");
            h.initialize_server();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Something went wrong.");
        }


    }
}