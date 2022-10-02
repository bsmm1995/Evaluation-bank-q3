package com.bp.cbe.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    PersonDTO dto;

    @BeforeEach
    void setUp() {
        dto = new PersonDTO("Bladimir", UserType.INTERNAL);
    }

    @Test
    @DisplayName("Create a person: It should have by default the status CREATED")
    void createPersonShouldHaveCreatedStatus() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(greaterThan(0))))
                .andExpect(jsonPath("$.names").exists())
                .andReturn();

        PersonDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonDTO.class);

        assertNotNull(result);
        assertEquals(Status.CREATED, result.getStatus());

        mvc.perform(MockMvcRequestBuilders
                        .get("/persons/" + result.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.names", is("Bladimir")))
                .andExpect(jsonPath("$.loans").isEmpty());
    }

    @Test
    @DisplayName("Create a person: Should throw error user type does not exist")
    void createPersonShouldErrorUserTypeNotExist() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"names\":\"Bladimir Minga\",\"type\":\"NONE\"}"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("User type does not exist")));
    }

    @Test
    @DisplayName("Update a person: You should update only the typeUser and status.")
    void updatePersonShouldUpdateTypeUserAndStatus() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        PersonDTO personSaved = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonDTO.class);

        PersonDTO personToUpdate = new PersonDTO("New name", UserType.GUEST);
        personToUpdate.setId(99999L);
        personToUpdate.setStatus(Status.ACTIVE);

        mvc.perform(MockMvcRequestBuilders
                        .put("/persons/" + personSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personToUpdate)))
                .andExpect(status().isOk());

        mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/persons/" + personSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PersonDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonDTO.class);

        assertEquals(personSaved.getId(), result.getId());
        assertEquals(personSaved.getNames(), result.getNames());
        assertEquals(personSaved.getId(), result.getId());
        assertEquals(Status.ACTIVE, result.getStatus());
        assertEquals(UserType.GUEST, result.getType());
    }
}