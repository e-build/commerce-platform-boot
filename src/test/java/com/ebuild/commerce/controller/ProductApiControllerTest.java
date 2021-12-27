package com.ebuild.commerce.controller;

import com.ebuild.commerce.business.product.service.ProductCommandService;
import com.google.common.collect.Maps;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@AutoConfigureMockMvc
class ProductApiControllerTest {

  @Autowired
  private ProductApiController productApiController;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ProductCommandService productCommandService;

  private Map<String, Object> requestBodyMap;

  @BeforeEach
  public void setUp(){
    requestBodyMap = Maps.newHashMap();
  }

//  @Test
//  public void register(){
//    requestBodyMap.put("student", new ProductSaveReqDto());
//    Long companyId = 1L;
//    Assertions.assertThat(productCommandService.register(companyId, )).is;
//    assertPost2xx("/companys/1/products", JsonUtils.serialize(requestBodyMap));
//  }
//
//  private void assertPost2xx(String url, String body){
//    perform(body, MockMvcRequestBuilders.post(url), MockMvcResultMatchers.status().is2xxSuccessful());
//  }
//
//  private void perform(String body, MockHttpServletRequestBuilder mockHttpServletRequestBuilder, ResultMatcher statusMatcher){
//    try{
//      mockMvc.perform(mockHttpServletRequestBuilder
//              .content(body)
//              .contentType(MediaType.APPLICATION_JSON))
//          .andExpect(statusMatcher)
//          .andDo(print());
//
//    } catch(Exception ignore){
//
//    }
//
//  }

}