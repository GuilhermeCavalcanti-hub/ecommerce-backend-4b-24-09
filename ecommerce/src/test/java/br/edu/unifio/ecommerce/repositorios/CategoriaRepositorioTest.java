package br.edu.unifio.ecommerce.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import br.edu.unifio.ecommerce.entidades.Categoria;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaRepositorioTest {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Test
    @Order(1)
    public void deveBuscarUmaCategoriaPorId() {

        Categoria categoria = categoriaRepositorio
                .findById(Short.parseShort("1"))
                .orElseThrow();

        assertEquals("Informática", categoria.getNome());
        assertEquals("Produtos de informática", categoria.getDescricao());
    }

    @Test
    @Order(2)
    public void deveBuscarTodasAsCategorias() {

        List<Categoria> categorias =
                categoriaRepositorio.findAll(Sort.by(Categoria::getNome));

         assertTrue(categorias.size() >= 5);
    }

    @Test
    @Order(3)
    public void deveExcluirUmaCategoriaId() {

        Categoria categoria = new Categoria();

        categoria.setNome("Categoria Teste");
        categoria.setDescricao("Descrição Teste");

        categoriaRepositorio.save(categoria);

        assertTrue(categoriaRepositorio.existsById(categoria.getId()));

        categoriaRepositorio.deleteById(categoria.getId());

        assertFalse(categoriaRepositorio.existsById(categoria.getId()));
    }

    @Test
    @Order(4)
    public void deveSalvarUmaCategoria() {

        Categoria categoria = new Categoria();

        categoria.setNome("Categoria Teste");
        categoria.setDescricao("Descrição Teste");

        categoriaRepositorio.save(categoria);

        assertTrue(categoriaRepositorio.existsById(categoria.getId()));

        assertEquals(
                "Categoria Teste",
                categoriaRepositorio.findById(categoria.getId())
                        .orElseThrow()
                        .getNome()
        );
    }

    @Test
    @Order(5)
    public void deveAlterarUmaCategoria() {

        Categoria categoria = new Categoria();

        categoria.setNome("Categoria Original");
        categoria.setDescricao("Descrição Original");

        categoriaRepositorio.save(categoria);

        Short id = categoria.getId();

        categoria.setNome("Categoria Alterada");

        categoriaRepositorio.save(categoria);

        Categoria categoriaAlterada =
                categoriaRepositorio.findById(id).orElseThrow();

        assertEquals(id, categoriaAlterada.getId());
        assertEquals("Categoria Alterada", categoriaAlterada.getNome());
    }
}

