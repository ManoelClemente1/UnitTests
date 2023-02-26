package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmesSemEstoqueException;
import exceptions.LocadoraException;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import utils.DataUtils;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

    private LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setup(){
        service = new LocacaoService();
    }



    @Test
    public void deveAlugarFilme() throws Exception {

        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));

        //cenario

        Usuario usuario = new Usuario("Usuario 1");
        Filme filme1 = new Filme("Filme 1", 1, 5.0);
        Filme filme2 = new Filme("Filme 2", 1, 5.0);
        Filme filme3 = new Filme("Filme 3", 1, 5.0);
        List<Filme> filmes = new ArrayList<>();

        filmes = Arrays.asList(filme1,filme2,filme3);

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(locacao.getValor(), is(equalTo(13.75)));
        assertThat(locacao.getValor(), is(not(6.0)));
        assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        assertFalse(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
    }


    @Test(expected = FilmesSemEstoqueException.class)
    public void naoDeveAlugarFilmeSemEstoque() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme1 = new Filme("Filme 1", 0, 5.0);
        Filme filme2 = new Filme("Filme 2", 1, 5.0);
        Filme filme3 = new Filme("Filme 3", 1, 5.0);
        List<Filme> filmes = new ArrayList<>();

        filmes = Arrays.asList(filme1,filme2,filme3);

        //acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemEstoque2() {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme1 = new Filme("Filme 1", 0, 5.0);
        Filme filme2 = new Filme("Filme 2", 1, 5.0);
        Filme filme3 = new Filme("Filme 3", 1, 5.0);
        List<Filme> filmes = new ArrayList<>();

        filmes = Arrays.asList(filme1,filme2,filme3);

        //acao
        try {
            service.alugarFilme(usuario, filmes);
            Assert.fail("Deveria ter lançado uma excecao");
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(),is("Filme sem estoque"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemEstoque3() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme1 = new Filme("Filme 1", 0, 5.0);
        Filme filme2 = new Filme("Filme 2", 1, 5.0);
        Filme filme3 = new Filme("Filme 3", 1, 5.0);
        List<Filme> filmes = new ArrayList<>();

        filmes = Arrays.asList(filme1,filme2,filme3);

        exception.expect(Exception.class);
        exception.expectMessage("Filme sem estoque");

        //acao
        service.alugarFilme(usuario, filmes);


    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmesSemEstoqueException {
        Filme filme1 = new Filme("Filme 1", 1, 5.0);
        Filme filme2 = new Filme("Filme 2", 1, 5.0);
        Filme filme3 = new Filme("Filme 3", 1, 5.0);
        List<Filme> filmes = new ArrayList<>();

        filmes = Arrays.asList(filme1,filme2,filme3);

        try {
            service.alugarFilme(null, filmes);
        } catch (LocadoraException e) {
            Assert.assertThat(e.getMessage(), is("Usuario vazio"));
        }

    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmesSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario 1");


        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        service.alugarFilme(usuario,null);

    }

    @Test
    public void devePagar75NoFilme3() throws FilmesSemEstoqueException, LocadoraException {

        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme1 = new Filme("Filme 1", 1, 4.0);
        Filme filme2 = new Filme("Filme 2", 1, 4.0);
        Filme filme3 = new Filme("Filme 3", 1, 4.0);
        List<Filme> filmes = new ArrayList<>();
        filmes = Arrays.asList(filme1,filme2,filme3);

        //acao
        Locacao resultado = service.alugarFilme(usuario,filmes);

        //verificacao
        Assert.assertThat(resultado.getValor(), is(11.0));

    }

    @Test
    public void devePagar50NoFilme4() throws FilmesSemEstoqueException, LocadoraException {

        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme1 = new Filme("Filme 1", 1, 4.0);
        Filme filme2 = new Filme("Filme 2", 1, 4.0);
        Filme filme3 = new Filme("Filme 3", 1, 4.0);
        Filme filme4 = new Filme("Filme 3", 1, 4.0);
        List<Filme> filmes = new ArrayList<>();
        filmes = Arrays.asList(filme1,filme2,filme3,filme4);

        //acao
        Locacao resultado = service.alugarFilme(usuario,filmes);

        //verificacao
        Assert.assertThat(resultado.getValor(), is(13.0));

    }

    @Test
    public void naoDeveDevolverFilmeNoDomingo() throws FilmesSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));

        Usuario usuario = new Usuario("Usuario 1");
        Filme filme1 = new Filme("Filme 1", 1, 4.0);
        Filme filme2 = new Filme("Filme 2", 1, 4.0);
        Filme filme3 = new Filme("Filme 3", 1, 4.0);
        List<Filme> filmes = new ArrayList<>();
        filmes = Arrays.asList(filme1,filme2,filme3);

        Locacao retorno = service.alugarFilme(usuario,filmes);

        boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);

        Assert.assertTrue(ehSegunda);

    }




}
