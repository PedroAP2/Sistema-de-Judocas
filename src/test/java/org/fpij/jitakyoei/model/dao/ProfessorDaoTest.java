package org.fpij.jitakyoei.model.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.model.beans.Endereco;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProfessorDaoTest {

    private static DAO<Professor> professorDao;
    private static Professor professor;
    private static Filiado filiado;
    private static Endereco endereco;

    @BeforeClass
    public static void setUp() {
        // Configura o ambiente de teste
        DatabaseManager.setEnviroment(DatabaseManager.TEST);

        endereco = new Endereco();
        endereco.setRua("Rua das Flores");
        endereco.setBairro("Centro");
        endereco.setCidade("Teresina");
        endereco.setEstado("PI");
        endereco.setCep("64000-000");

        filiado = new Filiado();
        filiado.setNome("João Silva");
        filiado.setCpf("123.456.789-00");
        filiado.setDataNascimento(new Date());
        filiado.setDataCadastro(new Date());
        filiado.setEndereco(endereco);
        filiado.setId(1L);

        professor = new Professor();
        professor.setFiliado(filiado);

        professorDao = new DAOImpl<>(Professor.class);
    }

    public static void clearDatabase() {
        // Limpa os dados do banco de teste
        List<Professor> all = professorDao.list();
        for (Professor each : all) {
            professorDao.delete(each);
        }
        assertEquals(0, professorDao.list().size());
    }

    @Test
    public void testSalvarProfessor() throws Exception {
        // Testa salvar um professor e verifica os dados associados
        clearDatabase();

        professorDao.save(professor);
        Professor savedProfessor = professorDao.get(professor);

        assertEquals("João Silva", savedProfessor.getFiliado().getNome());
        assertEquals("123.456.789-00", savedProfessor.getFiliado().getCpf());
        assertEquals("Rua das Flores", savedProfessor.getFiliado().getEndereco().getRua());
    }

    @Test
    public void testAtualizarProfessor() throws Exception {
        // Testa a atualização dos dados de um professor
        clearDatabase();

        professorDao.save(professor);
        Professor p = professorDao.get(professor);
        p.getFiliado().setNome("Carlos Souza");
        professorDao.save(p);

        Professor updatedProfessor = professorDao.get(p);
        assertEquals("Carlos Souza", updatedProfessor.getFiliado().getNome());
    }

    @Test
    public void testListarProfessores() {
        // Testa listar professores e validar a contagem
        clearDatabase();

        int initialCount = professorDao.list().size();
        professorDao.save(new Professor());
        professorDao.save(new Professor());
        professorDao.save(new Professor());

        assertEquals(initialCount + 3, professorDao.list().size());
    }

    @Test
    public void testPesquisarProfessor() throws Exception {
        // Testa a busca de professores com base em critérios
        clearDatabase();

        professorDao.save(professor);

        Filiado searchCriteria = new Filiado();
        searchCriteria.setNome("João Silva");
        Professor searchProfessor = new Professor();
        searchProfessor.setFiliado(searchCriteria);

        List<Professor> result = professorDao.search(searchProfessor);
        assertEquals(1, result.size());
        assertEquals("123.456.789-00", result.get(0).getFiliado().getCpf());
    }

    @AfterClass
    public static void closeDatabase() {
        // Fecha o banco de dados e limpa os dados
        clearDatabase();
        DatabaseManager.close();
    }
}
