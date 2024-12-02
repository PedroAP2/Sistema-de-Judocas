package org.fpij.jitakyoei.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppFacadeTest {

    private static AppFacade facade;
    private static Aluno aluno;
    private static Professor professor;
    private static Entidade entidade;

    @BeforeClass
    public static void setUp() {
        // Configura o ambiente de teste
        DatabaseManager.setEnviroment(DatabaseManager.TEST);

        // Configuração do Aluno
        Filiado filiadoAluno = new Filiado();
        filiadoAluno.setId(1L);
        filiadoAluno.setNome("Aluno Teste");
        filiadoAluno.setCpf("123.456.789-00");
        filiadoAluno.setDataCadastro(new Date());
        filiadoAluno.setDataNascimento(new Date());

        aluno = new Aluno();
        aluno.setFiliado(filiadoAluno);

        // Configuração do Professor
        Filiado filiadoProfessor = new Filiado();
        filiadoProfessor.setId(2L);
        filiadoProfessor.setNome("Professor Teste");
        filiadoProfessor.setCpf("987.654.321-00");
        filiadoProfessor.setDataCadastro(new Date());
        filiadoProfessor.setDataNascimento(new Date());

        professor = new Professor();
        professor.setFiliado(filiadoProfessor);

        // Configuração da Entidade
        entidade = new Entidade();
        entidade.setNome("Entidade Teste");
        entidade.setCnpj("12.345.678/0001-90");
        entidade.setTelefone1("(86) 99999-9999");

        // Inicializa a fachada
        facade = new AppFacadeImpl(null); // Passando null como view para testes simples
    }

    @AfterClass
    public static void tearDown() {
        DatabaseManager.close();
    }

    @Test
    public void testCreateAluno() {
        facade.createAluno(aluno);

        List<Aluno> alunos = facade.searchAluno(aluno);
        assertNotNull("A lista de alunos não deve ser nula", alunos);
        assertEquals("Deve haver apenas um aluno", 1, alunos.size());
        assertEquals("O nome do aluno deve ser 'Aluno Teste'", "Aluno Teste", alunos.get(0).getFiliado().getNome());
    }

    @Test
    public void testSearchAluno() {
        facade.createAluno(aluno);

        Aluno searchAluno = new Aluno();
        searchAluno.setFiliado(new Filiado());
        searchAluno.getFiliado().setNome("Aluno Teste");

        List<Aluno> result = facade.searchAluno(searchAluno);
        assertNotNull("A lista de resultados não deve ser nula", result);
        assertEquals("Deve haver apenas um resultado", 1, result.size());
        assertEquals("O CPF do aluno deve ser '123.456.789-00'", "123.456.789-00", result.get(0).getFiliado().getCpf());
    }

    @Test
    public void testCreateProfessor() {
        facade.createProfessor(professor);

        List<Professor> professores = facade.searchProfessor(professor);
        assertNotNull("A lista de professores não deve ser nula", professores);
        assertEquals("Deve haver apenas um professor", 1, professores.size());
        assertEquals("O nome do professor deve ser 'Professor Teste'", "Professor Teste", professores.get(0).getFiliado().getNome());
    }

    @Test
    public void testCreateEntidade() {
        facade.createEntidade(entidade);

        List<Entidade> entidades = facade.listEntidade();
        assertNotNull("A lista de entidades não deve ser nula", entidades);
        assertEquals("Deve haver apenas uma entidade", 1, entidades.size());
        assertEquals("O nome da entidade deve ser 'Entidade Teste'", "Entidade Teste", entidades.get(0).getNome());
    }
}

