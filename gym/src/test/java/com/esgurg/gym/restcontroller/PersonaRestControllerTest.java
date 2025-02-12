package com.esgurg.gym.restcontroller;

import com.esgurg.gym.dto.PersonaDTO;
import com.esgurg.gym.entity.enumeration.Genero;
import com.esgurg.gym.entity.enumeration.TipoDocumento;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonaRestControllerTest {

    // ESTAS RUTAS HAN SIDO IGNORADAS POR EL GITIGNORE, POR LO QUE NO LAS ENCONTRARÁS EN EL REPOSITORIO
    public final String PERSONA_URL = "media/persona.json";
    public final String PERSONA_UPDATE_URL = "media/persona_update.json";

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
            return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    })
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
                    }
            })
            .create();

    public void jsonPrettyPrint(String json) {
        System.out.println(gson.toJson(gson.fromJson(json, JsonElement.class)));
    }

    public String readFile(String url) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader r = new BufferedReader(new FileReader(url))) {
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    // TESTS ===============================================================================
    @Test
    public void testGetPersonas() throws Exception {
        mockMvc.perform(
                MockMvcTester.create(mockMvc).get()
                        .uri("/api/persona")
                        .contentType("application/json")
        )
        .andExpect(status().isOk())
        .andDo(result -> {
            String content = result.getResponse().getContentAsString();
            jsonPrettyPrint(content);
        });
    }

    @Test
    public void testGetPersona() throws Exception {
        final long id = 1L;
        mockMvc.perform(
                MockMvcTester.create(mockMvc).get()
                        .uri("/api/persona/find/" + id)
                        .contentType("application/json")
        ).andExpect(status().isOk())
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    jsonPrettyPrint(content);
                });
    }

    @Test
    public void testSavePersona() throws Exception {
        List<PersonaDTO> personas = gson.fromJson(readFile(PERSONA_URL), new TypeToken<List<PersonaDTO>>(){}.getType());
        for (PersonaDTO persona : personas) {
            mockMvc.perform(
                    MockMvcTester.create(mockMvc).post()
                            .uri("/api/persona")
                            .contentType("application/json")
                            .content(gson.toJson(persona))
            );/*.andExpect(status().isOk())
                    .andDo(result -> {
                        String content = result.getResponse().getContentAsString();
                        jsonPrettyPrint(content);
                    });*/
        }
    }

    @Test
    public void testUpdatePersona() throws Exception {
        List<PersonaDTO> personas = gson.fromJson(readFile(PERSONA_UPDATE_URL), new TypeToken<List<PersonaDTO>>(){}.getType());
        for (PersonaDTO persona : personas) {
            mockMvc.perform(
                    MockMvcTester.create(mockMvc).patch()
                            .uri("/api/persona")
                            .contentType("application/json")
                            .content(gson.toJson(persona))
            );/*.andExpect(status().isOk())
                    .andDo(result -> {
                        String content = result.getResponse().getContentAsString();
                        jsonPrettyPrint(content);
                    });*/
        }
    }

    @Test
    public void testDeletePersona() throws Exception {
        final long id = 6L;
        /*mockMvc.perform(
                MockMvcTester.create(mockMvc).delete()
                        .uri("/api/persona/" + id)
                        .contentType("application/json")
        ).andExpect(status().isOk())
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    jsonPrettyPrint(content);
                });*/
        for (int i = 1; i <= 6; i++) {
            mockMvc.perform(
                    MockMvcTester.create(mockMvc).delete()
                            .uri("/api/persona/" + i)
                            .contentType("application/json")
            );/*.andExpect(status().isOk())
                    .andDo(result -> {
                        String content = result.getResponse().getContentAsString();
                        jsonPrettyPrint(content);
                    });*/
        }
    }

    @Test
    public void testFindPersonaByDocument() throws Exception {
        final String document = "7777777777";
        mockMvc.perform(
                MockMvcTester.create(mockMvc).get()
                        .uri("/api/persona/find/document/" + document)
                        .contentType("application/json")
        ).andExpect(status().isNotFound())
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    System.out.println("Document TEST !!!!!!!!!!!!!!!!!!!!!");
                    jsonPrettyPrint(content);
                });
    }

    @Test
    public void testFindPersonaByName() throws Exception {
        final String name = "Juan";
        mockMvc.perform(
                MockMvcTester.create(mockMvc).get()
                        .uri("/api/persona/find/name/" + name)
                        .contentType("application/json")
        ).andExpect(status().isOk())
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    jsonPrettyPrint(content);
                });
    }

    @Test
    public void testFindPersonaByTipoDocumento() throws Exception {
        final TipoDocumento tipoDocumento = TipoDocumento.CEDULA;
        mockMvc.perform(
                MockMvcTester.create(mockMvc).get()
                        .uri("/api/persona/find/doctype/" + tipoDocumento)
                        .contentType("application/json")
        ).andExpect(status().isOk())
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    jsonPrettyPrint(content);
                });
    }

}
