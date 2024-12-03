package org.fpij.jitakyoei.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.db4o.ext.ExtObjectContainer;

public class DatabaseManagerTest {

    @BeforeClass
    public static void setUp() {
        // Configura o ambiente para testes
        DatabaseManager.setEnviroment(DatabaseManager.TEST);
    }

    @AfterClass
    public static void tearDown() {
        // Fecha a conexão após os testes
        DatabaseManager.close();
    }

    @Test
    public void testOpenConnection() {
        // Testa se a conexão é aberta corretamente
        ExtObjectContainer connection = DatabaseManager.getConnection();
        assertNotNull("A conexão com o banco de dados não deve ser nula", connection);
        assertTrue("A conexão deve estar aberta", !connection.isClosed());
    }

    @Test
    public void testCloseConnection() {
        // Testa se a conexão é fechada corretamente
        ExtObjectContainer connection = DatabaseManager.getConnection();
        assertNotNull("A conexão com o banco de dados não deve ser nula", connection);

        DatabaseManager.close();
        assertTrue("A conexão deve estar fechada após o fechamento", connection.isClosed());
    }
}
