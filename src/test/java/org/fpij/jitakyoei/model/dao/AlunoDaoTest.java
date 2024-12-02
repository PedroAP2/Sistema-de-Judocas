package org.fpij.jitakyoei.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.junit.BeforeClass;
import org.junit.Test;


public class AlunoDaoTest {

    private static DAO<Aluno> alunoDao;
    private static Aluno aluno;

    @BeforeClass
    public static void setUp() {
        // Inicializa o DAO e o objeto de teste simplificado
        alunoDao = new DAOImpl<>(Aluno.class);

        // Configura um Aluno básico para ser usado em todos os testes
        Filiado filiado = new Filiado();
        filiado.setNome("Aécio");
        filiado.setCpf("036.464.453-27");
        filiado.setDataNascimento(new Date());
        filiado.setDataCadastro(new Date());

        aluno = new Aluno();
        aluno.setFiliado(filiado);
    }

    @Test
    public void testSalvarAluno() {
        alunoDao.save(aluno);

        Aluno alunoSalvo = alunoDao.get(aluno);
        assertNotNull(alunoSalvo);
        assertEquals("Aécio", alunoSalvo.getFiliado().getNome());
        assertEquals("036.464.453-27", alunoSalvo.getFiliado().getCpf());
    }

    @Test
    public void testUpdateAluno() {
        alunoDao.save(aluno);

        Aluno alunoParaAtualizar = alunoDao.get(aluno);
        assertNotNull(alunoParaAtualizar);

        alunoParaAtualizar.getFiliado().setNome("TesteUpdate");
        alunoDao.save(alunoParaAtualizar);

        Aluno alunoAtualizado = alunoDao.get(alunoParaAtualizar);
        assertEquals("TesteUpdate", alunoAtualizado.getFiliado().getNome());
    }

    @Test
    public void testListarAlunos() {
        int qtdInicial = alunoDao.list().size();

        alunoDao.save(new Aluno());
        assertEquals(qtdInicial + 1, alunoDao.list().size());

        alunoDao.save(new Aluno());
        assertEquals(qtdInicial + 2, alunoDao.list().size());
    }

    @Test
    public void testSearchAluno() {
        alunoDao.save(aluno);

        Filiado filiadoBusca = new Filiado();
        filiadoBusca.setNome("Aécio");
        Aluno alunoBusca = new Aluno();
        alunoBusca.setFiliado(filiadoBusca);

        List<Aluno> resultadoBusca = alunoDao.search(alunoBusca);
        assertNotNull(resultadoBusca);
        assertEquals(1, resultadoBusca.size());
    }
}
