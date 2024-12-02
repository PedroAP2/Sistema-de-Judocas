package org.fpij.jitakyoei.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.fpij.jitakyoei.model.beans.Endereco;
import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class EntidadeBOTest {

    private static EntidadeBO entidadeBO;
    private static Entidade entidade;

    @BeforeClass
    public static void setUp() {
        DatabaseManager.setEnviroment(DatabaseManager.TEST);

        Endereco endereco = new Endereco();
        endereco.setRua("Rua Principal");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("Teresina");
        endereco.setEstado("PI");
        endereco.setCep("64000-000");

        entidade = new Entidade();
        entidade.setNome("Entidade Teste");
        entidade.setCnpj("12.345.678/0001-90");
        entidade.setTelefone1("123456789");
        entidade.setEndereco(endereco);

        entidadeBO = new EntidadeBOImpl(null); // Passando null como view para testes simples
    }

    @AfterClass
    public static void tearDown() {
        DatabaseManager.close();
    }

    @Test
    public void testCreateEntidade() throws Exception {
        entidadeBO.createEntidade(entidade);

        List<Entidade> entidades = entidadeBO.listAll();
        assertEquals(1, entidades.size());
        assertEquals("Entidade Teste", entidades.get(0).getNome());
    }

    @Test
    public void testUpdateEntidade() throws Exception {
        entidadeBO.createEntidade(entidade);

        entidade.setNome("Entidade Atualizada");
        entidadeBO.updateEntidade(entidade);

        List<Entidade> result = entidadeBO.searchEntidade(entidade);
        assertEquals("Entidade Atualizada", result.get(0).getNome());
    }

    @Test
    public void testSearchEntidade() throws Exception {
        entidadeBO.createEntidade(entidade);

        Entidade searchCriteria = new Entidade();
        searchCriteria.setNome("Entidade Teste");

        List<Entidade> result = entidadeBO.searchEntidade(searchCriteria);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("12.345.678/0001-90", result.get(0).getCnpj());
    }
}

