import java.io.BufferedReader;
import java.io.*;
import java.util.ArrayList;

class Login{

    String username, password;

    public Login (){
        this.username = username;
        this.password = password;
    }

    public void login(){

        System.out.println("Login");

        System.out.print("Username: ");
        //Ler o nome do username e guardar numa variável user
        //Ir buscar a base de dados o username para ver se existe e a correspodente password

        System.out.print("Password: ");
        //Ler a password e guardar numa variável pass
        //Ver se a password coincide com a que esta associada ao respetivo username

        //Se tudo for bem sucedido, o login é efetuado
    }

}


class Register{

    String nome, email, username, password;

    public Register (){
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.password = password;
    }


    public void register() throws IOException {
        String nome, email, user, pass, pass2;
        String auxp, auxp1;
        ArrayList<String> input = new ArrayList<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Register");

        System.out.print("Nome: ");
        if ((nome = in.readLine()) != null && nome.length() != 0 )
            input.add(nome);

        System.out.print("Email: ");
        if ((email = in.readLine()) != null && email.length() != 0 )
            input.add(email);

        System.out.print("Username pretendido: ");
        if ((user = in.readLine()) != null && user.length() != 0 )
            input.add(user);

        //Verificar se as passwords coincidem
        do {
            System.out.print("Password pretendida: ");
            if ((pass = in.readLine()) != null && pass.length() != 0)
                auxp = pass;

            System.out.print("Confirmar password: ");
            if ((pass2 = in.readLine()) != null && pass2.length() != 0)
                auxp1 = pass2;
        } while (!pass.equals(pass2));

        input.add(pass);
        input.add(pass2);

        /*System.out.print ("hey.multicast.User: " + user + " Password: " + pass + " Confirmacao: " + pass2);
        System.out.print("Nome completo: " + nome + " Email: " + email);

        for (int i = 0; i < input.size(); i++)
          System.out.println(input.get(i));
        */

    }


    //Estes dados que foram recolhidos no registo vao ser enviados pelo RMI se nao me engano
    //Enviar para o cliente RMI a informação recolhida do utilizador
}


public class Main{


    public static void main(String[] args) throws IOException {

        Register r1 = new Register();
        r1.register();

    }
}