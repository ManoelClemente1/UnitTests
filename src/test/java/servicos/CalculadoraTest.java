package servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {


    private Calculadora calculadora;

    @Before
    public void setup(){
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomar(){
        int a = 1;
        int b = 2;

        int resultado  = calculadora.soma(a,b);
        Assert.assertEquals(3, resultado);

    }

}
