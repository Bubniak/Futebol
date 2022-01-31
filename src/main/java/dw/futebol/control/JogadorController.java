package dw.futebol.control;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dw.futebol.model.Jogador;
import dw.futebol.repository.JogadorRepository;

@RestController
public class JogadorController 
{
    @Autowired
    JogadorRepository jogadorRepository;

    /* GET /api/jogadores : retorna todos os jogadores */
    @RequestMapping(method = RequestMethod.GET, value = "/api/jogadores")
    public ResponseEntity<List<Jogador>> getJogadores(@RequestParam(required = false) String nome)
    {
        try
        {
            List<Jogador> jogadores = new ArrayList<Jogador>();

            if(nome == null)
            {
                jogadorRepository.findAll().forEach(jogadores::add);
            }

            else
            {
                jogadorRepository.findByNomeContaining(nome).forEach(jogadores::add);
            }

            if(jogadores.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(jogadores, HttpStatus.OK);
            
        }

        catch(Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* POST /api/jogadores : cadastra um jogador */
    @RequestMapping(method = RequestMethod.POST, value = "/api/jogadores")
    public ResponseEntity<Jogador> createJogador(@RequestBody Jogador jogador)
    {
        try
        {
            Jogador j = jogadorRepository.save(new Jogador(jogador.getNome(), jogador.getEmail(), jogador.getDataNascimento(), null));

            return new ResponseEntity<>(j, HttpStatus.CREATED);
        }

        catch(Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /* GET /api/jogadores/{id} : retorna o jogador que possui o id especificado */
    @RequestMapping(method = RequestMethod.GET, value = "/api/jogadores/{id}")
    public ResponseEntity<Jogador> getJogadorById(@PathVariable("id") long id)
    {
        Optional<Jogador> jogador = jogadorRepository.findById(id);

        if(jogador.isPresent()) return new ResponseEntity<>(jogador.get(), HttpStatus.OK);

        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /* PUT /api/jogadores/{id} : atualiza o jogador que possui o id especificado */
    @RequestMapping(method = RequestMethod.PUT, value = "/api/jogadores/{id}")
    public ResponseEntity<Jogador> updateJogador(@PathVariable("id") long id, @RequestBody Jogador j)
    {
        Optional<Jogador> jogador = jogadorRepository.findById(id);

        if(jogador.isPresent())
        {
            Jogador _j = jogador.get();

            _j.setNome(j.getNome());
            _j.setEmail(j.getEmail());
            _j.setDataNascimento(j.getDataNascimento());
            _j.setPagamentos(j.getPagamentos());

            return new ResponseEntity<>(jogadorRepository.save(_j), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /* DELETE /api/jogadores/{id} : deleta um jogador dado um id */
    @RequestMapping(method = RequestMethod.DELETE, value = "/api/jogadores/{id}")
    public ResponseEntity<HttpStatus> deleteJogador(@PathVariable("id") long id)
    {
        try
        {
            jogadorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* DELETE /api/jogadores : deleta todos os jogadores */
    @RequestMapping(method = RequestMethod.DELETE, value = "/api/jogadores")
    public ResponseEntity<HttpStatus> deleteAllJogadores()
    {
        try
        {
            jogadorRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
