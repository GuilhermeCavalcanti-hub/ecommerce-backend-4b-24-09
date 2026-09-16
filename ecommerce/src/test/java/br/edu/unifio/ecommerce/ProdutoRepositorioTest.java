package br.edu.unifio.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import br.edu.unifio.ecommerce.entidades.Produto;
import br.edu.unifio.ecommerce.repositorios.CategoriaRepositorio;
import br.edu.unifio.ecommerce.repositorios.ProdutoRepositorio;

@SpringBootTest 
@TestMethodOrder (MethodOrderer.OrderAnnotation.class)
public class ProdutoRepositorioTest {
    @Autowired 
    private ProdutoRepositorio produtoRepositorio;

    @Autowired 
    private CategoriaRepositorio categoriaRepositorio;

    @Test 
    @Order (1)
    public void deveBuscarUmProdutoPorId (){
        var produto = produtoRepositorio.findById(Integer.parseInt("1")).orElseThrow();
        System.out.println(produto);
    }

     @Test
     @Order (2)
    public void deveBuscarTodosOsProdutos (){
        List<Produto> produtos = produtoRepositorio.findAll(Sort.by("nome"));

        assertEquals(5, produtos.size());
        assertEquals("Código Limpo", produtos.get(0).getNome());
        assertEquals("Fone Bluetooth", produtos.get(1).getNome());
    }

    @Test
    @Order (3)
    public void deveExcluirUmProdutoId (){
        Produto produto = new Produto ();
        produto.setNome("Nome Teste");
        produto.setDescricao("Descrição Teste");
        produto.setEstoque(Short.parseShort("1"));
        produto.setPreco(new BigDecimal("1.00"));
        produto.setCategoria(categoriaRepositorio.findById(Short.parseShort("1")).orElseThrow());
        produtoRepositorio.save(produto);


        assertTrue(produtoRepositorio.existsById(produto.getId()));
        produtoRepositorio.deleteById(produto.getId());
        assertFalse(produtoRepositorio.existsById(produto.getId()));
    }
      
      @Test 
      @Order (4)
    public void deveSalvarUmProduto () {
        Produto produto = new Produto ();
        produto.setNome("Nome Teste");
        produto.setDescricao("Descrição Teste");
        produto.setEstoque(Short.parseShort("1"));
        produto.setPreco(new BigDecimal("1.00"));
        produto.setCategoria(categoriaRepositorio.findById(Short.parseShort("1")).orElseThrow());
        produtoRepositorio.save(produto);


        assertTrue(produtoRepositorio.existsById(produto.getId()));
        assertEquals("Nome Teste", produtoRepositorio.findById(produto.getId()).orElseThrow().getNome());
    }
}
