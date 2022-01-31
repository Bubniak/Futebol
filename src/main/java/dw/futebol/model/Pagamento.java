package dw.futebol.model;

import javax.persistence.*;



@Entity
@Table(name = "pagamento", schema = "futebol")
public class Pagamento 
{
    @Id
    @GeneratedValue
    private long id;

    @Column
    private short ano;

    @Column
    private byte mes;

    @Column
    private float valor;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_jogador")
    private Jogador jogador;


//region constructors

    public Pagamento(){};

    public Pagamento(short ano, byte mes, float valor, Jogador jogador) 
    {
        this.setAno(ano);
        this.setMes(mes);
        this.setValor(valor);
        this.setJogador(jogador);
    }


//endregion
    
//region getters and setters

    public long getId() 
    {
        return id;
    }


    public short getAno() 
    {
        return ano;
    }
    
    public void setAno(short ano) 
    {
        this.ano = ano;
    }


    public byte getMes() 
    {
        return mes;
    }

    public void setMes(byte mes) 
    {
        this.mes = mes;
    }


    public float getValor() 
    {
        return valor;
    }

    public void setValor(float valor) 
    {
        this.valor = valor;
    }


    public Jogador getJogador() 
    {
        return jogador;
    }

    public void setJogador(Jogador jogador) 
    {
        this.jogador = jogador;
    }

//endregion

}
