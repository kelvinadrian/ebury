package com.ebury.cadastrocliente.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ClientePj")
@Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClientePj extends Cliente {
    
    @Column(length = 18, nullable = false)
    private String cnpj;
    
    @ManyToOne
    @JoinColumn(name = "ultimocontato_id")
    private ContatoDoCliente ultimoContato;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientePj", fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<ContatoDoCliente> contatoDoClienteVOs;
    
    @Column(length = 20)
    private String inscricaoEstadual;
    
    @Column(length = 2)
    private String estadoDeEmissao;
    
    @ManyToOne
    @JoinColumn(name = "portedocliente_id")
    private PorteDoCliente porteDoCliente;
    
    @Column(length = 50, nullable = true)
    private String razaoSocial;
    
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date dataDaFundacao;
    
    @ManyToOne
    @JoinColumn(name = "origemdecapital_id")
    private OrigemDeCapital origemDeCapital;
    
    @ManyToOne
    @JoinColumn(name = "tipodecapital_id")
    private TipoDeCapital tipoDeCapital;
    
    @ManyToOne
    @JoinColumn(name = "tipodesetor_id")
    private TipoDeSetor tipoDeSetor;
    
    @ManyToOne
    @JoinColumn(name = "ramodeatividade_id")
    private RamoDeAtividade ramoDeAtividade;
    
    @ManyToOne
    @JoinColumn(name = "atividadeprincipal_id")
    private AtividadePrincipal atividadePrincipal;
    
    @Column(name = "faturamentomediomensal", precision = 19, scale = 4, nullable = true)
    private BigDecimal faturamentomediomensal;
    
    @ManyToOne
    @JoinColumn(name = "formadeconstituicao_id")
    private FormaDeConstituicao formaDeConstituicao;
    
    @Column(length = 25, nullable = true)
    private String numeroDeIdentificacaoFiscalNoExterior;
    
    @Column(length = 100, nullable = true)
    private String nomeDeclarado;
} 