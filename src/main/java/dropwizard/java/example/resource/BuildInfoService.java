package dropwizard.java.example.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesJson;

import java.util.Map;

public class BuildInfoService {
    private String name;

    public BuildInfoService(String name) {
        this.name = name;
    }

    @Get("/buildInfo")
    @ProducesJson
    public HttpResponse list() throws JsonProcessingException {
        Map entity = ImmutableMap.builder()
                .put("name", name)
                .put("java.version", System.getProperty("java.version"))
                .put("java.runtime.name", System.getProperty("java.runtime.name"))
                .build();
        return HttpResponse.of(
                HttpStatus.OK,
                MediaType.HTML_UTF_8,
                new ObjectMapper().writeValueAsString(entity));
    }
}
