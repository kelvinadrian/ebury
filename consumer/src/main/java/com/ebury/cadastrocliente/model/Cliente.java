package com.ebury.cadastrocliente.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Dados básicos do cliente
    @Column(name = "clitipodemanutencao")
    private String cliTipoDeManutencao;
    
    @Column(name = "tipodepessoa")
    private String tipoDePessoa;
    
    @Column(name = "cpfcnpj", nullable = false, unique = true)
    private String cpfCnpj;
    
    @Column(name = "codexterno")
    private String codExterno;
    
    @Column(name = "codcorporativo")
    private String codCorporativo;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "datadocadastro")
    private String dataDoCadastro;
    
    @Column(name = "datadedesativacao")
    private String dataDeDesativacao;
    
    @Column(name = "desabilitado")
    private String desabilitado;
    
    @Column(name = "utilizaassinaturadigital")
    private String utilizaAssinaturaDigital;
    
    @Column(name = "negociacao")
    private String negociacao;
    
    @Column(name = "complementodanatureza")
    private Integer complementoDaNatureza;
    
    @Column(name = "naturezajuridican1")
    private Integer naturezaJuridicaN1;
    
    @Column(name = "naturezajuridican2")
    private Integer naturezaJuridicaN2;
    
    @Column(name = "originador")
    private String originador;
    
    @Column(name = "tipoderesidencia")
    private String tipoDeResidencia;
    
    @Column(name = "gerenteanalista")
    private String gerenteAnalista;
    
    @Column(name = "gerenteanalistaoriginador")
    private String gerenteAnalistaOriginador;
    
    @Column(name = "pep")
    private String pep;
    
    @Column(name = "iban")
    private String iban;
    
    // Dados do cliente pessoa física
    @Column(name = "sexo")
    private String sexo;
    
    @Column(name = "estadocivil")
    private String estadoCivil;
    
    @Column(name = "datadenascimento")
    private String dataDeNascimento;
    
    @Column(name = "descrdocumidentifcacao")
    private String descrDocumIdentifcacao;
    
    @Column(name = "documidentificacao")
    private String documIdentificacao;
    
    @Column(name = "emissordocumidentificacao")
    private String emissorDocumIdentificacao;
    
    @Column(name = "ufemissordocumidentificacao")
    private String ufEmissorDocumIdentificacao;
    
    @Column(name = "datadocumidentificacao")
    private String dataDocumIdentificacao;
    
    @Column(name = "nomedamae")
    private String nomeDaMae;
    
    @Column(name = "nomedopai")
    private String nomeDoPai;
    
    @Column(name = "nacionalidade")
    private String nacionalidade;
    
    @Column(name = "municipiodanaturalidade")
    private String municipioDaNaturalidade;
    
    @Column(name = "ufdanaturalidade")
    private String ufDaNaturalidade;
    
    @Column(name = "nomedoconjuge")
    private String nomeDoConjuge;
    
    @Column(name = "telefoneresidencial")
    private Integer telefoneResidencial;
    
    @Column(name = "telefonecomercial")
    private Integer telefoneComercial;
    
    @Column(name = "telefonecelular")
    private Integer telefoneCelular;
    
    @Column(name = "rendamensal")
    private Integer rendaMensal;
    
    @Column(name = "patrimonio")
    private Integer patrimonio;
    
    // Dados do cliente pessoa jurídica
    @Column(name = "inscricaoestadual")
    private Integer inscricaoEstadual;
    
    @Column(name = "ufemissorinscricaoestadual")
    private String ufEmissorInscricaoEstadual;
    
    @Column(name = "porte")
    private String porte;
    
    @Column(name = "ramodeatividade")
    private String ramoDeAtividade;
    
    @Column(name = "faturamentomediomensal")
    private Integer faturamentoMedioMensal;
    
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