package hey.rmiserver;

import java.io.Serializable;
import java.net.*;
import java.io.IOException;
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

    void login (String pack_id, String username, String password, MulticastSocket socket, int server_id){
        System.out.println("O cliente deseja fazer login");
        System.out.println("Username inserido: " + username);
        System.out.println("Password inserida: " + password);
        //Temos que ir à base de dados checkar se o username existe e qual a password associada e se é igual à inserida ou não


        if (data.username_match(username, server_id)){
            if (data.password_match(username, password, server_id)){ //SUCESSO

                /*JABARDICE
                ArrayList <hey.rmiserver.Notification> nots = data.get_NotificationList(server_id);
                data.write_notifications_file(nots, server_id);
                */

                String notif = data.check(username, server_id); //Vai checkar se há notificações a ser entregues

                String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|login" + ";username|" + username +";status|on; " + "notifications|" + notif;

                try{
                    byte[] buffer = rsp.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else { //PASSWORD ERRADA
                String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|login" + ";username|" + username + ";status|off; " + "msg|Password incorreta";

                try{
                    byte[] buffer = rsp.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                    socket.send(packet);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        else{ //UTILIZADOR NAO EXISTE
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


    }

    void register (String pack_id, String username, String password, MulticastSocket socket, int server_id) throws IOException {

        ArrayList <User> users = new ArrayList<>(); //ArrayList que contém todos os utilizadores
        //Abrir para a leitura do ficheiro de objetos
        try (ObjectInputStream oin = new ObjectInputStream (new FileInputStream("Users" + server_id + "_obj.txt"))) {
            users = (ArrayList) oin.readObject();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("O cliente deseja registar-se");
        System.out.println("Username inserido: " + username);
        System.out.println("Password inserida: " + password);

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

            String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|register;" + "status|successful; " + "msg|Welcome to DropMusic";
            try{
                byte[] buffer = rsp.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                socket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|register;" + "status|unsuccessful; " + "msg|Esse username ja existe";

            try{
                byte[] buffer = rsp.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                socket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //SÓ PARA VER QUEM TENHO REGISTADO
        for (User uss: users){
            System.out.println(uss.getUsername() + " " + uss.getPassword() + " -> " + uss.getUser_type());
        }


    }

    void search_music (String pack_id, String choice, String name, MulticastSocket socket, int server_id){
        System.out.println("O cliente deseja procurar musica");
        ArrayList <Album> album_list = data.get_AlbumList(server_id); //Vai buscar o ficheiro de albuns e põe nesta arraylist
        ArrayList <Music> music_list = data.get_MusicList(server_id);
        ArrayList <Artist> artist_list = data.get_ArtistList(server_id);
        ArrayList <Music> music_list1 = data.get_MusicList(server_id); //Vai buscar o ficheiro de albuns e põe nesta arraylist


        switch (choice){
            case "all": //Vai procurar por tudo o que os outros "cases" vao procurar
                int geral = 0;

                String st1 = "";
                int conta1 = 0;

                for (Album a: album_list) {
                    if (a.getAlbum_name().equals(name)) {
                        int item_count = a.getMusic_list().size();
                        for (int k = 0; k < item_count; k++) {
                            st1 = st1 + "music_" + geral + "_name>" + a.getMusic_list().get(k).getName() + "-";
                            conta1++;
                            geral++;
                        }
                    }
                }

                String st2 = "";
                int conta2 = 0;
                for (Music m: music_list){
                    if (m.getName().equals(name)){
                        st2 = st2 +  "music_"+ geral +"_name>" + m.getName() + "-";
                        conta2++;
                        geral++;
                    }
                }

                String st3 = "";
                int conta3 = 0;

                for (Artist a: artist_list){
                    if (a.getNome().equals(name)){
                        for (int j = 0; j < a.getMusic_list().size(); j++){
                            st3 = st3 + "music_"+ geral +"_name>" + a.getMusic_list().get(j).getName() + "-";
                            conta3++;
                            geral++;
                        }
                    }
                }

                String st4 = "";
                int conta4 = 0;

                for (Music m: music_list1){
                    if (m.getGenre().equals(name)){
                        st4 = st4 + "music_"+ geral +"_name>" + m.getName() + "-";
                        conta4++;
                        geral++;
                    }
                }

                int total = conta1 + conta2 + conta3 + conta4;

                String rsp_all = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|search music;" + "status|successful; " + "music_count|" + total + ";music_list|" + st1 + st2 + st3 + st4;
                try{
                    byte[] buffer = rsp_all.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case "album": //Vai ao ficheiro de albuns e apresenta a lista de musicas desse album
                String x = "";
                int conta = 0;

                for (Album a: album_list) {
                    if (a.getAlbum_name().equals(name)) {
                        int item_count = a.getMusic_list().size();
                        for (int k = 0; k < item_count; k++) {
                            x = x + "music_" + k + "_name>" + a.getMusic_list().get(k).getName() + "-";
                            conta++;
                        }
                    }
                }

                String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|search music;" + "status|successful; " + "music_count|" + conta + ";music_list|" + x;
                try{
                    byte[] buffer = rsp.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "music":  //Vai ao ficheiro de musicas e apresenta uma lista de musicas que contenham esse nome
                String s = "";
                int item_size = 0;
                for (Music m: music_list){
                    if (m.getName().equals(name)){
                        s = s +  "music_"+item_size+"_name>" + m.getName() + "-";
                        item_size++;
                    }
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
                break;

            case "artist": //Vai ao ficheiro de artistas e apresenta as musicas desse artista
                String z = "";
                int tam = 0;

                for (Artist a: artist_list){
                    if (a.getNome().equals(name)){
                        for (int j = 0; j < a.getMusic_list().size(); j++){
                            z = z + "music_"+ j +"_name>" + a.getMusic_list().get(j).getName() + "-";
                            tam++;
                        }
                    }
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
                break;

            case "genre": //Vai ao ficheiro de musicas e procura no arraylist de musicas pelo parâmetro "genre"
                String aux = "";
                int counter = 0;

                for (Music m: music_list1){
                    if (m.getGenre().equals(name)){
                        aux = aux + "item_"+ counter +"_name>" + m.getName() + "-";
                        counter++;
                    }
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
                break;

        }

    }

    void make_editor (String pack_id, String username, String novo_editor, MulticastSocket socket, int server_id){
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
                                String rsp = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|make editor" + ";new_editor|" + novo_editor + ";status|successful; " + "msg|You can edit now";
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
                    else{ //SE NAO TEM PERMISSAO -> CONDICAO 2
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

        }
        /*
        else { //ESSE MANO NAO CONSTA DA BASE DE DADOS
            String rsp = "pack_id|" + pack_id +  "; server_id|" + server_id + "; type|make editor"  + ";new_editor|" + novo_editor + "; status|unsuccessful; " + "msg|The user you entered is not in our database";
            try {
                byte[] buffer = rsp.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        ArrayList <String> test = new ArrayList<>();
        for (User u: users){
            test.add(u.getUsername());
        }

        if (!test.contains(novo_editor)){
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

    void manage_data (String pack_id, String username, String choice, String search_name, String op_type, String field_type, String name, MulticastSocket socket, int server_id) { //EM FALTA
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
                                        data.write_musiclist_file(music_list, server_id);

                                        String rsp_client = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Artist added";
                                        try{
                                            byte[] buffer = rsp_client.getBytes();
                                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT); //mudei aqui
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
                                        data.write_musiclist_file(music_list, server_id);

                                        String rsp_client = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Album added";
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
                                        Music m = new Music(artist, genre, album, music_name, Integer.parseInt(duration), "no", allowed_users);
                                        music_list.add(m);
                                        data.write_musiclist_file(music_list, server_id);

                                        String rsp_client = "pack_id|" + pack_id +  ";server_id|" + server_id + ";type|edit info" + ";status|successful; " + "msg|hey.rmiserver.Music added";
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

    void search_info (String pack_id, String choice, String msg, MulticastSocket socket, int server_id){
        System.out.println("O cliente pretende consultar detalhes sobre álbum e sobre artista");

        ArrayList <Album> show_albuns = data.get_AlbumList(server_id);
        ArrayList <Music> music_list = data.get_MusicList(server_id);
        ArrayList <Artist> artist_list = data.get_ArtistList(server_id);


        switch (choice){
            case "album": //Ir ao ficheiro de albuns e mostrar toda a informacao sobre o album e mandar ao cliente
                String info = "";
                String lista_music = "music_list>>";
                String lista_review = "review_list>>";

                for (Album a: show_albuns){
                    if (a.getAlbum_name().equals(msg)){
                        info = info + "album_name>" + a.getAlbum_name() + "-artist_name>" +  a.getArtist() + "-description>" + a.getDescription() + "-average_rating>" + a.getRating() + "-";
                    }
                }

                for (Album a: show_albuns) {
                    if (a.getAlbum_name().equals(msg)) {
                        if (!a.getMusic_list().isEmpty()) {
                            for (int j = 0; j < a.getMusic_list().size(); j++) {
                                lista_music = lista_music + "item_" + j + "_name>" + a.getMusic_list().get(j).getName() + "-";
                            }
                        }
                    }
                }

                for (Album a: show_albuns) {
                    if (a.getAlbum_name().equals(msg)) {
                        if (!a.getReview_list().isEmpty()) {
                            for (int k = 0; k < a.getReview_list().size(); k++) {
                                lista_review += "item_" + k + "_name>>" + "-user>" + a.getReview_list().get(k).getUser()+ "-rating>" + a.getReview_list().get(k).getRating() +
                                        "-review>" + a.getReview_list().get(k).getText() + "-";
                            }
                        }
                    }
                }

                String answer = "pack_id|" + pack_id +  ";" + "server_id|" + server_id + ";" + "type|search info" + ";status|successful" + ";msg|" +  info + lista_music + lista_review;
                //ENVIAR RESPOSTA PARA O CLIENTE
                try {
                    byte[] buffer = answer.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case "artist": //Ir ao ficheiro de artistas e mostrar toda a informacao sobre o artista e mandar ao cliente
                String msg_send = "";
                String musicas = "";

                for (Artist a : artist_list){
                    if (a.getNome().equals(msg)){
                        msg_send = msg_send + "group>" + a.getGrupo() + "-data_nasc>" + a.getData_nasc() + "-genre>" + a.getGenre() + "-biography>" + a.getBiography();
                    }
                }

                for (Artist a: artist_list) {
                    if (a.getNome().equals(msg)) {
                        for (int j = 0; j < a.getMusic_list().size(); j++) {
                            musicas = musicas + "item_" + j + "_name" + a.getMusic_list().get(j).getName();
                        }
                    }
                }

                String env_answer = "pack_id|" + pack_id + ";server_id|" + server_id + ";" + "type|search info" + ";status|successful;" + "msg|" + "artist_info>>" + msg_send + "-music list>>" +  musicas;
                //ENVIAR RESPOSTA PARA O CLIENTE
                try {
                    byte[] buffer = env_answer.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case "music":
                String resp_aux = "";

                for (Music m: music_list){
                    if (m.getName().equals(msg)){
                        resp_aux = resp_aux + "music_name>" + m.getName() + "-album_title>" + m.getAlbum_title() + "-genre>" + m.getGenre() +
                                "-artist_name>" + m.getArtist() + "-duration>" + m.getDuration();
                    }
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

                break;
        }
    }

    void album_review (String pack_id, String user, String album_title, String rating, String text, MulticastSocket socket, int server_id){ //TESTAR
        System.out.println("O cliente " + user + " pretende escrever uma crítica a um álbum");

        //1. TENHO QUE ABRIR O FICHEIRO E PÔR LÁ A INFORMACAO
        ArrayList <Album> album_list = data.get_AlbumList(server_id);
        Review r = new Review(text, Integer.parseInt(rating), user);

        for (Album a: album_list) {
            if (a.getAlbum_name().equals(album_title)) {
                a.getReview_list().add(r); //Adicionar a review à lista de reviews
                data.write_albumlist_file(album_list, server_id);

                String rsp = "pack_id|" + pack_id + ";server_id|" + server_id + ";type|review" + ";status|successful; " + "msg|Your review was submitted";
                try {
                    byte[] buffer = rsp.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ArrayList<String> aux = new ArrayList<>();

        for (Album a: album_list) {
            aux.add(a.getAlbum_name());
        }

        if (!aux.contains(album_title)){//ÁLBUM NÃO CONSTA DA BASE DE DADOS
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

}


class AnswerRequests extends Thread implements Serializable{

    private String message;
    private String ip;
    private int porto;
    private MulticastSocket socket;
    private int server_id;

    MessageParsing parsing = new MessageParsing();

    public AnswerRequests(String message, String ip, int porto, MulticastSocket socket, int server_id) {
        this.message = message;
        this.ip = ip;
        this.porto = porto;
        this.socket = socket;
        this.server_id = server_id;
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
                prot.login(pack_id, pares[3], pares[4], socket, server_id);
                break;

            case "search music":
                prot.search_music(pack_id, pares[3], pares[4], socket, server_id);
                break;

            case "make editor":
                String username = pares[3];
                String new_editor = pares[4];
                prot.make_editor(pack_id, username, new_editor, socket, server_id); //EDITOR
                break;

            case "register":
                try {
                    prot.register(pack_id, pares[3], pares[4], socket, server_id);
                } catch (IOException e) {
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
                prot.manage_data(pack_id, user, search, search_name, op_type, field_type, msg, socket, server_id); //EDITOR
                break;

            case "search info":
                String search_type = pares[3];
                String choice_name = pares[4];
                prot.search_info(pack_id, search_type,choice_name, socket, server_id);
                break;

            case "review":
                String userr = pares[3];
                String album_name = pares[4];
                String rating = pares[5];
                String review_text = pares[6];
                prot.album_review(pack_id, userr, album_name, rating, review_text, socket, server_id); //ANY USER
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

        try {
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
                    AnswerRequests work = new AnswerRequests(message, clientIP , clientport, socket, SERVER_ID); //Também vou passar a socket por parâmetro
                    work.start();
                }
                else{
                    RedundantAnswer work_too = new RedundantAnswer(message, SERVER_ID);
                    work_too.start();
                    //Se for um registo, mudança de informação de álbuns ou cenas que têm que ser replicadas
                    //Ou seja, tem que ir para as 3 bases de dados e vai ser este "else" que vai tratar disso
                    System.out.println("VOU ESTAR QUIETO -> SERVER " + SERVER_ID);

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close(); //Fechar a socket
        }
    }

}



 /*JABARDICE
        data.write_albumlist_file(album_list, server_id);
        data.write_artislist_file(artist_list, server_id);
        data.write_musiclist_file(music_list, server_id);
           */