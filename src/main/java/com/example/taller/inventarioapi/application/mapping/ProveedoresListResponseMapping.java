package com.example.taller.inventarioapi.application.mapping;
import org.springframework.stereotype.Component;
import com.example.taller.inventarioapi.application.dtos.ProveedoresListResponse;
import com.example.taller.inventarioapi.domain.aggregates.Proveedor;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ProveedoresListResponseMapping {

    public ProveedoresListResponse toResponse(Proveedor proveedor) {

        return new ProveedoresListResponse(
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
