package com.ebury.cadastrocliente.dto;

import com.ebury.cadastrocliente.validation.annotations.cliente.*;
import com.ebury.cadastrocliente.validation.annotations.endereco.*;
import com.ebury.cadastrocliente.validation.annotations.enderecoexterior.*;
import com.ebury.cadastrocliente.validation.annotations.contacorrente.*;
import com.ebury.cadastrocliente.validation.annotations.contacorrenteteddoc.*;
import com.ebury.cadastrocliente.validation.annotations.emailsdocumentos.*;
import com.ebury.cadastrocliente.validation.annotations.operacoespermitidas.*;
import com.ebury.cadastrocliente.validation.annotations.corretorasquerepresentam.*;
import com.ebury.cadastrocliente.validation.annotations.clientepf.*;
import com.ebury.cadastrocliente.validation.annotations.clientepj.*;
import com.ebury.cadastrocliente.validation.annotations.contato.*;
import com.ebury.cadastrocliente.validation.annotations.socioacionista.*;
import com.ebury.cadastrocliente.validation.annotations.documentos.*;
import com.ebury.cadastrocliente.validation.annotations.representantelegal.*;
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
        @ValidacaoClienteTipoDeManutencao(ordem = 1)
        private String cliTipoDeManutencao;
        
        @ValidacaoClienteTipoDePessoa(ordem = 2)
        private String tipoDePessoa;
        
        @ValidacaoClienteCpfCnpj(ordem = 3)
        private String cpfCnpj;
        
        @ValidacaoClienteCodigoCorporativo(ordem = 4)
        private String codCorporativo;
        
        @ValidacaoClienteNome(ordem = 5)
        private String nome;
        
        @ValidacaoClienteDataDoCadastro(ordem = 6)
        private String dataDoCadastro;
        
        @ValidacaoClienteDataDeDesativacao(ordem = 7)
        private String dataDeDesativacao;
        
        @ValidacaoClienteDesabilitado(ordem = 8)
        private String desabilitado;
        
        @ValidacaoClienteAssinaturaDigital(ordem = 9)
        private String utilizaAssinaturaDigital;
        
        @ValidacaoClienteNegociacao(ordem = 10)
        private String negociacao;
        
        @ValidacaoClienteComplementoNatureza(ordem = 11)
        private Integer complementoDaNatureza;
        
        @ValidacaoClienteNaturezaJuridicaN1(ordem = 12)
        private Integer naturezaJuridicaN1;
        
        @ValidacaoClienteNaturezaJuridicaN2(ordem = 13)
        private Integer naturezaJuridicaN2;
        
        @ValidacaoClienteOriginador(ordem = 14)
        private String originador;
        
        @ValidacaoClienteTipoResidencia(ordem = 15)
        private String tipoDeResidencia;
        
        @ValidacaoClienteGerenteAnalista(ordem = 16)
        private String gerenteAnalista;
        
        @ValidacaoClienteGerenteAnalistaOriginador(ordem = 17)
        private String gerenteAnalistaOriginador;
        
        @ValidacaoClientePep(ordem = 18)
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
        
        @ValidacaoClienteIban(ordem = 28)
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
        @ValidacaoEnderecoTipo(ordem = 58)
        private String endTipo;
        
        @ValidacaoEnderecoTipoDeLogradouro(ordem = 59)
        private String endTipoDeLogradouro;
        
        @ValidacaoEnderecoLogradouro(ordem = 60)
        private String endLogradouro;
        
        @ValidacaoEnderecoNumero(ordem = 61)
        private String endNumero;
        
        @ValidacaoEnderecoComplemento(ordem = 62)
        private String endComplemento;
        
        @ValidacaoEnderecoCep(ordem = 63)
        private String endCep;
        
        @ValidacaoEnderecoBairro(ordem = 64)
        private String endBairro;
        
        @ValidacaoEnderecoMunicipio(ordem = 65)
        private String endMunicipio;
        
        @ValidacaoEnderecoUf(ordem = 66)
        private String endUf;
        
        @ValidacaoEnderecoPreferencial(ordem = 67)
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
        @ValidacaoEnderecoExteriorEndereco(ordem = 68)
        private String endereco;
        
        @ValidacaoEnderecoExteriorCidade(ordem = 69)
        private String endCidade;
        
        @ValidacaoEnderecoExteriorEstado(ordem = 70)
        private String endEstado;
        
        @ValidacaoEnderecoExteriorCep(ordem = 71)
        private String endCep;
        
        @ValidacaoEnderecoExteriorPais(ordem = 72)
        private String endPais;
        
        @ValidacaoEnderecoExteriorTelefone(ordem = 73)
        private String endTelefone;
        
        @ValidacaoEnderecoExteriorFax(ordem = 74)
        private String endFax;
        
        @ValidacaoEnderecoExteriorEmail(ordem = 75)
        private String endEmail;
        
        @ValidacaoEnderecoExteriorResidenciaFiscal(ordem = 76)
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
        @ValidacaoContaCorrenteTipoDeManutencao(ordem = 77)
        private String ccTipoDeManutencao;
        
        @ValidacaoContaCorrenteAgencia(ordem = 78)
        private String ccAgencia;
        
        @ValidacaoContaCorrenteNumero(ordem = 79)
        private String ccNumero;
        
        @ValidacaoContaCorrentePreferencial(ordem = 80)
        private String ccPreferencial;
        
        @ValidacaoContaCorrenteDesativada(ordem = 81)
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
        
        @ValidacaoContaCorrenteTedDocCodBacenBanco(ordem = 83)
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
        @ValidacaoEmailsDocumentosDocumento(ordem = 84)
        private String documento;
        
        @ValidacaoEmailsDocumentosEnviarPara(ordem = 85)
        private String enviarPara;
        
        @ValidacaoEmailsDocumentosEnviarCcPara(ordem = 86)
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
        @ValidacaoOperacoesPermitidasTipoDeManutencao(ordem = 87)
        private String operacaoTipoDeManutencao;
        
        @ValidacaoOperacoesPermitidasTipoOperacaoPermitida(ordem = 88)
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
        @ValidacaoCorretorasQueRepresentamCnpjCorretora(ordem = 89)
        private String cnpjCorretora;
        
        @ValidacaoCorretorasQueRepresentamInicioVigencia(ordem = 90)
        private String inicioVigencia;
        
        @ValidacaoCorretorasQueRepresentamTerminoVigencia(ordem = 91)
        private String terminoVigencia;
        
        @ValidacaoCorretorasQueRepresentamPercentCorretagem(ordem = 92)
        private Integer percentCorretagem;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientePfDTO {
        @ValidacaoClientePfSexo(ordem = 29)
        private String sexo;
        
        @ValidacaoClientePfEstadoCivil(ordem = 30)
        private String estadoCivil;
        
        @ValidacaoClientePfDataDeNascimento(ordem = 31)
        private String dataDeNascimento;
        
        private String descrDocumIdentifcacao;
        
        @ValidacaoClientePfDocumIdentificacao(ordem = 32)
        private String documIdentificacao;
        
        @ValidacaoClientePfEmissorDocumIdentificacao(ordem = 33)
        private String emissorDocumIdentificacao;
        
        @ValidacaoClientePfUfEmissorDocumIdentificacao(ordem = 34)
        private String ufEmissorDocumIdentificacao;
        
        @ValidacaoClientePfDataDocumIdentificacao(ordem = 35)
        private String dataDocumIdentificacao;
        
        @ValidacaoClientePfNomeDaMae(ordem = 36)
        private String nomeDaMae;
        
        @ValidacaoClientePfNomeDoPai(ordem = 37)
        private String nomeDoPai;
        
        @ValidacaoClientePfNacionalidade(ordem = 38)
        private String nacionalidade;
        
        @ValidacaoClientePfMunicipioDaNaturalidade(ordem = 39)
        private String municipioDaNaturalidade;
        
        @ValidacaoClientePfUfDaNaturalidade(ordem = 40)
        private String ufDaNaturalidade;
        
        @ValidacaoClientePfNomeDoConjuge(ordem = 41)
        private String nomeDoConjuge;
        
        @ValidacaoClientePfTelefoneResidencial(ordem = 42)
        private Integer telefoneResidencial;
        
        @ValidacaoClientePfTelefoneComercial(ordem = 43)
        private Integer telefoneComercial;
        
        @ValidacaoClientePfTelefoneCelular(ordem = 44)
        private Integer telefoneCelular;
        
        @ValidacaoClientePfRendaMensal(ordem = 45)
        private Integer rendaMensal;
        
        @ValidacaoClientePfPatrimonio(ordem = 46)
        private Integer patrimonio;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientePjDTO {
        @ValidacaoClientePjInscricaoEstadual(ordem = 47)
        private Integer inscricaoEstadual;
        
        @ValidacaoClientePjUfEmissorInscricaoEstadual(ordem = 48)
        private String ufEmissorInscricaoEstadual;
        
        @ValidacaoClientePjPorte(ordem = 49)
        private String porte;
        
        @ValidacaoClientePjRamoDeAtividade(ordem = 50)
        private String ramoDeAtividade;
        
        @ValidacaoClientePjFaturamentoMedioMensal(ordem = 51)
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
        @ValidacaoContatoTipoDeManutencao(ordem = 93)
        private String contatoTipoDeManutencao;
        
        @ValidacaoContatoNome(ordem = 94)
        private String contatoNome;
        
        @ValidacaoContatoTelefone1(ordem = 95)
        private Integer contatoTelefone1;
        
        @ValidacaoContatoTelefone2(ordem = 96)
        private Integer contatoTelefone2;
        
        @ValidacaoContatoEmail(ordem = 97)
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
        @ValidacaoSocioAcionistaNivel(ordem = 98)
        private String nivel;
        
        @ValidacaoSocioAcionistaOrdenLista(ordem = 99)
        private Integer ordenLista;
        
        @ValidacaoSocioAcionistaTipoDePessoa(ordem = 100)
        private String tipoDePessoa;
        
        @ValidacaoSocioAcionistaVigenciaFinal(ordem = 101)
        private String vigenciaFinal;
        
        @ValidacaoSocioAcionistaNomeRazaoSocial(ordem = 102)
        private String nomeRazaoSocial;
        
        @ValidacaoSocioAcionistaCpfCnpj(ordem = 103)
        private String cpfCnpj;
        
        @ValidacaoSocioAcionistaPaisDeOrigem(ordem = 104)
        private String paisDeOrigem;
        
        @ValidacaoSocioAcionistaQtdeAcoesCotas(ordem = 105)
        private Integer qtdeAcoesCotas;
        
        @ValidacaoSocioAcionistaPercentualDeParticipacao(ordem = 106)
        private Integer percentualDeParticipacao;
        
        @ValidacaoSocioAcionistaNacionalidade(ordem = 107)
        private String nacionalidade;
        
        @ValidacaoSocioAcionistaTipoDeIdentificacao(ordem = 108)
        private String tipoDeIdentificacao;
        
        @ValidacaoSocioAcionistaNumeroIdentificacao(ordem = 109)
        private String numeroIdentificacao;
        
        @ValidacaoSocioAcionistaDataEmissaoDocumento(ordem = 110)
        private String dataEmissaoDocumento;
        
        @ValidacaoSocioAcionistaOrgaoEmissor(ordem = 111)
        private String orgaoEmissor;
        
        @ValidacaoSocioAcionistaEstado(ordem = 112)
        private String estado;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaDeDocumentosDTO {
        @ValidacaoDocumentosTipoDocumentoDoCliente(ordem = 52)
        private String tipoDocumentoDoCliente;
        
        @ValidacaoDocumentosDataDoDocumento(ordem = 53)
        private String dataDoDocumento;
        
        @ValidacaoDocumentosDataDoVencimento(ordem = 54)
        private String dataDoVencimento;
        
        @ValidacaoDocumentosObservacoes(ordem = 55)
        private String observacoes;
        
        @ValidacaoDocumentosIdsDosArquivos(ordem = 56)
        private String idsDosArquivos;
        
        @ValidacaoDocumentosTipoDaOperacao(ordem = 57)
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
        @ValidacaoRepresentanteLegalRepTipo(ordem = 113)
        private String repTipo;
        
        @ValidacaoRepresentanteLegalNome(ordem = 114)
        private String nome;
        
        @ValidacaoRepresentanteLegalCpf(ordem = 115)
        private String cpf;
        
        @ValidacaoRepresentanteLegalDescrDocumIdentifcacao(ordem = 116)
        private String descrDocumIdentifcacao;
        
        @ValidacaoRepresentanteLegalDocumIdentificacao(ordem = 117)
        private String documIdentificacao;
        
        @ValidacaoRepresentanteLegalDataDocumIdentificacao(ordem = 118)
        private String dataDocumIdentificacao;
        
        @ValidacaoRepresentanteLegalEmissorDocumIdentificacao(ordem = 119)
        private String emissorDocumIdentificacao;
        
        @ValidacaoRepresentanteLegalUfEmissorDocumIdentificacao(ordem = 120)
        private String ufEmissorDocumIdentificacao;
        
        @ValidacaoRepresentanteLegalNacionalidade(ordem = 121)
        private String nacionalidade;
        
        @ValidacaoRepresentanteLegalEndTipo(ordem = 122)
        private String endTipo;
        
        @ValidacaoRepresentanteLegalEndTipoDeLogradouro(ordem = 123)
        private String endTipoDeLogradouro;
        
        @ValidacaoRepresentanteLegalEndLogradouro(ordem = 124)
        private String endLogradouro;
        
        @ValidacaoRepresentanteLegalEndNumero(ordem = 125)
        private String endNumero;
        
        @ValidacaoRepresentanteLegalEndComplemento(ordem = 126)
        private String endComplemento;
        
        @ValidacaoRepresentanteLegalEndCep(ordem = 127)
        private String endCep;
        
        @ValidacaoRepresentanteLegalEndBairro(ordem = 128)
        private String endBairro;
        
        @ValidacaoRepresentanteLegalEndMunicipio(ordem = 129)
        private String endMunicipio;
        
        @ValidacaoRepresentanteLegalEndUf(ordem = 130)
        private String endUf;
    }
} 