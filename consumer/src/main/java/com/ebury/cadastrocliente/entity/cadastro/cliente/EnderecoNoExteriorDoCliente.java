package com.ebury.cadastrocliente.entity.cadastro.cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EnderecoNoExteriorDoCliente")
@Data
@EqualsAndHashCode(callSuper = false)
public class EnderecoNoExteriorDoCliente {

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

    @Column(length = 100)
    protected String estado;

    @Column(length = 20)
    protected String codigoPostal;

    @Column(length = 100)
    protected String pais;

    @Column(length = 3)
    protected String codigoPais;

    @Column(nullable = true)
    protected Boolean ativo;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dataCadastro;

    // Construtores
    public EnderecoNoExteriorDoCliente() {
    }

    public EnderecoNoExteriorDoCliente(Long id) {
        this.id = id;
    }
}
