import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Produto> listaProdutos = new ArrayList<>();

        System.out.print("Entre com o caminho do arquivo: ");
        String caminho = sc.nextLine();

        File origemFile = new File(caminho);
        String pastaDoArquivo = origemFile.getParent();

        boolean sucesso = new File(pastaDoArquivo + "\\out").mkdir();
        if (sucesso) {
            System.out.println("Pasta criada com sucesso!");
        }

        sc.close();

        String destinoFile = pastaDoArquivo + "\\out\\sumario.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String itemTxt = br.readLine();

            while (itemTxt != null) {
                String[] campos = itemTxt.split(",");
                String nome = campos[0];
                Double preco = Double.parseDouble(campos[1]);
                Integer quantidade = Integer.parseInt(campos[2]);

                Produto produto = new Produto(nome, preco, quantidade);
                listaProdutos.add(produto);
                itemTxt = br.readLine();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(destinoFile, true))) {
                for (Produto p : listaProdutos) {
                    bw.write(p.getNome() + ", " + String.format("%.2f", p.valorTotal()));
                    bw.newLine();
                }

            } catch (IOException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
