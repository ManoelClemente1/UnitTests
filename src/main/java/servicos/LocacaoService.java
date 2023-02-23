package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmesSemEstoqueException;
import exceptions.LocadoraException;

import java.util.Date;


public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmesSemEstoqueException, LocadoraException {


		if(usuario == null){
			throw new LocadoraException("Usuario vazio");
		}

		if(filme == null){
			throw new LocadoraException("Filme vazio");
		}

		if(filme.getEstoque() == 0){
			throw new FilmesSemEstoqueException("Filme sem estoque");
		}


		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
//		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}


}