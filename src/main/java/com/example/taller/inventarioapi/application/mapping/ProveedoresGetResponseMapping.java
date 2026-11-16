package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;
import com.example.taller.inventarioapi.application.dtos.ProveedoresGetResponse;
import com.example.taller.inventarioapi.domain.aggregates.Proveedor;

@Component 
public class ProveedoresGetResponseMapping {
    public ProveedoresGetResponse toResponse(Proveedor proveedor) {

        return new ProveedoresGetResponse(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getContacto(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                proveedor.getDireccion(),
                proveedor.getEstadoId()
        );
    }
}
