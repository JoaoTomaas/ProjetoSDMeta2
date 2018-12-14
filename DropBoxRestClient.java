import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.github.scribejava.apis.DropBoxApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;


//teste
import java.awt.Desktop;
import java.net.URI;


import uc.sd.apis.DropBoxApi2;



// Step 1: Create Dropbox Account

// Step 2: Create Application (https://www.dropbox.com/developers)

public class DropBoxRestClient {


	// Access codes #1: per application used to get access codes #2
	private static final String API_APP_KEY = "l2z0axbwma3hu05";
	private static final String API_APP_SECRET = "wo8j0lek2lfvi6s";

	// Access codes #2: per user per application
	//private static final String API_USER_TOKEN = "7s_a00GyGCAAAAAAAAAAE3aDFA34iA8X_iT_eV_PnFC2sZKe-JLfw8Y15lJGLg7z";
	//private static final String API_USER_TOKEN = "7s_a00GyGCAAAAAAAAAAKjbEur_uu7CHJgELPmFrsyw";
	//private static  String API_USER_TOKEN = "";

	public static void main(String[] args) {

		loginDropbox();

/*
		Scanner in = new Scanner(System.in);



		OAuthService service = new ServiceBuilder()
				.provider(DropBoxApi2.class)
				.apiKey(API_APP_KEY)
				.apiSecret(API_APP_SECRET)
				.callback("https://eden.dei.uc.pt/~fmduarte/echo.php")
				.build();

		try {

			//if ( API_USER_TOKEN.equals("") ) {
			//javardice a partir daqui

			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop().browse(new URI((service.getAuthorizationUrl(null))));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}


			System.out.println("Authorize scribe here:");
			System.out.println(service.getAuthorizationUrl(null));
			//System.out.println("Press enter when done.");
			System.out.print(">>");
			Verifier verifier = new Verifier(in.nextLine());
			Token accessToken = service.getAccessToken(null, verifier);



			//API_USER_TOKEN=accessToken.getToken();
			//System.out.println("ola:"+API_USER_TOKEN);
			//System.out.println("Define API_USER_TOKEN: " + accessToken.getToken());
			//System.out.println("Define API_USER_SECRET: " + accessToken.getSecret());
			//System.exit(0);
			//}«
			Token accessToken1 = new Token( accessToken.getToken(), "");


			playMusic(service,accessToken1);
			//listMusic(service, accessToken1);

			//addFile("teste.txt", service, accessToken);
			//listFiles(service, accessToken);
			//deleteFile("teste.txt", service, accessToken);
			//listFiles(service, accessToken);


		} catch(OAuthException e) {
			e.printStackTrace();
		} finally {
			in.close();
		}

		*/
	}



	private static void loginDropbox(){

		//é suposto guardarmos isto na BD
		String emailNaBaseDados = "sddropbox12345@gmail.com";

		Scanner in = new Scanner(System.in);



		OAuthService service = new ServiceBuilder()
				.provider(DropBoxApi2.class)
				.apiKey(API_APP_KEY)
				.apiSecret(API_APP_SECRET)
				.callback("https://eden.dei.uc.pt/~fmduarte/echo.php")
				.build();

		try {


			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop().browse(new URI((service.getAuthorizationUrl(null))));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}


			System.out.println("Authorize scribe here:");
			System.out.println(service.getAuthorizationUrl(null));
			System.out.print(">>");
			Verifier verifier = new Verifier(in.nextLine());
			Token accessToken = service.getAccessToken(null, verifier);
			Token accessToken1 = new Token( accessToken.getToken(), "");


			OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.dropboxapi.com/2/users/get_current_account", service);
			request.addHeader("authorization", "Bearer " + accessToken.getToken());
			request.addHeader("Content-Type",  "application/json");
			request.addPayload((String) "null");
			System.out.println("asd->>"+request.getBodyContents());
			Response response = request.send();
			System.out.println(response.getBody());
			JSONObject rj = (JSONObject) JSONValue.parse(response.getBody());

			String emailCurrent = (String) rj.get("email");

			if(emailNaBaseDados.equals(emailCurrent)){
				System.out.println("holy shit");
			}



		} catch(OAuthException e) {
			e.printStackTrace();
		} finally {
			in.close();
		}






	}



	private static JSONArray listMusic(OAuthService service, Token accessToken) {

		//imprimir todos os nomes de ficheiro do dropbox
		//listMusic na pasta de nome Music

		OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.dropboxapi.com/2/files/list_folder", service);
		request.addHeader("authorization", "Bearer " + accessToken.getToken());
		request.addHeader("Content-Type",  "application/json");
		request.addPayload("{\n" +
				"    \"path\": \"/Teste\",\n" +
				"    \"recursive\": false,\n" +
				"    \"include_media_info\": false,\n" +
				"    \"include_deleted\": false,\n" +
				"    \"include_has_explicit_shared_members\": false,\n" +
				"    \"include_mounted_folders\": true\n" +
				"}");

		Response response = request.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println("HTTP RESPONSE: =============");
		System.out.println(response.getCode());
		System.out.println(response.getBody());
		System.out.println("END RESPONSE ===============");

		//imprimo com o numero no array
		JSONObject rj = (JSONObject) JSONValue.parse(response.getBody());
		JSONArray contents = (JSONArray) rj.get("entries");
		return contents;




	}

	private static void playMusic(OAuthService service, Token accessToken){

		JSONArray contents = listMusic(service,accessToken);


		for (int i=0; i<contents.size(); i++) {
			JSONObject item = (JSONObject) contents.get(i);
			String path = (String) item.get("name");
			System.out.println( (i+1) + " - " + path );
		}



		//depois o user seleciona o nº pretendido e pega nos dados que estao la

		Scanner in = new Scanner(System.in);

		String opcao = in.nextLine();
		int index = Integer.parseInt(opcao) - 1;
		JSONObject itemFilename = (JSONObject) contents.get(index);
		String  name = (String) itemFilename.get("name");
		String  nameURL = name.replace(" ","+");
		String URL = "https://www.dropbox.com/home/Teste?preview="+nameURL;

		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			try {
				Desktop.getDesktop().browse(new URI(URL));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}


	}

	private static void associarFicheiro(OAuthService service, Token accessToken){


		//seleciona musica pelo nome
		//search_music acho eu




		//guarda o id da musica para a localizar depois

		JSONArray contents = listMusic(service,accessToken);

		for (int i=0; i<contents.size(); i++) {
			JSONObject item = (JSONObject) contents.get(i);
			String path = (String) item.get("name");
			System.out.println( (i+1) + " - " + path );
		}



		//depois o user seleciona o nº pretendido e pega nos dados que estao la

		Scanner in = new Scanner(System.in);

		String opcao = in.nextLine();
		int index = Integer.parseInt(opcao) - 1;
		JSONObject itemFilename = (JSONObject) contents.get(index);
		String filename = (String) itemFilename.get("path_display");

		//o nome passa a ser o filename da musica que procuramos
		System.out.println(filename+"->goodchoice");





	}

	private static void addFile(String path, OAuthService service, Token accessToken) {
		// TODO
/*


		// imprime assim: "path": "",


		OAuthRequest request = new OAuthRequest(Verb.POST, "https://content.dropboxapi.com/2/files/upload", service);
		request.addHeader("authorization", "Bearer " + accessToken.getToken());
		request.addHeader("Content-Type",  "application/octet-stream");
		request.addPayload("{\n" +
				"    \"path\": \"/Teste/"+path+"\",\n" +
				"}");

*/


	}

	private static void deleteFile(String path, OAuthService service, Token accessToken) {
		// TODO
	}


}
