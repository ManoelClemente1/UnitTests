package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmesSemEstoqueException;
import exceptions.LocadoraException;
import utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static utils.DataUtils.adicionarDias;


public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmesSemEstoqueException, LocadoraException {


		if(usuario == null){
			throw new LocadoraException("Usuario vazio");
		}

		if(filmes == null){
			throw new LocadoraException("Filme vazio");
		}

		for(Filme filme1 : filmes){
			if(filme1.getEstoque()==0) {
				throw new FilmesSemEstoqueException("Filme sem estoque");
			}
		}


		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		for (int i = 0; i< filmes.size(); i++){
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();

			switch (i){
				case 2:
					valorFilme = valorFilme * 0.75;
				break;
				case 3:
					valorFilme = valorFilme * 0.5;
				break;
			}

			valorTotal += valorFilme;
		}
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)){
			dataEntrega = adicionarDias(dataEntrega,1);
		}

		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}


}