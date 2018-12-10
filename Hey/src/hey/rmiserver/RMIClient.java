package hey.rmiserver;

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

// is used for exporting a remote object with Java Remote Method Protocol (JRMP)
// and obtaining a stub that communicates to the remote object.

public class RMIClient extends UnicastRemoteObject implements RMIClient_I {

    private String username = null;

    RMIClient() throws RemoteException {
        super();
    }

    public void updateUsername(String u) throws RemoteException{
        this.username = u;
    }

    public void print_on_client(String s) throws RemoteException {
        System.out.print("\n"+s+"\n");
    }

    public String read_on_client() throws RemoteException{
        String a ="";
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        try {

            //hey.rmiserver.RMIServer_I h = (hey.rmiserver.RMIServer_I) Naming.lookup("XPTO");
            //lookup ( "rmi://localhost:7000/benfica")
            //hey.rmiserver.RMIClient c = new hey.rmiserver.RMIClient();
            //h.subscribe(args[0], (hey.rmiserver.RMIClient_I) c);
            //System.out.println("Client sent subscription to server");
            System.out.print("> ");
            a = reader.readLine();
            while(a.equals("")){
                System.out.println("Insira novamente");
                System.out.print("> ");
                a = reader.readLine();
            }



        } catch (Exception e) {
            System.out.println("Exception in main: " + e);
        }
        return a;
    }

    //--------------------------------------------------------------------------

    public RMIServer_I ServerChooser() throws RemoteException{
        try{
            RMIServer_I h = (RMIServer_I ) Naming.lookup("PRIMARY");
            try{
                h.pingCliente(this,username);
                return h;
            }
            catch(Exception e){
                try{
                    System.out.println("O primario nao esta on.");
                    RMIServer_I h1 = (RMIServer_I )Naming.lookup("SECONDARY");
                    h1.pingCliente(this,username);
                    //isto quer dizer que o secundário esta on e o primario esta off
                    return h1;
                }
                catch(Exception e2){
                    System.out.println("o secundario nao esta on,  clock of doom started");
                    long start = System.currentTimeMillis();
                    long end = start + 30*1000;
                    while(System.currentTimeMillis() < end){
                        RMIServer_I h2 = (RMIServer_I )Naming.lookup("PRIMARY");
                        try{
                            h2.pingCliente(this,username);
                            System.out.println("e nao é que deu o primario");
                            return h2;
                        }catch(Exception e4){
                            RMIServer_I h3 = (RMIServer_I )Naming.lookup("SECONDARY");
                            try{
                                h3.pingCliente(this,username);
                                System.out.println("e nao é que deu o secundario");
                                return h3;
                            }catch(Exception e5){
                                System.out.println("try again");
                            }
                        }
                    }
                    System.exit(0);
                }
            }
        }
        catch(Exception e3){
            System.out.println("Não existe servidor primário");
        }

        return null;



    }

    public void initializeClient(RMIServer_I h) throws RemoteException{

       //try {


            h.pingCliente(this,username);
            menu_inicial( ServerChooser() );



            /*
        } catch (Exception e) {
            System.out.println("n me digas que entraste aqui");
            //aqui damos ping do servidor primario
            menu_inicial(ServerChooser());
        }*/

    }

    public void menu_inicial(RMIServer_I h)throws RemoteException {

        print_on_client("Menu:\nLogin\nRegister\n");
        String opcao = read_on_client();
        System.out.println("Insira username:");
        String user = read_on_client();
        System.out.println("Insira password:");
        String pass = read_on_client();
        boolean valor;
        //aqui tenho de ver se a hashmap já tem algum cliente
        if (opcao.equals("Login")) {

                valor = ServerChooser().login(  user, pass);

                if(valor){
                    System.out.println("Login efetuado com sucesso");
                    username = user;
                    menu(ServerChooser());
                }
                else{
                    System.out.println("Erro no login");
                    menu_inicial(ServerChooser());
                }
            }
         else if (opcao.equals("Register")){

                valor = ServerChooser().registo( user, pass);
                if(valor){
                    System.out.println("Registo efetuado com sucesso! Pode agora fazer login");
                    menu_inicial(ServerChooser());
                }
                else {
                    System.out.println("Erro no registo");
                    menu_inicial(ServerChooser());
                }
            }



    }

    public void menu( RMIServer_I h)throws RemoteException{


        //algumas opções só devem aparecer se o cliente é editor
        //as opçoes só dos editores adicionamos no fim para ser mais fácil

        print_on_client("Menu:\nsearch info\npesquisar\nfazer editor\nreview\nmanage data\nlog out");

        String opcao = read_on_client();

        boolean valor;

        //notificações

        switch( opcao ){

            //Ver dados
            case "search info":

                System.out.println("Insira a área\n\talbum\n\tartist\n\tmusic");
                String searchType = read_on_client();
                System.out.println("Insira ");
                String search = read_on_client();

                //chamar um método do hey.rmiserver que pede a info ao hey.multicast server
                String info = ServerChooser().ver_dados(this,searchType,search)  ;
                if(!info.equals("unsuccessful")) {
                    System.out.println(info);
                }
                else {
                    System.out.println("Erro no search info");
                }

                break;

            //Pesquisar
            case "pesquisar":

                System.out.println("Insira search type\n\talbum\n\tartist\n\tmusic\n\tgenre");
                String searchT = read_on_client();
                System.out.println("Insira \n");
                String s = read_on_client();

                String searchM = ServerChooser().searchMusic(this,searchT,s) ;
                if(!searchM.equals("unsuccessful")) {
                    System.out.println(searchM);
                }
                else {
                    System.out.println("Erro no search music");
                }


                break;

            case "fazer editor":

                System.out.println("Insira username do novo editor");
                String newEd = read_on_client();

                valor = ServerChooser().makeEditor(username,newEd);

                if(valor){
                    System.out.println("Efetuado com sucesso");
                }
                else {
                    System.out.println("Erro no make editor");
                }


                break;

            case "review":

                System.out.println("Insira o nome de album");
                String nAlbum = read_on_client();
                System.out.println("Insira uma pontuação");
                String pont = read_on_client();
                System.out.println("Insira uma critica");
                String review = read_on_client();

                while (review.length() > 300) {
                    System.out.println("Erro na escrita da hey.multicast.Review: excede os 300 carateres");
                    review = read_on_client();

                }

                valor = ServerChooser().albumReview(this,username,nAlbum,pont,review);

                if (valor){
                    System.out.println("Operacao efetuada com sucesso");
                }
                else{
                    System.out.println("Erro no review");
                }



                break;

            case "manage data":

                System.out.println("Insira search type\n\talbum\n\tartist\n\tmusic");
                String tipoS = read_on_client();
                System.out.println("Nome que quer adicionar/editar/remover?");
                String nome = read_on_client();
                System.out.println("Insira o tipo de operacao:\n\tinsert\n\tchange\n\tremove");
                String tipoO = read_on_client();
                String msg="null";
                String tipoI = "null";
                if(tipoO.equals("insert")){
                    if(tipoS.equals("artist"))
                        System.out.println("grupo-data de nascimento-genre-biography-numero de musicas a adicionar-nome da musica");
                    else if (tipoS.equals("album"))
                        System.out.println("artista-descricao do album-numero de musicas a adicionar-nome da musica");
                    else
                        System.out.println("artista-genre-album-duracao em segundos");
                        msg = read_on_client();
                }
                else if (tipoO.equals("change"))
                {
                    System.out.println("Escolha o campo que pretende alterar: \n\tname\n\tgrupo\n\tdata_nasc\n\tgenre\n\tdescription\n\tbiography\n\talbum name\n\tartist\n\tduration\n\talbum");
                    tipoI = read_on_client();
                }


                valor = ServerChooser().manageData(this,username,tipoS,nome,tipoO,tipoI,msg);

                if (valor){
                    System.out.println("Operacao efetuada com sucesso");
                }
                else{
                    System.out.println("Erro no manage data");
                }

                break;

            case "log out":
                System.out.println("Logging out...");
                h.logOut(this, username);
                username = null;
                System.exit(0);
                break;


        }

        menu(ServerChooser());

    }

//------------------------------------------------------

    public static void main(String args[]) {

        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        String a;

        try {
            RMIClient c = new RMIClient();

            try{

                RMIServer_I h = (RMIServer_I) Naming.lookup("PRIMARY");
                c.initializeClient(h);


            }
            catch(Exception NotBoundException){
                System.out.println("Ainda nao existe servidor primario.");
            }



        }catch(Exception e){
            System.out.println("correu mal");
        }
    }



}
