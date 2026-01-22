package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

        @Autowired
        TransacaoService transacaoService;
    /**
     * Commit -> confirma as alterações
     * Rollback -> desfazer as alterações
     */
    @Test
    @Transactional
    @Commit
    void transacaoSimples() {
        transacaoService.executar();
    }
}
        // salvar um livro
        // salvar o autor
        // akugar o livro
        // enviar email pro locatário
        // notificar que o livro saiu da livraria
