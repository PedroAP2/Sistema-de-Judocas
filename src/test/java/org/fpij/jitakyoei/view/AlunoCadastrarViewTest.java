package org.fpij.jitakyoei.view;

import static org.mockito.Mockito.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.fpij.jitakyoei.facade.AppFacade;
import org.fpij.jitakyoei.model.beans.Aluno;
import org.junit.Before;
import org.junit.Test;

public class AlunoCadastrarViewTest {

    private AlunoCadastrarView view;
    private AppFacade facadeMock;
    private MainAppView parentMock;

    @Before
    public void setUp() {
        // Mock de dependências
        facadeMock = mock(AppFacade.class);
        parentMock = mock(MainAppView.class);

        // Instancia a view
        view = new AlunoCadastrarView(parentMock);
        view.registerFacade(facadeMock);
    }

    @Test
    public void testCadastrarAluno() {
        // Simula a ação de clicar no botão "Cadastrar"
        Aluno alunoMock = new Aluno();
        when(facadeMock.createAluno(any(Aluno.class))).thenReturn(null); // Simula o método da fachada

        // Obtém o botão "Cadastrar" e simula o clique
        JButton cadastrarButton = view.getCadastrarButton();
        cadastrarButton.doClick(); // Simula o clique no botão

        // Verifica se o método correto foi chamado
        verify(facadeMock, times(1)).createAluno(any(Aluno.class));
        verify(parentMock, times(1)).removeTabPanel(any(JPanel.class));
    }
}
