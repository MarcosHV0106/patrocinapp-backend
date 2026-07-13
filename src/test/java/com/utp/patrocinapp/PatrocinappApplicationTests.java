package com.utp.patrocinapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PatrocinappApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void exponeHealthYOpenApiSinAutenticacion() throws Exception {
		mockMvc.perform(get("/actuator/health"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("UP"));
		mockMvc.perform(get("/api-docs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.openapi").exists());
	}

	@Test
	void atiendeCincuentaConsultasHealthSecuenciales() throws Exception {
		for (int intento = 0; intento < 50; intento++) {
			mockMvc.perform(get("/actuator/health"))
					.andExpect(status().isOk());
		}
	}

}
