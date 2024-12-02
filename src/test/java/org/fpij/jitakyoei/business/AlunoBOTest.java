package org.fpij.jitakyoei.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AlunoBOTest {

    private static AlunoBO alunoBO;
    private static Aluno aluno;

    @BeforeClass
    public static void setUp() {
        DatabaseManager.setEnviroment(DatabaseManager.TEST);

        Filiado filiado = new Filiado();
        filiado.setId(1L);
        filiado.setNome("Teste Aluno");
        filiado.setCpf("123.456.789-00");
        filiado.setDataCadastro(new Date());
        filiado.setDataNascimento(new Date());

        aluno = new Aluno();
        aluno.setFiliado(filiado);

        alunoBO = new AlunoBOImpl(null); // Passando null como view para testes simples
    }

    @AfterClass
    public static void tearDown() {
        DatabaseManager.close();
    }

    @Test
    public void testCreateAluno() throws Exception {
        alunoBO.createAluno(aluno);

        List<Aluno> alunos = alunoBO.listAll();
        assertEquals(1, alunos.size());
        assertEquals("Teste Aluno", alunos.get(0).getFiliado().getNome());
    }

    @Test
    public void testUpdateAluno() throws Exception {
        alunoBO.createAluno(aluno);

        aluno.getFiliado().setNome("Aluno Atualizado");
        alunoBO.updateAluno(aluno);

        Aluno updatedAluno = alunoBO.searchAluno(aluno).get(0);
        assertEquals("Aluno Atualizado", updatedAluno.getFiliado().getNome());
    }

    @Test
    public void testSearchAluno() throws Exception {
        alunoBO.createAluno(aluno);

        Filiado searchCriteria = new Filiado();
        searchCriteria.setNome("Teste Aluno");

        Aluno searchAluno = new Aluno();
        searchAluno.setFiliado(searchCriteria);

        List<Aluno> result = alunoBO.searchAluno(searchAluno);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123.456.789-00", result.get(0).getFiliado().getCpf());
    }
}

