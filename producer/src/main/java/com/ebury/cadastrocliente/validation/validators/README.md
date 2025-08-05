# Estrutura de Validadores Reorganizada

Esta documentação descreve a nova estrutura de validadores organizados por DTO/classe, onde cada campo específico de cada DTO tem seu próprio validador único.

## Estrutura de Packages

### 1. `cliente/` - Validadores para ClienteDTO
- `ClienteTipoDeManutencaoValidator.java`
- `ClienteTipoDePessoaValidator.java`
- `ClienteCpfCnpjValidator.java`
- `ClienteCodigoCorporativoValidator.java`
- `ClienteNomeValidator.java`
- `ClienteDataDoCadastroValidator.java`
- `ClienteDataDeDesativacaoValidator.java`
- `ClienteDesabilitadoValidator.java`
- `ClienteAssinaturaDigitalValidator.java`
- `ClienteNegociacaoValidator.java`
- `ClienteComplementoNaturezaValidator.java`
- `ClienteNaturezaJuridicaN1Validator.java`
- `ClienteNaturezaJuridicaN2Validator.java`
- `ClienteOriginadorValidator.java`
- `ClienteTipoResidenciaValidator.java`
- `ClienteGerenteAnalistaValidator.java`
- `ClienteGerenteAnalistaOriginadorValidator.java`
- `ClientePepValidator.java`
- `ClienteIbanValidator.java`

### 2. `endereco/` - Validadores para EnderecoDTO
- `EnderecoTipoValidator.java`
- `EnderecoTipoDeLogradouroValidator.java`
- `EnderecoLogradouroValidator.java`
- `EnderecoNumeroValidator.java`
- `EnderecoComplementoValidator.java`
- `EnderecoCepValidator.java`
- `EnderecoBairroValidator.java`
- `EnderecoMunicipioValidator.java`
- `EnderecoUfValidator.java`
- `EnderecoPreferencialValidator.java`

### 3. `enderecoexterior/` - Validadores para EnderecoNoExteriorDTO
- `EnderecoExteriorEnderecoValidator.java`
- `EnderecoExteriorCidadeValidator.java`
- `EnderecoExteriorEstadoValidator.java`
- `EnderecoExteriorCepValidator.java`
- `EnderecoExteriorPaisValidator.java`
- `EnderecoExteriorTelefoneValidator.java`
- `EnderecoExteriorFaxValidator.java`
- `EnderecoExteriorEmailValidator.java`
- `EnderecoExteriorResidenciaFiscalValidator.java`

### 4. `contacorrente/` - Validadores para ContaCorrenteDTO
- `ContaCorrenteTipoDeManutencaoValidator.java`
- `ContaCorrenteAgenciaValidator.java`
- `ContaCorrenteNumeroValidator.java`
- `ContaCorrentePreferencialValidator.java`
- `ContaCorrenteDesativadaValidator.java`

### 5. `contacorrenteteddoc/` - Validadores para ContaCorrenteTedDocDTO
- `ContaCorrenteTedDocCodBacenBancoValidator.java`

### 6. `emailsdocumentos/` - Validadores para EmailsDocumentoDTO
- `EmailsDocumentosDocumentoValidator.java`
- `EmailsDocumentosEnviarParaValidator.java`
- `EmailsDocumentosEnviarCcParaValidator.java`

### 7. `operacoespermitidas/` - Validadores para OperacaoPermitidaDTO
- `OperacoesPermitidasTipoDeManutencaoValidator.java`
- `OperacoesPermitidasTipoOperacaoPermitidaValidator.java`

### 8. `corretorasquerepresentam/` - Validadores para CorretoraQueRepresentaDTO
- `CorretorasQueRepresentamCnpjCorretoraValidator.java`
- `CorretorasQueRepresentamInicioVigenciaValidator.java`
- `CorretorasQueRepresentamTerminoVigenciaValidator.java`
- `CorretorasQueRepresentamPercentCorretagemValidator.java`

### 9. `clientepf/` - Validadores para ClientePfDTO
- `ClientePfSexoValidator.java`
- `ClientePfEstadoCivilValidator.java`
- `ClientePfDataDeNascimentoValidator.java`
- `ClientePfDocumIdentificacaoValidator.java`
- `ClientePfEmissorDocumIdentificacaoValidator.java`
- `ClientePfUfEmissorDocumIdentificacaoValidator.java`
- `ClientePfDataDocumIdentificacaoValidator.java`
- `ClientePfNomeDaMaeValidator.java`
- `ClientePfNomeDoPaiValidator.java`
- `ClientePfNacionalidadeValidator.java`
- `ClientePfMunicipioDaNaturalidadeValidator.java`
- `ClientePfUfDaNaturalidadeValidator.java`
- `ClientePfNomeDoConjugeValidator.java`
- `ClientePfTelefoneResidencialValidator.java`
- `ClientePfTelefoneComercialValidator.java`
- `ClientePfTelefoneCelularValidator.java`
- `ClientePfRendaMensalValidator.java`
- `ClientePfPatrimonioValidator.java`

### 10. `clientepj/` - Validadores para ClientePjDTO
- `ClientePjInscricaoEstadualValidator.java`
- `ClientePjUfEmissorInscricaoEstadualValidator.java`
- `ClientePjPorteValidator.java`
- `ClientePjRamoDeAtividadeValidator.java`
- `ClientePjFaturamentoMedioMensalValidator.java`

### 11. `contato/` - Validadores para ContatoDTO
- `ContatoTipoDeManutencaoValidator.java`
- `ContatoNomeValidator.java`
- `ContatoTelefone1Validator.java`
- `ContatoTelefone2Validator.java`
- `ContatoEmailValidator.java`

### 12. `socioacionista/` - Validadores para SocioAcionistaDTO
- `SocioAcionistaNivelValidator.java`
- `SocioAcionistaOrdenListaValidator.java`
- `SocioAcionistaTipoDePessoaValidator.java`
- `SocioAcionistaVigenciaFinalValidator.java`
- `SocioAcionistaNomeRazaoSocialValidator.java`
- `SocioAcionistaCpfCnpjValidator.java`
- `SocioAcionistaPaisDeOrigemValidator.java`
- `SocioAcionistaQtdeAcoesCotasValidator.java`
- `SocioAcionistaPercentualDeParticipacaoValidator.java`
- `SocioAcionistaNacionalidadeValidator.java`
- `SocioAcionistaTipoDeIdentificacaoValidator.java`
- `SocioAcionistaNumeroIdentificacaoValidator.java`
- `SocioAcionistaDataEmissaoDocumentoValidator.java`
- `SocioAcionistaOrgaoEmissorValidator.java`
- `SocioAcionistaEstadoValidator.java`

### 13. `documentos/` - Validadores para ListaDeDocumentosDTO
- `DocumentosTipoDocumentoDoClienteValidator.java`
- `DocumentosDataDoDocumentoValidator.java`
- `DocumentosDataDoVencimentoValidator.java`
- `DocumentosObservacoesValidator.java`
- `DocumentosIdsDosArquivosValidator.java`
- `DocumentosTipoDaOperacaoValidator.java`

### 14. `representantelegal/` - Validadores para RepresentanteLegalDTO
- `RepresentanteLegalRepTipoValidator.java`
- `RepresentanteLegalNomeValidator.java`
- `RepresentanteLegalCpfValidator.java`
- `RepresentanteLegalDescrDocumIdentifcacaoValidator.java`
- `RepresentanteLegalDocumIdentificacaoValidator.java`
- `RepresentanteLegalDataDocumIdentificacaoValidator.java`
- `RepresentanteLegalEmissorDocumIdentificacaoValidator.java`
- `RepresentanteLegalUfEmissorDocumIdentificacaoValidator.java`
- `RepresentanteLegalNacionalidadeValidator.java`
- `RepresentanteLegalEndTipoValidator.java`
- `RepresentanteLegalEndTipoDeLogradouroValidator.java`
- `RepresentanteLegalEndLogradouroValidator.java`
- `RepresentanteLegalEndNumeroValidator.java`
- `RepresentanteLegalEndComplementoValidator.java`
- `RepresentanteLegalEndCepValidator.java`
- `RepresentanteLegalEndBairroValidator.java`
- `RepresentanteLegalEndMunicipioValidator.java`
- `RepresentanteLegalEndUfValidator.java`

## Benefícios da Nova Estrutura

1. **Especificidade**: Cada validador é específico para um campo de um DTO particular
2. **Organização**: Validadores organizados por package de acordo com o DTO/classe
3. **Manutenibilidade**: Fácil localização e manutenção dos validadores
4. **Escalabilidade**: Estrutura preparada para adicionar novos validadores específicos
5. **Clareza**: Nomes dos validadores indicam claramente qual campo de qual DTO validam

## Exemplo de Uso

```java
// Antes (validador genérico)
@ValidacaoCpfCnpj
private String cpfCnpj; // Usava CpfCnpjValidator genérico

// Agora (validador específico)
@ValidacaoCpfCnpj
private String cpfCnpj; // Usa ClienteCpfCnpjValidator específico

@ValidacaoCpfCnpjSocioAcionista
private String cpfCnpj; // Usa SocioAcionistaCpfCnpjValidator específico
```

## Próximos Passos

1. Implementar as regras de validação específicas em cada validador
2. Atualizar as anotações para usar os validadores específicos
3. Remover os validadores genéricos antigos
4. Atualizar testes para usar os novos validadores 