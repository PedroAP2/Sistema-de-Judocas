package org.fpij.jitakyoei.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class CorFaixaTest {

    @Test
    public void testToString() {
        // Verifica se o método toString retorna a descrição correta para uma cor específica
        CorFaixa faixa = CorFaixa.PRETA1DAN;
        assertEquals("Preta 1º Dan", faixa.toString());
    }

    @Test
    public void testGetDescricao() {
        // Verifica se o método getDescricao retorna a descrição correta
        CorFaixa faixa = CorFaixa.AZUL;
        assertEquals("Azul", faixa.getDescricao());
    }

    @Test
    public void testGetCoresFaixa() {
        // Verifica se a lista de cores de faixa é retornada corretamente
        List<CorFaixa> cores = CorFaixa.getCoresFaixa();
        assertNotNull("A lista de cores não deve ser nula", cores);
        assertTrue("A lista de cores deve conter pelo menos uma cor", cores.size() > 0);
        assertTrue("A lista de cores deve conter 'Branca'", cores.contains(CorFaixa.BRANCA));
        assertTrue("A lista de cores deve conter 'Preta 1º Dan'", cores.contains(CorFaixa.PRETA1DAN));
    }
}

