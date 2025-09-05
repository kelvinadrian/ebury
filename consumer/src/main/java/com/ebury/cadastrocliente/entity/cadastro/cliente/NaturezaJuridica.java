package com.ebury.cadastrocliente.entity.cadastro.cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "NaturezaJuridica")
@Data
@EqualsAndHashCode(callSuper = false)
public class NaturezaJuridica {

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

    @Column(length = 200, nullable = false)
    protected String descricao;

    @Column(length = 10, nullable = false)
    protected String codigo;

    @Column(nullable = true)
    protected Boolean ativo;

    // Construtores
    public NaturezaJuridica() {
    }

    public NaturezaJuridica(Long id) {
        this.id = id;
    }
}
