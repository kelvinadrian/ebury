package com.ebury.cadastrocliente.dto;

import com.ebury.cadastrocliente.validation.annotations.*;
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
        @ValidacaoTipoDeManutencao(ordem = 1)
        private String cliTipoDeManutencao;
        
        @ValidacaoTipoDePessoa(ordem = 2)
        private String tipoDePessoa;
        
        @ValidacaoCpfCnpj(ordem = 3)
        private String cpfCnpj;
        
        @ValidacaoCodigoCorporativo(ordem = 4)
        private String codCorporativo;
        
        @ValidacaoNome(ordem = 5)
        private String nome;
        
        @ValidacaoData(ordem = 6)
        private String dataDoCadastro;
        
        @ValidacaoDataDesativacao(ordem = 7)
        private String dataDeDesativacao;
        
        @ValidacaoDesabilitado(ordem = 8)
        private String desabilitado;
        
        @ValidacaoAssinaturaDigital(ordem = 9)
        private String utilizaAssinaturaDigital;
        
        @ValidacaoNegociacao(ordem = 10)
        private String negociacao;
        
        @ValidacaoComplementoNatureza(ordem = 11)
        private Integer complementoDaNatureza;
        
        @ValidacaoNaturezaJuridica(ordem = 12, nivel = 1)
        private Integer naturezaJuridicaN1;
        
        @ValidacaoNaturezaJuridica(ordem = 13, nivel = 2)
        private Integer naturezaJuridicaN2;
        
        @ValidacaoOriginador(ordem = 14)
        private String originador;
        
        @ValidacaoTipoResidencia(ordem = 15)
        private String tipoDeResidencia;
        
        @ValidacaoGerenteAnalista(ordem = 16)
        private String gerenteAnalista;
        
        @ValidacaoGerenteAnalista(ordem = 17)
        private String gerenteAnalistaOriginador;
        
        @ValidacaoPep(ordem = 18)
        private String pep;
        
        @ValidacaoEndereco(ordem = 19)
        private ListaEnderecosDTO listaEnderecos;
        
        @ValidacaoEnderecoNoExterior(ordem = 20)
        private ListaEnderecosNoExteriorDTO listaEnderecosNoExterior;
        
        @ValidacaoContaCorrente(ordem = 21)
        private ListaContasCorrentesDTO listaContasCorrentes;
        
        @ValidacaoContaCorrenteTedDoc(ordem = 22)
        private ListaContasCorrentesTedDocDTO listaContasCorrentesTedDoc;
        
        @ValidacaoEmailsDocumentos(ordem = 23)
        private ListaEmailsDocumentosDTO listaEmailsDocumentos;
        
        @ValidacaoOperacoesPermitidas(ordem = 24)
        private ListaOperacoesPermitidasDTO listaOperacoesPermitidas;
        
        @ValidacaoCorretorasQueRepresentam(ordem = 25)
        private ListaCorretorasQueRepresentamDTO listaCorretorasQueRepresentam;
        
        private ClientePfDTO clientePf;
        private ClientePjDTO clientePj;
        
        @ValidacaoListaDocumentos(ordem = 26)
        private List<ListaDeDocumentosDTO> listaDeDocumentos;
        
        @ValidacaoRepresentanteLegal(ordem = 27)
        private ListaRepresentanteLegalDTO listaRepresentanteLegal;
        
        @ValidacaoIban(ordem = 28)
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
        @ValidacaoTipoOperacao(ordem = 58)
        private String endTipo;
        
        @ValidacaoTipoOperacao(ordem = 59)
        private String endTipoDeLogradouro;
        
        @ValidacaoLogradouro(ordem = 60)
        private String endLogradouro;
        
        @ValidacaoNumero(ordem = 61)
        private String endNumero;
        
        @ValidacaoComplemento(ordem = 62)
        private String endComplemento;
        
        @ValidacaoCep(ordem = 63)
        private String endCep;
        
        @ValidacaoBairro(ordem = 64)
        private String endBairro;
        
        @ValidacaoMunicipio(ordem = 65)
        private String endMunicipio;
        
        @ValidacaoUfEmissor(ordem = 66)
        private String endUf;
        
        @ValidacaoTipoOperacao(ordem = 67)
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
        @ValidacaoEndereco(ordem = 68)
        private String endereco;
        
        @ValidacaoMunicipio(ordem = 69)
        private String endCidade;
        
        @ValidacaoUfEmissor(ordem = 70)
        private String endEstado;
        
        @ValidacaoCep(ordem = 71)
        private String endCep;
        
        @ValidacaoPais(ordem = 72)
        private String endPais;
        
        @ValidacaoTelefone(ordem = 73)
        private String endTelefone;
        
        @ValidacaoTelefone(ordem = 74)
        private String endFax;
        
        @ValidacaoEmail(ordem = 75)
        private String endEmail;
        
        @ValidacaoTipoOperacao(ordem = 76)
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
        @ValidacaoTipoDeManutencao(ordem = 77)
        private String ccTipoDeManutencao;
        
        @ValidacaoAgencia(ordem = 78)
        private String ccAgencia;
        
        @ValidacaoConta(ordem = 79)
        private String ccNumero;
        
        @ValidacaoTipoOperacao(ordem = 80)
        private String ccPreferencial;
        
        @ValidacaoTipoOperacao(ordem = 81)
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
        @ValidacaoContaCorrente(ordem = 82)
        private ContaCorrenteDTO contaCorrente;
        
        @ValidacaoTipoOperacao(ordem = 83)
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
        @ValidacaoTipoDocumento(ordem = 84)
        private String documento;
        
        @ValidacaoEmail(ordem = 85)
        private String enviarPara;
        
        @ValidacaoEmail(ordem = 86)
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
        @ValidacaoTipoDeManutencao(ordem = 87)
        private String operacaoTipoDeManutencao;
        
        @ValidacaoTipoOperacao(ordem = 88)
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
        @ValidacaoCnpjCorretora(ordem = 89)
        private String cnpjCorretora;
        
        @ValidacaoVigencia(ordem = 90)
        private String inicioVigencia;
        
        @ValidacaoVigencia(ordem = 91)
        private String terminoVigencia;
        
        @ValidacaoPercentual(ordem = 92)
        private Integer percentCorretagem;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientePfDTO {
        @ValidacaoSexo(ordem = 29)
        private String sexo;
        
        @ValidacaoEstadoCivil(ordem = 30)
        private String estadoCivil;
        
        @ValidacaoData(ordem = 31)
        private String dataDeNascimento;
        
        private String descrDocumIdentifcacao;
        
        @ValidacaoDocumentoIdentificacao(ordem = 32)
        private String documIdentificacao;
        
        @ValidacaoEmissorDocumento(ordem = 33)
        private String emissorDocumIdentificacao;
        
        @ValidacaoUfEmissor(ordem = 34)
        private String ufEmissorDocumIdentificacao;
        
        @ValidacaoData(ordem = 35)
        private String dataDocumIdentificacao;
        
        @ValidacaoNomeMae(ordem = 36)
        private String nomeDaMae;
        
        @ValidacaoNomePai(ordem = 37)
        private String nomeDoPai;
        
        @ValidacaoNacionalidade(ordem = 38)
        private String nacionalidade;
        
        @ValidacaoMunicipioNaturalidade(ordem = 39)
        private String municipioDaNaturalidade;
        
        @ValidacaoUfNaturalidade(ordem = 40)
        private String ufDaNaturalidade;
        
        @ValidacaoNomeConjuge(ordem = 41)
        private String nomeDoConjuge;
        
        @ValidacaoTelefone(ordem = 42)
        private Integer telefoneResidencial;
        
        @ValidacaoTelefone(ordem = 43)
        private Integer telefoneComercial;
        
        @ValidacaoTelefone(ordem = 44)
        private Integer telefoneCelular;
        
        @ValidacaoRenda(ordem = 45)
        private Integer rendaMensal;
        
        @ValidacaoRenda(ordem = 46)
        private Integer patrimonio;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientePjDTO {
        @ValidacaoInscricaoEstadual(ordem = 47)
        private Integer inscricaoEstadual;
        
        @ValidacaoUfEmissor(ordem = 48)
        private String ufEmissorInscricaoEstadual;
        
        @ValidacaoPorte(ordem = 49)
        private String porte;
        
        @ValidacaoRamoAtividade(ordem = 50)
        private String ramoDeAtividade;
        
        @ValidacaoFaturamentoMedio(ordem = 51)
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
        @ValidacaoTipoDeManutencao(ordem = 93)
        private String contatoTipoDeManutencao;
        
        @ValidacaoNome(ordem = 94)
        private String contatoNome;
        
        @ValidacaoTelefone(ordem = 95)
        private Integer contatoTelefone1;
        
        @ValidacaoTelefone(ordem = 96)
        private Integer contatoTelefone2;
        
        @ValidacaoEmail(ordem = 97)
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
        @ValidacaoNivel(ordem = 98)
        private String nivel;
        
        @ValidacaoOrdenLista(ordem = 99)
        private Integer ordenLista;
        
        @ValidacaoTipoDePessoa(ordem = 100)
        private String tipoDePessoa;
        
        @ValidacaoVigencia(ordem = 101)
        private String vigenciaFinal;
        
        @ValidacaoRazaoSocial(ordem = 102)
        private String nomeRazaoSocial;
        
        @ValidacaoCpfCnpj(ordem = 103)
        private String cpfCnpj;
        
        @ValidacaoPais(ordem = 104)
        private String paisDeOrigem;
        
        @ValidacaoQuantidade(ordem = 105)
        private Integer qtdeAcoesCotas;
        
        @ValidacaoParticipacao(ordem = 106)
        private Integer percentualDeParticipacao;
        
        @ValidacaoNacionalidade(ordem = 107)
        private String nacionalidade;
        
        @ValidacaoTipoIdentificacao(ordem = 108)
        private String tipoDeIdentificacao;
        
        @ValidacaoNumeroIdentificacao(ordem = 109)
        private String numeroIdentificacao;
        
        @ValidacaoData(ordem = 110)
        private String dataEmissaoDocumento;
        
        @ValidacaoOrgaoEmissor(ordem = 111)
        private String orgaoEmissor;
        
        @ValidacaoUfEmissor(ordem = 112)
        private String estado;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaDeDocumentosDTO {
        @ValidacaoTipoDocumento(ordem = 52)
        private String tipoDocumentoDoCliente;
        
        @ValidacaoData(ordem = 53)
        private String dataDoDocumento;
        
        @ValidacaoVencimento(ordem = 54)
        private String dataDoVencimento;
        
        @ValidacaoObservacoes(ordem = 55)
        private String observacoes;
        
        @ValidacaoArquivos(ordem = 56)
        private String idsDosArquivos;
        
        @ValidacaoTipoOperacao(ordem = 57)
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
        @ValidacaoTipoOperacao(ordem = 113)
        private String repTipo;
        
        @ValidacaoNome(ordem = 114)
        private String nome;
        
        @ValidacaoCpfCnpj(ordem = 115)
        private String cpf;
        
        @ValidacaoTipoDocumento(ordem = 116)
        private String descrDocumIdentifcacao;
        
        @ValidacaoDocumentoIdentificacao(ordem = 117)
        private String documIdentificacao;
        
        @ValidacaoData(ordem = 118)
        private String dataDocumIdentificacao;
        
        @ValidacaoEmissorDocumento(ordem = 119)
        private String emissorDocumIdentificacao;
        
        @ValidacaoUfEmissor(ordem = 120)
        private String ufEmissorDocumIdentificacao;
        
        @ValidacaoNacionalidade(ordem = 121)
        private String nacionalidade;
        
        @ValidacaoTipoOperacao(ordem = 122)
        private String endTipo;
        
        @ValidacaoTipoOperacao(ordem = 123)
        private String endTipoDeLogradouro;
        
        @ValidacaoLogradouro(ordem = 124)
        private String endLogradouro;
        
        @ValidacaoNumero(ordem = 125)
        private String endNumero;
        
        @ValidacaoComplemento(ordem = 126)
        private String endComplemento;
        
        @ValidacaoCep(ordem = 127)
        private String endCep;
        
        @ValidacaoBairro(ordem = 128)
        private String endBairro;
        
        @ValidacaoMunicipio(ordem = 129)
        private String endMunicipio;
        
        @ValidacaoUfEmissor(ordem = 130)
        private String endUf;
    }
} 