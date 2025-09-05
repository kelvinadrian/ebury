package com.ebury.cadastrocliente.entity.cadastro.cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SociosAcionistasDoCliente")
@Data
@EqualsAndHashCode(callSuper = false)
public class SociosAcionistasDoCliente {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    protected Cliente cliente;

    @Column(length = 100, nullable = false)
    protected String nome;

    @Column(length = 14, nullable = true)
    protected String cpf;

    @Column(length = 18, nullable = true)
    protected String cnpj;

    @Column(length = 20, nullable = true)
    protected String documento;

    @Column(precision = 10, scale = 2, nullable = true)
    protected BigDecimal percentualParticipacao;

    @Column(precision = 15, scale = 2, nullable = true)
    protected BigDecimal valorParticipacao;

    @Temporal(TemporalType.DATE)
    protected Date dataEntrada;

    @Temporal(TemporalType.DATE)
    protected Date dataSaida;

    @Column(nullable = true)
    protected Boolean ativo;

    @Column(nullable = true)
    protected Integer ordenlista;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dataCadastro;

    // Construtores
    public SociosAcionistasDoCliente() {
    }

    public SociosAcionistasDoCliente(Long id) {
        this.id = id;
    }
}
