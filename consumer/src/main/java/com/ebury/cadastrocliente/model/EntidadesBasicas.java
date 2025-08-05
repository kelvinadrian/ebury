package com.ebury.cadastrocliente.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.util.Date;

// Entidades básicas referenciadas no modelo Cliente

@Entity
@Table(name = "Sexo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class Sexo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 20, nullable = false)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    private SexoEnum tipo;
    
    @Column(nullable = false)
    private Boolean desabilitado;
}

@Entity
@Table(name = "EstadoCivil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class EstadoCivil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 20, nullable = false)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    private EstadoCivilEnum tipo;
    
    @Column(nullable = false)
    private Boolean desabilitado;
}

@Entity
@Table(name = "Nacionalidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class Nacionalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 30)
    private String descricao;
    
    @Column
    private Boolean desabilitado;
    
    @Column(name = "restrito", nullable = true)
    private Boolean restrito;
}

@Entity
@Table(name = "TipoDeIdentificacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class TipoDeIdentificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String descricao;
}

@Entity
@Table(name = "Profissao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class Profissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 255, nullable = false)
    private String descricao;
    
    @Column(name = "altorisco", nullable = false)
    private boolean altorisco;
    
    @Column(name = "desabilitado", nullable = false)
    private boolean desabilitado;
}

@Entity
@Table(name = "PorteDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class PorteDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 20, nullable = false)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    private PorteDoClienteEnum tipo;
    
    @Column(nullable = false)
    private Boolean desabilitado;
    
    @Column(length = 20, nullable = true)
    private String codigoExterno;
}

@Entity
@Table(name = "RamoDeAtividade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class RamoDeAtividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 255, nullable = false)
    private String codigo;
    
    @Column(length = 255, nullable = false)
    private String descricao;
    
    @Column(name = "altorisco", nullable = false)
    private boolean altorisco;
}

@Entity
@Table(name = "NaturezaJuridica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class NaturezaJuridica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigonivelum", nullable = false, length = 3)
    private String codigoNivelUm;
    
    @Column(name = "codigoniveldois", nullable = false, length = 3)
    private String codigoNivelDois;
    
    @Column(name = "digito", nullable = false, length = 2)
    private String digito;
    
    @Column(name = "descricao", nullable = false, length = 200)
    private String descricao;
    
    @Column(name = "codigotipocontrole", nullable = true)
    private Long codigoTipoControle;
    
    @Column(name = "datadeativacao")
    @Temporal(TemporalType.DATE)
    private Date dataDeAtivacao;
    
    @Column(name = "datadedesativacao")
    @Temporal(TemporalType.DATE)
    private Date dataDeDesativacao;
}

@Entity
@Table(name = "Originador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class Originador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50, nullable = false)
    private String descricao;
    
    @Column(nullable = false)
    private boolean desabilitado;
    
    @Column
    private boolean utilizaApiExterna;
    
    @Column
    private boolean validaCompliance = true;
    
    @Column
    private boolean validaComplianceCadastro = true;
    
    @Column
    private boolean agruparOpEnviada;
    
    @Column
    private boolean enviaDocPendente;
    
    @Column
    private boolean enviaOpEnviada;
    
    @Column
    private boolean enviaOpRecebida;
    
    @Column
    private boolean enviaAvisodeLancamento;
    
    @Column
    private boolean enviaContrato;
    
    @Column
    private boolean enviaComunicacaoresumida;
    
    @Column
    private String caminhoDoArquivo;
    
    @Column(name = "referencia")
    private String referenciaDoArquivo;
    
    @Column(length = 163, nullable = true)
    private String nomeDoRecebedorPagador;
    
    @Column(length = 80, nullable = true)
    private String enderecoDoRecebedorPagador;
    
    @Column(length = 50, nullable = true)
    private String cidadeDoRecebedorPagador;
    
    @Column(length = 120, nullable = true)
    private String banqueiroDoRecebedorPagador;
    
    @Column(length = 12, nullable = true)
    private String codigoSwiftDoBanqueiro;
    
    @Column(length = 9, nullable = true)
    private String codigoAbaDoBanqueiro;
    
    @Column(length = 16, nullable = true)
    private String codigoChipsDoBanqueiro;
    
    @Column(length = 40, nullable = true)
    private String contaNoBanqueiro;
    
    @Column(length = 60, nullable = true)
    private String banqueiroIntermediario;
    
    @Column(length = 12, nullable = true)
    private String codigoSwiftDoIntermediario;
    
    @Column(length = 9, nullable = true)
    private String codigoAbaDoIntermediario;
    
    @Column(length = 16, nullable = true)
    private String codigoChipsDoIntermediario;
    
    @Column(length = 40, nullable = true)
    private String contaNoIntermediario;
    
    @Column(length = 35, nullable = true)
    private String detalhePagamento1;
    
    @Column(length = 35, nullable = true)
    private String detalhePagamento2;
    
    @Column(length = 35, nullable = true)
    private String detalhePagamento3;
    
    @Column(length = 35, nullable = true)
    private String detalhePagamento4;
    
    @Column(length = 35, nullable = true)
    private String enderecoBanqueiro1;
    
    @Column(length = 35, nullable = true)
    private String enderecoBanqueiro2;
    
    @Column(length = 35, nullable = true)
    private String enderecoBanqueiro3;
    
    @Column(length = 35, nullable = true)
    private String enderecoBanqueiro4;
    
    @Column(length = 4)
    private String agencia;
    
    @Column(length = 10)
    private String numero;
    
    @Column(length = 100)
    private String chavePix;
}

@Entity
@Table(name = "Usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false, unique = false)
    private String nome;
    
    @Column(length = 100, nullable = false, unique = true)
    private String username;
    
    @Column(length = 64, nullable = false)
    private String senha;
    
    @Column(length = 80, nullable = false)
    private String email;
    
    @Column(length = 20)
    private String codigoExterno;
    
    @Column(nullable = false)
    private Boolean habilitadoARetroagirData;
    
    @Column(nullable = true)
    private Boolean visualizaUnidadeConsolidadora;
    
    @Column(nullable = true)
    private Boolean usuariobloqueado;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDoBloqueio;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDaUltimaAlteracaoDeSenha;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimoLogin;
    
    @Column(nullable = true)
    private Boolean gerente;
    
    @Column(nullable = true)
    private Boolean aprovaCadastro;
    
    @Column(nullable = true)
    private Integer quantidadeDeItensPorPagina;
    
    @Column(nullable = true)
    private Boolean usuariobloqueado2;
}

@Entity
@Table(name = "Pais")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 30, nullable = false)
    private String nome;
    
    @Column(length = 30)
    private String codigoExterno;
    
    @Column
    private Boolean restrito;
    
    @Column(length = 2)
    private String codigoIso;
    
    @Column(length = 255)
    private String nomeEmIngles;
    
    @Column(nullable = true)
    private Boolean dispensaNumeroDeIdentificacaoFiscal;
    
    @Column(name = "listaofac")
    private Boolean listaOFAC;
    
    @Column(name = "listaparaisosfiscais")
    private Boolean listaParaisosFiscais;
    
    @Column(name = "listaonu")
    private Boolean listaONU;
    
    @Column(name = "listafatfgafi")
    private Boolean listaFATFGAFI;
}

@Entity
@Table(name = "AtividadePrincipal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class AtividadePrincipal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 255, nullable = false)
    private String codigo;
    
    @Column(length = 255, nullable = false)
    private String descricao;
    
    @Column
    private boolean altorisco;
    
    @Column
    private boolean restrito;
}

@Entity
@Table(name = "ComplementoDaNaturezaDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ComplementoDaNaturezaDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 255, nullable = false)
    private String codigo;
    
    @Column(length = 255, nullable = false)
    private String descricao;
    
    @Column(nullable = true)
    private Boolean instituicaoFinanceira;
}

@Entity
@Table(name = "Endereco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 80)
    private String logradouro;
    
    @Column(length = 10)
    private String numero;
    
    @Column(length = 20)
    private String complemento;
    
    @Column(length = 40)
    private String bairro;
    
    @Column(length = 40)
    private String municipio;
    
    @Column(length = 2)
    private String uf;
    
    @Column(length = 9)
    private String cep;
    
    @Column
    private boolean residenciaFiscal = false;
    
    @Column(nullable = false)
    private Boolean preferencial = false;
}

@Entity
@Table(name = "EnderecoNoExteriorDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class EnderecoNoExteriorDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 80, nullable = false)
    private String endereco;
    
    @Column(length = 40, nullable = false)
    private String cidade;
    
    @Column(length = 50, nullable = true)
    private String estado;
    
    @Column(length = 20, nullable = true)
    private String cep;
    
    @Column(length = 20, nullable = true)
    private String telefone;
    
    @Column(length = 20, nullable = true)
    private String fax;
    
    @Column(length = 100, nullable = true)
    private String email;
    
    @Column
    private boolean residenciaFiscal = false;
}

@Entity
@Table(name = "ContaCorrente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ContaCorrente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Boolean contaPreferencial;
    
    @Column(length = 15, nullable = false)
    private String numero;
    
    @Column(length = 50, nullable = false)
    private String categoria;
    
    @Column
    private Boolean desativado;
}

@Entity
@Table(name = "ContaCorrenteME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ContaCorrenteME {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = true)
    private Boolean desabilitado;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDaConclusaoDoDesembolso;
    
    @Column(length = 15, nullable = false)
    private String numero;
    
    @Column(length = 15, nullable = false)
    private String complemento;
    
    @Column(nullable = false)
    private Boolean contaPreferencial;
}

@Entity
@Table(name = "ContaCorrenteTedDoc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ContaCorrenteTedDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 5, nullable = false)
    private String codigoDaAgencia;
    
    @Column(nullable = false)
    private Boolean contaPreferencial;
    
    @Column(length = 15, nullable = false)
    private String numero;
    
    @Column(name = "contaveiculolegal", nullable = true)
    private Boolean contaVeiculoLegal;
    
    @Column(name = "desativado", nullable = true)
    private Boolean desativado;
}

@Entity
@Table(name = "ContatoDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ContatoDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 80, nullable = false)
    private String email;
    
    @Column(length = 50, nullable = false)
    private String nome;
    
    @Column(length = 14, nullable = false)
    private String telefone;
    
    @Column(length = 14, nullable = true)
    private String telefone2;
}

@Entity
@Table(name = "ClienteEmailsParaReceberDocumento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ClienteEmailsParaReceberDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String enviarPara;
    
    @Column(nullable = true, length = 500)
    private String enviarCcPara;
}

@Entity
@Table(name = "ClientePorModalidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ClientePorModalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Transient
    private String tipoDeManutencao;
}

@Entity
@Table(name = "ComplianceDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ComplianceDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Boolean aderente;
    
    @Column(length = 255, nullable = true)
    private String observacao;
}

@Entity
@Table(name = "EntregaDeDocumentosDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class EntregaDeDocumentosDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private boolean entregue;
    
    @Column(length = 255, nullable = true)
    private String observacao;
    
    @Column(length = 255, nullable = true)
    private String caminhodoarquivo;
    
    @Column(name = "datadodocumento", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDoDocumento;
    
    @Column(name = "datadovencimento", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDoVencimento;
    
    @Column(length = 255, nullable = true)
    private String referenciaDoArquivo;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDoArquivamentoLogico;
    
    @Column(nullable = true)
    private Long idDoArquivo;
    
    @Column(nullable = true)
    private String idsDosArquivos;
    
    @Column
    private Boolean desabilitado = Boolean.FALSE;
}

@Entity
@Table(name = "FirmasEPoderesDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class FirmasEPoderesDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDaAnalise;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDoDocumento;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDoVencimento;
    
    @Column(length = 255, nullable = false)
    private String poderes;
    
    @Column(name = "caminhodoarquivo", length = 255, nullable = true)
    private String caminhoDoArquivo;
    
    @Column(name = "referenciadoarquivo", length = 255, nullable = true)
    private String referenciaDoArquivo;
}

@Entity
@Table(name = "HistoricoDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class HistoricoDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String xml;
}

@Entity
@Table(name = "PagamentoInstituicaoFinanceira")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class PagamentoInstituicaoFinanceira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "finalidadeif", nullable = false, length = 2)
    private String finalidadeIf;
    
    @Column(name = "agenciacreditada", nullable = false, length = 4)
    private String agenciaCreditada;
    
    @Column(name = "identificadortransf", nullable = false, length = 25)
    private String identificadorTransf;
}

@Entity
@Table(name = "ParceiroDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ParceiroDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicioVigencia;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTerminoVigencia;
    
    @Column
    private BigDecimal percentualDeComissao;
}

@Entity
@Table(name = "ProcuracoesDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ProcuracoesDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = true)
    private String nomeDoProcurador;
    
    @Column(length = 14, nullable = true)
    private String cpfdoprocurador;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeVencimento;
    
    @Column(length = 14, nullable = true)
    private String telefone;
    
    @Column(length = 255, nullable = true)
    private String caminhodoarquivo;
}

@Entity
@Table(name = "RepresentantesDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class RepresentantesDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 14, nullable = false)
    private String cpf;
    
    @Column(length = 100, nullable = true)
    private String email;
    
    @Column(length = 20, nullable = true)
    private String telefone;
    
    @Column(length = 20, nullable = true)
    private String celular;
    
    @Column(length = 100, nullable = true)
    private String cargo;
    
    @Column(length = 100, nullable = true)
    private String departamento;
    
    @Column(length = 255, nullable = true)
    private String observacoes;
    
    @Column(nullable = true)
    private Boolean ativo;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeInicio;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeFim;
}

@Entity
@Table(name = "SinonimoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class SinonimoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 255, nullable = true)
    private String observacao;
    
    @Column(nullable = true)
    private Boolean ativo;
}

@Entity
@Table(name = "SociosAcionistasDoCliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class SociosAcionistasDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 14, nullable = false)
    private String cpfCnpj;
    
    @Column(length = 100, nullable = true)
    private String email;
    
    @Column(length = 20, nullable = true)
    private String telefone;
    
    @Column(length = 20, nullable = true)
    private String celular;
    
    @Column(length = 100, nullable = true)
    private String cargo;
    
    @Column(length = 100, nullable = true)
    private String departamento;
    
    @Column(length = 255, nullable = true)
    private String observacoes;
    
    @Column(nullable = true)
    private Boolean ativo;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeInicio;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeFim;
    
    @Column(nullable = true)
    private BigDecimal percentualParticipacao;
}

@Entity
@Table(name = "VeiculoLegal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class VeiculoLegal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 255, nullable = true)
    private String descricao;
    
    @Column(nullable = true)
    private Boolean ativo;
}

@Entity
@Table(name = "ModoDeContratacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class ModoDeContratacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 255, nullable = true)
    private String descricao;
    
    @Column(nullable = true)
    private Boolean ativo;
}

@Entity
@Table(name = "UnidadeDeNegocios")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class UnidadeDeNegocios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 255, nullable = true)
    private String descricao;
    
    @Column(nullable = true)
    private Boolean ativo;
}

@Entity
@Table(name = "CapacidadeFinanceira")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class CapacidadeFinanceira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 255, nullable = true)
    private String descricao;
    
    @Column(nullable = true)
    private Boolean ativo;
}

@Entity
@Table(name = "EbiAtividade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class EbiAtividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 255, nullable = true)
    private String descricao;
    
    @Column(nullable = true)
    private Boolean ativo;
}

@Entity
@Table(name = "TipoDeCapital")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class TipoDeCapital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 255, nullable = true)
    private String descricao;
    
    @Column(nullable = true)
    private Boolean ativo;
}

@Entity
@Table(name = "TipoDeSetor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class TipoDeSetor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 255, nullable = true)
    private String descricao;
    
    @Column(nullable = true)
    private Boolean ativo;
}

@Entity
@Table(name = "FormaDeConstituicao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class FormaDeConstituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 255, nullable = true)
    private String descricao;
    
    @Column(nullable = true)
    private Boolean ativo;
}

@Entity
@Table(name = "OrigemDeCapital")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
class OrigemDeCapital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 255, nullable = true)
    private String descricao;
    
    @Column(nullable = true)
    private Boolean ativo;
}

// Enums necessários
enum SexoEnum {
    MASCULINO, FEMININO
}

enum EstadoCivilEnum {
    SOLTEIRO, CASADO, DIVORCIADO, VIUVO, UNIAO_ESTAVEL
}

enum PorteDoClienteEnum {
    PEQUENA, MEDIA, GRANDE
} 