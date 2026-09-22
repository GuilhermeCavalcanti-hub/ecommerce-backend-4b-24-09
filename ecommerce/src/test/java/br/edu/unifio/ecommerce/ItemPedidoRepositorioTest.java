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

import br.edu.unifio.ecommerce.entidades.ItemPedido;
import br.edu.unifio.ecommerce.repositorios.ItemPedidoRepositorio;
import br.edu.unifio.ecommerce.repositorios.PedidoRepositorio;
import br.edu.unifio.ecommerce.repositorios.ProdutoRepositorio;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemPedidoRepositorioTest {

    @Autowired
    private ItemPedidoRepositorio itemPedidoRepositorio;

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Test
    @Order(1)
    public void deveBuscarUmItemPedidoPorId() {

        ItemPedido itemPedido = new ItemPedido();

        itemPedido.setQuantidade(2);
        itemPedido.setValorUnitario(new BigDecimal("87.34"));

        itemPedido.setPedido(
                pedidoRepositorio.findById(Integer.parseInt("1"))
                        .orElseThrow()
        );

        itemPedido.setProduto(
                produtoRepositorio.findById(Integer.parseInt("1"))
                        .orElseThrow()
        );

        itemPedidoRepositorio.save(itemPedido);

        Integer id = itemPedido.getId();

        ItemPedido itemEncontrado =
                itemPedidoRepositorio.findById(id).orElseThrow();

        assertEquals(2, itemEncontrado.getQuantidade());

        assertEquals(
                new BigDecimal("87.34"),
                itemEncontrado.getValorUnitario()
        );
    }

    @Test
    @Order(2)
    public void deveBuscarTodosOsItensPedido() {

        List<ItemPedido> itens =
                itemPedidoRepositorio.findAll(Sort.by(ItemPedido::getId));

        assertTrue(itens.size() >= 5);
    }

    @Test
    @Order(3)
    public void deveExcluirUmItemPedidoId() {

        ItemPedido itemPedido = new ItemPedido();

        itemPedido.setQuantidade(1);
        itemPedido.setValorUnitario(new BigDecimal("100.00"));

        itemPedido.setPedido(
                pedidoRepositorio.findById(Integer.parseInt("1"))
                        .orElseThrow()
        );

        itemPedido.setProduto(
                produtoRepositorio.findById(Integer.parseInt("1"))
                        .orElseThrow()
        );

        itemPedidoRepositorio.save(itemPedido);

        assertTrue(
                itemPedidoRepositorio.existsById(itemPedido.getId())
        );

        itemPedidoRepositorio.deleteById(itemPedido.getId());

        assertFalse(
                itemPedidoRepositorio.existsById(itemPedido.getId())
        );
    }

    @Test
    @Order(4)
    public void deveSalvarUmItemPedido() {

        ItemPedido itemPedido = new ItemPedido();

        itemPedido.setQuantidade(2);
        itemPedido.setValorUnitario(new BigDecimal("87.34"));

        itemPedido.setPedido(
                pedidoRepositorio.findById(Integer.parseInt("1"))
                        .orElseThrow()
        );

        itemPedido.setProduto(
                produtoRepositorio.findById(Integer.parseInt("1"))
                        .orElseThrow()
        );

        itemPedidoRepositorio.save(itemPedido);

        assertTrue(
                itemPedidoRepositorio.existsById(itemPedido.getId())
        );

        assertEquals(
                2,
                itemPedidoRepositorio.findById(itemPedido.getId())
                        .orElseThrow()
                        .getQuantidade()
        );
    }

    @Test
    @Order(5)
    public void deveAlterarUmItemPedido() {

        ItemPedido itemPedido = new ItemPedido();

        itemPedido.setQuantidade(2);
        itemPedido.setValorUnitario(new BigDecimal("87.34"));

        itemPedido.setPedido(
                pedidoRepositorio.findById(Integer.parseInt("1"))
                        .orElseThrow()
        );

        itemPedido.setProduto(
                produtoRepositorio.findById(Integer.parseInt("1"))
                        .orElseThrow()
        );

        itemPedidoRepositorio.save(itemPedido);

        Integer id = itemPedido.getId();

        itemPedido.setQuantidade(5);

        itemPedidoRepositorio.save(itemPedido);

        ItemPedido itemAlterado =
                itemPedidoRepositorio.findById(id).orElseThrow();

        assertEquals(id, itemAlterado.getId());
        assertEquals(5, itemAlterado.getQuantidade());
    }
}
