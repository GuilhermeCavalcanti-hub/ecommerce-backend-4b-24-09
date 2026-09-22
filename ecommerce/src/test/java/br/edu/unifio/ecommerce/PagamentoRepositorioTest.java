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

import br.edu.unifio.ecommerce.entidades.Pagamento;
import br.edu.unifio.ecommerce.repositorios.PagamentoRepositorio;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PagamentoRepositorioTest {

    @Autowired
    private PagamentoRepositorio pagamentoRepositorio;

    @Test
    @Order(1)
    public void deveBuscarUmPagamentoPorId() {

        Pagamento pagamento = pagamentoRepositorio
                .findById(Integer.parseInt("1"))
                .orElseThrow();

        assertEquals(new BigDecimal("174.68"),
                pagamento.getValorUnitario());

        assertEquals("PIX",
                pagamento.getTipo().toString());
    }

    @Test
    @Order(2)
    public void deveBuscarTodosOsPagamentos() {

        List<Pagamento> pagamentos =
                pagamentoRepositorio.findAll(Sort.by(Pagamento::getId));

        assertEquals(5, pagamentos.size());
    }

    @Test
    @Order(3)
    public void deveExcluirUmPagamentoId() {

        Pagamento pagamento = new Pagamento();

        pagamento.setValorUnitario(new BigDecimal("100.00"));
        pagamento.setData(LocalDateTime.of(2026, 9, 10, 10, 0));
        pagamento.setStatus("APROVADO");
        pagamento.setTipo("PIX");

        pagamentoRepositorio.save(pagamento);

        assertTrue(pagamentoRepositorio.existsById(pagamento.getId()));

        pagamentoRepositorio.deleteById(pagamento.getId());

        assertFalse(pagamentoRepositorio.existsById(pagamento.getId()));
    }

    @Test
    @Order(4)
    public void deveSalvarUmPagamento() {

        Pagamento pagamento = new Pagamento();

        pagamento.setValorUnitario(new BigDecimal("100.00"));
        pagamento.setData(LocalDateTime.of(2026, 9, 10, 10, 0));
        pagamento.setStatus("APROVADO");
        pagamento.setTipo("PIX");

        pagamentoRepositorio.save(pagamento);

        assertTrue(pagamentoRepositorio.existsById(pagamento.getId()));

        assertEquals(
                new BigDecimal("100.00"),
                pagamentoRepositorio.findById(pagamento.getId())
                        .orElseThrow()
                        .getValorUnitario()
        );
    }

    @Test
    @Order(5)
    public void deveAlterarUmPagamento() {

        Pagamento pagamento = new Pagamento();

        pagamento.setValorUnitario(new BigDecimal("100.00"));
        pagamento.setData(LocalDateTime.of(2026, 9, 10, 10, 0));
        pagamento.setStatus("APROVADO");
        pagamento.setTipo("PIX");

        pagamentoRepositorio.save(pagamento);

        Integer id = pagamento.getId();

        pagamento.setValorUnitario(new BigDecimal("250.00"));

        pagamentoRepositorio.save(pagamento);

        Pagamento pagamentoAlterado =
                pagamentoRepositorio.findById(id).orElseThrow();

        assertEquals(id, pagamentoAlterado.getId());
        assertEquals(
                new BigDecimal("250.00"),
                pagamentoAlterado.getValorUnitario()
        );
    }
}
