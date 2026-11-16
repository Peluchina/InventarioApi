package com.example.taller.iventarioapi.inventarioapi;

import com.example.taller.inventarioapi.inventarioapi.InventarioApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = InventarioApiApplication.class, properties = {
		"spring.datasource.password=test",
		"spring.data.jdbc.repositories.enabled=false",
		"spring.security.oauth2.client.registration.google.client-id=test-client",
		"spring.security.oauth2.client.registration.google.client-secret=test-secret"
})
class InventarioapiApplicationTests {

	@Test
	void contextLoads() {
	}

}
