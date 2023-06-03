package com.irrigation.irrigation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irrigation.irrigation.model.land;
import com.irrigation.irrigation.model.slot;
import com.irrigation.irrigation.services.IrrigationSystem;
import com.irrigation.irrigation.services.LandService;
import com.irrigation.irrigation.services.SlotService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LandController.class)
public class LandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LandService landService;
    @MockBean
    private SlotService slotService;

    @MockBean
    private  IrrigationSystem irrigationSystem;

    @Autowired
    private ObjectMapper objectMapper;

    private land LondanLand;

    private land JapanLand;


    JSONObject json ;


    @BeforeEach
    void init() {
         json = new JSONObject();

         json.put("name","londan");
         json.put("crop","rice");
         json.put("size",30);

        LondanLand = new land();
        LondanLand.setId(1L);
        LondanLand.setName("londan");
        LondanLand.setSize(30);
        LondanLand.setCrop("fruits");

        JapanLand = new land();
        JapanLand.setName("japan");
        JapanLand.setSize(50);
        JapanLand.setCrop("rice");


    }
    @Test
    void shouldCreateLand() throws Exception {

        when(landService.create(any(land.class))).thenReturn(LondanLand);

        this.mockMvc.perform(post("/plots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(LondanLand)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name", is(LondanLand.getName())))
                .andExpect(jsonPath("size", is(LondanLand.getSize())))
                .andExpect(jsonPath("crop", is(LondanLand.getCrop())));
    }

    @Test
    void shouldFindAllLands() throws Exception {

        List<land> list = new ArrayList<>();
        list.add(LondanLand);
        list.add(JapanLand);

        when(landService.findAll()).thenReturn(list);

        this.mockMvc.perform(get("/plots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void shouldFINDOneLandById() throws Exception {

        when(landService.findById(anyLong())).thenReturn(Optional.ofNullable(LondanLand));

        this.mockMvc.perform(get("/plots/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(LondanLand.getName())))
                .andExpect(jsonPath("crop", is(LondanLand.getCrop())));
    }
    @Test
    void shouldDeleteLand() throws Exception {

        when(landService.findById(anyLong())).thenReturn(Optional.ofNullable(LondanLand));

        this.mockMvc.perform(delete("/plots/{id}", 1L))
                .andExpect(status().isNoContent());

    }
    @Test
    void shouldUpdateLand() throws Exception {

        when(landService.update(any(land.class))).thenReturn(LondanLand);
        when(landService.findById(anyLong())).thenReturn(Optional.ofNullable(LondanLand));

        this.mockMvc.perform(patch("/plots/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(json)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(LondanLand.getName())))
                .andExpect(jsonPath("crop", is(LondanLand.getCrop())));
    }
}
