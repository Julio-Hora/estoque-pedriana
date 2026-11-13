package dao;

import java.util.List;

import modelo.Produto;

public interface ProdutoDAO {

    void salvar(Produto produto);

    Produto buscarPorId(int id);

    List<Produto> listarTodos();

    boolean atualizar(Produto produto);

    boolean deletar(int id);
}
