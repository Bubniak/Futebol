package dw.futebol.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "jogador", schema = "futebol")
public class Jogador 
{
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 60)
    private String nome;

    @Column(length = 60)
    private String email;

    @Column
    private Date dataNascimento;

    @JsonIgnore
    @OneToMany(targetEntity = Pagamento.class, mappedBy = "jogador", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos;


//region constructors

    public Jogador(){}

    public Jogador(String nome, String email, Date dataNascimento, List<Pagamento> pagamentos) 
    {
        this.setNome(nome);
        this.setEmail(email);
        this.setDataNascimento(dataNascimento);
        this.setPagamentos(pagamentos);
    }

//endregion

//region getters and setters

    public long getId()
    {
        return id;
    }


    public String getNome() 
    {
        return nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }


    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }


    public Date getDataNascimento() 
    {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) 
    {
        this.dataNascimento = dataNascimento;
    }


    public List<Pagamento> getPagamentos()
    {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos)
    {
        this.pagamentos = pagamentos;
    }

//endregion


}
