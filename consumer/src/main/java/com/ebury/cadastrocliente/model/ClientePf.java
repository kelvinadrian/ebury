package com.ebury.cadastrocliente.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Entity;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ClientePf")
@Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClientePf extends Cliente {
    
    @Column(length = 14, nullable = false)
    private String cpf;
    
    @ManyToOne
    @JoinColumn(name = "sexo_id")
    private Sexo sexo;
    
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date dataDeNascimento;
    
    @Column(length = 255)
    private String profissao;
    
    @Column(length = 40)
    private String identidade;
    
    @Column(length = 40)
    private String orgaoEmissor;
    
    @Column(length = 2)
    private String ufDeEmissao;
    
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    private Date dataDeEmissao;
    
    @Column(length = 50, nullable = true)
    private String nomeDaMae;
    
    @Column(length = 50, nullable = true)
    private String nomeDoPai;
    
    @Column(length = 50, nullable = true)
    private String municipioDaNaturalidade;
    
    @Enumerated(EnumType.ORDINAL)
    private UFComOutroEnum estadoDaNaturalidade;
    
    @Column(length = 50, nullable = true)
    private String estadoDaNaturalidadeExterior;
    
    @Column(length = 14, nullable = true)
    private String telefoneResidencial;
    
    @Column(length = 14, nullable = true)
    private String telefoneComercial;
    
    @Column(length = 14, nullable = true)
    private String telefoneCelular;
    
    @Column(length = 255, nullable = true)
    private String nomedoconjuge;
    
    @ManyToOne
    @JoinColumn(name = "nacionalidade_id")
    private Nacionalidade nacionalidade;
    
    @Column(precision = 19, scale = 4, nullable = true)
    private BigDecimal rendamensal;
    
    @Column(precision = 19, scale = 4, nullable = true)
    private BigDecimal patrimonio;
    
    @ManyToOne
    @JoinColumn(name = "estadocivil_id")
    private EstadoCivil estadoCivil;
    
    @ManyToOne
    @JoinColumn(name = "tipoDeIdentificacao_id")
    private TipoDeIdentificacao tipoDeIdentificacao;
    
    @ManyToOne
    @JoinColumn(name = "profissao_id")
    private Profissao profissaoCliente;
    
    @Column(length = 100, nullable = true)
    private String numeroDeIdentificacaoFiscalNoExterior;
    
    @Column(length = 50, nullable = true)
    private String nomeDeclarado;
    
    public enum UFComOutroEnum {
        AC, AL, AP, AM, BA, CE, DF, ES, GO, MA, MT, MS, MG, PA, PB, PR, PE, PI, RJ, RN, RS, RO, RR, SC, SP, SE, TO, OUTRO
    }
} 