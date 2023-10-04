package com.tv.demo.simpleecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class BaseControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    protected final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    protected ResultActions makePostRequest(String url, String content) throws Exception {
        var request = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        return mockMvc.perform(request);
    }

    protected ResultActions makePostRequest(String url) throws Exception {
        var request = MockMvcRequestBuilders.post(url);
        return mockMvc.perform(request);
    }


    protected ResultActions makePutRequest(String url, String content) throws Exception {
        var request = MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        return mockMvc.perform(request);
    }

    protected ResultActions makeDeleteRequest(String url) throws Exception {
        var request = MockMvcRequestBuilders.delete(url);
        return mockMvc.perform(request);
    }

    protected ResultActions makeGetRequest(String url) throws Exception {
        var request = MockMvcRequestBuilders.get(url);
        return mockMvc.perform(request);
    }
}
