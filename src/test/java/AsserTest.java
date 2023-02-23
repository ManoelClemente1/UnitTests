import entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AsserTest {

    @Test
    public void test(){

        assertTrue(true);
        assertFalse(false);

        assertEquals(1,1);
        assertEquals(0.51,0.51, 0.01); // 0.01 margem de erro de comparação


        int i = 5;
        Integer i2 = 5;
        assertEquals(Integer.valueOf(i),i2);

        assertEquals("bola","bola");
        assertNotEquals("bola","casa");
        assertTrue("bola".equalsIgnoreCase("Bola"));
        assertTrue("bola".startsWith("bo"));

        Usuario u1 = new Usuario("Usuario 1");
        Usuario u2 = new Usuario("Usuario 1");
        Usuario u3 = new Usuario("Usuario 3");
        Usuario u4 = null;

        assertEquals(u1,u2);
        assertSame(u3,u3);
        assertTrue(u4 == null);
        assertNull(u4);
        assertNotNull(u3);

    }

}
