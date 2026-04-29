package com.oriontek.customer_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CustomerApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring Boot cargue correctamente.
    }

    @Test
    void shouldCreateCustomerSuccessfully() throws Exception {
        String customerJson = """
                {
                  "firstName": "Junior",
                  "lastName": "Carpenter",
                  "documentNumber": "00112345678",
                  "email": "junior.test@example.com",
                  "phone": "8298056075"
                }
                """;

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Junior"))
                .andExpect(jsonPath("$.lastName").value("Carpenter"))
                .andExpect(jsonPath("$.documentNumber").value("00112345678"))
                .andExpect(jsonPath("$.email").value("junior.test@example.com"))
                .andExpect(jsonPath("$.phone").value("8298056075"))
                .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    void shouldReturnBadRequestWhenCustomerEmailIsInvalid() throws Exception {
        String customerJson = """
                {
                  "firstName": "Junior",
                  "lastName": "Carpenter",
                  "documentNumber": "00112345679",
                  "email": "correo-invalido",
                  "phone": "8298056075"
                }
                """;

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists());
    }

    @Test
    void shouldReturnBadRequestWhenRequiredCustomerFieldsAreMissing() throws Exception {
        String customerJson = """
                {
                  "firstName": "",
                  "lastName": "",
                  "documentNumber": "",
                  "email": "",
                  "phone": "8298056075"
                }
                """;

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.documentNumber").exists())
                .andExpect(jsonPath("$.email").exists());
    }

    @Test
    void shouldGetCustomerByIdSuccessfully() throws Exception {
        String customerJson = """
                {
                  "firstName": "Carlos",
                  "lastName": "Ramirez",
                  "documentNumber": "00198765432",
                  "email": "carlos.ramirez@example.com",
                  "phone": "8095551111"
                }
                """;

        String response = mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(response);
        String customerId = jsonNode.get("id").asText();

        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.firstName").value("Carlos"))
                .andExpect(jsonPath("$.lastName").value("Ramirez"));
    }

    @Test
    void shouldReturnNotFoundWhenCustomerDoesNotExist() throws Exception {
        UUID customerId = UUID.randomUUID();

        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente no encontrado."));
    }

    @Test
    void shouldUpdateCustomerSuccessfully() throws Exception {
        String customerJson = """
                {
                  "firstName": "Maria",
                  "lastName": "Gomez",
                  "documentNumber": "00122233344",
                  "email": "maria.gomez@example.com",
                  "phone": "8091112222"
                }
                """;

        String response = mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(response);
        String customerId = jsonNode.get("id").asText();

        String updateJson = """
                {
                  "firstName": "Maria Isabel",
                  "lastName": "Gomez",
                  "documentNumber": "00122233344",
                  "email": "maria.isabel@example.com",
                  "phone": "8091113333"
                }
                """;

        mockMvc.perform(put("/api/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Maria Isabel"))
                .andExpect(jsonPath("$.email").value("maria.isabel@example.com"))
                .andExpect(jsonPath("$.phone").value("8091113333"));
    }

    @Test
    void shouldDeleteCustomerSuccessfully() throws Exception {
        String customerJson = """
                {
                  "firstName": "Pedro",
                  "lastName": "Lopez",
                  "documentNumber": "00155566677",
                  "email": "pedro.lopez@example.com",
                  "phone": "8099998888"
                }
                """;

        String response = mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(response);
        String customerId = jsonNode.get("id").asText();

        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateAddressForCustomerSuccessfully() throws Exception {
        String customerJson = """
                {
                  "firstName": "Ana",
                  "lastName": "Martinez",
                  "documentNumber": "00144455566",
                  "email": "ana.martinez@example.com",
                  "phone": "8294445555"
                }
                """;

        String customerResponse = mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode customerNode = objectMapper.readTree(customerResponse);
        String customerId = customerNode.get("id").asText();

        String addressJson = """
                {
                  "street": "Calle Principal #10",
                  "city": "Santo Domingo Este",
                  "province": "Santo Domingo",
                  "country": "República Dominicana",
                  "postalCode": "11500",
                  "isPrimary": true
                }
                """;

        mockMvc.perform(post("/api/customers/{customerId}/addresses", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.street").value("Calle Principal #10"))
                .andExpect(jsonPath("$.city").value("Santo Domingo Este"))
                .andExpect(jsonPath("$.province").value("Santo Domingo"))
                .andExpect(jsonPath("$.country").value("República Dominicana"))
                .andExpect(jsonPath("$.postalCode").value("11500"))
                .andExpect(jsonPath("$.isPrimary").value(true));
    }

    @Test
    void shouldReturnNotFoundWhenCreatingAddressForInvalidCustomer() throws Exception {
        UUID invalidCustomerId = UUID.randomUUID();

        String addressJson = """
                {
                  "street": "Calle Principal #10",
                  "city": "Santo Domingo Este",
                  "province": "Santo Domingo",
                  "country": "República Dominicana",
                  "postalCode": "11500",
                  "isPrimary": true
                }
                """;

        mockMvc.perform(post("/api/customers/{customerId}/addresses", invalidCustomerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente no encontrado."));
    }

    @Test
    void shouldReturnBadRequestWhenAddressRequiredFieldsAreMissing() throws Exception {
        String customerJson = """
                {
                  "firstName": "Luis",
                  "lastName": "Perez",
                  "documentNumber": "00177788899",
                  "email": "luis.perez@example.com",
                  "phone": "8297778888"
                }
                """;

        String customerResponse = mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode customerNode = objectMapper.readTree(customerResponse);
        String customerId = customerNode.get("id").asText();

        String addressJson = """
                {
                  "street": "",
                  "city": "",
                  "province": "",
                  "country": "",
                  "postalCode": "11500",
                  "isPrimary": true
                }
                """;

        mockMvc.perform(post("/api/customers/{customerId}/addresses", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.street").exists())
                .andExpect(jsonPath("$.city").exists())
                .andExpect(jsonPath("$.province").exists())
                .andExpect(jsonPath("$.country").exists());
    }
}