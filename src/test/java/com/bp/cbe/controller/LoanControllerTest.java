package com.bp.cbe.controller;

import com.bp.cbe.domain.dto.LoanDTO;
import com.bp.cbe.domain.dto.PersonDTO;
import com.bp.cbe.domain.enums.Status;
import com.bp.cbe.domain.enums.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class LoanControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    LoanDTO loanDTO;
    PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        loanDTO = new LoanDTO();
        loanDTO.setEquipmentSeries("asd-123");
        personDTO = new PersonDTO("Bladimir", UserType.INTERNAL);
    }

    @Test
    @DisplayName("Generate a loan: Should change person status")
    void generateLoan() throws Exception {
        personDTO = createPerson();

        mvc.perform(MockMvcRequestBuilders
                        .post("/persons/" + personDTO.getId() + "/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(greaterThan(0))))
                .andExpect(jsonPath("$.equipmentSeries", is(loanDTO.getEquipmentSeries())))
                .andExpect(jsonPath("$.loanDate").exists())
                .andExpect(jsonPath("$.returnDate").exists());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/persons/" + personDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andReturn();

        PersonDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonDTO.class);

        assertNotNull(result);
        assertEquals(Status.ACTIVE, result.getStatus());
    }

    private PersonDTO createPerson() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonDTO.class);
    }

}