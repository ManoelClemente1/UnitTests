package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmesSemEstoqueException;
import exceptions.LocadoraException;

import java.util.Date;
import java.util.List;


public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filme) throws FilmesSemEstoqueException, LocadoraException {


		if(usuario == null){
			throw new LocadoraException("Usuario vazio");
		}

		if(filme == null){
			throw new LocadoraException("Filme vazio");
		}

		for(Filme filme1 : filme){
			if(filme1.getEstoque()==0) {
				throw new FilmesSemEstoqueException("Filme sem estoque");
			}
		}


		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		for (Filme filme1 : filme){
			valorTotal += filme1.getPrecoLocacao();
		}
		locacao.setValor(valorTotal);
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
//		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}


}