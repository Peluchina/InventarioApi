package com.example.taller.inventarioapi.infrastructure.security;

import java.time.Instant;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service 
public class JwtService {
    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    

    public String generarToken(
            Integer usuarioId,
            String email,
            String rol
    ) {
        return generarToken(usuarioId, email, rol, null, null);
    }

    public String generarToken(
            Integer usuarioId,
            String email,
            String rol,
            String nombre,
            String apellidos
    ) {
        Instant ahora = Instant.now();

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("inventario-api")
                .issuedAt(ahora)
                .expiresAt(ahora.plusSeconds(3600))
                .subject(String.valueOf(usuarioId))
                .claim("email", email)
                .claim("rol", RolAutorizado.canonicalizar(rol));
        if (nombre != null && !nombre.isBlank()) {
            claimsBuilder.claim("nombre", nombre);
        }
        if (apellidos != null && !apellidos.isBlank()) {
            claimsBuilder.claim("apellidos", apellidos);
        }
        JwtClaimsSet claims = claimsBuilder.build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}
