package com.ebury.cadastrocliente.entity.cadastro.cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ClientePj")
@PrimaryKeyJoinColumn(name = "id")
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientePj extends Cliente {

    @Column(length = 18, nullable = false)
    protected String cnpj;

    @Column(length = 20)
    protected String inscricaoEstadual;

    @Column(length = 2)
    protected String ufEmissorInscricaoEstadual;

    @Column(length = 20)
    protected String inscricaoMunicipal;

    @Temporal(TemporalType.DATE)
    protected Date dataConstituicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "naturezaJuridica_id")
    protected NaturezaJuridica naturezaJuridica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "porteDoCliente_id")
    protected PorteDoCliente porteDoCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ramoDeAtividade_id")
    protected RamoDeAtividade ramoDeAtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividadePrincipal_id")
    protected AtividadePrincipal atividadePrincipal;

    @Column(length = 100)
    protected String nomeFantasia;

    @Column(length = 100)
    protected String razaoSocial;

    @Column(length = 20)
    protected String faturamentoMedioMensal;

    @Column(length = 100)
    protected String ultimoContato;

    // Construtores
    public ClientePj() {
        super();
    }

    public ClientePj(Long id) {
        super(id);
    }
}
