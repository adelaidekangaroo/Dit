package org.sber.simonov.dit.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sber.simonov.dit.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static data.MessageTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/schema.sql", "/data.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class MessageRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String REST_URL = MessageRestController.REST_URL + '/';

    @Test
    public void offer() throws Exception {

        Message created = new Message("newmsg");

        String json = objectMapper.writeValueAsString(created);

        MvcResult mvcResult = this.mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Message actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Message.class);
        created.setId(MSG4.getId() + 1);

        Assert.assertEquals(created, actual);
    }

    @Test
    public void peek() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(REST_URL + "peek"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Message actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Message.class);

        Assert.assertEquals(MSG1, actual);
    }

    @Test
    public void peekMax() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(REST_URL + "peekmax"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Message actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Message.class);

        Assert.assertEquals(MSG4, actual);
    }

    @Test
    public void poll() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(REST_URL + "poll"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Message actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Message.class);

        Assert.assertEquals(MSG1, actual);

        mvcResult = this.mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Message> actualList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Message>>() {
        });

        MatcherAssert.assertThat(actualList, Matchers.contains(MSG2, MSG3, MSG4));
    }

    @Test
    public void all() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Message> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Message>>() {
        });

        MatcherAssert.assertThat(actual, Matchers.contains(MSG1, MSG2, MSG3, MSG4));
    }
}