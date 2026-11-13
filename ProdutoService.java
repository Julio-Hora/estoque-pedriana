package servico;

import java.util.List;

import dao.ProdutoDAO;
import modelo.Produto;

public class ProdutoService {

    private ProdutoDAO dao;

    public ProdutoService(ProdutoDAO dao) {
        this.dao = dao;
    }

    public Produto cadastrar(String nome, double preco, int quantidade) {
        validar(nome, preco, quantidade);
        Produto produto = new Produto(nome, preco, quantidade);
        dao.salvar(produto);
        return produto;
    }

    public Produto buscar(int id) {
        return dao.buscarPorId(id);
    }

    public List<Produto> listar() {
        return dao.listarTodos();
    }

    public boolean atualizar(int id, String nome, double preco, int quantidade) {
        validar(nome, preco, quantidade);
        Produto produto = new Produto(id, nome, preco, quantidade);
        return dao.atualizar(produto);
    }

    public boolean remover(int id) {
        return dao.deletar(id);
    }

    public double valorTotalEstoque() {
        double total = 0;
        for (Produto p : dao.listarTodos()) {
            total += p.getPreco() * p.getQuantidade();
        }
        return total;
    }

    private void validar(String nome, double preco, int quantidade) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto nao pode ser vazio");
        }
        if (nome.contains(";")) {
            throw new IllegalArgumentException("O nome do produto nao pode ter ponto e virgula");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("O preco nao pode ser negativo");
        }
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade nao pode ser negativa");
        }
    }
}
