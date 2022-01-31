package dw.futebol.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dw.futebol.model.Jogador;
import dw.futebol.model.Pagamento;
import dw.futebol.repository.JogadorRepository;
import dw.futebol.repository.PagamentoRepository;

@RestController
public class PagamentoController 
{
    @Autowired
    PagamentoRepository pagamentoRepository;
    
    @Autowired
    JogadorRepository jogadorRepository;


    /* GET /api/pagamentos : retorna todos os pagamentos */ 
    @RequestMapping(method = RequestMethod.GET, value = "/api/pagamentos")
    public ResponseEntity<List<Pagamento>> getPagamentos()
    {
        try
        {
            List<Pagamento> pagamentos = new ArrayList<Pagamento>();

            pagamentoRepository.findAll().forEach(pagamentos::add);

            if(pagamentos.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(pagamentos, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* GET /api/pagamentos/{id} : retorna o pagamento por um id */
    @RequestMapping(method = RequestMethod.GET, value = "/api/pagamentos/{id}")
    public ResponseEntity<Pagamento> getPagamentoById(@PathVariable("id") long id)
    {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if(pagamento.isPresent()) return new ResponseEntity<>(pagamento.get(), HttpStatus.OK);

        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /* GET /api/jogadores/{jogadorId}/pagamentos : pega os pagamentos de um determinado jogador */ 
    @RequestMapping(method = RequestMethod.GET, value = "/api/jogadores/{jogadorId}/pagamentos")
    public ResponseEntity<List<Pagamento>> getPagamentosByJogador(@PathVariable("jogadorId") long jogadorId)
    {
        

        try
        {
            List<Pagamento> pagamentos = new ArrayList<Pagamento>();
            pagamentoRepository.findByJogadorId(jogadorId).forEach(pagamentos::add);

            if(pagamentos.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(pagamentos, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* DELETE /api/pagamentos/{pagamentoId} : deleta o pagamento dado um id */
    @RequestMapping(method = RequestMethod.DELETE, value = "api/pagamentos/{pagamentoId}")
    public ResponseEntity<HttpStatus> deletePagamentoById(@PathVariable("pagamentoId") long pagamentoId)
    {
        try
        {
            pagamentoRepository.deleteById(pagamentoId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* DELETE /api/pagamentos : deleta todos os pagamentos */
    @RequestMapping(method = RequestMethod.DELETE, value = "/api/pagamentos")
    public ResponseEntity<HttpStatus> deleteAllPagamentos()
    {
        try
        {
            pagamentoRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* DELETE /api/jogadores/{jogadorId}/pagamentos : deleta todos os pagamentos de um jogador */ 
    @RequestMapping(method = RequestMethod.DELETE, value = "/api/jogadores/{jogadorId}/pagamentos")
    public ResponseEntity<HttpStatus> deleteAllPagamentosByJogador(@PathVariable("jogadorId") long jogadorId)
    {
        try
        {
            pagamentoRepository.deleteByJogadorId(jogadorId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* POST /api/jogadores/{jogadorId}/pagamentos : adiciona um pagamento */
    @RequestMapping(method = RequestMethod.POST, value = "/api/jogadores/{jogadorId}/pagamentos")
    public ResponseEntity<Pagamento> createPagamento(@PathVariable("jogadorId") long jogadorId, @RequestBody Pagamento pagamento)
    {
        try
        {
            Optional<Jogador> jogador = jogadorRepository.findById(jogadorId);

            
            if(jogador.isPresent())
            {
                pagamento.setJogador(jogador.get());
                Pagamento p = pagamentoRepository.save(new Pagamento(pagamento.getAno(), pagamento.getMes(), pagamento.getValor(), pagamento.getJogador()));
                return new ResponseEntity<>(p, HttpStatus.CREATED);
            }

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            
        }

        catch(Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /* PUT /api/pagamentos/{pagamentoId} : atualiza o pagamento dado um id (não altera o jogador) */
    @RequestMapping(method = RequestMethod.PUT, value = "api/pagamentos/{pagamentoId}")
    public ResponseEntity<Pagamento> updatePagamento(@PathVariable("pagamentoId") long pagamentoId, @RequestBody Pagamento pagamento)
    {

        Optional<Pagamento> p = pagamentoRepository.findById(pagamentoId);

        if(p.isPresent())
        {
            Pagamento _p = p.get();

            _p.setAno(pagamento.getAno());
            _p.setMes(pagamento.getMes());
            _p.setValor(pagamento.getValor());

        //    if(!pagamento.getJogador().equals(null))
          //      _p.setJogador(pagamento.getJogador());

            return new ResponseEntity<>(pagamentoRepository.save(_p), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
