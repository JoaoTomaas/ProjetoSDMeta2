package hey.rmiserver;

import java.io.Serializable;
import java.net.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import static java.lang.Thread.sleep;

class User implements Serializable {
    private String username, password, user_type;

    public User(String username, String password, String user_type) {
        this.username = username;
        this.password = password;
        this.user_type = user_type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_type() { return user_type; }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}

class Music implements Serializable {
    private static final long serialVersionUID = 4L;
    private String artist, genre, album, name, flag; //Nao sei se fica String album ou se mudo para hey.rmiserver.Album album
    private int duration;

    private ArrayList <String>  allowed_users = new ArrayList<>();

    public Music(String artist, String genre, String album, String name, int duration, String flag, ArrayList <String> allowed_users) {
        this.artist = artist;
        this.genre = genre;
        this.album = album;
        this.name = name;
        this.duration = duration;
        this.flag = flag;
        this.allowed_users = allowed_users;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getAlbum_title() {
        return album;
    }

    public String getName() {
        return name;
    }

    public String getAlbum() {
        return album;
    }

    public String getFlag() {
        return flag;
    }

    public ArrayList<String> getAllowed_users() {
        return allowed_users;
    }

    public int getDuration() {
        return duration;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setAllowed_users(ArrayList<String> allowed_users) {
        this.allowed_users = allowed_users;
    }
}

class Album implements Serializable {
    private static final long serialVersionUID = -7470644985434923303L;
    private String album_name, description;
    private double rating; //rating médio //Nao sei se fica cá ou se o retiro
    private String artist;
    private ArrayList <Music> music_list = new ArrayList<>();
    private ArrayList <Review> review_list = new ArrayList<>();

    private ArrayList <String> not_others = new ArrayList<>();

    public Album(String album_name, String artist, String description, int rating, ArrayList<Music> music_list, ArrayList<Review> review_list, ArrayList <String> not_others) {
        this.album_name = album_name;
        this.description = description;
        this.rating = rating;
        this.artist = artist;
        this.music_list = music_list;
        this.review_list = review_list;
        this.not_others = not_others;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        rating = average_rating();
        return rating;
    }

    public ArrayList<Music> getMusic_list() {
        return music_list;
    }

    public ArrayList<Review> getReview_list() {
        if (review_list == null){
            System.out.println("OH GOD HELP ME PLEASE");
        }
        return review_list;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtist() {
        return artist;
    }

    public double average_rating (){
        int soma = 0;
        int size = review_list.size();
        double average_rating;

        for (Review r: review_list){
            soma += r.getRating();
        }

        average_rating = (double) soma / size;

        return average_rating;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public ArrayList<String> getNot_others() {
        return not_others;
    }

    public void setNot_others(ArrayList<String> not_others) {
        this.not_others = not_others;
    }
}

class Review implements Serializable {
    private String text;
    private int rating;
    private String user;

    public Review(String text, int rating, String user) {
        this.text = text;
        this.rating = rating;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public String getUser() {
        return user;
    }

}

class Artist implements  Serializable{
    private static final long serialVersionUID = -2240199346454994060L;
    private String nome, grupo, genre, biography;
    private String data_nasc; //TALVIZ CRIAR UMA CLASSE DATA
    ArrayList <Music> music_list;

    private ArrayList <String> notificar = new ArrayList<>();

    public Artist(String nome, String grupo, String data_nasc, String genre, String biography, ArrayList <Music> music_list, ArrayList <String> notificar) {
        this.nome = nome;
        this.grupo = grupo;
        this.data_nasc = data_nasc;
        this.genre = genre;
        this.biography = biography;
        this.music_list = music_list;
        this.notificar = notificar;
    }

    public String getNome() {
        return nome;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getData_nasc() {
        return data_nasc;
    }

    public String getGenre() {
        return genre;
    }

    public String getBiography() {
        return biography;
    }

    public ArrayList<Music> getMusic_list() {
        return music_list;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public void setMusic_list(ArrayList<Music> music_list) {
        this.music_list = music_list;
    }

    public ArrayList<String> getNotificar() {
        return notificar;
    }

    public void setNotificar(ArrayList<String> notificar) {
        this.notificar = notificar;
    }

}

class Notification implements  Serializable{
    private String username, message;

    public Notification(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}


class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    //int thread_number;
    String flag;

    public Connection (Socket aClientSocket, String flag) {
        //thread_number = numero;
        try{
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start(); //chama o método run()
        }catch(IOException e){System.out.println("hey.rmiserver.Connection:" + e.getMessage());}
    }
    //=============================
    public void run() {
        //String resposta;
        if (flag.equals("upload")) {

            try {
                while (true) {
                    //an echo server
                    String data = in.readUTF();
                    //System.out.println("T["+thread_number + "] Recebeu: "+data);
                    //Alterado
                    //resposta=data.toUpperCase();
                    //out.writeUTF(data);
                }
            } catch (EOFException e) {
                System.out.println("EOF:" + e);
            } catch (IOException e) {
                System.out.println("IO:" + e);
            }
        } //multiplos do sector size, talvez 4k no meu computador
        else{//CÓDIGO PARA FAZER DOWNLOAD
            try {
                System.out.println("A enviar ficheiro...");
                File f = new File ("C:\\Users\\joaom\\Desktop"); //AQUI É QUE VOU PÔR A DIRETORIA
                FileInputStream fin = new FileInputStream(f);
                //long file_size = (int) f.length();

                byte buffer [] = new byte [4096];
                int read;

                //out.writeUTF(Long.toString(file_size));
                //out.flush();

                while ((read = fin.read(buffer)) != -1){
                    out.write(buffer, 0, read);
                    out.flush();
                }
                fin.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

class DatabaseConnection implements Serializable{
    //Ver como ir buscar a música a uma diretoria e colocar música nessa diretoria

    public DatabaseConnection() {
    }

    boolean username_match (String username, int server_id){

        ArrayList <User> users = new ArrayList<>();

        //Abrir para a leitura do ficheiro de objetos
        try (ObjectInputStream oin = new ObjectInputStream (new FileInputStream("Users" + server_id + "_obj.txt"))) {
            users = (ArrayList) oin.readObject();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (User u: users){
            if (u.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    boolean password_match (String username, String password, int server_id){
        //Tenho que procurar por username e depois por password
        //Porque se procuro apenas pela password, pode encontrar a password certa, mas ser de outro utilizador

        ArrayList <User> users = new ArrayList<>();

        try (ObjectInputStream oin = new ObjectInputStream (new FileInputStream("Users" + server_id + "_obj.txt"))) {
            users = (ArrayList) oin.readObject();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (User u: users) {
            if (u.getUsername().equals(username)) {
                if (u.getPassword().equals(password)) {
                    return true;
                }
            }

        }
        return false;
    }

    void write_user_file(ArrayList <User> users, int server_id) { //Para escrever no ficheiro de utilizadores
        try {
            ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("Users" + server_id + "_obj.txt"));
            oS.writeObject(users);
            oS.close();

        } catch (IOException e) {
            System.out.print("ERRO");
            System.out.printf("Ocorreu a exceçao %s ao escrever no ficheiro\n", e);
        }
    }

    ArrayList <Album> get_AlbumList (int server_id){
        ArrayList <Album> album_list = new ArrayList<>();

        try (ObjectInputStream oin = new ObjectInputStream (new FileInputStream("AlbumList" + server_id + "_obj.txt"))) {
            album_list = (ArrayList) oin.readObject();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return album_list;
    }

    void write_albumlist_file (ArrayList <Album> album_list, int server_id) { //Para escrever no ficheiro de álbuns
        try {
            ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("AlbumList" + server_id + "_obj.txt"));
            oS.writeObject(album_list);
            oS.close();

        } catch (IOException e) {
            System.out.print("ERRO");
            System.out.printf("Ocorreu a exceçao %s ao escrever no ficheiro\n", e);
        }
    }

    void write_artislist_file (ArrayList <Artist> artist_list, int server_id) {

        try {
            ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("ArtistList" + server_id + "_obj.txt"));
            oS.writeObject(artist_list);
            oS.close();

        } catch (IOException e) {
            System.out.print("ERRO");
            System.out.printf("Ocorreu a exceçao %s ao escrever no ficheiro\n", e);
        }
    }

    void write_musiclist_file (ArrayList <Music> music_list, int server_id){
        try {
            ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("MusicList" + server_id +"_obj.txt"));
            oS.writeObject(music_list);
            oS.close();

        } catch (IOException e) {
            System.out.print("ERRO");
            System.out.printf("Ocorreu a exceçao %s ao escrever no ficheiro\n", e);
        }

    }

    ArrayList <Music> get_MusicList (int server_id){
        ArrayList <Music> music_list = new ArrayList<>();

        try (ObjectInputStream oin = new ObjectInputStream (new FileInputStream("MusicList" + server_id + "_obj.txt"))) {
            music_list = (ArrayList) oin.readObject();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return music_list;
    }

    ArrayList <Artist> get_ArtistList (int server_id){
        ArrayList <Artist> artist_list = new ArrayList<>();

        try (ObjectInputStream oin = new ObjectInputStream (new FileInputStream("ArtistList" + server_id + "_obj.txt"))) {
            artist_list = (ArrayList) oin.readObject();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return artist_list;
    }

    ArrayList <User> get_UserList (int server_id){
        ArrayList <User> user_list = new ArrayList<>();

        try (ObjectInputStream oin = new ObjectInputStream (new FileInputStream("Users" + server_id + "_obj.txt"))) {
            user_list = (ArrayList) oin.readObject();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return user_list;
    }

    void write_notifications_file (ArrayList <Notification> notific_list, int server_id){
        try {
            ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("NotificList" + server_id + "_obj.txt"));
            oS.writeObject(notific_list);
            oS.close();

        } catch (IOException e) {
            System.out.print("ERRO");
            System.out.printf("Ocorreu a exceçao %s ao escrever no ficheiro\n", e);
        }


    }

    ArrayList <Notification> get_NotificationList (int server_id){
        ArrayList <Notification> notific_list = new ArrayList<>();

        try (ObjectInputStream oin = new ObjectInputStream (new FileInputStream("NotificList" + server_id + "_obj.txt"))) {
            notific_list = (ArrayList) oin.readObject();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return notific_list;
    }

    String check (String user, int server_id){
        ArrayList <Notification> not_list = get_NotificationList(server_id);

        Iterator <Notification> iter = not_list.iterator();

        String send = "nulo";
        int conta = 0;
        for (Notification n: not_list){
            if (n.getUsername().equals(user)){
                if (send.equals("nulo")) send = "";
                //send = send + "item_" + conta +"_name|" +  n.getMessage() + ";";
                if (!n.getMessage().equals("nulo")) send = send + "item_" + conta + "_name>>" + n.getMessage() + "\n";
                //send = send + "item_" + conta + "_name>>" + n.getMessage() + "-";
                conta++;
            }
        }

        while (iter.hasNext()) {
            if(iter.next().getUsername().equals(user)){
                iter.remove();
            }
        }

        write_notifications_file(not_list, server_id); //Vai escrever o novo arraylist no ficheiro de objetos

        return send;
    }

}

class AutoMessage extends Thread{

    private int id_server;
    private MulticastSocket socket;
    private String MULTICAST_ADDRESS = "224.3.2.1";

    public AutoMessage (int id_server, MulticastSocket socket) throws UnknownHostException {
        this.id_server = id_server;
        this.socket = socket;
    }

    public void run() {
        String automsg = "pack_id|0;" + "server_id|" +id_server;

        while (true) {
            try{
                byte[] buffer = automsg.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), 4444);
                socket.send(packet);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                sleep((long)(5000));
            } catch (InterruptedException e) {
            }

        }
    }
}

class RedundantAnswer extends Thread implements Serializable{

    private String message;
    private int server_id;

    MessageParsing parsing = new MessageParsing();
    DatabaseConnection data = new DatabaseConnection();

    public RedundantAnswer (String message, int server_id) {
        this.message = message;
        this.server_id = server_id;
    }

    void login (String username, String password, int server_id) {
        System.out.println("O cliente deseja fazer login");
        System.out.println("Username inserido: " + username);
        System.out.println("Password inserida: " + password);

        if (data.username_match(username, server_id)) {
            if (data.password_match(username, password, server_id)) { //SUCESSO
                String notif = data.check(username, server_id); //Vai checkar se há notificações a ser entregues
                System.out.println("Login efetuado com sucesso: " + "notifications|" + notif);
            } else { //PASSWORD ERRADA
                System.out.println("Password incorreta");
            }
        } else { //UTILIZADOR NAO EXISTE
            System.out.println("Utilizador nao existe");
        }
    }

    void register (String username, String password, int server_id) {

        ArrayList <User> users = new ArrayList<>(); //ArrayList que contém todos os utilizadores
        //Abrir para a leitura do ficheiro de objetos
        try (ObjectInputStream oin = new ObjectInputStream (new FileInputStream("Users" + server_id + "_obj.txt"))) {
            users = (ArrayList) oin.readObject();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Temos que ver se existe o username ou não
        if (!data.username_match(username, server_id)) {

            //Abrir para escrita no ficheiro de objetos
            if (users.isEmpty()){ //Se der mal, vou ver se o tamanho e zero
                User u = new User(username, password, "admin");
                users.add(u);
            }
            else{
                User u = new User(username, password, "normal");
                users.add(u);
            }

            try {
                ObjectOutputStream oS = new ObjectOutputStream(new FileOutputStream("Users" + server_id +"_obj.txt"));
                oS.writeObject(users);
                oS.close();

            } catch (IOException e) {
                System.out.print("ERRO");
                System.out.printf("Ocorreu a exceçao %s ao escrever no ficheiro\n", e);
            }

            System.out.println("Registo bem sucedido");
        }

        else {
            System.out.println("esse username ja existe");
        }

        //SÓ PARA VER QUEM TENHO REGISTADO
        /*for (hey.rmiserver.User uss: users){
            System.out.println(uss.getUsername() + " " + uss.getPassword() + " -> " + uss.getUser_type());
        }*/


    }

    void make_editor (String username, String novo_editor, int server_id){
        System.out.println("O cliente deseja tornar o utilizador " + novo_editor + " editor");

        ArrayList <User> users = new ArrayList<>();

        try (ObjectInputStream oin = new ObjectInputStream (new FileInputStream("Users" + server_id + "_obj.txt"))) {
            users = (ArrayList) oin.readObject();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Verificar se o utilizador que está a querer tornar o outro num editor é também um editor
        if (data.username_match(username, server_id)) { //SUCESSO
            for (User u : users) {
                if (u.getUsername().equals(username)) {
                    if ((u.getUser_type().equals("editor")) || (u.getUser_type().equals("admin"))) { //SE TEM PERMISSAO -> CONDICAO 1
                        System.out.println("Tem permissao para tornar o utilizador " + novo_editor + " editor");
                        for (User us : users) {
                            if (us.getUsername().equals(novo_editor)) {
                                us.setUser_type("editor");
                                data.write_user_file(users, server_id);
                                //ENVIAR RESPOSTA PARA O RMI SERVER
                                System.out.println("You can edito now");
                            }
                        }
                    }
                    else{ //SE NAO TEM PERMISSAO -> CONDICAO 2
                        System.out.println("You don't have permission to perform this operation");
                    }
                }
            }

        }

        ArrayList <String> test = new ArrayList<>();
        for (User u: users){
            test.add(u.getUsername());
        }

        if (!test.contains(novo_editor)){
            System.out.println("The person you entered was not found");
        }
    }

    void add (String user, String text, int server_id){
        ArrayList <Notification> n_list = data.get_NotificationList(server_id);
        Notification notif = new Notification(user, text);
        n_list.add(notif);

        data.write_notifications_file(n_list, server_id);

        System.out.println("hey.rmiserver.Notification added to the list");
    }

    void album_review (String user, String album_title, String rating, String text, int server_id){ //TESTAR
        System.out.println("O cliente " + user + " pretende escrever uma crítica a um álbum");

        //1. TENHO QUE ABRIR O FICHEIRO E PÔR LÁ A INFORMACAO
        ArrayList <Album> album_list = data.get_AlbumList(server_id);
        Review r = new Review(text, Integer.parseInt(rating), user);

        for (Album a: album_list) {
            if (a.getAlbum_name().equals(album_title)) {
                a.getReview_list().add(r); //Adicionar a review à lista de reviews
                data.write_albumlist_file(album_list, server_id);

                System.out.println("Your review was submitted");
            }
        }

        ArrayList<String> aux = new ArrayList<>();

        for (Album a: album_list) {
            aux.add(a.getAlbum_name());
        }

        if (!aux.contains(album_title)){//ÁLBUM NÃO CONSTA DA BASE DE DADOS
            System.out.println("The selected album is not in our database");
        }

    }

    void manage_data (String username, String choice, String search_name, String op_type, String field_type, String name, int server_id) {
        System.out.println("O cliente pretende gerir artistas, álbuns e músicas");

        //1. Primeiro tenho que ver se o utilizador que quer editar a info é editor
        ArrayList<User> users = data.get_UserList(server_id);

        ArrayList<Artist> artist_list = data.get_ArtistList(server_id);
        ArrayList<Album> album_list = data.get_AlbumList(server_id);
        ArrayList<Music> music_list = data.get_MusicList(server_id);

        if (data.username_match(username, server_id)) { //SUCESSO
            for (User u : users) {
                if (u.getUsername().equals(username)) {
                    if (u.getUser_type().equals("editor") || u.getUser_type().equals("admin")) {
                        System.out.println("Tem permissao para editar");

                        switch (choice) {
                            case "artist": /*-------------------------ARTIST--------------------------*/
                                String artist_name = search_name;
                                switch (op_type){
                                    case "insert": /*-*************INSERT***************PARECE ESTAR BEM**/

                                        String [] artist_details = name.split("-");
                                        String grupo = artist_details[0];
                                        String data_nasc = artist_details[1];
                                        String genre = artist_details[2];
                                        String biography = artist_details[3];
                                        String music_counter = artist_details[4];

                                        ArrayList <Music> aux_musiclist = new ArrayList<>();
                                        Artist new_artist;
                                        ArrayList <String> allowed_users = new ArrayList<>();
                                        ArrayList <Music> music_i = new ArrayList<>();
                                        ArrayList <String> to_notify = new ArrayList<>();
                                        to_notify.add(username);

                                        if (Integer.parseInt(music_counter) != 0) {
                                            for (int j = 5; j < artist_details.length; j++) {
                                                Music m = new Music("", "", "", artist_details[j], 0, "no", allowed_users);
                                                aux_musiclist.add(m);
                                                music_list.add(m); //PORQUE TAMBÉM TEMOS QUE MANDAR AS MÚSICAS PARA O FICHEIRO DE MÚSICAS
                                            }
                                            new_artist = new Artist(artist_name, grupo, data_nasc, genre, biography, aux_musiclist, to_notify);
                                        }
                                        else{
                                            new_artist = new Artist(artist_name, grupo, data_nasc, genre, biography, music_i, to_notify);
                                        }
                                        artist_list.add(new_artist);
                                        data.write_artislist_file(artist_list, server_id);
                                        data.write_musiclist_file(music_list, server_id);

                                        System.out.println("hey.rmiserver.Artist added");
                                        break;

                                    case "change": /*-*************CHANGE**************ACHO QUE ESTA BEM, SO FALTA O CHANGE MUSIC LIST***/
                                        switch (field_type) {
                                            case "name":
                                                for (Artist a: artist_list){
                                                    if (a.getNome().equals(artist_name)){
                                                        a.setNome(name);
                                                    }
                                                }
                                                data.write_artislist_file(artist_list, server_id);
                                                break;

                                            case "grupo":
                                                for (Artist a: artist_list){
                                                    if (a.getNome().equals(artist_name)){
                                                        a.setGrupo(name);
                                                    }
                                                }
                                                data.write_artislist_file(artist_list, server_id);
                                                break;

                                            case "genre":
                                                for (Artist a: artist_list){
                                                    if (a.getNome().equals(artist_name)){
                                                        a.setGenre(name);
                                                    }
                                                }
                                                data.write_artislist_file(artist_list, server_id);
                                                break;

                                            case "biography":
                                                for (Artist a: artist_list){
                                                    if (a.getNome().equals(artist_name)){
                                                        a.setBiography(name);
                                                        a.getNotificar().add(username);
                                                    }
                                                }
                                                data.write_artislist_file(artist_list, server_id);
                                                System.out.println("hey.rmiserver.Artist biography changed");
                                                break;

                                            case "data_nasc":
                                                for (Artist a: artist_list){
                                                    if (a.getNome().equals(artist_name)){
                                                        a.setData_nasc(name);
                                                    }
                                                }
                                                data.write_artislist_file(artist_list, server_id);
                                                break;
                                            case "music list": //-----------------------------NAO FACO PUTO DE IDEIA, TENHO DÚVIDAS

                                                //Depois tenho que guardar num ficheiro a alteracao feita
                                                //data.write_artislist_file(artist_list, server_id);
                                                break;
                                        }

                                        System.out.println("hey.rmiserver.Artist info changed");
                                        break;

                                    case "remove": /*-*************REMOVE*****************/
                                        Iterator <Artist> iter = artist_list.iterator();
                                        while(iter.hasNext()){
                                            if(iter.next().getNome().equals(artist_name)){
                                                iter.remove();
                                            }
                                        }

                                        //Acho que e assim fora do ciclo que tem que estar
                                        data.write_artislist_file(artist_list, server_id);

                                        System.out.println("hey.rmiserver.Artist removed");

                                        break;
                                }
                                break;

                            case "album": /*-------------------------ALBUM-------------------------ACHO QUE ESTA BEM, SO FALTA O CHANGE MUSIC LIST-*/
                                String album_title = search_name;
                                switch (op_type) {
                                    case "insert":
                                        String [] album_details = name.split("-");
                                        String artist = album_details[0];
                                        String description = album_details[1];
                                        String music_counter = album_details[2];

                                        ArrayList <Music> aux_music = new ArrayList<>();
                                        Album new_album;

                                        //Duas arraylists apenas inicializadas para não estrabuchar
                                        ArrayList <Music> music_i = new ArrayList<>();
                                        ArrayList <Review> review_i = new ArrayList<>();

                                        ArrayList <String> allowed_users = new ArrayList<>();
                                        ArrayList <String> user_notify = new ArrayList<>();
                                        user_notify.add(username);

                                        if (Integer.parseInt(music_counter) != 0) {
                                            for (int j = 3; j < album_details.length; j++) {
                                                Music m = new Music("", "", "", album_details[j], 0, "no", allowed_users);
                                                aux_music.add(m);
                                                music_list.add(m);
                                            }
                                            new_album = new Album(album_title, artist, description, 0, aux_music, review_i, user_notify);
                                        }
                                        else{
                                            new_album = new Album(album_title, artist, description, 0, music_i, review_i, user_notify);
                                        }
                                        album_list.add(new_album);
                                        data.write_albumlist_file(album_list, server_id);
                                        data.write_musiclist_file(music_list, server_id);

                                        System.out.println("hey.rmiserver.Album added");
                                        break;

                                    case "change":
                                        switch (field_type) {
                                            case "album name":
                                                for (Album a : album_list) {
                                                    if (a.getAlbum_name().equals(album_title)) {
                                                        a.setAlbum_name(name);
                                                    }
                                                }
                                                data.write_albumlist_file(album_list, server_id);
                                                break;

                                            case "description":
                                                for (Album a : album_list) {
                                                    if (a.getAlbum_name().equals(album_title)) {
                                                        a.setDescription(name);
                                                        a.getNot_others().add(username);
                                                    }
                                                }
                                                data.write_albumlist_file(album_list, server_id);
                                                System.out.println("hey.rmiserver.Album description changed");
                                                break;

                                            case "artist":
                                                for (Album a : album_list) {
                                                    if (a.getAlbum_name().equals(album_title)) {
                                                        a.setArtist(name);
                                                    }
                                                }
                                                data.write_albumlist_file(album_list, server_id);
                                                break;

                                            case "music list": //DUVIDA //MUDO O QUÊ???????????????????????????
                                                ArrayList <String> allowed_userss = new ArrayList<>();
                                                for (Album a : album_list) {
                                                    if (a.getAlbum_name().equals(album_title)) {
                                                        Music m = new Music(null, null, null, name, 0, "no", allowed_userss); //POSSO MUDAR PARA UM CONSTRUTOR APENAS COM O CAMPO NOME
                                                        a.getMusic_list().add(m);
                                                    }
                                                }
                                                //se for para manter, depois tenho que gravar as alteracoes
                                                //data.write_albumlist_file(album_list, server_id);
                                                break;
                                        }
                                        System.out.println("hey.rmiserver.Album info changed");
                                        break;

                                    case "remove":
                                        Iterator <Album> iter = album_list.iterator();
                                        while(iter.hasNext()){
                                            if(iter.next().getAlbum_name().equals(album_title)){
                                                iter.remove();
                                            }
                                        }
                                        //Acho que tem que ser fora do ciclo
                                        data.write_albumlist_file(album_list, server_id);

                                        System.out.println("hey.rmiserver.Album removed");
                                        break;
                                }
                                break;

                            case "music": /*-------------------------MUSIC--------------------------*/
                                String music_name = search_name;
                                switch (op_type) {
                                    case "insert":
                                        String [] music_details = name.split("-");
                                        String artist = music_details[0];
                                        String genre = music_details[1];
                                        String album = music_details[2];
                                        String duration = music_details[3];

                                        ArrayList <String> allowed_users = new ArrayList<>();
                                        //pack_id|54;server_id|1;type|edit info;username|jt;search_type|music;search_name|Devia Ir;op_type|insert;field_type|null;msg|Wet Bed Gang-Hip Hop-Cenas-320
                                        //A duracao com : faz isto estrabuchar
                                        Music m = new Music(artist, genre, album, music_name, Integer.parseInt(duration), "no", allowed_users);
                                        music_list.add(m);
                                        data.write_musiclist_file(music_list, server_id);

                                        System.out.println("hey.rmiserver.Music added");
                                        break;

                                    case "change":
                                        switch (field_type) {
                                            case "name":
                                                for (Music mu : music_list) {
                                                    if(mu.getName().equals(music_name)){
                                                        mu.setName(name);
                                                    }
                                                }
                                                data.write_musiclist_file(music_list, server_id);
                                                break;

                                            case "genre":
                                                for (Music mu : music_list) {
                                                    if(mu.getName().equals(music_name)){
                                                        mu.setGenre(name);
                                                    }
                                                }
                                                data.write_musiclist_file(music_list, server_id);
                                                break;

                                            case "artist":
                                                for (Music mu : music_list) {
                                                    if(mu.getName().equals(music_name)){
                                                        mu.setArtist(name);
                                                    }
                                                }
                                                data.write_musiclist_file(music_list, server_id);
                                                break;

                                            case "duration":
                                                for (Music mu : music_list) {
                                                    if(mu.getName().equals(music_name)){
                                                        mu.setDuration(Integer.parseInt(name));
                                                    }
                                                }
                                                data.write_musiclist_file(music_list, server_id);
                                                break;

                                            case "album":
                                                for (Music mu : music_list) {
                                                    if(mu.getName().equals(music_name)){
                                                        mu.setAlbum(name); //VER SE POSSO FAZER ASSIM OU SE TEM QUE SER UM ARRAYLIST ONDE TENHO QUE ADICIONAR O OBJETO
                                                    }
                                                }
                                                data.write_musiclist_file(music_list, server_id);
                                                break;
                                        }

                                        System.out.println("hey.rmiserver.Music info changed");
                                        break;

                                    case "remove":
                                        Iterator <Music> iter = music_list.iterator();
                                        while(iter.hasNext()){
                                            if(iter.next().getName().equals(music_name)){
                                                iter.remove();
                                            }
                                        }

                                        data.write_musiclist_file(music_list, server_id);

                                        System.out.println("hey.rmiserver.Music removed");
                                        break;
                                }
                                break;

                        }
                    }
                    else{
                        System.out.println("You don't have permission to perform this operation");
                    }
                }
            }
        }
    }

    public void run(){
        //Parsing da mensagem
        String [] pares = parsing.MessageParsing(message);
        String type = pares[2];

        switch (type) {
            case "login":
                login(pares[3], pares[4], server_id);
                break;

            case "make editor":
                String username = pares[3];
                String new_editor = pares[4];
                make_editor(username, new_editor, server_id); //EDITOR
                break;

            case "register":
                register(pares[3], pares[4], server_id);
                break;

            case "edit info":
                String user = pares[3];
                String search = pares[4];
                String search_name = pares[5];
                String op_type = pares[6];
                String field_type = pares[7];
                String msg = pares[8];
                manage_data(user, search, search_name, op_type, field_type, msg, server_id); //EDITOR
                break;

            case "review":
                String userr = pares[3];
                String album_name = pares[4];
                String rating = pares[5];
                String review_text = pares[6];
                album_review(userr, album_name, rating, review_text, server_id); //ANY USER
                break;

            /*case "share music": //DEPOIS
                String Uuser = pares[3];
                String musicc = pares[4];
                //String counter = pares[5]; //ACHO QUE NAO VOU PRECISAR DISTO PARA NADA
                ArrayList <String> lista = new ArrayList<>();
                for (int j = 6; j < pares.length; j++){
                    lista.add(pares[j]);
                }
                share_music(Uuser, musicc, lista, server_id);
                break;*/

            case "add":
                String us = pares[3];
                String txt = pares[4];
                add(us, txt, server_id);
                break;

            default:
                System.out.println("Esta funcao nao e de escrita, logo nao vou fazer nada");
                break;
        }




    }

}

class MessageParsing{

    public String [] MessageParsing (String message){

        //Separar por ";" e saber o número de pares
        String [] pares  = message.split(";");
        String  [] campos = new String[pares.length] ;

        //if (pares.length >= 3){ //NAO SEI SE PRECISO DESTA CONDICAO OU NAO, TENHO QUE TER ATENCAO POR CAUSA DA EXCEPTION
        for (int i = 0; i < pares.length; i++){
            String [] aux = pares[i].split("\\|");
            campos[i] = aux[1];
        }
        //}

        //É melhor imprimir num ciclo que vai ate ao total de elementos do array, caso contrario opde criar exception
        //System.out.println ("Tipo da mensagem: " + campos[0] + " campo2: " + campos[1] + " campo3: " + campos[2]);

        return campos;
    }

}


class UDPMulticastProtocol implements Serializable {

    private static final long serialVersionUID = 123;
    private String MULTICAST_ADDRESS = "224.3.2.1";
    private int PORT = 4444; //Porto de envio
    DatabaseConnection data = new DatabaseConnection();

    public UDPMulticastProtocol() {
    }

    void login (String pack_id, String username, String password, MulticastSocket socket, int server_id, java.sql.Connection connection) throws SQLException{
        System.out.println("O cliente deseja fazer login");
        System.out.println("Username inserido: " + username);
        System.out.println("Password inserida: " + password);
        //Temos que ir à base de dados checkar se o username existe e qual a password associada e se é igual à inserida ou não

        String SQL = "select username, password from utilizador where username = ?";
        PreparedStatement pstmt = connection.prepareStatement(SQL);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("Esse mano ja existe na base de dados");

            if (password.equals(rs.getString(2))) {
                System.out.println("Password correta, welcome to DropMusic");

                String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|login" + ";username|" + username +";status|on; " + "notifications|"; // + notif;
                //As notificações estao em comentario porque supostamente ja nao sao precisas para esta meta

                try{
                    byte[] buffer = rsp.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Password errada, try again!");
                String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|login" + ";username|" + username + ";status|off; " + "msg|Password incorreta";

                try{
                    byte[] buffer = rsp.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                    socket.send(packet);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else { //UTILIZADOR NAO EXISTE
            System.out.println("Tens que te registar mano");
            String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|login" + ";username|" + username + ";status|off; " + "msg|Utilizador nao existe";

            try{
                byte[] buffer = rsp.getBytes();
                InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                socket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        rs.close();
        pstmt.close();
    }

    void register (String pack_id, String username, String password, MulticastSocket socket, int server_id, java.sql.Connection connection) throws IOException, SQLException {

        System.out.println("O cliente deseja registar-se");
        System.out.println("Username inserido: " + username);
        System.out.println("Password inserida: " + password);


        String SQL = "select username, password from utilizador where username = ?";
        PreparedStatement pstmt = connection.prepareStatement(SQL);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("Esse mano ja se encontra registado");

            String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|register;" + "status|unsuccessful; " + "msg|Esse username ja existe";

            try{
                byte[] buffer = rsp.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                socket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //Verificar se é o primeiro elemento a ser introduzido, para o nomear como editor ou admin
            String SQLCONTA = "SELECT COUNT (*) FROM utilizador"; //Para verificar se a tabela está vazia ou não
            PreparedStatement ps = connection.prepareStatement(SQLCONTA);
            ResultSet r = ps.executeQuery();

            if (r.next()) { //Se for admin
                if (Integer.parseInt(r.getString(1)) == 0) {
                    String SQLRegAdmin = "insert into utilizador values (?, ?, ?)";
                    PreparedStatement pp = connection.prepareStatement(SQLRegAdmin);
                    pp.setString(1, username);
                    pp.setString(2, password);
                    pp.setString(3, "admin");
                    pp.executeUpdate();
                    System.out.println("Utilizador registado como admin, welcome to DropMusic");
                    pp.close();

                    //Foi registado como utilizador porque e o primeiro mano a ser registado
                    String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|register;" + "status|successful; " + "msg|Welcome to DropMusic";
                    try{
                        byte[] buffer = rsp.getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                        socket.send(packet);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else { //Se for normal
                    String SQLRegNormal = "insert into utilizador values (?, ?, ?)";
                    PreparedStatement p = connection.prepareStatement(SQLRegNormal);
                    p.setString(1, username);
                    p.setString(2, password);
                    p.setString(3, "normal");
                    p.executeUpdate();
                    System.out.println("Utilizador registado como normal, welcome to DropMusic");
                    p.close();

                    //Foi registado como normal porque foi um mano qualquer a ser registado
                    String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|register;" + "status|successful; " + "msg|Welcome to DropMusic";
                    try{
                        byte[] buffer = rsp.getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                        socket.send(packet);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            ps.close();
            r.close();
        }

        rs.close();
        pstmt.close();



    }

    void search_music (String pack_id, String choice, String name, MulticastSocket socket, int server_id, java.sql.Connection connection) throws SQLException{
        System.out.println("O cliente deseja procurar musica");

        /********************************************************************ALL*************************************************************************/
        if (choice.equals("all")) {
            String ALLSQL = "SELECT music_name FROM public.musica WHERE music_name = ? " +
                    "UNION " +

                    "SELECT music_name FROM musica, musicas_de_artista,artista " +
                    "WHERE musica.id_musica = musicas_de_artista.id_musica AND musicas_de_artista.id_artista = artista.id_artista " +
                    "AND artista.artist_name = ? " +
                    "UNION " +

                    "SELECT music_name FROM musica, musicas_de_album, album WHERE musica.id_musica = musicas_de_album.id_musica AND musicas_de_album.id_album = album.id_album " +
                    "AND album.album_name = ? " +
                    "UNION " +

                    "SELECT music_name FROM public.musica WHERE genre = ?";

            PreparedStatement pstmt = connection.prepareStatement(ALLSQL);
            pstmt.setString(1, name);
            pstmt.setString(2, name);
            pstmt.setString(3, name);
            pstmt.setString(4, name);
            ResultSet rs = pstmt.executeQuery();

            String st1 = "";
            int conta1 = 0;

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
            while (rs.next()) {
                //System.out.println("Nome da musica: " + rs.getString(1));
                st1 = st1 + "music_" + conta1 + "_name>" + rs.getString(1) + "-";
                conta1++;
            }


            /********************************************************************MUSIC*************************************************************************/
        } else if (choice.equals("music")) {
            String MUSICSQL = "SELECT music_name FROM public.musica WHERE music_name = ?";
            PreparedStatement pstmt = connection.prepareStatement(MUSICSQL);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            String s = "";
            int item_size = 0;

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
            while (rs.next()) {
                //System.out.println("Nome da musica: " + rs.getString(1));
                s = s +  "music_"+item_size+"_name>" + rs.getString(1) + "-";
                item_size++;
            }

            //2. ENVIAR RESPOSTA PARA O CLIENTE COM A LISTA DE MÚSICAS
            String ans = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|search music" + ";status|successful;" + "music_count|" + item_size +";music_list|" +  s;
            try {
                byte[] buffer = ans.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /********************************************************************ARTIST*************************************************************************/
        } else if (choice.equals("artist")) {
            String ARTISTSQL = "SELECT music_name FROM musica, musicas_de_artista,artista " +
                    "WHERE musica.id_musica = musicas_de_artista.id_musica AND musicas_de_artista.id_artista = artista.id_artista " +
                    "AND artista.artist_name = ?";
            PreparedStatement pstmt = connection.prepareStatement(ARTISTSQL);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            String z = "";
            int tam = 0;

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
            while (rs.next()) {
                //System.out.println("Nome da musica: " + rs.getString(1));
                z = z + "music_"+ tam +"_name>" + rs.getString(1) + "-";
                tam++;
            }

            //2. ENVIAR RESPOSTA PARA O CLIENTE COM A LISTA DE MÚSICAS
            String resp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|search music;" + "status|successful; " + "music_count|" + tam + ";music_list|" + z;
            try {
                byte[] buffer = resp.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /********************************************************************ALBUM*************************************************************************/
        } else if (choice.equals("album")) {
            String ALBUMSQL = "SELECT music_name FROM musica, musicas_de_album, album WHERE musica.id_musica = musicas_de_album.id_musica AND musicas_de_album.id_album = album.id_album " +
                    "AND album.album_name = ?";
            PreparedStatement pstmt = connection.prepareStatement(ALBUMSQL);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            String x = "";
            int conta = 0;

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
            while (rs.next()) {
                //System.out.println("Nome da musica: " + rs.getString(1));
                x = x + "music_" + conta + "_name>" + rs.getString(1) + "-";
                conta++;
            }

            String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|search music;" + "status|successful; " + "music_count|" + conta + ";music_list|" + x;
            try{
                byte[] buffer = rsp.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /********************************************************************GENRE*************************************************************************/
        } else if (choice.equals("genre")) {
            String GENRESQL = "SELECT music_name FROM public.musica WHERE genre = ?";
            PreparedStatement pstmt = connection.prepareStatement(GENRESQL);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            String aux = "";
            int counter = 0;

            //TRATAR DA INFORMAÇÃO RECEBIDA NO RESULT SET
            while (rs.next()) {
                //System.out.println("Nome da musica: " + rs.getString(1))
                aux = aux + "item_"+ counter +"_name>" + rs.getString(1) + "-";
                counter++;
            }

            //2. ENVIAR RESPOSTA PARA O CLIENTE COM A LISTA DE MÚSICAS
            String envia = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|search music;" + "status|successful; " + "music_count|" + counter + ";music_list|" + aux;
            try {
                byte[] buffer = envia.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    void make_editor (String pack_id, String username, String novo_editor, MulticastSocket socket, int server_id, java.sql.Connection connection) throws SQLException{
        System.out.println("O cliente deseja tornar o utilizador " + novo_editor + " editor");

    //Verificar se o utilizador que está a querer tornar o outro num editor é também um editor
        String CheckPermissaoSQL = "SELECT user_type FROM utilizador WHERE username = ?";
        PreparedStatement p = connection.prepareStatement(CheckPermissaoSQL);
        p.setString(1, username);
        ResultSet r = p.executeQuery();

        //SE TEM PERMISSAO -> CONDICAO 1
        while (r.next()) {
            if (r.getString(1).equals("editor") || r.getString(1).equals("admin")) {
                System.out.println("Pode tornar editor");

                //Verificar se o novo_user existe na base de dados
                String NovoUserExisteSQL = "SELECT username FROM utilizador WHERE username = ?";
                PreparedStatement pstt = connection.prepareStatement(NovoUserExisteSQL);
                pstt.setString(1, novo_editor);
                ResultSet rrst = pstt.executeQuery();

                if (rrst.next()) {
                    System.out.println("Utilizador existe");

                    //Fazer verificacao intermedia para ver se a pessoa ja e editora ou admin
                    String CheckIfEditSQL = "SELECT user_type FROM utilizador WHERE username = ?";
                    PreparedStatement pst = connection.prepareStatement(CheckIfEditSQL);
                    pst.setString(1, novo_editor);
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        if (rs.getString(1).equals("normal")) {
                            String MakeEditorSQL = "UPDATE utilizador SET user_type = 'editor' WHERE username = ?";
                            PreparedStatement ps = connection.prepareStatement(MakeEditorSQL);
                            ps.setString(1, novo_editor);
                            ps.executeUpdate();

                            //ENVIAR RESPOSTA PARA O RMI SERVER
                            String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|make editor" + ";new_editor|" + novo_editor + ";status|successful; " + "msg|You can edit now";
                            try {
                                byte[] buffer = rsp.getBytes();
                                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                socket.send(packet);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            System.out.println("Esse mano ja tem permissoes de editor");
                            //TAMBEM E PRECISO PARA ESTE OU NAO??
                        }
                    }
                }
                else {
                    System.out.println("Esse mano nao existe na nossa base de dados");
                    try{ //CASO NAO SEJA ENCONTRADO A PESSOA QUE NÓS QUEREMOS NOMEAR COMO EDITOR
                        String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|make editor"  + ";new_editor|" + novo_editor + ";status|unsuccessful; " + "msg|The person you entered was not found";
                        byte[] buffer = rsp.getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                        socket.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //SE NAO TEM PERMISSAO -> CONDICAO 2
            else {
                System.out.println("Nao tem autorizacao");
                String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|make editor" + ";new_editor|" + novo_editor + ";status|unsuccessful; " + "msg|You don't have permission to perform this operation";
                try {
                    byte[] buffer = rsp.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }

    }

    void manage_data (String pack_id, String username, String choice, String search_name, String op_type, String field_type, String name, MulticastSocket socket, int server_id, java.sql.Connection connection) throws SQLException {
        System.out.println("O cliente pretende gerir artistas, álbuns e músicas");

        //1. Primeiro tenho que ver se o utilizador que quer editar a info é editor
        ArrayList<User> users = data.get_UserList(server_id);

        ArrayList<Artist> artist_list = data.get_ArtistList(server_id);
        ArrayList<Album> album_list = data.get_AlbumList(server_id);
        ArrayList<Music> music_list = data.get_MusicList(server_id);

        if (data.username_match(username, server_id)) { //SUCESSO
            for (User u : users) {
                if (u.getUsername().equals(username)) {
                    if (u.getUser_type().equals("editor") || u.getUser_type().equals("admin")) {
                        System.out.println("Tem permissao para editar");

                        switch (choice) {
                            case "artist": /*-------------------------ARTIST--------------------------*/
                                String artist_name = search_name;
                                switch (op_type){
                                    case "insert": /*-*************INSERT***************PARECE ESTAR BEM**/

                                        String [] artist_details = name.split("-");
                                        String grupo = artist_details[0];
                                        String data_nasc = artist_details[1];
                                        String genre = artist_details[2];
                                        String biography = artist_details[3];
                                        //String music_counter = artist_details[4];
                                        /*
                                        ArrayList <Music> aux_musiclist = new ArrayList<>();
                                        Artist new_artist;
                                        ArrayList <String> allowed_users = new ArrayList<>();
                                        ArrayList <Music> music_i = new ArrayList<>();

                                        if (Integer.parseInt(music_counter) != 0) {
                                            for (int j = 5; j < artist_details.length; j++) {
                                                Music m = new Music(search_name, "", "", artist_details[j], 0, "no", allowed_users);
                                                aux_musiclist.add(m);
                                                music_list.add(m); //PORQUE TAMBÉM TEMOS QUE MANDAR AS MÚSICAS PARA O FICHEIRO DE MÚSICAS
                                            }
                                            new_artist = new Artist(artist_name, grupo, data_nasc, genre, biography, aux_musiclist, to_notify);
                                        }
                                        else{
                                            new_artist = new Artist(artist_name, grupo, data_nasc, genre, biography, music_i, to_notify);
                                        }
                                        artist_list.add(new_artist);
                                        data.write_artislist_file(artist_list, server_id);
                                        data.write_musiclist_file(music_list, server_id);*/

                                        //Funcao para inserir o artista na base de dados
                                        executa_insert_artista(username, genre, biography, grupo, data_nasc, artist_name, connection);

                                        String rsp_client = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|Artista adicionado";
                                        try{
                                            byte[] buffer = rsp_client.getBytes();
                                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                                            socket.send(packet);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;

                                    case "change": /*-*************CHANGE**************ACHO QUE ESTA BEM, SO FALTA O CHANGE MUSIC LIST***/
                                        switch (field_type) {
                                            case "name":
                                                for (Artist a: artist_list){
                                                    if (a.getNome().equals(artist_name)){
                                                        a.setNome(name);
                                                    }
                                                }

                                                //executa_update(username, choice, field_type, name, "name", String search_name, java.sql.Connection connection);

                                                data.write_artislist_file(artist_list, server_id);
                                                break;

                                            case "grupo":
                                                for (Artist a: artist_list){
                                                    if (a.getNome().equals(artist_name)){
                                                        a.setGrupo(name);
                                                    }
                                                }
                                                data.write_artislist_file(artist_list, server_id);
                                                break;

                                            case "genre":
                                                for (Artist a: artist_list){
                                                    if (a.getNome().equals(artist_name)){
                                                        a.setGenre(name);
                                                    }
                                                }
                                                data.write_artislist_file(artist_list, server_id);
                                                break;

                                            case "biography":
                                                for (Artist a: artist_list){
                                                    if (a.getNome().equals(artist_name)){
                                                        a.setBiography(name);
                                                        a.getNotificar().add(username);
                                                    }
                                                }
                                                data.write_artislist_file(artist_list, server_id);

                                                String ux = "";
                                                for (Artist a: artist_list){
                                                    for (int j = 0; j < a.getNotificar().size(); j++){
                                                        ux = ux + a.getNotificar().get(j) + "-";
                                                    }
                                                }

                                                String rr = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Artist biography changed;" + "users|" + ux;
                                                try{
                                                    byte[] buffer = rr.getBytes();
                                                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                                    socket.send(packet);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                break;

                                            case "data_nasc":
                                                for (Artist a: artist_list){
                                                    if (a.getNome().equals(artist_name)){
                                                        a.setData_nasc(name);
                                                    }
                                                }
                                                data.write_artislist_file(artist_list, server_id);
                                                break;
                                            case "music list": //-----------------------------NAO FACO PUTO DE IDEIA, TENHO DÚVIDAS

                                                //Depois tenho que guardar num ficheiro a alteracao feita
                                                //data.write_artislist_file(artist_list, server_id);
                                                break;
                                        }

                                        String r = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Artist info changed";
                                        try{
                                            byte[] buffer = r.getBytes();
                                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                            socket.send(packet);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;

                                    case "remove": /*-*************REMOVE*****************/
                                        Iterator <Artist> iter = artist_list.iterator();
                                        while(iter.hasNext()){
                                            if(iter.next().getNome().equals(artist_name)){
                                                iter.remove();
                                            }
                                        }

                                        //Acho que e assim fora do ciclo que tem que estar
                                        data.write_artislist_file(artist_list, server_id);

                                        String rr = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Artist removed";
                                        try{
                                            byte[] buffer = rr.getBytes();
                                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                            socket.send(packet);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        break;
                                }
                                break;

                            case "album": /*-------------------------ALBUM-------------------------*/
                                String album_title = search_name;
                                switch (op_type) {
                                    case "insert":
                                        String [] album_details = name.split("-");
                                        String artist = album_details[0];
                                        String description = album_details[1];
                                        String data = album_details[2]; //ADICIONEI NOVO
                                        String genre = album_details[3]; //ADICIONEI NOVO
                                        //String music_counter = album_details[2];
                                        //ArrayList <Music> aux_music = new ArrayList<>();
                                        //Album new_album;

                                        //Duas arraylists apenas inicializadas para não estrabuchar
                                        //ArrayList <Music> music_i = new ArrayList<>();
                                        //ArrayList <Review> review_i = new ArrayList<>();

                                       // ArrayList <String> allowed_users = new ArrayList<>();
                                        //ArrayList <String> user_notify = new ArrayList<>();
                                        //user_notify.add(username);
                                        /*
                                        if (Integer.parseInt(music_counter) != 0) {
                                            for (int j = 3; j < album_details.length; j++) {
                                                Music m = new Music(artist, "", "", album_details[j], 0, "no", allowed_users);
                                                aux_music.add(m);
                                                music_list.add(m);
                                            }
                                            new_album = new Album(album_title, artist, description, 0, aux_music, review_i, user_notify);
                                        }
                                        else{
                                            new_album = new Album(album_title, artist, description, 0, music_i, review_i, user_notify);
                                        }
                                        album_list.add(new_album);
                                        data.write_albumlist_file(album_list, server_id);
                                        data.write_musiclist_file(music_list, server_id);*/


                                        //Funcao para inserir o album na base de dados
                                        executa_insert_album(username, album_title, description, data, artist, genre, connection);

                                        String rsp_client = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|Album added";
                                        try{
                                            byte[] buffer = rsp_client.getBytes();
                                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                            socket.send(packet);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;

                                    case "change":
                                        switch (field_type) {
                                            case "album name":
                                                for (Album a : album_list) {
                                                    if (a.getAlbum_name().equals(album_title)) {
                                                        a.setAlbum_name(name);
                                                    }
                                                }
                                                data.write_albumlist_file(album_list, server_id);
                                                break;

                                            case "description":
                                                for (Album a : album_list) {
                                                    if (a.getAlbum_name().equals(album_title)) {
                                                        a.setDescription(name);
                                                        a.getNot_others().add(username);
                                                    }
                                                }
                                                data.write_albumlist_file(album_list, server_id);

                                                String uax = "";
                                                for (Artist a: artist_list){
                                                    for (int j = 0; j < a.getNotificar().size(); j++){
                                                        uax = uax + a.getNotificar().get(j) + "-";
                                                    }
                                                }

                                                String r1 = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Album description changed;" + "users|" + uax;
                                                try{
                                                    byte[] buffer = r1.getBytes();
                                                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                                    socket.send(packet);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                break;

                                            case "artist":
                                                for (Album a : album_list) {
                                                    if (a.getAlbum_name().equals(album_title)) {
                                                        a.setArtist(name);
                                                    }
                                                }
                                                data.write_albumlist_file(album_list, server_id);
                                                break;

                                            case "music list":
                                                ArrayList <String> allowed_userss = new ArrayList<>();
                                                for (Album a : album_list) {
                                                    if (a.getAlbum_name().equals(album_title)) {
                                                        Music m = new Music(null, null, null, name, 0, "no", allowed_userss); //POSSO MUDAR PARA UM CONSTRUTOR APENAS COM O CAMPO NOME
                                                        a.getMusic_list().add(m);
                                                    }
                                                }

                                                data.write_albumlist_file(album_list, server_id);
                                                break;
                                        }
                                        String rsp_send = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Album info changed";
                                        try{
                                            byte[] buffer = rsp_send.getBytes();
                                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                            socket.send(packet);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;

                                    case "remove":
                                        Iterator <Album> iter = album_list.iterator();
                                        while(iter.hasNext()){
                                            if(iter.next().getAlbum_name().equals(album_title)){
                                                iter.remove();
                                            }
                                        }

                                        //Acho que tem que ser fora do ciclo
                                        data.write_albumlist_file(album_list, server_id);

                                        String r = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Album removed";
                                        try{
                                            byte[] buffer = r.getBytes();
                                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                            socket.send(packet);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                                break;

                            case "music": /*-------------------------MUSIC--------------------------*/
                                String music_name = search_name;
                                switch (op_type) {
                                    case "insert":
                                        String [] music_details = name.split("-");
                                        String artist = music_details[0];
                                        String genre = music_details[1];
                                        String album = music_details[2];
                                        String duration = music_details[3];

                                        ArrayList <String> allowed_users = new ArrayList<>();
                                        //pack_id|54;server_id|1;type|edit info;username|jt;search_type|music;search_name|Devia Ir;op_type|insert;field_type|null;msg|Wet Bed Gang-Hip Hop-Cenas-320
                                        //A duracao com : faz isto estrabuchar

                                        //Funcao para adicionar a musica na base de dados
                                        executa_insert_musica(username, music_name, artist, album, genre, duration, connection);

                                        String rsp_client = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|Musica added";
                                        try{
                                            byte[] buffer = rsp_client.getBytes();
                                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                            socket.send(packet);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;

                                    case "change":
                                        switch (field_type) {
                                            case "name":
                                                for (Music mu : music_list) {
                                                    if(mu.getName().equals(music_name)){
                                                        mu.setName(name);
                                                    }
                                                }
                                                data.write_musiclist_file(music_list, server_id);
                                                break;

                                            case "genre":
                                                for (Music mu : music_list) {
                                                    if(mu.getName().equals(music_name)){
                                                        mu.setGenre(name);
                                                    }
                                                }
                                                data.write_musiclist_file(music_list, server_id);
                                                break;

                                            case "artist":
                                                for (Music mu : music_list) {
                                                    if(mu.getName().equals(music_name)){
                                                        mu.setArtist(name);
                                                    }
                                                }
                                                data.write_musiclist_file(music_list, server_id);
                                                break;

                                            case "duration":
                                                for (Music mu : music_list) {
                                                    if(mu.getName().equals(music_name)){
                                                        mu.setDuration(Integer.parseInt(name));
                                                    }
                                                }
                                                data.write_musiclist_file(music_list, server_id);
                                                break;

                                            case "album":
                                                for (Music mu : music_list) {
                                                    if(mu.getName().equals(music_name)){
                                                        mu.setAlbum(name); //VER SE POSSO FAZER ASSIM OU SE TEM QUE SER UM ARRAYLIST ONDE TENHO QUE ADICIONAR O OBJETO
                                                    }
                                                }
                                                data.write_musiclist_file(music_list, server_id);
                                                break;
                                        }

                                        String r1 = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Music info changed";
                                        try{
                                            byte[] buffer = r1.getBytes();
                                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                            socket.send(packet);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;

                                    case "remove":
                                        Iterator <Music> iter = music_list.iterator();
                                        while(iter.hasNext()){
                                            if(iter.next().getName().equals(music_name)){
                                                iter.remove();
                                            }
                                        }

                                        data.write_musiclist_file(music_list, server_id);

                                        String r = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Music removed";
                                        try{
                                            byte[] buffer = r.getBytes();
                                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                                            socket.send(packet);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                                break;

                        }
                    }
                    else{
                        String ss = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|unsuccessful; " + "msg|You don't have permission to perform this operation";
                        try{
                            byte[] buffer = ss.getBytes();
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                            socket.send(packet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    void search_info (String pack_id, String choice, String msg, MulticastSocket socket, int server_id, java.sql.Connection connection) throws SQLException {
        System.out.println("O cliente pretende consultar detalhes sobre álbum e sobre artista");

        //Musica
        if (choice.equals("music")) {
            //Info da musica
            String InfoMusicSQL = "SELECT music_name, artist_name, album_name, genre, duration FROM musica WHERE music_name = ?";
            PreparedStatement p = connection.prepareStatement(InfoMusicSQL);
            p.setString(1, msg);
            ResultSet rst = p.executeQuery();

            String resp_aux = "";

            //Lista de criticas da musica
            //TESTAR e VERIFICAR se e necessario inserir o atributo rating na musica tal como existe no album
            String MusicReviewList = "SELECT username, pontuacao, text FROM critica, musica WHERE critica.id_musica = musica.id_musica AND music_name = ?";
            PreparedStatement ps = connection.prepareStatement(MusicReviewList);
            ps.setString(1, msg);
            ResultSet rs = ps.executeQuery();

            while (rst.next()) {
                //System.out.println("Nome da musica: " + rst.getString(1));
                //System.out.println("Nome do artista: " + rst.getString(2));
                //System.out.println("Nome do album: " + rst.getString(3));
                //System.out.println("Genero msuical: " + rst.getString(4));
                //System.out.println("Duracao da musica: " + rst.getString(5));
                resp_aux = resp_aux + "music_name>" + rst.getString(1) + "-album_title>" + rst.getString(3) + "-genre>" + rst.getString(4) +
                        "-artist_name>" + rst.getString(2) + "-duration>" + rst.getString(5);
            }

            String resp = "pack_id|" + pack_id + ";server_id|" + server_id + ";" + "type|search info;" + "status|successful;" + "msg|" + resp_aux;
            //ENVIAR RESPOSTA PARA O CLIENTE
            try {
                byte[] buffer = resp.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            rst.close();
            rs.close();
            ps.close();
            p.close();
        }

        //Artista
        else if (choice.equals("artist")) {
            //Info do artista
            String InfoArtistSQL = "SELECT artist_name, birth_date, biography, genre, individuo, grupo, letrista, compositor FROM artista WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(InfoArtistSQL);
            ps.setString(1, msg);
            ResultSet rs = ps.executeQuery();

            String msg_send = "";
            String musicas = "";
            int contalm = 0;

            while (rs.next()) {
                //System.out.println("Nome do artista: " + rs.getString(1));
                //System.out.println("Data de aniversario: " + rs.getString(2));
                //System.out.println("Biografia: " + rs.getString(3));
                //System.out.println("Genero musical:  " + rs.getString(4));
                msg_send = msg_send + "group>" + rs.getString(1) + "-data_nasc>" + rs.getString(2) + "-genre>" + rs.getString(4) + "-biography>" + rs.getString(3);
            }


            //Lista de musicas do artista
            String ArtistMusicList = "SELECT music_name, musica.artist_name, album_name, musica.genre, duration FROM musica,musicas_de_artista, artista " +
                    "WHERE musica.id_musica = musicas_de_artista.id_musica AND musicas_de_artista.id_artista = artista.id_artista AND musica.artist_name = ?";
            PreparedStatement p = connection.prepareStatement(ArtistMusicList);
            p.setString(1, msg);
            ResultSet r = p.executeQuery();

            System.out.println("Lista de Musicas: ");
            while (r.next()) {
                //System.out.print("Nome da musica: " + r.getString(1) + "||");
                //System.out.print("Nome do album: " + r.getString(3) + "||");
                //System.out.print("Genero musical: " + r.getString(4) + "||");
                //System.out.println("Duracao da musica: " + r.getString(5));
                musicas = musicas + "item_" + contalm + "_name" + r.getString(1);
            }

            //Lista de albuns do artista
            /*String ArtistAlbumList = "SELECT album_name, artist_name, description, release_date, genre, rating FROM album WHERE artist_name = ?";
            PreparedStatement pst = connection.prepareStatement(ArtistAlbumList);
            pst.setString(1, msg);
            ResultSet rst = pst.executeQuery();

            System.out.println("Lista de Albuns: ");
            while (rst.next()) {
                System.out.println("Nome do album: " + rst.getString(1));
                System.out.println("Nome do artista: " + rst.getString(2));
                System.out.println("Descricao " + rst.getString(3));
                System.out.println("Data de lancamento: " + rst.getString(4));
                System.out.println("Genero musical: " + rst.getString(5));
                System.out.println("Rating: " + rst.getString(6));
            }*/

            String env_answer = "pack_id|" + pack_id + ";server_id|" + server_id + ";" + "type|search info" + ";status|successful;" + "msg|" + "artist_info>>" + msg_send + "-music list>>" + musicas;
            //ENVIAR RESPOSTA PARA O CLIENTE
            try {
                byte[] buffer = env_answer.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }


            p.close();
            ps.close();
            //pst.close();
            r.close();
            rs.close();
            //rst.close();
        }

        //Album
        else if (choice.equals("album")) {
            //Info do album
            String InfoAlbumSQL = "SELECT album_name, artist_name, description, release_date, genre, rating FROM album WHERE album_name = ?";
            PreparedStatement p = connection.prepareStatement(InfoAlbumSQL);
            p.setString(1, msg);
            ResultSet r = p.executeQuery();

            String info = "";
            String lista_music = "music_list>>";
            String lista_review = "review_list>>";
            int conta_music = 0;
            int conta_review = 0;

            //Info mais geral do album
            while (r.next()) {
                //System.out.println("Nome do album: " + r.getString(1));
                //System.out.println("Nome do artista: " + r.getString(2));
                //System.out.println("Descricao " + r.getString(3));
                //System.out.println("Data de lancamento: " + r.getString(4));
                //System.out.println("Genero musical: " + r.getString(5));
                //System.out.println("Rating : " + r.getString(6));
                info = info + "album_name>" + r.getString(1) + "-artist_name>" + r.getString(2) + "-description>" + r.getString(3) + "-average_rating>" + r.getString(6) + "-";
            }


            //Lista de musicas do album
            String AlbumMusicList = "SELECT music_name, musica.artist_name, musica.album_name, musica.genre, duration FROM musica, musicas_de_album, album WHERE musica.id_musica = musicas_de_album.id_musica AND " +
                    "musicas_de_album.id_album = album.id_album AND album.album_name = ?";
            PreparedStatement ps = connection.prepareStatement(AlbumMusicList);
            ps.setString(1, msg);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //System.out.println("Nome da musica: " + rs.getString(1));
                //System.out.println("Nome do artista: " + rs.getString(2));
                //System.out.println("Nome do album: " + rs.getString(3));
                //System.out.println("Genero musical: " + rs.getString(4));
                //System.out.println("Duracao da musica : " + rs.getString(5));
                lista_music = lista_music + "item_" + conta_music + "_name>" + rs.getString(1) + "-";
            }

            //Lista de reviews do album
            String AlbumReviewList = "SELECT username, pontuacao, text FROM critica WHERE id_album = " +
                    "(SELECT id_album FROM album WHERE album_name = ?)";
            PreparedStatement pst = connection.prepareStatement(AlbumReviewList);
            pst.setString(1, msg);
            ResultSet rst = pst.executeQuery();

            while (rst.next()) {
                //System.out.println("Username: " + rst.getString(1));
                //System.out.println("Pontuacao: " + rst.getString(2));
                //System.out.println("Texto: " + rst.getString(3));
                lista_review += "item_" + conta_review + "_name>>" + "-user>" + rs.getString(1) + "-rating>" + rs.getString(2) +
                        "-review>" + rs.getString(3) + "-";
            }

            String answer = "pack_id|" + pack_id + ";" + "server_id|" + server_id + ";" + "type|search info" + ";status|successful" + ";msg|" + info + lista_music + lista_review;
            //ENVIAR RESPOSTA PARA O CLIENTE
            try {
                byte[] buffer = answer.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            p.close();
            ps.close();
            pst.close();
            r.close();
            rs.close();
            rst.close();
        }
        }

    void album_review (String pack_id, String user, String album_title, String rating, String text, MulticastSocket socket, int server_id, java.sql.Connection connection) throws SQLException { //TESTAR
        System.out.println("O cliente " + user + " pretende escrever uma crítica a um álbum");

        //Verificar se o album existe ou nao
        String CheckaAlbumSQL = "SELECT * from album WHERE album_name = ?";
        PreparedStatement ppss = connection.prepareStatement(CheckaAlbumSQL);
        ppss.setString(1, album_title);
        ResultSet rrss = ppss.executeQuery();

        //SE O ALBUM EXISTIR
        if (rrss.next()) {
            String AlbumReviewSQL = "INSERT INTO critica\n" +
                    "VALUES (DEFAULT, null, (SELECT id_album FROM album WHERE album_name = ?), ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(AlbumReviewSQL);
            ps.setString(1, album_title);
            ps.setString(2, user);
            ps.setString(3, text);
            ps.setInt(4, Integer.parseInt(rating));
            ps.executeUpdate();

            String CalcRatingSQL = "SELECT ROUND(AVG(pontuacao),1) FROM critica WHERE id_album = " +
                    "(SELECT id_album FROM album WHERE album_name = ?)";
            PreparedStatement pst = connection.prepareStatement(CalcRatingSQL);
            pst.setString(1, album_title);
            ResultSet rs = pst.executeQuery();

            float rate = 20.0f;
            if (rs.next()) {
                rate = rs.getFloat(1);
            }

            String UpdateRatingSQL = "UPDATE album SET rating = ? WHERE album_name = ?";
            PreparedStatement pstt = connection.prepareStatement(UpdateRatingSQL);
            pstt.setFloat(1, rate);
            pstt.setString(2, album_title);
            pstt.executeUpdate();

            System.out.println("Review do album submetida com sucesso");
            ps.close();

            String rsp = "pack_id|" + pack_id + ";server_id|" + server_id + ";type|review" + ";status|successful; " + "msg|Your review was submitted";
            try {
                byte[] buffer = rsp.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            //ÁLBUM NÃO CONSTA DA BASE DE DADOS
            String rsp = "pack_id|" + pack_id  + ";server_id|" + server_id + ";type|review" + ";status|unsuccessful; " + "msg|The selected album is not in our database";
            try {
                byte[] buffer = rsp.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    void download (String pack_id, String user, String music_name, MulticastSocket socket, int server_id){ //EM FALTA
        System.out.println("O cliente quer fazer download de uma música");

        int  porto = 80 + server_id;
        String flag = "download";

        //1. VER SE A MÚSICA EXISTE NA BASE DE DADOS
        ArrayList <Music> music_list = data.get_MusicList(server_id);

        for (Music m: music_list){
            if (m.getName().equals(music_name)) { //A MUSICA EXISTE NA BASE DE DADOS
                if (m.getFlag().equals("yes")) { //VERIFICAR SE TEM A FLAG "YES", OU SEJA, SE EXISTE O FICHEIRO MP3 PARA QUE SEJA FEITO O DOWNLOAD
                    for (int j = 0; j < m.getAllowed_users().size(); j++) {
                        if (m.getAllowed_users().get(j).equals(user)) { //USER TEM PERMISSAO PARA FAZER DOWNLOAD DA MUSICA
                            //int numero=0;
                            // = "193.137.200.14" + server_id;
                            InetAddress IP = null; //VER SE ESTA BEM INICIALIZADA
                            try{
                                int serverPort = porto;
                                System.out.println("A Escuta no Porto " + porto);
                                ServerSocket listenSocket = new ServerSocket(serverPort);
                                IP = listenSocket.getInetAddress(); //DEVE SER ASSIM QUE SE VAI ARRANJAR O ENDERE&Ccedil;O IP PARA ENVIAR, MAS AINDA TENHO QUE CONFIRMAR
                                System.out.println("LISTEN SOCKET="+listenSocket);
                                while(true) {
                                    Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                                    System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);
                                    //numero ++;
                                    new Connection(clientSocket, flag); //Thread para tratar de cada canal de comunicação com o cliente
                                }
                            }catch(IOException e){
                                System.out.println("Listen:" + e.getMessage());
                            }

                            String resp = "pack_id|" + pack_id + ";server_id|" + server_id + ";username|" + user + ";type|download" + ";status|accepted; " +
                                    "porto|" + porto + ";ip|" + IP + ";msg|Download can start";
                            try {
                                byte[] buffer = resp.getBytes();
                                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                                socket.send(packet);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        }

        //2. VER SE O UTILIZADOR TEM ACESSO A ESSA MÚSICA OU NÃO

        //3. SE AMBOS OS REQUISITOS SE CUMPRIREM
        //3.A. -> IR BUSCAR O PORTO E O ENDEREÇO IP DO SERVIDOR MULTICAST

        //3.B. -> CRIAR A SOCKET TCP E DEIXAR ABERTO À ESPERA DE UM ACCEPT PARA QUE O DOWNLOAD SEJA INICIADO
    }

    void upload (String pack_id, String user, String music_name, MulticastSocket socket, int server_id){ //EM FALTA
        System.out.println("O cliente quer fazer upload de uma música");

        String IP = "193.137.200.14" + server_id;
        int  porto = 80 + server_id;
        String flag = "upload";

        //String diretoria; //ESTA VARIAVEL SÓ INTERESSA AQUI DENTRO, QUE É PARA SABER ONDE VOU GUARDAR QUANDO FAÇO UPLOAD

        ArrayList <Music> music_list = data.get_MusicList(server_id);
        ArrayList <String> musics = new ArrayList<>();
        for (Music m: music_list) {
            if (m.getFlag().equals("yes")) {
                musics.add(m.getName());
            }
        }

        //1. VER SE A MÚSICA EXISTE NA BASE DE DADOS (SE JÁ ESTIVER, NÃO DEIXA ADICIONAR DE NOVO)
        if (!musics.contains(music_name)){
            for (Music m:music_list){
                if (m.getName().equals(music_name)){
                    m.setFlag("yes"); //ASSOCIAR O FICHEIRO QUE FOI UPLOADED, TALVEZ ADICIONAR LÁ O NOME DO FICHEIRO NUM NOVO CAMPO



                    try{ //IR BUSCAR O PORTO E O ENDEREÇO IP DO SERVIDOR MULLTICAST
                        // B -> CRIAR A SOCKET TCP E DEIXAR ABERTO À ESPERA DE UM ACCEPT PARA QUE O DOWNLOAD SEJA INICIADO
                        int serverPort = porto;
                        System.out.println("A Escuta no Porto " + porto);
                        ServerSocket listenSocket = new ServerSocket(serverPort);
                        System.out.println("LISTEN SOCKET="+listenSocket);
                        while(true) {
                            Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                            System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);

                            new Connection(clientSocket, flag); //Thread para tratar de cada canal de comunicação com o cliente
                        }
                    }catch(IOException e)
                    {System.out.println("Listen:" + e.getMessage());}



                }
            }
        }
        else{ //MANDA RESPOSTA
            String rsp = "pack_id|" + pack_id + ";server_id|" + server_id + ";type|download" + ";status|unsuccessful; " + "msg|That music file is already in our database";
            try {
                byte[] buffer = rsp.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //2. ASSOCIAR O FICHEIRO QUE FOI UPLOADED (Isto é, pôr uma String com a diretoria desse ficheiro associada a um parâmetro da classe hey.rmiserver.Music)
        //R: TEMOS A STRING DIRETORIA CASO SEJA NECESSÁRIO

    }

    void share_music (String pack_id, String user, String music_name, ArrayList<String>user_list, MulticastSocket socket, int server_id){ //EM FALTA
        System.out.println("O cliente pretende partilhar música com outros utilizadores");

        //1. PRIMEIRO QUE TUDO TENHO QUE IR AO ARRAYLIST DE USERS ASSOCIADO A ESTA MUSICA E VER SE O USER TEM ACESSO A ESTA MÚSICA
        //NA CLASSE MUSIC TENHO UM ARRAYLIST DE ALLOWED_USERS
        ArrayList <Music> music_list = data.get_MusicList(server_id);
        ArrayList <String> aux = new ArrayList<>();

        for (Music m: music_list){
            if (m.getName().equals(music_name)){ //MUSICA ENCONTRADA
                if (m.getFlag().equals("yes")){ //MUSICA EXISTE E POR ISSO PODE SER PARTILHADA
                    for (int j = 0; j < m.getAllowed_users().size(); j++){
                        if (aux.get(j).equals(user)){ //O UTILIZADOR TEM ACESSO A ESSA MÚSICA
                            m.setAllowed_users(user_list);
                            data.write_musiclist_file(music_list, server_id);

                            //ENVIAR A RESPOSTA AO CLIENTE
                            String rsp = "pack_id|" + pack_id + ";server_id|" + server_id + ";type|share music" + ";status|successful; " + "msg|The music file was shared with the selected users";
                            try {
                                byte[] buffer = rsp.getBytes();
                                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                                socket.send(packet);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            String rspa = "pack_id|" + pack_id + ";server_id|" + server_id + ";type|share music" + ";status|unsuccessful; " + "msg|This user doesn't have access to this music file";
                            try {
                                byte[] buffer = rspa.getBytes();
                                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                                socket.send(packet);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else{
                    String rs = "pack_id|" + pack_id + ";server_id|" + server_id + ";type|share music" + ";status|unsuccessful; " + "msg|This music file can't be shared";
                    try {
                        byte[] buffer = rs.getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                        socket.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                String r = "pack_id|" + pack_id + ";server_id|" + server_id + ";type|share music" + ";status|unsuccessful; " + "msg|hey.rmiserver.Music file not found";
                try {
                    byte[] buffer = r.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void add (String pack_id, String user, String text, MulticastSocket socket, int server_id){
        ArrayList <Notification> n_list = data.get_NotificationList(server_id);
        Notification notif = new Notification(user, text);
        n_list.add(notif);

        data.write_notifications_file(n_list, server_id);

        String rsp = "pack_id|" + pack_id + ";server_id|" + server_id + ";type|add" + ";status|successful; " + "msg|hey.rmiserver.Notification added to the list";
        try {
            byte[] buffer = rsp.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /*INICIO DOS METODOS IMPORTADOS DA BASE DE DADOS*/

    //DONE
    void executa_insert_musica(String username, String music_name, String artist_name, String album_name, String genre, String duration, java.sql.Connection connection) throws SQLException{

        int duracao = Integer.parseInt(duration);

        String SQLN1 = "select id_album,id_artista from albuns_de_artista\n" +
                "where \n" +
                "\tid_album = (\n" +
                "\t\tselect id_album \n" +
                "\t\tfrom album\n" +
                "\t\twhere album_name = ?\n" +
                "\t\t)\n" +
                "\n" +
                "\tand\n" +
                "\t\n" +
                "\tid_artista = (\n" +
                "\t\tselect id_artista\n" +
                "\t\tfrom artista\n" +
                "\t\twhere artist_name = ?\n" +
                "\t\t)";
        PreparedStatement pstmtN1 = connection.prepareStatement(SQLN1);
        pstmtN1.setString(1,album_name);
        pstmtN1.setString(2, artist_name);

        ResultSet rsN1 = pstmtN1.executeQuery();

        int id_album_teste = 0;
        int id_artist_teste = 0;
        while  (rsN1.next()) {
            id_album_teste = rsN1.getInt(1);
            id_artist_teste = rsN1.getInt(2);
        }


        String SQLN2 = "insert into musica values (default,null,?,?,?,?,?)";
        PreparedStatement pstmtN2 = connection.prepareStatement(SQLN2);
        //pstmtN2.setInt(1,default);
        pstmtN2.setString(1, music_name);
        pstmtN2.setString(2, artist_name);
        pstmtN2.setString(3, album_name);
        pstmtN2.setString(4, genre);
        pstmtN2.setInt(5,duracao);
        pstmtN2.executeUpdate();

        String SQLSelect = "select id_musica from musica where music_name = ?";
        PreparedStatement ps = connection.prepareStatement(SQLSelect);
        ps.setString(1,music_name);
        ResultSet rselect = ps.executeQuery();
        rselect.next();
        int id_musica = rselect.getInt(1);






        String SQLN3 = "insert into musicas_de_album values (?,?) ";
        PreparedStatement pstmtN3 = connection.prepareStatement(SQLN3);
        pstmtN3.setInt(1,id_album_teste);
        pstmtN3.setInt(2, id_musica);

        String SQLN4 = "insert into musicas_de_artista values(?,?)";
        PreparedStatement pstmtN4 = connection.prepareStatement(SQLN4);
        pstmtN4.setInt(1, id_musica);
        pstmtN4.setInt(2, id_artist_teste);

        pstmtN3.executeUpdate();
        pstmtN4.executeUpdate();



    }
    void executa_insert_artista(String username, String genre, String biography, String nome_grupo, String data_nasc, String artist_name, java.sql.Connection connection) throws SQLException{

        //String grupo = artist_details[0];
        //String data_nasc = artist_details[1];
        //String genre = artist_details[2];
        //String biography = artist_details[3];
        //String music_counter = artist_details[4];


        //String [] details = charutoUnparsed.split("-");
        //String genre =details[1];
        //String biography=details[2];

        //String start_date=details[3];
        //String artist_name=details[4];
        //String tipo=details[5];
        //String nome_grupo=details[6];

        /*
        Boolean individuo = false;
        Boolean grupo = false;
        Boolean compositor = false;
        Boolean letrista = false;
        switch (tipo){
            case "individuo":
                individuo = true;
            case "grupo":
                grupo = true;
            //case "compositor":
                compositor = true;
            case "letrista":
                letrista = true;
        }*/

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        Date date1;
        try{
            date1 = sdf1.parse(data_nasc); //start_date
        }
        catch(ParseException p){
            System.out.println("omd correu mal");
            return;

        }
        java.sql.Date birth_date = new java.sql.Date(date1.getTime());


        String SQL1 = "insert into artista values (default,?,?,?,?,?,?,?,?) ";
        PreparedStatement pstmt1 = connection.prepareStatement(SQL1);
        //pstmt1.setInt(1,id_artista);
        pstmt1.setString(1, genre);
        pstmt1.setString(2, biography);
        pstmt1.setDate(3, birth_date);
        pstmt1.setString(4, artist_name);
        //COMO E QUE VAMOS FAZER PARA DECIDIR SE E ARTISTA OU BANDA????????
        //pstmt1.setBoolean(5,individuo);
        //pstmt1.setBoolean(6,grupo);
        //pstmt1.setBoolean(7,compositor);
        //pstmt1.setBoolean(8,letrista);
        pstmt1.executeUpdate();


        //VER COMO FAZER ESTA FUNCAO MELHOR, NOMEADAMENTE ESTE IFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
        //if(individuo){
            String SQLSelect = "select id_artista from artista where artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(SQLSelect);
            ps.setString(1,artist_name);
            ResultSet rselect = ps.executeQuery();
            rselect.next();
            int id_artista = rselect.getInt(1);
            System.out.println("ADD 1");


            String SQL2 = "select id_artista from artista where artist_name = ?";
            PreparedStatement pstmt2 = connection.prepareStatement(SQL2);
            pstmt2.setString(1,nome_grupo);
            ResultSet rs = pstmt2.executeQuery();
            int art_id_artista = 0;
            while(rs.next())
                art_id_artista=rs.getInt(1);
            System.out.println("ADD 2");



            String SQL3 = "insert into pertenca values (?,?)";
            PreparedStatement pstmt3 = connection.prepareStatement(SQL3);
            pstmt3.setInt(1,art_id_artista);
            pstmt3.setInt(2,id_artista);
            pstmt3.executeUpdate();
            System.out.println("ADD FINAL");
        //}
    }
    void executa_insert_album(String username, String album_name, String description, String data, String artist_name, String genre, java.sql.Connection connection) throws SQLException {

        //split a cada espaço
        //String [] details = charutoUnparsed.split("-");
        //String album_name = details[1];
        //String description = details[2];
        //String startDate = details[3];
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        Date date1;
        try{
            date1 = sdf1.parse(data);
        }
        catch(ParseException p){
            System.out.println("excecao");
            return;
        }
        java.sql.Date release_date = new java.sql.Date(date1.getTime());

        //String artist_name = details[4];
        //String genre = details[5];



        String SQL1 = "insert into album values (default,?,?,?,?,?,null) ";
        PreparedStatement pstmt1 = connection.prepareStatement(SQL1);
        //pstmt1.setInt(1,id_album);
        pstmt1.setString(1, album_name);
        pstmt1.setString(2, description);
        pstmt1.setDate(3, release_date);
        pstmt1.setString(4, artist_name);
        pstmt1.setString(5, genre);
        pstmt1.executeUpdate();


        String SQLSelect = "select id_album from album where album_name = ?";
        PreparedStatement ps = connection.prepareStatement(SQLSelect);
        ps.setString(1,album_name);
        ResultSet rselect = ps.executeQuery();
        rselect.next();
        int id_album =  rselect.getInt(1);





        String SQL2 = "select id_artista from artista where artist_name = ?";
        PreparedStatement pstmt2 = connection.prepareStatement(SQL2);
        pstmt2.setString(1,artist_name);

        ResultSet rs = pstmt2.executeQuery();
        rs.next();
        int id_artista = rs.getInt(1);


        String SQL3 = "insert into albuns_de_artista values (?,?)";
        PreparedStatement pstmt3 = connection.prepareStatement(SQL3);
        pstmt3.setInt(1,id_album);
        pstmt3.setInt(2,id_artista);
        pstmt3.executeUpdate();




    }

    //TO DO
    void executa_update(String username, String choice, String field_type, String name, String search_type, String search_name, java.sql.Connection connection)throws SQLException{
        //String [] details = charutoUnparsed.split("-");
        //este charuto tem a choice - artist, music, album

        //String choice = details[1]; // artist ou musica ou album
        //String field_type = details[2]; // duration, birth_date, genre...
        //String name = details[3]; // quando quero mudar para "METAL"
        //String search_type = details[4]; // como vamos procurar "artist_name"
        //String search_name = details[5]; // "david bowie"


        String SQL1 = "update " + choice + " set " + field_type + " = ? where "+ search_type + " = ? ";
        PreparedStatement pstmt1 = connection.prepareStatement(SQL1);
        switch (field_type) {
            case "release_date":
            case "birth_date":

                String startDate1 = name;
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                Date date1;
                try {
                    date1 = sdf1.parse(startDate1);
                } catch (ParseException p) {
                    System.out.println("excecao");
                    return;
                }
                java.sql.Date release_date = new java.sql.Date(date1.getTime());
                pstmt1.setDate(1, release_date);
                break;
            case "duration":
                pstmt1.setInt(1, Integer.parseInt(name));
                break;
            default:
                pstmt1.setString(1, name);
                break;
        }
        switch (search_type) {
            case "release_date":
            case "birth_date":
                String startDate2 = search_name;
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                Date date2;
                try {
                    date2 = sdf2.parse(startDate2);
                } catch (ParseException p) {
                    System.out.println("excecao");
                    return;
                }
                java.sql.Date release_date1 = new java.sql.Date(date2.getTime());
                pstmt1.setDate(2, release_date1);
                break;
            case "duration":
                pstmt1.setInt(2, Integer.parseInt(search_name));
                break;
            default:
                pstmt1.setString(2, search_name);
                break;
        }
        pstmt1.executeUpdate();

    }


    void executa_delete_album(String username, String  charutoUnparsed, java.sql.Connection connection) throws  SQLException  {

        // este charuto tem a choice - artist, music, album
        // isto elimina mesmo registos da tabela, nao atributos.
        // Para atributos podemos fazer update e pomos a null ou algo assim
        String [] charutoParsed = charutoUnparsed.split("-");
        String choice = charutoParsed[1]; // artist ou musica ou album
        String field_type = charutoParsed[2]; // duration, birth_date, genre...
        String name = charutoParsed[3]; // o nome do que vou eliminar


        String SQL1 = "delete from musica\n" +
                "where id_musica = \n" +
                "\t(\n" +
                "\tselect id_musica\n" +
                "\tfrom musicas_de_album\n" +
                "\twhere id_album = \n" +
                "\t\t(\n" +
                "\t\tselect id_album\n" +
                "\t\tfrom album\n" +
                "\t\twhere "+field_type+ " = ?\n" +
                "\t\t)\n" +
                "\t)";
        PreparedStatement pstmt1 = connection.prepareStatement(SQL1);


        switch (field_type) {
            case "release_date":
            case "birth_date":

                String startDate1 = name;
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                Date date1;
                try {
                    date1 = sdf1.parse(startDate1);
                } catch (ParseException p) {
                    System.out.println("excecao no parse");
                    return;
                }
                java.sql.Date release_date = new java.sql.Date(date1.getTime());
                pstmt1.setDate(1, release_date);
                break;
            case "duration":
            case "id_album":
                pstmt1.setInt(1, Integer.parseInt(name));
                break;
            default:
                pstmt1.setString(1, name);
                break;
        }
        pstmt1.executeUpdate();

        String SQL2 = "delete from album where "+field_type+" = ?";
        PreparedStatement pstmt2 = connection.prepareStatement(SQL2);

        switch (field_type) {
            case "release_date":
            case "birth_date":

                String startDate2 = name;
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                Date date2;
                try {
                    date2 = sdf2.parse(startDate2);
                } catch (ParseException p) {
                    System.out.println("excecao no parse");
                    return;
                }
                java.sql.Date release_date2 = new java.sql.Date(date2.getTime());
                pstmt2.setDate(1, release_date2);
                break;
            case "duration":
            case "id_album":
                pstmt2.setInt(1, Integer.parseInt(name));
                break;
            default:
                pstmt2.setString(1, name);
                break;
        }
        pstmt2.executeUpdate();

    }
    void executa_delete_musica(String username, String  charutoUnparsed, java.sql.Connection connection)throws  SQLException{


        //este charuto tem a choice - artist, music, album
        // isto elimina mesmo registos da tabela, nao atributos.
        // Para atributos podemos fazer update e pomos a null ou algo assim

        String [] charutoParsed = charutoUnparsed.split("-");
        String choice = charutoParsed[1]; // artist ou musica ou album
        String field_type = charutoParsed[2]; // duration, birth_date, genre...
        String name = charutoParsed[3]; // o nome do que vou eliminar



        String SQL1 = "delete from musica where field_type = ?";
        PreparedStatement pstmt1 = connection.prepareStatement(SQL1);
        switch (field_type) {
            case "release_date":
            case "birth_date":

                String startDate1 = name;
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                Date date1;
                try {
                    date1 = sdf1.parse(startDate1);
                } catch (ParseException p) {
                    System.out.println("excecao no parse");
                    return;
                }
                java.sql.Date release_date = new java.sql.Date(date1.getTime());
                pstmt1.setDate(1, release_date);
                break;
            case "duration":
            case "id_musica":
                pstmt1.setInt(1, Integer.parseInt(name));
                break;
            default:
                pstmt1.setString(1, name);
                break;
        }
        pstmt1.executeUpdate();






    }
    void executa_delete_artista(String username, String  charutoUnparsed, java.sql.Connection connection)throws  SQLException{


        String [] charutoParsed = charutoUnparsed.split("-");
        //este charuto tem a choice - artist, music, album
        // isto elimina mesmo registos da tabela, nao atributos.
        // Para atributos podemos fazer update e pomos a null ou algo assim
        String choice = charutoParsed[1]; // artist ou musica ou album
        String field_type = charutoParsed[2]; // duration, birth_date, genre...
        String name = charutoParsed[3]; // o nome do que vou eliminar

        String SQL1 = "select id_album \n" +
                "from albuns_de_artista\n" +
                "where id_artista = \n" +
                "(\n" +
                "\tselect id_artista\n" +
                "\tfrom artista\n" +
                "\twhere "+field_type+" = ? \n" +
                ")";
        PreparedStatement pstmt1 = connection.prepareStatement(SQL1);


        switch (field_type) {
            case "release_date":
            case "birth_date":

                String startDate1 = name;
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                Date date1;
                try {
                    date1 = sdf1.parse(startDate1);
                } catch (ParseException p) {
                    System.out.println("excecao no parsezao");
                    return;
                }
                java.sql.Date release_date = new java.sql.Date(date1.getTime());
                pstmt1.setDate(1, release_date);
                break;
            case "duration":
            case "id_artista":
                pstmt1.setInt(1, Integer.parseInt(name));
                break;
            default:
                pstmt1.setString(1, name);
                break;
        }

        ResultSet rs = pstmt1.executeQuery();


        String SQL2 = "delete from artista where "+field_type+"=?";
        PreparedStatement pstmt2 = connection.prepareStatement(SQL2);

        switch (field_type) {
            case "release_date":
            case "birth_date":

                String startDate1 = name;
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                Date date1;
                try {
                    date1 = sdf1.parse(startDate1);
                } catch (ParseException p) {
                    System.out.println("excecao no parsezao");
                    return;
                }
                java.sql.Date release_date = new java.sql.Date(date1.getTime());
                pstmt2.setDate(1, release_date);
                break;
            case "duration":
            case "id_artista":
                pstmt2.setInt(1, Integer.parseInt(name));
                break;
            default:
                pstmt2.setString(1, name);
                break;
        }
        pstmt2.executeUpdate();


        while(rs.next()){
            String  arr = "delete-album-id_album-"+String.valueOf(rs.getInt(1));
            executa_delete_album("joao",arr,connection);
        }

    }


    void executa_share_music(String username, String [] charutoParsed, java.sql.Connection connection){
        String usernameShare = charutoParsed[0];
        String filename = charutoParsed[1];


        try {
            String SQL1 = "insert into acesso_a_ficheiro(username,filename) select ?,? from acesso_a_ficheiro where username = ? and filename = ?";
            PreparedStatement pstmt1 = connection.prepareStatement(SQL1);
            pstmt1.setString(1,usernameShare);
            pstmt1.setString(2,filename);
            pstmt1.setString(3,username);
            pstmt1.setString(4,filename);

            pstmt1.executeUpdate();
        }catch(Exception e){

            e.printStackTrace();
        }


    }
    void executa_upload_music(String username, String [] charutoParsed, java.sql.Connection connection)throws SQLException{

        String filename = charutoParsed[0];
        String nomeMusica = charutoParsed[1];
        String diretoria = charutoParsed[2];

        String SQLSelect = "select id_musica from musica where music_name = ?";
        PreparedStatement ps = connection.prepareStatement(SQLSelect);
        ps.setString(1,nomeMusica);
        ResultSet rSelect = ps.executeQuery();
        rSelect.next();
        int id_m = rSelect.getInt(1);

        String SQL1 = "insert into musicfile values(?,?,?)";
        PreparedStatement pstmt1 = connection.prepareStatement(SQL1);
        pstmt1.setString(1,filename);
        pstmt1.setInt(2,id_m);
        pstmt1.setString(3,diretoria);



        String SQL2 = "insert into acesso_a_ficheiro values(?,?)";
        PreparedStatement pstmt2 = connection.prepareStatement(SQL2);

        pstmt2.setString(1,username);
        pstmt2.setString(2,filename);



        pstmt1.executeUpdate();
        pstmt2.executeUpdate();


    }

    /*FIM DOS METODOS IMPORTADOS DA BASE DE DADOS*/
}


class AnswerRequests extends Thread implements Serializable{

    private String message;
    private String ip;
    private int porto;
    private MulticastSocket socket;
    private int server_id;
    private java.sql.Connection connection;

    MessageParsing parsing = new MessageParsing();

    public AnswerRequests(String message, String ip, int porto, MulticastSocket socket, int server_id, java.sql.Connection connection) {
        this.message = message;
        this.ip = ip;
        this.porto = porto;
        this.socket = socket;
        this.server_id = server_id;
        this.connection = connection;
    }

    public void run(){

        UDPMulticastProtocol prot = new UDPMulticastProtocol(); //Ver se é aqui que devo chamar o construtor ou não
        System.out.println("Mensagem do cliente (IP: " + ip + ";Porto: " + porto + ")" + ": " + message);

        //Parsing da mensagem
        String [] pares = parsing.MessageParsing(message);
        String type = pares[2];
        String pack_id = pares[0];

        // De acordo com o tipo de argumento no campo "type" vamos definir a operacao pretendida e vai ser invocado o respetivo método
        //Trata do pedido para depois enviar a resposta para o cliente
        switch (type) {
            case "login":
                //Ver como devo lidar com o try catch de forma a remocer o warning que aparece no login
                try {
                    prot.login(pack_id, pares[3], pares[4], socket, server_id, connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "search music":
                try {
                    prot.search_music(pack_id, pares[3], pares[4], socket, server_id, connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "make editor":
                String username = pares[3];
                String new_editor = pares[4];
                try {
                    prot.make_editor(pack_id, username, new_editor, socket, server_id, connection); //EDITOR
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "register":
                try {
                    prot.register(pack_id, pares[3], pares[4], socket, server_id, connection);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "edit info":
                String user = pares[3];
                String search = pares[4];
                String search_name = pares[5];
                String op_type = pares[6];
                String field_type = pares[7];
                String msg = pares[8];
                try {
                    prot.manage_data(pack_id, user, search, search_name, op_type, field_type, msg, socket, server_id, connection); //EDITOR
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "search info":
                String search_type = pares[3];
                String choice_name = pares[4];
                try {
                    prot.search_info(pack_id, search_type,choice_name, socket, server_id, connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "review":
                String userr = pares[3];
                String album_name = pares[4];
                String rating = pares[5];
                String review_text = pares[6];
                try {
                    prot.album_review(pack_id, userr, album_name, rating, review_text, socket, server_id, connection); //ANY USER
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "share music": //DEPOIS
                String Uuser = pares[3];
                String musicc = pares[4];
                //String counter = pares[5]; //ACHO QUE NAO VOU PRECISAR DISTO PARA NADA
                ArrayList <String> lista = new ArrayList<>();
                for (int j = 6; j < pares.length; j++){
                    lista.add(pares[j]);
                }
                prot.share_music(pack_id, Uuser, musicc, lista, socket, server_id);
                break;

            case "download":
                String userrrr = pares[3];
                String music = pares[4];
                prot.download(pack_id, userrrr, music, socket, server_id);
                break;

            case "upload":
                String userrr = pares[3];
                String music_name = pares[4];
                prot.upload(pack_id,userrr, music_name, socket, server_id);
                break;

            case "add":
                String us = pares[3];
                String txt = pares[4];
                prot.add(pack_id, us, txt, socket, server_id);
                break;
        }

    }

}

public class MulticastServer extends Thread implements Serializable{

    private String MULTICAST_ADDRESS = "224.3.2.1";
    private int PORT = 4321; //Porto de recepção
    private int BUFFER_SIZE = 4096;
    private static int SERVER_ID;

    MessageParsing parsemsg = new MessageParsing();

    public static void main(String[] args) {
        //SERVER_ID = Integer.parseInt(args[0]); //ID DO SERVIDOR
        SERVER_ID = 1;
        MulticastServer server = new MulticastServer();
        server.start(); //Ao invocar o start da thread, estamos a chamar o metodo run()
    }

    public MulticastServer() {
        super ("Server Multicast #" + SERVER_ID);
    }

    public void run(){
        MulticastSocket socket = null;
        System.out.println(this.getName() + " running..."); //Vai buscar o que está dentro do método do construtor

        java.sql.Connection connection = null;

        try {
            //INICIO DAS CENAS DA BASE DE DADOS
            Class.forName("org.postgresql.Driver"); // the postgresql driver string
            String url = "jdbc:postgresql://localhost:5432/dropmusic"; // the postgresql url
            connection = DriverManager.getConnection(url, "postgres", "postgresadmin1"); // get the postgresql database connection
            //FIM DAS CENAS DA BASE DE DADOS

            socket = new MulticastSocket(PORT); //cria socket e dá bind
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(group); //dá join ao grupo hey.multicast

            //hey.rmiserver.AutoMessage de 5 em 5 segundos
            AutoMessage auto = new AutoMessage(SERVER_ID, socket);
            auto.start();

            while (true) {
                //Recepcao
                byte[] inBuffer = new byte[BUFFER_SIZE];
                DatagramPacket msgIn = new DatagramPacket(inBuffer, inBuffer.length);
                socket.receive(msgIn); //Recebe o datagrama UDP

                String clientIP = msgIn.getAddress().getHostAddress(); //Endereço IP do cliente que enviou a mensagem
                int clientport = msgIn.getPort(); //Porto do cliente

                String message = new String(msgIn.getData(), 0, msgIn.getLength());
                String [] fields = parsemsg.MessageParsing(message);


                if (Integer.parseInt(fields[1]) == SERVER_ID){
                    System.out.println("SO VOU RESPONDER EU -> SERVER " + SERVER_ID);
                    //Vai buscar a informação da mensagem e lança a thread para tratar do pedido
                    AnswerRequests work = new AnswerRequests(message, clientIP , clientport, socket, SERVER_ID, connection); //Também vou passar a socket por parâmetro
                    work.start();
                }
                else{
                    RedundantAnswer work_too = new RedundantAnswer(message, SERVER_ID);
                    work_too.start();
                    System.out.println("VOU ESTAR QUIETO -> SERVER " + SERVER_ID);

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            socket.close(); //Fechar a socket
        }
    }

}
