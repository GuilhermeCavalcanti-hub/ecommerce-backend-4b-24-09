package br.edu.unifio.ecommerce;

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

import br.edu.unifio.ecommerce.entidades.Cliente;
import br.edu.unifio.ecommerce.repositorios.ClienteRepositorio;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteRepositorioTest {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Test
    @Order(1)
    public void deveBuscarUmClientePorId() {

        Cliente cliente = clienteRepositorio
                .findById((int) Short.parseShort("1"))
                .orElseThrow();

        assertEquals("João da Silva", cliente.getNome());
        assertEquals("joao@email.com", cliente.getEmail());
    }

    @Test
    @Order(2)
public void deveBuscarTodosOsClientes() {

    List<Cliente> clientes =
            clienteRepositorio.findAll(Sort.by(Cliente::getNome));

    assertTrue(clientes.size() >= 5);
}
    

    @Test
    @Order(3)
    public void deveExcluirUmClienteId() {

        Cliente cliente = new Cliente();

        cliente.setNome("Cliente Teste");
        cliente.setEmail("cliente@teste.com");
        cliente.setCpf("99999999999");
        cliente.setSenha("123456");

        clienteRepositorio.save(cliente);

        assertTrue(clienteRepositorio.existsById(cliente.getId()));

        clienteRepositorio.deleteById(cliente.getId());

        assertFalse(clienteRepositorio.existsById(cliente.getId()));
    }

    @Test
    @Order(4)
    public void deveSalvarUmCliente() {

        Cliente cliente = new Cliente();

        cliente.setNome("Cliente Teste");
        cliente.setEmail("cliente@teste.com");
        cliente.setCpf("99999999999");
        cliente.setSenha("123456");

        clienteRepositorio.save(cliente);

        assertTrue(clienteRepositorio.existsById(cliente.getId()));

        assertEquals(
                "Cliente Teste",
                clienteRepositorio.findById(cliente.getId())
                        .orElseThrow()
                        .getNome()
        );
    }

    @Test
    @Order(5)
    public void deveAlterarUmCliente() {

        Cliente cliente = new Cliente();

        cliente.setNome("Cliente Original");
        cliente.setEmail("original@teste.com");
        cliente.setCpf("88888888888");
        cliente.setSenha("123456");

        clienteRepositorio.save(cliente);

        Integer id = cliente.getId();

        cliente.setNome("Cliente Alterado");

        clienteRepositorio.save(cliente);

        Cliente clienteAlterado =
                clienteRepositorio.findById(id).orElseThrow();

        assertEquals(id, clienteAlterado.getId());
        assertEquals("Cliente Alterado", clienteAlterado.getNome());
    }
}