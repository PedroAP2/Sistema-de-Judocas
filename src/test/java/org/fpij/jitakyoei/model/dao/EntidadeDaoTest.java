package org.fpij.jitakyoei.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.fpij.jitakyoei.model.beans.Endereco;
import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class EntidadeDaoTest {

    private static DAO<Entidade> entidadeDao;
    private static Entidade entidade;

    @BeforeClass
    public static void setUp() {
        // Configura o ambiente de teste
        DatabaseManager.setEnviroment(DatabaseManager.TEST);

        Endereco endereco = new Endereco();
        endereco.setRua("Rua Principal");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("Teresina");
        endereco.setEstado("PI");
        endereco.setCep("64000-000");

        entidade = new Entidade();
        entidade.setNome("Academia Central");
        entidade.setCnpj("12.345.678/0001-90");
        entidade.setTelefone1("(086)9876-5432");
        entidade.setTelefone2("(086)1234-5678");
        entidade.setEndereco(endereco);

        entidadeDao = new DAOImpl<>(Entidade.class);
    }

    public static void clearDatabase() {
        // Limpa todos os dados do banco de teste
        List<Entidade> all = entidadeDao.list();
        for (Entidade each : all) {
            entidadeDao.delete(each);
        }
        assertEquals(0, entidadeDao.list().size());
    }

    @Test
    public void testSalvarEntidade() throws Exception {
        // Testa salvar uma entidade
        clearDatabase();

        entidadeDao.save(entidade);
        Entidade savedEntidade = entidadeDao.get(entidade);

        assertEquals("Academia Central", savedEntidade.getNome());
        assertEquals("12.345.678/0001-90", savedEntidade.getCnpj());
        assertEquals("Rua Principal", savedEntidade.getEndereco().getRua());
    }

    @Test
    public void testAtualizarEntidade() throws Exception {
        // Testa a atualização de uma entidade
        clearDatabase();

        entidadeDao.save(entidade);
        Entidade e = entidadeDao.get(entidade);
        e.setNome("Nova Academia");
        entidadeDao.save(e);

        Entidade updatedEntidade = entidadeDao.get(e);
        assertEquals("Nova Academia", updatedEntidade.getNome());
    }

    @Test
    public void testListarEntidades() {
        // Testa a listagem de entidades
        clearDatabase();

        int initialCount = entidadeDao.list().size();
        entidadeDao.save(new Entidade());
        entidadeDao.save(new Entidade());
        entidadeDao.save(new Entidade());

        assertEquals(initialCount + 3, entidadeDao.list().size());
    }

    @Test
    public void testPesquisarEntidade() throws Exception {
        // Testa a pesquisa de entidades
        clearDatabase();

        entidadeDao.save(entidade);

        Entidade searchCriteria = new Entidade();
        searchCriteria.setNome("Academia Central");

        List<Entidade> result = entidadeDao.search(searchCriteria);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("12.345.678/0001-90", result.get(0).getCnpj());
    }

    @AfterClass
    public static void closeDatabase() {
        // Fecha o banco de dados e limpa os dados
        clearDatabase();
        DatabaseManager.close();
    }
}
