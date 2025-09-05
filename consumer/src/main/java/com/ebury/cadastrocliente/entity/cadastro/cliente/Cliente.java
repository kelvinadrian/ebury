package com.ebury.cadastrocliente.entity.cadastro.cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Cliente")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper = false)
public class Cliente {

    @Id
    @GeneratedValue(generator = "seq_sqcambio")
    @org.hibernate.annotations.GenericGenerator(
            name = "seq_sqcambio",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "sequences_sqcambio"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "4"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", columnDefinition = "bigint default 0")
    protected long version;

    @Column(length = 250, nullable = false)
    protected String nome;

    @Column(length = 20)
    protected String codigoExterno;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dataDeCadastro;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dataDeDesativacao;

    @ManyToOne(fetch = FetchType.EAGER)
    protected TipoDePessoa tipoDePessoa;

    @Column(name = "codigocorporativo")
    protected String codigoCorporativo;

    @Column(length = 100, nullable = true)
    protected String observacoesContratacao;

    @Column(name = "indicadorpep", nullable = true)
    protected Boolean indicadorPep;

    @Column(name = "altorisco", nullable = true)
    protected Boolean altoRisco;

    @Column(name = "especialatencao", nullable = true)
    protected Boolean especialAtencao;

    @Column(name = "desabilitado", nullable = true)
    protected Boolean desabilitado;

    @Column(length = 1, nullable = true)
    protected Character eventual;

    @Column(nullable = true)
    protected String observacoesGerais;

    @Column(nullable = true)
    protected Boolean indicadorDeUtilizacaoDeAssinaturaDigital;

    @Column(name = "ebiAutoridadeMonetaria", nullable = true)
    protected Boolean ebiAutoridadeMonetaria;

    @Column(length = 10, nullable = false)
    protected String tipoDeResidencia;

    @Column(name = "bloqueiodocumentacao", nullable = true)
    private Boolean bloqueioDocumentacao;

    @Column(name = "bloqueiocontratonaoassinado", nullable = true)
    private Boolean bloqueioContratoNaoAssinado;

    @Column(name = "dataBloqueioDocumentacao", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataBloqueioDocumentacao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataBloqueioContratoNaoAssinado;

    @Column(nullable = true)
    protected Boolean pendenteDeAprovacao;

    @Column(length = 100, nullable = true)
    protected String numeroDeIdentificacaoFiscalNoExterior;

    @Column(length = 50, nullable = true)
    protected String nomeDeclarado;

    @Column(length = 30, nullable = true)
    protected String classificacaoPessoa;

    @Column(name = "clienteFornecedor", nullable = true)
    private Boolean clienteFornecedor;

    @Column(name = "whitelist", nullable = true)
    private boolean whiteList;

    @Column(name = "verificadocompliance", nullable = true)
    private Boolean verificadoCompliance;

    @Column(name = "instituicaoFinanceiraBanco", nullable = true)
    protected Boolean instituicaoFinanceiraBanco;

    @Column(name = "outrasInstituicoesFinanceiras", nullable = true)
    protected Boolean outrasInstituicoesFinanceiras;

    @Column(name = "iban", length = 30, nullable = true)
    protected String iban;

    // Listas de relacionamentos
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    protected List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    protected List<EnderecoNoExteriorDoCliente> enderecosNoExterior = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    protected List<ContaCorrente> contasCorrente = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    protected List<ContaCorrenteTedDoc> contasCorrenteTedDoc = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    protected List<ContatoDoCliente> contatos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    protected List<SociosAcionistasDoCliente> sociosAcionistas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    protected List<RepresentantesDoCliente> representantes = new ArrayList<>();

    // Construtores
    public Cliente() {
    }

    public Cliente(Long id) {
        this.id = id;
    }

}
