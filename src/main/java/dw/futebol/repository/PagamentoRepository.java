package dw.futebol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import dw.futebol.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> 
{
    List<Pagamento> findByJogadorId(long jogadorId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM pagamento p WHERE p.id_jogador = ?1")
    void deleteByJogadorId(long jogadorId);
}