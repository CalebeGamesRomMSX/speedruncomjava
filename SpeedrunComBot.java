import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class SpeedrunComBot {

    public static void main(String[] args) {
        // Substitua "seu_nome_de_usuario" e "sua_senha" pelas credenciais reais
        String username = "seu_nome_de_usuario";
        String password = "sua_senha";

        try {
            // URL para autenticação (substitua pelo endpoint correto da API do Speedrun.com)
            String loginUrl = "https://www.speedrun.com/api/v1/auth";

            // Criando o JSON para o corpo da requisição
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", username);
            jsonBody.put("password", password);

            // Criando o cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Criando a requisição HTTP com o corpo JSON
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(loginUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString(), StandardCharsets.UTF_8))
                    .build();

            // Enviando a requisição e obtendo a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Exibindo a resposta
            if (response.statusCode() == 200) {
                System.out.println("Login bem-sucedido!");
                System.out.println("Resposta: " + response.body());

                // Processando o token da resposta
                JSONObject responseBody = new JSONObject(response.body());
                String token = responseBody.getString("token");
                System.out.println("Token de autenticação: " + token);

            } else {
                System.out.println("Falha no login. Status: " + response.statusCode());
                System.out.println("Mensagem: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
