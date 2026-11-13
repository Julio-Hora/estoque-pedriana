package dao;

import java.util.ArrayList;
import java.util.List;

import modelo.Produto;

public class ProdutoDAOMemoria implements ProdutoDAO {

    private List<Produto> produtos = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void salvar(Produto produto) {
        produto.setId(proximoId);
        proximoId++;
        produtos.add(produto);
    }

    @Override
    public Produto buscarPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }

    @Override
    public boolean atualizar(Produto produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()) {
                produtos.set(i, produto);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletar(int id) {
        Produto p = buscarPorId(id);
        if (p == null) {
            return false;
        }
        produtos.remove(p);
        return true;
    }
}
