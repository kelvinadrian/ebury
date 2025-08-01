package com.ebury.cadastrocliente.controller;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import com.ebury.cadastrocliente.dto.ClienteResponseDTO;
import com.ebury.cadastrocliente.service.PubSubProducerService;
import com.ebury.cadastrocliente.validation.ValidationService;
import com.ebury.cadastrocliente.validation.ValidationResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Cliente", description = "Endpoints para cadastro de clientes")
public class ClienteController {
    
    private final PubSubProducerService pubSubProducerService;
    private final ValidationService validationService;
    
    @PostMapping("/cadastro")
    @Operation(
        summary = "Cadastrar cliente",
        description = "Recebe dados do cliente e envia para processamento assíncrono via Google Cloud Pub/Sub"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Cliente enviado para processamento com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ClienteResponseDTO.class),
                examples = @ExampleObject(
                    name = "Sucesso",
                    value = "{\"idDeClienteTree\": 1705312200000}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos ou inconsistências encontradas",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ClienteResponseDTO.class),
                examples = @ExampleObject(
                    name = "Erro de Validação",
                    value = """
                        {
                          "listaInconsistencias": {
                            "inconsistencia": [
                              {
                                "atributo": "cpfCnpj",
                                "mensagem": "CPF/CNPJ já cadastrado"
                              },
                              {
                                "atributo": "nome",
                                "mensagem": "Nome é obrigatório"
                              }
                            ]
                          }
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor"
        )
    })
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(
            @Parameter(
                description = "Dados do cliente para cadastro",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClienteRequestDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de Cliente",
                        value = """
                            {
                              "cliente": {
                                "cliTipoDeManutencao": "s",
                                "tipoDePessoa": "st",
                                "cpfCnpj": "12345678901",
                                "codExterno": "CLI001",
                                "codCorporativo": "CORP001",
                                "nome": "João Silva",
                                "dataDoCadastro": "15012024",
                                "dataDeDesativacao": "",
                                "desabilitado": "n",
                                "utilizaAssinaturaDigital": "s",
                                "negociacao": "NEG001",
                                "complementoDaNatureza": 2,
                                "naturezaJuridicaN1": 1,
                                "naturezaJuridicaN2": 2,
                                "originador": "ORIG001",
                                "tipoDeResidencia": "RESIDENCIAL",
                                "gerenteAnalista": "GER001",
                                "gerenteAnalistaOriginador": "GER001",
                                "pep": "n",
                                "iban": "BR1234567890123456789012345",
                                "clientePf": {
                                  "sexo": "M",
                                  "estadoCivil": "SOLTEIRO",
                                  "dataDeNascimento": "15011990",
                                  "descrDocumIdentifcacao": "RG",
                                  "documIdentificacao": "12345678",
                                  "emissorDocumIdentificacao": "SSP",
                                  "ufEmissorDocumIdentificacao": "SP",
                                  "dataDocumIdentificacao": "15012010",
                                  "nomeDaMae": "Maria Silva",
                                  "nomeDoPai": "José Silva",
                                  "nacionalidade": "BRASILEIRA",
                                  "municipioDaNaturalidade": "SÃO PAULO",
                                  "ufDaNaturalidade": "SP",
                                  "nomeDoConjuge": "",
                                  "telefoneResidencial": 11999999999,
                                  "telefoneComercial": 11888888888,
                                  "telefoneCelular": 11777777777,
                                  "rendaMensal": 10000,
                                  "patrimonio": 50000
                                }
                              }
                            }
                            """
                    )
                )
            )
            @RequestBody ClienteRequestDTO clienteRequest) {
        
        log.info("Recebendo requisição para cadastro de cliente: {}", 
                clienteRequest.getCliente() != null ? clienteRequest.getCliente().getNome() : "N/A");
        
        try {
            // Validar o objeto completo usando o sistema de validação personalizada
            List<ValidationResult> validationResults = validationService.validateObject(clienteRequest);
            
            // Verificar se há erros de validação
            List<ValidationResult> errors = validationResults.stream()
                    .filter(result -> !result.isValid())
                    .toList();
            
            if (!errors.isEmpty()) {
                log.warn("Validação falhou com {} erros", errors.size());
                
                // Converter resultados de validação para inconsistências
                List<ClienteResponseDTO.InconsistenciaDTO> inconsistenciaList = errors.stream()
                        .map(error -> new ClienteResponseDTO.InconsistenciaDTO(
                                error.getFieldPath() != null ? error.getFieldPath() : "campo", 
                                error.getMessage()))
                        .toList();
                
                ClienteResponseDTO response = ClienteResponseDTO.error(inconsistenciaList);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Se passou na validação, envia para processamento
            Long idDeClienteTree = pubSubProducerService.enviarClienteParaProcessamento(clienteRequest);
            ClienteResponseDTO response = ClienteResponseDTO.success(idDeClienteTree);
            
            log.info("Cliente validado e enviado para processamento com sucesso. ID: {}", idDeClienteTree);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Erro ao processar cadastro do cliente: {}", e.getMessage(), e);
            
            // Criar lista de inconsistências para erro
            List<ClienteResponseDTO.InconsistenciaDTO> inconsistenciaList = new ArrayList<>();
            inconsistenciaList.add(new ClienteResponseDTO.InconsistenciaDTO("erro", e.getMessage()));
            
            ClienteResponseDTO response = ClienteResponseDTO.error(inconsistenciaList);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @GetMapping("/health")
    @Operation(
        summary = "Health check",
        description = "Verifica se a API está funcionando corretamente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "API funcionando normalmente",
            content = @Content(
                mediaType = "text/plain",
                examples = @ExampleObject(
                    name = "Sucesso",
                    value = "Producer API está funcionando!"
                )
            )
        )
    })
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Producer API está funcionando!");
    }
} 