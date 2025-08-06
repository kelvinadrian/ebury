# Estrutura de Validadores e Anotações Reorganizada

Esta documentação descreve a nova estrutura de validadores e anotações organizados por DTO/classe, onde cada campo específico de cada DTO tem seu próprio validador e anotação únicos.

## ✅ Status da Reorganização

### ✅ **Concluído:**
1. **Validadores reorganizados** por package específico para cada DTO
2. **Anotações reorganizadas** por package específico para cada DTO  
3. **DTOs atualizados** para usar as novas anotações específicas
4. **Remoção das anotações genéricas** antigas
5. **Compilação bem-sucedida** sem erros

### 🔄 **Próximos Passos:**
1. Implementar as regras de validação específicas em cada validador
2. Atualizar testes para usar os novos validadores e anotações
3. Remover imports das anotações antigas nos DTOs

## Estrutura de Packages

### 1. `cliente/` - Validadores e Anotações para ClienteDTO
**Validadores:**
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

**Anotações:**
- `ValidacaoClienteTipoDeManutencao.java`
- `ValidacaoClienteTipoDePessoa.java`
- `ValidacaoClienteCpfCnpj.java`
- `ValidacaoClienteCodigoCorporativo.java`
- `ValidacaoClienteNome.java`
- `ValidacaoClienteDataDoCadastro.java`
- `ValidacaoClienteDataDeDesativacao.java`
- `ValidacaoClienteDesabilitado.java`
- `ValidacaoClienteAssinaturaDigital.java`
- `ValidacaoClienteNegociacao.java`
- `ValidacaoClienteComplementoNatureza.java`
- `ValidacaoClienteNaturezaJuridicaN1.java`
- `ValidacaoClienteNaturezaJuridicaN2.java`
- `ValidacaoClienteOriginador.java`
- `ValidacaoClienteTipoResidencia.java`
- `ValidacaoClienteGerenteAnalista.java`
- `ValidacaoClienteGerenteAnalistaOriginador.java`
- `ValidacaoClientePep.java`
- `ValidacaoClienteIban.java`

### 2. `endereco/` - Validadores e Anotações para EnderecoDTO
**Validadores:**
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

**Anotações:**
- `ValidacaoEnderecoTipo.java`
- `ValidacaoEnderecoTipoDeLogradouro.java`
- `ValidacaoEnderecoLogradouro.java`
- `ValidacaoEnderecoNumero.java`
- `ValidacaoEnderecoComplemento.java`
- `ValidacaoEnderecoCep.java`
- `ValidacaoEnderecoBairro.java`
- `ValidacaoEnderecoMunicipio.java`
- `ValidacaoEnderecoUf.java`
- `ValidacaoEnderecoPreferencial.java`

### 3. `enderecoexterior/` - Validadores e Anotações para EnderecoNoExteriorDTO
**Validadores:**
- `EnderecoExteriorEnderecoValidator.java`
- `EnderecoExteriorCidadeValidator.java`
- `EnderecoExteriorEstadoValidator.java`
- `EnderecoExteriorCepValidator.java`
- `EnderecoExteriorPaisValidator.java`
- `EnderecoExteriorTelefoneValidator.java`
- `EnderecoExteriorFaxValidator.java`
- `EnderecoExteriorEmailValidator.java`
- `EnderecoExteriorResidenciaFiscalValidator.java`

**Anotações:**
- `ValidacaoEnderecoExteriorEndereco.java`
- `ValidacaoEnderecoExteriorCidade.java`
- `ValidacaoEnderecoExteriorEstado.java`
- `ValidacaoEnderecoExteriorCep.java`
- `ValidacaoEnderecoExteriorPais.java`
- `ValidacaoEnderecoExteriorTelefone.java`
- `ValidacaoEnderecoExteriorFax.java`
- `ValidacaoEnderecoExteriorEmail.java`
- `ValidacaoEnderecoExteriorResidenciaFiscal.java`

### 4. `contacorrente/` - Validadores e Anotações para ContaCorrenteDTO
**Validadores:**
- `ContaCorrenteTipoDeManutencaoValidator.java`
- `ContaCorrenteAgenciaValidator.java`
- `ContaCorrenteNumeroValidator.java`
- `ContaCorrentePreferencialValidator.java`
- `ContaCorrenteDesativadaValidator.java`

**Anotações:**
- `ValidacaoContaCorrenteTipoDeManutencao.java`
- `ValidacaoContaCorrenteAgencia.java`
- `ValidacaoContaCorrenteNumero.java`
- `ValidacaoContaCorrentePreferencial.java`
- `ValidacaoContaCorrenteDesativada.java`

### 5. `contacorrenteteddoc/` - Validadores e Anotações para ContaCorrenteTedDocDTO
**Validadores:**
- `ContaCorrenteTedDocCodBacenBancoValidator.java`

**Anotações:**
- `ValidacaoContaCorrenteTedDocCodBacenBanco.java`

### 6. `emailsdocumentos/` - Validadores e Anotações para EmailsDocumentoDTO
**Validadores:**
- `EmailsDocumentosDocumentoValidator.java`
- `EmailsDocumentosEnviarParaValidator.java`
- `EmailsDocumentosEnviarCcParaValidator.java`

**Anotações:**
- `ValidacaoEmailsDocumentosDocumento.java`
- `ValidacaoEmailsDocumentosEnviarPara.java`
- `ValidacaoEmailsDocumentosEnviarCcPara.java`

### 7. `operacoespermitidas/` - Validadores e Anotações para OperacaoPermitidaDTO
**Validadores:**
- `OperacoesPermitidasTipoDeManutencaoValidator.java`
- `OperacoesPermitidasTipoOperacaoPermitidaValidator.java`

**Anotações:**
- `ValidacaoOperacoesPermitidasTipoDeManutencao.java`
- `ValidacaoOperacoesPermitidasTipoOperacaoPermitida.java`

### 8. `corretorasquerepresentam/` - Validadores e Anotações para CorretoraQueRepresentaDTO
**Validadores:**
- `CorretorasQueRepresentamCnpjCorretoraValidator.java`
- `CorretorasQueRepresentamInicioVigenciaValidator.java`
- `CorretorasQueRepresentamTerminoVigenciaValidator.java`
- `CorretorasQueRepresentamPercentCorretagemValidator.java`

**Anotações:**
- `ValidacaoCorretorasQueRepresentamCnpjCorretora.java`
- `ValidacaoCorretorasQueRepresentamInicioVigencia.java`
- `ValidacaoCorretorasQueRepresentamTerminoVigencia.java`
- `ValidacaoCorretorasQueRepresentamPercentCorretagem.java`

### 9. `clientepf/` - Validadores e Anotações para ClientePfDTO
**Validadores:**
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

**Anotações:**
- `ValidacaoClientePfSexo.java`
- `ValidacaoClientePfEstadoCivil.java`
- `ValidacaoClientePfDataDeNascimento.java`
- `ValidacaoClientePfDocumIdentificacao.java`
- `ValidacaoClientePfEmissorDocumIdentificacao.java`
- `ValidacaoClientePfUfEmissorDocumIdentificacao.java`
- `ValidacaoClientePfDataDocumIdentificacao.java`
- `ValidacaoClientePfNomeDaMae.java`
- `ValidacaoClientePfNomeDoPai.java`
- `ValidacaoClientePfNacionalidade.java`
- `ValidacaoClientePfMunicipioDaNaturalidade.java`
- `ValidacaoClientePfUfDaNaturalidade.java`
- `ValidacaoClientePfNomeDoConjuge.java`
- `ValidacaoClientePfTelefoneResidencial.java`
- `ValidacaoClientePfTelefoneComercial.java`
- `ValidacaoClientePfTelefoneCelular.java`
- `ValidacaoClientePfRendaMensal.java`
- `ValidacaoClientePfPatrimonio.java`

### 10. `clientepj/` - Validadores e Anotações para ClientePjDTO
**Validadores:**
- `ClientePjInscricaoEstadualValidator.java`
- `ClientePjUfEmissorInscricaoEstadualValidator.java`
- `ClientePjPorteValidator.java`
- `ClientePjRamoDeAtividadeValidator.java`
- `ClientePjFaturamentoMedioMensalValidator.java`

**Anotações:**
- `ValidacaoClientePjInscricaoEstadual.java`
- `ValidacaoClientePjUfEmissorInscricaoEstadual.java`
- `ValidacaoClientePjPorte.java`
- `ValidacaoClientePjRamoDeAtividade.java`
- `ValidacaoClientePjFaturamentoMedioMensal.java`

### 11. `contato/` - Validadores e Anotações para ContatoDTO
**Validadores:**
- `ContatoTipoDeManutencaoValidator.java`
- `ContatoNomeValidator.java`
- `ContatoTelefone1Validator.java`
- `ContatoTelefone2Validator.java`
- `ContatoEmailValidator.java`

**Anotações:**
- `ValidacaoContatoTipoDeManutencao.java`
- `ValidacaoContatoNome.java`
- `ValidacaoContatoTelefone1.java`
- `ValidacaoContatoTelefone2.java`
- `ValidacaoContatoEmail.java`

### 12. `socioacionista/` - Validadores e Anotações para SocioAcionistaDTO
**Validadores:**
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

**Anotações:**
- `ValidacaoSocioAcionistaNivel.java`
- `ValidacaoSocioAcionistaOrdenLista.java`
- `ValidacaoSocioAcionistaTipoDePessoa.java`
- `ValidacaoSocioAcionistaVigenciaFinal.java`
- `ValidacaoSocioAcionistaNomeRazaoSocial.java`
- `ValidacaoSocioAcionistaCpfCnpj.java`
- `ValidacaoSocioAcionistaPaisDeOrigem.java`
- `ValidacaoSocioAcionistaQtdeAcoesCotas.java`
- `ValidacaoSocioAcionistaPercentualDeParticipacao.java`
- `ValidacaoSocioAcionistaNacionalidade.java`
- `ValidacaoSocioAcionistaTipoDeIdentificacao.java`
- `ValidacaoSocioAcionistaNumeroIdentificacao.java`
- `ValidacaoSocioAcionistaDataEmissaoDocumento.java`
- `ValidacaoSocioAcionistaOrgaoEmissor.java`
- `ValidacaoSocioAcionistaEstado.java`

### 13. `documentos/` - Validadores e Anotações para ListaDeDocumentosDTO
**Validadores:**
- `DocumentosTipoDocumentoDoClienteValidator.java`
- `DocumentosDataDoDocumentoValidator.java`
- `DocumentosDataDoVencimentoValidator.java`
- `DocumentosObservacoesValidator.java`
- `DocumentosIdsDosArquivosValidator.java`
- `DocumentosTipoDaOperacaoValidator.java`

**Anotações:**
- `ValidacaoDocumentosTipoDocumentoDoCliente.java`
- `ValidacaoDocumentosDataDoDocumento.java`
- `ValidacaoDocumentosDataDoVencimento.java`
- `ValidacaoDocumentosObservacoes.java`
- `ValidacaoDocumentosIdsDosArquivos.java`
- `ValidacaoDocumentosTipoDaOperacao.java`

### 14. `representantelegal/` - Validadores e Anotações para RepresentanteLegalDTO
**Validadores:**
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

**Anotações:**
- `ValidacaoRepresentanteLegalRepTipo.java`
- `ValidacaoRepresentanteLegalNome.java`
- `ValidacaoRepresentanteLegalCpf.java`
- `ValidacaoRepresentanteLegalDescrDocumIdentifcacao.java`
- `ValidacaoRepresentanteLegalDocumIdentificacao.java`
- `ValidacaoRepresentanteLegalDataDocumIdentificacao.java`
- `ValidacaoRepresentanteLegalEmissorDocumIdentificacao.java`
- `ValidacaoRepresentanteLegalUfEmissorDocumIdentificacao.java`
- `ValidacaoRepresentanteLegalNacionalidade.java`
- `ValidacaoRepresentanteLegalEndTipo.java`
- `ValidacaoRepresentanteLegalEndTipoDeLogradouro.java`
- `ValidacaoRepresentanteLegalEndLogradouro.java`
- `ValidacaoRepresentanteLegalEndNumero.java`
- `ValidacaoRepresentanteLegalEndComplemento.java`
- `ValidacaoRepresentanteLegalEndCep.java`
- `ValidacaoRepresentanteLegalEndBairro.java`
- `ValidacaoRepresentanteLegalEndMunicipio.java`
- `ValidacaoRepresentanteLegalEndUf.java`

## Benefícios da Nova Estrutura

1. **Especificidade**: Cada validador e anotação é específico para um campo de um DTO particular
2. **Organização**: Validadores e anotações organizados por package de acordo com o DTO/classe
3. **Manutenibilidade**: Fácil localização e manutenção dos validadores e anotações
4. **Escalabilidade**: Estrutura preparada para adicionar novos validadores e anotações específicos
5. **Clareza**: Nomes dos validadores e anotações indicam claramente qual campo de qual DTO validam
6. **Separação de Responsabilidades**: Cada DTO tem seus próprios validadores e anotações

## Exemplo de Uso

```java
// Antes (validador e anotação genéricos)
@ValidacaoCpfCnpj
private String cpfCnpj; // Usava CpfCnpjValidator genérico

// Agora (validador e anotação específicos)
@ValidacaoClienteCpfCnpj
private String cpfCnpj; // Usa ClienteCpfCnpjValidator específico

@ValidacaoSocioAcionistaCpfCnpj
private String cpfCnpj; // Usa SocioAcionistaCpfCnpjValidator específico
```

## Mudanças Realizadas nos DTOs

### ✅ **ClienteRequestDTO.java atualizado:**
- **Imports reorganizados** para usar as novas anotações específicas por package
- **ClienteDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoCliente*`
- **EnderecoDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoEndereco*`
- **EnderecoNoExteriorDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoEnderecoExterior*`
- **ContaCorrenteDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoContaCorrente*`
- **ContaCorrenteTedDocDTO** - Anotação atualizada para usar prefixo `ValidacaoContaCorrenteTedDoc*`
- **EmailsDocumentoDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoEmailsDocumentos*`
- **OperacaoPermitidaDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoOperacoesPermitidas*`
- **CorretoraQueRepresentaDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoCorretorasQueRepresentam*`
- **ClientePfDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoClientePf*`
- **ClientePjDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoClientePj*`
- **ContatoDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoContato*`
- **SocioAcionistaDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoSocioAcionista*`
- **ListaDeDocumentosDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoDocumentos*`
- **RepresentanteLegalDTO** - Todas as anotações atualizadas para usar prefixo `ValidacaoRepresentanteLegal*`

## Próximos Passos

1. ✅ **Implementar as regras de validação específicas** em cada validador
2. ✅ **Atualizar testes** para usar os novos validadores e anotações
3. ✅ **Remover imports** das anotações antigas nos DTOs 