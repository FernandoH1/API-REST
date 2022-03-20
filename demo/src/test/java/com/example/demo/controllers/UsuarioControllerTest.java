package com.example.demo.controllers;

import com.example.demo.models.UsuarioModel;
import com.example.demo.servicios.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @MockBean
    private UsuarioService serviceUsuario;

    @Autowired
    private MockMvc mockMvc;

    //Todos Los USUARIOS(No se pidio como requisito pero lo hice para probar)
    @Test
    public void testGet() throws Exception {
        UsuarioModel usuario = new UsuarioModel("Fernando");
        UsuarioModel usuario2 = new UsuarioModel("XD");
        doReturn(Lists.newArrayList(usuario, usuario2)).when(serviceUsuario).obtenerUsuarios();

        mockMvc.perform(get("/usuario"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[1].nombre").value("XD"))
                .andExpect(jsonPath("$[0].nombre").value("Fernando"));
    }

    //GET para obtener un elemento por ID que se encuentra en la base de datos
    @Test
    public void testGetForID() throws Exception {
        UsuarioModel usuarioModel = new UsuarioModel(3L, "Fernando", "Fernando@gmail.com", 2);
        doReturn(Optional.of(usuarioModel)).when(serviceUsuario).obtenerPorId(usuarioModel.getId());

        URI uri = new URI("/usuario/3");
        //mockMvc.perform(get("/usuario/{id}",3L))
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Fernando"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //PUT para modificar un elemento que se encuentra en la base de datos
    @Test
    void testPutModificarElemento() throws Exception {
        UsuarioModel usuarioModel = new UsuarioModel(3L, "Fernandossssss", "Fernando@gmail.com", 2);
        doReturn(Optional.of(usuarioModel)).when(serviceUsuario).obtenerPorId(usuarioModel.getId());
        //Post maping no se ejecuta

        URI uri = new URI("/usuario");
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UsuarioModel(3L, "User", "email@gmail.com", 2)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Dato modificado"));

    }

    //PUT para modificar un elemento que no se encuentra en la base de datos (not found)
    @Test
    void testPutModificarElementoNotFound() throws Exception {
        UsuarioModel usuarioModel = new UsuarioModel(3L, "Fernandossssss", "Fernando@gmail.com", 2);
        doReturn(null).when(serviceUsuario).obtenerPorId(usuarioModel.getId());
        //Post maping no se ejecuta

        URI uri = new URI("/usuario");
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UsuarioModel(3L, "User", "email@gmail.com", 2)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("No se encontro"));

    }

}

