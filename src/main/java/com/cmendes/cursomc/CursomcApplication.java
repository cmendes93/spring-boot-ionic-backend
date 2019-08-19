package com.cmendes.cursomc;

import java.text.SimpleDateFormat;import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cmendes.cursomc.domain.Categoria;
import com.cmendes.cursomc.domain.Cidade;
import com.cmendes.cursomc.domain.Cliente;
import com.cmendes.cursomc.domain.Endereco;
import com.cmendes.cursomc.domain.Estado;
import com.cmendes.cursomc.domain.ItemPedido;
import com.cmendes.cursomc.domain.Pagamento;
import com.cmendes.cursomc.domain.PagamentoComBoleto;
import com.cmendes.cursomc.domain.PagamentoComCartao;
import com.cmendes.cursomc.domain.Pedido;
import com.cmendes.cursomc.domain.Produto;
import com.cmendes.cursomc.domain.enums.EstadoPagamento;
import com.cmendes.cursomc.domain.enums.TipoCliente;
import com.cmendes.cursomc.repositories.CategoriaRepository;
import com.cmendes.cursomc.repositories.CidadeRepository;
import com.cmendes.cursomc.repositories.ClienteRepository;
import com.cmendes.cursomc.repositories.EnderecoRepository;
import com.cmendes.cursomc.repositories.EstadoRepository;
import com.cmendes.cursomc.repositories.ItemPedidoRepository;
import com.cmendes.cursomc.repositories.PagamentoRepository;
import com.cmendes.cursomc.repositories.PedidoRepository;
import com.cmendes.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired
	private EstadoRepository estadoReposytori;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Categoria categoria = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impresora", 750.00);
		Produto p3 = new Produto(null, "Mouse", 59.99);
		
		categoria.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		categoria2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(categoria));
		p2.getCategorias().addAll(Arrays.asList(categoria,categoria2));
		p3.getCategorias().addAll(Arrays.asList(categoria));
		
		categoriaRepo.saveAll(Arrays.asList(categoria,categoria2));
		produtoRepo.saveAll(Arrays.asList(p1,p2,p3));
		
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Uberlândia", est2);
		
		estadoReposytori.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","36378912377",TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("27363332","93838393"));
		
		Endereco e1 = new Endereco(null,"Rua Flores","300","Apto. 300","Jardim","38220834",cli1,c1);
		Endereco e2 = new Endereco(null,"Avenida Matos","105","Sala 800","Centro","38777012",cli1,c2);
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32") , cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35") , cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00") , null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}

}
