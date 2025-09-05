package com.ebury.cadastrocliente.entity.cadastro.cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ContatoDoCliente")
@Data
@EqualsAndHashCode(callSuper = false)
public class ContatoDoCliente {

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

    @Column(length = 100, nullable = true)
    protected String email;

    @Column(length = 20, nullable = true)
    protected String telefone;

    @Column(length = 20, nullable = true)
    protected String celular;

    @Column(length = 50, nullable = true)
    protected String cargo;

    @Column(length = 100, nullable = true)
    protected String departamento;

    @Column(nullable = true)
    protected Boolean ativo;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dataCadastro;

    // Construtores
    public ContatoDoCliente() {
    }

    public ContatoDoCliente(Long id) {
        this.id = id;
    }
}
