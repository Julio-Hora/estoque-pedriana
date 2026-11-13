import dao.ProdutoDAO;
import dao.ProdutoDAOArquivo;
import dao.ProdutoDAOMemoria;
import modelo.Produto;
import servico.ProdutoService;

public class Principal {

    public static void main(String[] args) {
        System.out.println("===== FONTE DE DADOS: MEMORIA =====");
        ProdutoDAO daoMemoria = new ProdutoDAOMemoria();
        testarCrud(new ProdutoService(daoMemoria));

        System.out.println();
        System.out.println("===== FONTE DE DADOS: ARQUIVO =====");
        ProdutoDAO daoArquivo = new ProdutoDAOArquivo("produtos.txt");
        testarCrud(new ProdutoService(daoArquivo));
    }

    private static void testarCrud(ProdutoService service) {
        System.out.println("\n--- Criando produtos ---");
        service.cadastrar("Teclado", 89.90, 15);
        service.cadastrar("Mouse", 45.50, 30);
        service.cadastrar("Monitor", 799.00, 8);
        listar(service);

        System.out.println("\n--- Buscando produto de id 2 ---");
        Produto p = service.buscar(2);
        System.out.println(p);

        System.out.println("\n--- Atualizando produto de id 2 ---");
        service.atualizar(2, "Mouse Gamer", 120.00, 25);
        System.out.println(service.buscar(2));

        System.out.println("\n--- Deletando produto de id 1 ---");
        boolean removido = service.remover(1);
        System.out.println(removido ? "Produto removido" : "Produto nao encontrado");
        listar(service);

        System.out.println("\n--- Tentando deletar id que nao existe ---");
        boolean removido2 = service.remover(99);
        System.out.println(removido2 ? "Produto removido" : "Produto nao encontrado");

        System.out.println("\n--- Tentando cadastrar produto invalido ---");
        try {
            service.cadastrar("", 10.0, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("\nValor total do estoque: R$ " + String.format("%.2f", service.valorTotalEstoque()));
    }

    private static void listar(ProdutoService service) {
        System.out.println("Lista de produtos:");
        for (Produto p : service.listar()) {
            System.out.println(p);
        }
    }
}
