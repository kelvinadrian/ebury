package com.ebury.cadastrocliente.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {
    
    private Long idDeClienteTree;
    private ListaInconsistenciasDTO listaInconsistencias;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListaInconsistenciasDTO {
        private List<InconsistenciaDTO> inconsistencia;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InconsistenciaDTO {
        private String atributo;
        private String mensagem;
    }
    
    public static ClienteResponseDTO success(Long idDeClienteTree) {
        return new ClienteResponseDTO(idDeClienteTree, null);
    }
    
    public static ClienteResponseDTO error(List<InconsistenciaDTO> inconsistenciaList) {
        ListaInconsistenciasDTO listaInconsistencias = new ListaInconsistenciasDTO();
        listaInconsistencias.setInconsistencia(inconsistenciaList);
        return new ClienteResponseDTO(null, listaInconsistencias);
    }
} 