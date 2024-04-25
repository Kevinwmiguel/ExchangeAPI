package src;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONObject;



public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/eb4df93d34bfe216b28ae45d/latest/USD"))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(response.body());

        // Verifique se a resposta é bem-sucedida
        double taxaConversao;

        // Verifica se a resposta foi bem-sucedida

        if (jsonObject.getString("result").equals("success")) {
            // Obtenha a taxa de conversão do objeto JSON
            JSONObject rates = jsonObject.getJSONObject("conversion_rates");

            do {
                System.out.println("\n******************************************");
                System.out.println("Seja bem vindo/a ao conversor de Moeda :)");
                System.out.println("******************************************");
                System.out.println("1) Dolar =>> Peso argentino");
                System.out.println("2) Peso argentino =>> Dolar");
                System.out.println("3) Dolar =>> Real");
                System.out.println("4) Real =>> Dolar");
                System.out.println("5) Dolar =>> Peso Colombiano");
                System.out.println("6) Peso Colombiano =>> Dolar");
                System.out.println("7) Sair");
                System.out.print("Escolha uma opção válida: \n");
                System.out.println("******************************************\n");
                escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        taxaConversao = rates.getDouble("ARS");
                        System.out.println("Digite o valor em Dólar: ");
                        double valorDolar = scanner.nextDouble();
                        System.out.println("O valor em Peso Argentino é: ARS$" + valorDolar * taxaConversao);
                        Thread.sleep(3000);
                        break;
                    case 2:
                        taxaConversao = rates.getDouble("ARS");
                        System.out.println("Digite o valor em Peso Argentino: ARS$");
                        double valorPesoArgentino = scanner.nextDouble();
                        System.out.println("O valor em Dólar é $: " + valorPesoArgentino / taxaConversao);
                        Thread.sleep(3000);
                        break;
                    case 3:
                        taxaConversao = rates.getDouble("BRL");
                        System.out.println("Digite o valor em Dólar: $");
                        double valorDolarReal = readDoubleWithTwoDecimals(scanner);
                        System.out.println("O valor em Real é R$: " + String.format("%.2f", valorDolarReal * taxaConversao));
                        Thread.sleep(3000);
                        break;
                    case 4:
                        taxaConversao = rates.getDouble("BRL");
                        System.out.println("Digite o valor em Real: R$");
                        double valorRealDolar = readDoubleWithTwoDecimals(scanner);
                        System.out.println("O valor em Dólar é $: " + String.format("%.2f", valorRealDolar / taxaConversao));
                        Thread.sleep(3000);
                        break;
                    case 5:
                        taxaConversao = rates.getDouble("COP");
                        System.out.println("Digite o valor em Dólar: ");
                        double valorDolarPesoColombiano = scanner.nextDouble();
                        System.out.println("O valor em Peso Colombiano é: COL$" + valorDolarPesoColombiano * taxaConversao);
                        Thread.sleep(3000);
                        break;
                    case 6:
                        taxaConversao = rates.getDouble("COP");
                        System.out.println("Digite o valor em Peso Colombiano: COL$");
                        double valorPesoColombianoDolar = scanner.nextDouble();
                        System.out.println("O valor em Dólar é $: " + valorPesoColombianoDolar / taxaConversao);
                        Thread.sleep(3000);
                        break;
                    case 7:
                        System.out.println("Sair");
                        Thread.sleep(3000);
                        break;
                    default:
                        System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
                }
            } while (escolha != 7);
            scanner.close();
        } else {
            // Se a resposta não for bem-sucedida, imprima a mensagem de erro
            System.out.println("Erro ao obter a taxa de conversão. Status: " + jsonObject.getString("result"));
        }
    }
    private static double readDoubleWithTwoDecimals(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.next();
                return Double.parseDouble(input.replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, insira um número válido.");
                scanner.nextLine(); // Limpa o buffer do scanner
            }
        }
    }
}


