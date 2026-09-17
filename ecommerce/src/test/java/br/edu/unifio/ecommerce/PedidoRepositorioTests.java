package br.edu.unifio.ecommerce;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.unifio.ecommerce.entidades.Pedido;
import br.edu.unifio.ecommerce.repositorios.ClienteRepositorio;
import br.edu.unifio.ecommerce.repositorios.PedidoRepositorio;
import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@SpringBootTest 
@TestMethodOrder (MethodOrderer.OrderAnnotation.class)
public class PedidoRepositorioTests {
    @Autowired 
    private PedidoRepositorio pedidoRepositorio;

    @Autowired 
    private ClienteRepositorio clienteRepositorio;


    @Test
    @Order (1)
        public void deveBuscarUmPedidoPorId (){
        Pedido pedido = pedidoRepositorio.findById(1).orElseThrow();
        
       assertNotNull(pedido);
       assertEquals("PAGO", pedido.getStatus());

 }

@Test 
 @Order (2)
    public void deveListarTodososPedidosId (){
       List<Pedido> pedidos = pedidoRepositorio.findAll();
        
    }
}
