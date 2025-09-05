package com.ebury.cadastrocliente.entity.cadastro.cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ClientePf")
@PrimaryKeyJoinColumn(name = "id")
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientePf extends Cliente {

    @Column(length = 14, nullable = false)
    protected String cpf;

    @Column(length = 20)
    protected String identidade;

    @Column(length = 10)
    protected String orgaoExpedidor;

    @Column(length = 2)
    protected String ufExpedidor;

    @Temporal(TemporalType.DATE)
    protected Date dataExpedicao;

    @Temporal(TemporalType.DATE)
    protected Date dataNascimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sexo_id")
    protected Sexo sexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estadoCivil_id")
    protected EstadoCivil estadoCivil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nacionalidade_id")
    protected Nacionalidade nacionalidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissao_id")
    protected Profissao profissao;

    @Column(length = 100)
    protected String nomeMae;

    @Column(length = 100)
    protected String nomePai;

    @Column(length = 50)
    protected String naturalidade;

    @Column(length = 2)
    protected String ufNaturalidade;

    // Construtores
    public ClientePf() {
        super();
    }

    public ClientePf(Long id) {
        super(id);
    }
}
