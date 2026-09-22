package br.edu.unifio.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import br.edu.unifio.ecommerce.entidades.Cliente;
import br.edu.unifio.ecommerce.entidades.Pedido;
import br.edu.unifio.ecommerce.repositorios.ClienteRepositorio;
import br.edu.unifio.ecommerce.repositorios.PedidoRepositorio;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidoRepositorioTest {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Test
    @Order(1)
    public void deveBuscarUmPedidoPorId() {

        Pedido pedido = pedidoRepositorio
                .findById(1)
                .orElseThrow();

        assertEquals(
                new BigDecimal("174.68"),
                pedido.getValorTotal()
        );

        assertEquals(
                "PAGO",
                pedido.getStatus()
        );
    }

    @Test
    @Order(2)
    public void deveBuscarTodosOsPedidos() {

        List<Pedido> pedidos =
                pedidoRepositorio.findAll(Sort.by(Pedido::getId));

        assertTrue(pedidos.size() >= 5);
    }

    @Test
    @Order(3)
    public void deveExcluirUmPedidoId() {

        Pedido pedido = new Pedido();

        pedido.setData(
                LocalDateTime.of(2026, 9, 10, 10, 0)
        );

        pedido.setValorTotal(
                new BigDecimal("100.00")
        );

        pedido.setStatus("PENDENTE");

        Cliente cliente = clienteRepositorio
                .findById(1)
                .orElseThrow();

        pedido.setCliente(cliente);

        pedidoRepositorio.save(pedido);

        assertTrue(
                pedidoRepositorio.existsById(pedido.getId())
        );

        pedidoRepositorio.deleteById(pedido.getId());

        assertFalse(
                pedidoRepositorio.existsById(pedido.getId())
        );
    }

    @Test
    @Order(4)
    public void deveSalvarUmPedido() {

        Pedido pedido = new Pedido();

        pedido.setData(
                LocalDateTime.of(2026, 9, 10, 10, 0)
        );

        pedido.setValorTotal(
                new BigDecimal("200.00")
        );

        pedido.setStatus("PENDENTE");

        Cliente cliente = clienteRepositorio
                .findById(1)
                .orElseThrow();

        pedido.setCliente(cliente);

        pedidoRepositorio.save(pedido);

        assertTrue(
                pedidoRepositorio.existsById(pedido.getId())
        );

        assertEquals(
                new BigDecimal("200.00"),
                pedidoRepositorio
                        .findById(pedido.getId())
                        .orElseThrow()
                        .getValorTotal()
        );
    }

    @Test
    @Order(5)
    public void deveAlterarUmPedido() {

        Pedido pedido = new Pedido();

        pedido.setData(
                LocalDateTime.of(2026, 9, 10, 10, 0)
        );

        pedido.setValorTotal(
                new BigDecimal("200.00")
        );

        pedido.setStatus("PENDENTE");

        Cliente cliente = clienteRepositorio
                .findById(1)
                .orElseThrow();

        pedido.setCliente(cliente);

        pedidoRepositorio.save(pedido);

        Integer id = pedido.getId();

        pedido.setValorTotal(
                new BigDecimal("350.00")
        );

        pedidoRepositorio.save(pedido);

        Pedido pedidoAlterado =
                pedidoRepositorio.findById(id).orElseThrow();

        assertEquals(
                id,
                pedidoAlterado.getId()
        );

        assertEquals(
                new BigDecimal("350.00"),
                pedidoAlterado.getValorTotal()
        );
    }
}
