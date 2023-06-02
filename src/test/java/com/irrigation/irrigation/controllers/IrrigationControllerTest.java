package com.irrigation.irrigation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irrigation.irrigation.component.RetryQueue;
import com.irrigation.irrigation.model.land;
import com.irrigation.irrigation.model.slot;
import com.irrigation.irrigation.services.*;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IrrigationController.class)
public class IrrigationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LandService landService;
    @MockBean
    private SlotService slotService;

    @MockBean
    private IrrigationSystem irrigationSystem;

    @MockBean
    private  SensorService sensorService;

    @MockBean
    private RetryQueue retryQueue;

    @MockBean
    private AlertingSystem alertingSystem;

    @Autowired
    private ObjectMapper objectMapper;

    private land LondanLand;
    private land JapanLand;
    slot LondanSlot;
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

        LondanSlot = new slot();
        LondanSlot.setLand_id(1L);
        LondanSlot.setIrrigating(false);

        JapanLand = new land();
        JapanLand.setName("japan");
        JapanLand.setSize(50);
        JapanLand.setCrop("rice");
    }
    @Test
    void shouldCheckStatus() throws Exception {

        this.mockMvc.perform(get("/irrigation/status"))
                .andExpect(status().isOk()).andExpect(jsonPath("sIrrigating").
                        value(irrigationSystem.getsIrrigating()));
    }
    @Test
    void shouldStopSystem() throws Exception {
        irrigationSystem.setsIrrigating(false);
        this.mockMvc.perform(post("/irrigation/stop"))
                .andExpect(status().isOk()).andExpect(jsonPath("sIrrigating").
                        value(irrigationSystem.getsIrrigating()));
    }
    @Test
    void shouldIrrigateThisPlot() throws Exception {
        when(landService.findById(anyLong())).thenReturn(Optional.ofNullable(LondanLand));
        when(slotService.findById(anyLong())).thenReturn(Optional.ofNullable(LondanSlot));

        this.mockMvc.perform(post("/irrigation/irrigate/{plotId}", 1L))
                .andExpect(status().isOk());
    }

}
