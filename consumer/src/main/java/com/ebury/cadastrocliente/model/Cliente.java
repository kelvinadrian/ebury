package com.ebury.cadastrocliente.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cliente")
@Inheritance(strategy = InheritanceType.JOINED)
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    
    public static final String CLASSIFICACAO_PESSOA_CLIENTE = "CLIENTE";
    public static final String CLASSIFICACAO_PESSOA_FORNECEDOR = "FORNECEDOR";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 250, nullable = false)
    private String nome;
    
    @Column(length = 20)
    private String codigoExterno;
    
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private java.util.Date dataDeCadastro;
    
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private java.util.Date dataDeDesativacao;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoDePessoa tipoDePessoa;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<Endereco> enderecos = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<HistoricoDoCliente> historicoDoClienteVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<EnderecoNoExteriorDoCliente> enderecoNoExteriorDoClienteVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<ClienteEmailsParaReceberDocumento> clienteEmailsParaReceberDocumentoVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<ContaCorrente> contaCorrenteVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<ContaCorrenteME> contaCorrenteMEVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<ContaCorrenteTedDoc> contaCorrenteTedDocVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<SinonimoCliente> listaSinonimoCliente;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veiculoLegal_id")
    private VeiculoLegal veiculoLegal;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "naturezajuridica_id", nullable = true)
    private NaturezaJuridica naturezaJuridica;
    
    @Column(name = "codigocorporativo")
    private String codigoCorporativo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modoDeContratacao_id", nullable = true)
    private ModoDeContratacao modoDeContratacao;
    
    @Column(length = 100, nullable = true)
    private String observacoesContratacao;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CorretoraDoCliente> corretoraDoClienteVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<ComplianceDoCliente> compliancesDoClienteVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<EntregaDeDocumentosDoCliente> entregaDeDocumentosDoClienteVOs = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<ProcuracoesDoCliente> procuracoesDoClienteVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<RepresentantesDoCliente> representantesDoClienteVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<FirmasEPoderesDoCliente> firmasEPoderesDoClienteVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    @OrderBy("ordenlista ASC")
    private List<SociosAcionistasDoCliente> sociosAcionistasDoClienteVOs;
    
    @Column(name = "indicadorpep", nullable = true)
    private Boolean indicadorPep;
    
    @Column(name = "altorisco", nullable = true)
    private Boolean altoRisco;
    
    @Column(name = "especialatencao", nullable = true)
    private Boolean especialAtencao;
    
    @Column(name = "desabilitado", nullable = true)
    private Boolean desabilitado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidadedenegocio_id", nullable = true)
    private UnidadeDeNegocios unidadeDeNegocio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gestor_id", nullable = true)
    private Usuario gestor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gerenteanalistaoriginador_id", nullable = true)
    private Usuario gerenteAnalistaOriginador;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<ParceiroDoCliente> parceiroDoClientVOs;
    
    @Column(length = 1, nullable = true)
    private Character eventual;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complementodanaturezadocliente_id", nullable = true)
    private ComplementoDaNaturezaDoCliente complementoDaNaturezaDoCliente;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<CapacidadeFinanceira> capacidadeFinanceiraVOs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<ClientePorModalidade> clientePorModalidadeVOs = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EventoDoCompliance> listaEventoDoCompliance;
    
    @Column(nullable = true)
    private String observacoesGerais;
    
    @Column(nullable = true)
    private Boolean indicadorDeUtilizacaoDeAssinaturaDigital;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "originador_id", nullable = true)
    private Originador originador;
    
    @Column(insertable = false, updatable = false, name = "originador_id")
    private Long originadorId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ebiatividade_id", nullable = true)
    private EbiAtividade ebiAtividade;
    
    @Column(name = "ebiAutoridadeMonetaria", nullable = true)
    private Boolean ebiAutoridadeMonetaria;
    
    @Column(length = 10, nullable = false)
    private String tipoDeResidencia;
    
    @Column(name = "bloqueiodocumentacao", nullable = true)
    private Boolean bloqueioDocumentacao;
    
    @Column(name = "bloqueiocontratonaoassinado", nullable = true)
    private Boolean bloqueioContratoNaoAssinado;
    
    @Column(name = "dataBloqueioDocumentacao", nullable = true)
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private java.util.Date dataBloqueioDocumentacao;
    
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private java.util.Date dataBloqueioContratoNaoAssinado;
    
    @Column(length = 100, nullable = true)
    private String numeroDeIdentificacaoFiscalNoExterior;
    
    @ManyToOne
    @JoinColumn(name = "paisemissaodonif_id")
    private Pais paisEmissaoDoNif;
    
    @Column(name = "paisemissaodonif_id", insertable = false, updatable = false)
    private Long paisEmissaoDoNifId;
    
    @Column(length = 50, nullable = true)
    private String nomeDeclarado;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cliente", fetch = FetchType.LAZY)
    private PagamentoInstituicaoFinanceira pagamentoInstituicaoFinanceira;
    
    @Column(length = 30, nullable = true)
    private String classificacaoPessoa;
    
    @Column(name = "clienteFornecedor", nullable = true)
    private Boolean clienteFornecedor;
    
    @Column(name = "whitelist", nullable = true)
    private boolean whiteList;
    
    @Column(name = "verificadocompliance", nullable = true)
    private Boolean verificadoCompliance;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "originadorpendencia_id", nullable = true)
    private Originador originadorPendencia;
    
    @Column(name = "instituicaoFinanceiraBanco", nullable = true)
    private Boolean instituicaoFinanceiraBanco;
    
    @Column(name = "outrasInstituicoesFinanceiras", nullable = true)
    private Boolean outrasInstituicoesFinanceiras;
    
    @Column(name = "iban", length = 30, nullable = true)
    private String iban;
    
    @Column(nullable = true)
    private Boolean pendenteDeAprovacao;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operadorCadastro_id", nullable = true)
    private Usuario operadorCadastro;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operadorAprovacao_id", nullable = true)
    private Usuario operadorAprovacao;
    
    // Campos de controle
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusCliente status = StatusCliente.ATIVO;
    
    @CreationTimestamp
    @Column(name = "datacriacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    @UpdateTimestamp
    @Column(name = "dataatualizacao")
    private LocalDateTime dataAtualizacao;
    
    public enum StatusCliente {
        ATIVO, INATIVO, PENDENTE
    }
} 