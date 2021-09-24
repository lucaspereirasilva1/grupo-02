package br.com.meli.desafiospring.util;

import br.com.meli.desafiospring.model.entity.Consulta;
import br.com.meli.desafiospring.model.entity.Medico;
import br.com.meli.desafiospring.model.entity.Paciente;
import br.com.meli.desafiospring.model.entity.Proprietario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ArquivoUtil {

    public static void collectionToJson(File file, List<Consulta> users) throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.writeValue(file, users);
    }

    public static List jsonToCollection(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, new TypeReference<List>() {});
    }

    // Utilidado no medico Service
    public static void collectionToJsonMedico(File file, List<Medico> users) throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.writeValue(file, users);
    }

    public static void collectionToJsonProprietario(File file, List<Proprietario> users) throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.writeValue(file, users);
    }

    public static void collectionToJsonPaciente(File file, List<Paciente> users) throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.writeValue(file, users);
    }
}
