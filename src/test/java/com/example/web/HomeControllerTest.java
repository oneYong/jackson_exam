package com.example.web;

import com.example.config.WebAppConfig;
import com.example.model.User;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kimwonyong on 2016. 1. 22..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class})
public class HomeControllerTest {

    private static final Logger logger =
            LoggerFactory.getLogger(HomeControllerTest.class);
    private MockMvc mockMvc;

    @Autowired
    private HomeController homeController;


    @Before
    public void init(){
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        this.mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .addFilter(filter).build();
    }

    @Test
    public void 루트매핑_테스트()throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void GET방식_ModelAttribute_테스트()throws Exception{
        MultiValueMap<String,String> params
                = new LinkedMultiValueMap<String,String>();

        params.add("userId","iersans");
        params.add("userName","원디");

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/exam/ModelAttribute")
                        .accept(MediaType.APPLICATION_JSON)
                        .params(params))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void GET방식_RequestParam_테스트()throws Exception{
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/exam/RequestParam")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userName","원디"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void GET방식_Restful방식_테스트()throws Exception{
        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                        .get("/exam/rest/iersans")
                        .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId",is("iersans1")))
                .andExpect(jsonPath("$.userName",is("원디1")));
    }

    @Test
    public void POST방식_RequestBody_테스트()throws Exception{
        User user = new User("testUser","관리자");

        // java to json
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(user);

        logger.debug("content = {}", content);

        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                .post("/exam/requestBody/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void PUT방식_RequestBody_테스트()throws Exception{
        User user = new User("iersans","원드래곤");

        // java to json
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(user);

        logger.debug("content = {}", content);

        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                .put("/exam/requestBody/put/iersans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId",is("iersans")))
                .andExpect(jsonPath("$.userName",is("원드래곤")));
    }

    @Test
    public void DELETE방식_Restful_테스트()throws Exception{


        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                .delete("/exam/rest/delete/iersans")
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId",is("iersans")));
    }


}
