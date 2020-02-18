package com.joffryferrater.opadatafilterspringbootstarter.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joffryferrater.opadatafilterspringbootstarter.model.response.OpaCompilerResponse;
import com.joffryferrater.opadatafilterspringbootstarter.model.response.Query;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class AstToSqlQueryConverterTest {

    /*
     * Deserialize sample compiler response file from OPA (test classpath: opa-compiler-response.json)
     * and verify translation from OPA Abstract Syntax Tree compiler response to SQL statements
     */
    @Test
    void shouldConvertTermToSqlQuery() throws IOException {
        AstToSqlQueryConverter target = new AstToSqlQueryConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        OpaCompilerResponse opaCompilerResponse = objectMapper.readValue(response(), OpaCompilerResponse.class);
        List<List<Query>> queries = opaCompilerResponse.getResult().getQueries();
        Query query = queries.iterator().next().get(0);

        String result = target.astQueryToSqlQuery(query);

        assertThat(result, is("alice=pets.owner"));
    }

    private String response() throws IOException {
        return Files.readString(Paths.get("src/test/resources/opa-compiler-response.json"));
    }

}
