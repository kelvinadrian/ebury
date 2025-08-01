package com.ebury.cadastrocliente.validation;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Exemplo de como o sistema de validação funciona com listas.
 * Este componente demonstra como as mensagens de erro são estruturadas
 * para identificar exatamente qual item da lista e qual campo falhou.
 */
@Component
public class ListValidationExample implements CommandLineRunner {
    
    private final ValidationService validationService;
    
    public ListValidationExample(ValidationService validationService) {
        this.validationService = validationService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Exemplo de como criar um objeto com lista de documentos
        ClienteRequestDTO request = createExampleRequest();
        
        // Executa a validação
        System.out.println("=== EXEMPLO DE VALIDAÇÃO COM LISTAS ===");
        System.out.println("Validando objeto com lista de documentos...");
        
        List<ValidationResult> results = validationService.validateObject(request);
        
        if (results.isEmpty()) {
            System.out.println("✅ Nenhum erro de validação encontrado!");
        } else {
            System.out.println("❌ Erros de validação encontrados:");
            System.out.println();
            
            for (ValidationResult result : results) {
                System.out.println("🔴 Campo: " + result.getFieldPath());
                System.out.println("   Mensagem: " + result.getMessage());
                System.out.println("   Ordem: " + result.getOrdem());
                System.out.println();
            }
        }
        
        System.out.println("=== EXPLICAÇÃO DOS CAMINHOS DE CAMPO ===");
        System.out.println("• cliente.listaDeDocumentos[0].dataDoDocumento - Item 1 da lista, campo dataDoDocumento");
        System.out.println("• cliente.listaDeDocumentos[1].tipoDocumentoDoCliente - Item 2 da lista, campo tipoDocumentoDoCliente");
        System.out.println("• cliente.listaDeDocumentos[2].dataDoVencimento - Item 3 da lista, campo dataDoVencimento");
        System.out.println("• cliente.listaDeDocumentos[3].observacoes - Item 4 da lista, campo observacoes");
        System.out.println();
        System.out.println("O sistema automaticamente identifica a posição do item na lista usando [índice]");
    }
    
    private ClienteRequestDTO createExampleRequest() {
        ClienteRequestDTO request = new ClienteRequestDTO();
        
        // Cria o cliente principal
        ClienteRequestDTO.ClienteDTO cliente = new ClienteRequestDTO.ClienteDTO();
        cliente.setCliTipoDeManutencao("I"); // Inclusão
        cliente.setTipoDePessoa("F"); // Física
        cliente.setCpfCnpj("12345678901");
        cliente.setNome("João Silva");
        cliente.setDataDoCadastro("01/01/2024");
        
        // Cria lista de documentos com dados inválidos para demonstrar validação
        ClienteRequestDTO.ListaDeDocumentosDTO doc1 = new ClienteRequestDTO.ListaDeDocumentosDTO();
        doc1.setTipoDocumentoDoCliente("RG");
        doc1.setDataDoDocumento("32/13/2024"); // Data inválida
        doc1.setDataDoVencimento("01/01/2025");
        doc1.setObservacoes("Documento de identidade");
        doc1.setIdsDosArquivos("arquivo1.pdf");
        doc1.setTipoDaOperacao("I");
        
        ClienteRequestDTO.ListaDeDocumentosDTO doc2 = new ClienteRequestDTO.ListaDeDocumentosDTO();
        doc2.setTipoDocumentoDoCliente("CPF");
        doc2.setDataDoDocumento("15/06/2023");
        doc2.setDataDoVencimento("15/06/2033");
        doc2.setObservacoes("CPF do cliente");
        doc2.setIdsDosArquivos("arquivo2.pdf");
        doc2.setTipoDaOperacao("I");
        
        ClienteRequestDTO.ListaDeDocumentosDTO doc3 = new ClienteRequestDTO.ListaDeDocumentosDTO();
        doc3.setTipoDocumentoDoCliente("CNH");
        doc3.setDataDoDocumento("20/03/2022");
        doc3.setDataDoVencimento("45/13/2032"); // Data de vencimento inválida
        doc3.setObservacoes("Carteira de motorista");
        doc3.setIdsDosArquivos("arquivo3.pdf");
        doc3.setTipoDaOperacao("I");
        
        ClienteRequestDTO.ListaDeDocumentosDTO doc4 = new ClienteRequestDTO.ListaDeDocumentosDTO();
        doc4.setTipoDocumentoDoCliente("Comprovante de Residência");
        doc4.setDataDoDocumento("10/12/2023");
        doc4.setDataDoVencimento("10/12/2024");
        doc4.setObservacoes("Conta de luz");
        doc4.setIdsDosArquivos("arquivo4.pdf");
        doc4.setTipoDaOperacao("I");
        
        // Adiciona os documentos à lista
        cliente.setListaDeDocumentos(Arrays.asList(doc1, doc2, doc3, doc4));
        
        request.setCliente(cliente);
        
        return request;
    }
} 