package com.ebury.cadastrocliente.entity.cadastro.cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Endereco")
@Data
@EqualsAndHashCode(callSuper = false)
public class Endereco {

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

    @Column(length = 200)
    protected String logradouro;

    @Column(length = 10)
    protected String numero;

    @Column(length = 100)
    protected String complemento;

    @Column(length = 100)
    protected String bairro;

    @Column(length = 100)
    protected String cidade;

    @Column(length = 2)
    protected String uf;

    @Column(length = 10)
    protected String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoDeEndereco_id")
    protected TipoDeEndereco tipoDeEndereco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoDeLogradouro_id")
    protected TipoDeLogradouro tipoDeLogradouro;

    @Column(nullable = true)
    protected Boolean ativo;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dataCadastro;

    // Construtores
    public Endereco() {
    }

    public Endereco(Long id) {
        this.id = id;
    }
}
