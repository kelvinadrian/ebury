package com.ebury.cadastrocliente.entity.cadastro.cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ContaCorrente")
@Data
@EqualsAndHashCode(callSuper = false)
public class ContaCorrente {

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

    @Column(length = 10, nullable = false)
    protected String agencia;

    @Column(length = 20, nullable = false)
    protected String numero;

    @Column(length = 10, nullable = false)
    protected String digito;

    @Column(nullable = true)
    protected Boolean preferencial;

    @Column(nullable = true)
    protected Boolean desativada;

    @Column(length = 20, nullable = true)
    protected String tipoDeManutencao;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dataCadastro;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dataDesativacao;

    // Construtores
    public ContaCorrente() {
    }

    public ContaCorrente(Long id) {
        this.id = id;
    }
}
