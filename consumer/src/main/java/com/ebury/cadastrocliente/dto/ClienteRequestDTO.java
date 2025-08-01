package com.ebury.cadastrocliente.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {
    
    private ClienteDTO cliente;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClienteDTO {
        private String cliTipoDeManutencao;
        private String tipoDePessoa;
        private String cpfCnpj;
        private String codExterno;
        private String codCorporativo;
        private String nome;
        private String dataDoCadastro;
        private String dataDeDesativacao;
        private String desabilitado;
        private String utilizaAssinaturaDigital;
        private String negociacao;
        private Integer complementoDaNatureza;
        private Integer naturezaJuridicaN1;
        private Integer naturezaJuridicaN2;
        private String originador;
        private String tipoDeResidencia;
        private String gerenteAnalista;
        private String gerenteAnalistaOriginador;
        private String pep;
        private ListaEnderecosDTO listaEnderecos;
        private ListaEnderecosNoExteriorDTO listaEnderecosNoExterior;
        private ListaContasCorrentesDTO listaContasCorrentes;
        private ListaContasCorrentesTedDocDTO listaContasCorrentesTedDoc;
        private ListaEmailsDocumentosDTO listaEmailsDocumentos;
        private ListaOperacoesPermitidasDTO listaOperacoesPermitidas;
        private ListaCorretorasQueRepresentamDTO listaCorretorasQueRepresentam;
        private ClientePfDTO clientePf;
        private ClientePjDTO clientePj;
        private List<ListaDeDocumentosDTO> listaDeDocumentos;
        private ListaRepresentanteLegalDTO listaRepresentanteLegal;
        private String iban;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaEnderecosDTO {
        private List<EnderecoDTO> endereco;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnderecoDTO {
        private String endTipo;
        private String endTipoDeLogradouro;
        private String endLogradouro;
        private String endNumero;
        private String endComplemento;
        private String endCep;
        private String endBairro;
        private String endMunicipio;
        private String endUf;
        private Boolean endPreferencial;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaEnderecosNoExteriorDTO {
        private List<EnderecoNoExteriorDTO> enderecoNoExterior;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnderecoNoExteriorDTO {
        private String endereco;
        private String endCidade;
        private String endEstado;
        private String endCep;
        private String endPais;
        private String endTelefone;
        private String endFax;
        private String endEmail;
        private Boolean endResidenciaFiscal;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaContasCorrentesDTO {
        private List<ContaCorrenteDTO> contaCorrente;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContaCorrenteDTO {
        private String ccTipoDeManutencao;
        private String ccAgencia;
        private String ccNumero;
        private String ccPreferencial;
        private String ccDesativada;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaContasCorrentesTedDocDTO {
        private List<ContaCorrenteTedDocDTO> contaCorrenteTedDoc;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContaCorrenteTedDocDTO {
        private ContaCorrenteDTO contaCorrente;
        private String ccTedCodBacenBanco;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaEmailsDocumentosDTO {
        private List<EmailsDocumentoDTO> emailsDocumento;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailsDocumentoDTO {
        private String documento;
        private String enviarPara;
        private String enviarCcPara;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaOperacoesPermitidasDTO {
        private List<OperacaoPermitidaDTO> operacaoPermitida;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperacaoPermitidaDTO {
        private String operacaoTipoDeManutencao;
        private String tipoOperacaoPermitida;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaCorretorasQueRepresentamDTO {
        private List<CorretoraQueRepresentaDTO> corretoraQueRepresenta;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CorretoraQueRepresentaDTO {
        private String cnpjCorretora;
        private String inicioVigencia;
        private String terminoVigencia;
        private Integer percentCorretagem;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientePfDTO {
        private String sexo;
        private String estadoCivil;
        private String dataDeNascimento;
        private String descrDocumIdentifcacao;
        private String documIdentificacao;
        private String emissorDocumIdentificacao;
        private String ufEmissorDocumIdentificacao;
        private String dataDocumIdentificacao;
        private String nomeDaMae;
        private String nomeDoPai;
        private String nacionalidade;
        private String municipioDaNaturalidade;
        private String ufDaNaturalidade;
        private String nomeDoConjuge;
        private Integer telefoneResidencial;
        private Integer telefoneComercial;
        private Integer telefoneCelular;
        private Integer rendaMensal;
        private Integer patrimonio;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientePjDTO {
        private Integer inscricaoEstadual;
        private String ufEmissorInscricaoEstadual;
        private String porte;
        private String ramoDeAtividade;
        private Integer faturamentoMedioMensal;
        private ListaContatosDTO listaContatos;
        private ListaSocioAcionistaDTO listaSocioAcionista;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaContatosDTO {
        private List<ContatoDTO> contato;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContatoDTO {
        private String contatoTipoDeManutencao;
        private String contatoNome;
        private Integer contatoTelefone1;
        private Integer contatoTelefone2;
        private String contatoEmail;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaSocioAcionistaDTO {
        private List<SocioAcionistaDTO> socioAcionista;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocioAcionistaDTO {
        private String nivel;
        private Integer ordenLista;
        private String tipoDePessoa;
        private String vigenciaFinal;
        private String nomeRazaoSocial;
        private String cpfCnpj;
        private String paisDeOrigem;
        private Integer qtdeAcoesCotas;
        private Integer percentualDeParticipacao;
        private String nacionalidade;
        private String tipoDeIdentificacao;
        private String numeroIdentificacao;
        private String dataEmissaoDocumento;
        private String orgaoEmissor;
        private String estado;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaDeDocumentosDTO {
        private String tipoDocumentoDoCliente;
        private String dataDoDocumento;
        private String dataDoVencimento;
        private String observacoes;
        private String idsDosArquivos;
        private String tipoDaOperacao;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaRepresentanteLegalDTO {
        private List<RepresentanteLegalDTO> representanteLegal;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RepresentanteLegalDTO {
        private String repTipo;
        private String nome;
        private String cpf;
        private String descrDocumIdentifcacao;
        private String documIdentificacao;
        private String dataDocumIdentificacao;
        private String emissorDocumIdentificacao;
        private String ufEmissorDocumIdentificacao;
        private String nacionalidade;
        private String endTipo;
        private String endTipoDeLogradouro;
        private String endLogradouro;
        private String endNumero;
        private String endComplemento;
        private String endCep;
        private String endBairro;
        private String endMunicipio;
        private String endUf;
    }
} 