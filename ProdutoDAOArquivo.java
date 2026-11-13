package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import modelo.Produto;

public class ProdutoDAOArquivo implements ProdutoDAO {

    private String caminho;

    public ProdutoDAOArquivo(String caminho) {
        this.caminho = caminho;
    }

    @Override
    public void salvar(Produto produto) {
        List<Produto> produtos = lerArquivo();
        int maiorId = 0;
        for (Produto p : produtos) {
            if (p.getId() > maiorId) {
                maiorId = p.getId();
            }
        }
        produto.setId(maiorId + 1);
        produtos.add(produto);
        gravarArquivo(produtos);
    }

    @Override
    public Produto buscarPorId(int id) {
        for (Produto p : lerArquivo()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Produto> listarTodos() {
        return lerArquivo();
    }

    @Override
    public boolean atualizar(Produto produto) {
        List<Produto> produtos = lerArquivo();
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()) {
                produtos.set(i, produto);
                gravarArquivo(produtos);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletar(int id) {
        List<Produto> produtos = lerArquivo();
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == id) {
                produtos.remove(i);
                gravarArquivo(produtos);
                return true;
            }
        }
        return false;
    }

    private List<Produto> lerArquivo() {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linha.split(";");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                double preco = Double.parseDouble(partes[2]);
                int quantidade = Integer.parseInt(partes[3]);
                produtos.add(new Produto(id, nome, preco, quantidade));
            }
        } catch (IOException e) {
            return produtos;
        }
        return produtos;
    }

    private void gravarArquivo(List<Produto> produtos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {
            for (Produto p : produtos) {
                pw.println(p.getId() + ";" + p.getNome() + ";" + p.getPreco() + ";" + p.getQuantidade());
            }
        } catch (IOException e) {
            System.out.println("Erro ao gravar o arquivo: " + e.getMessage());
        }
    }
}
