package ru.postal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.postal.dto.MailingDto;
import ru.postal.service.MailingService;
import ru.postal.service.OperationService;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest(MailingController.class)
class MailingControllerTest {

//    @Autowired
//    private MailingController mailingController;

    @MockBean
    private MailingService mailingService;

    @MockBean
    private OperationService operationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMailing() {

    }

    @Test
    void getMailing() throws Exception {
        MailingDto mailingDto = new MailingDto();

        mailingDto.setId(1L);
        mailingDto.setBeneficiaryName("John");
        mailingDto.setMailingTypeId(4L);
        mailingDto.setBeneficiaryOfficeIndex("125478");
        mailingDto.setBeneficiaryAddress("125478");
        mailingDto.setStatusId(4L);
        mailingDto.setOfficeIndex("900500");

        Mockito.when(mailingService.getMailing(1L))
                .thenReturn(mailingDto);

        String mailingJson = objectMapper.writeValueAsString(mailingDto);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/mailing/{id}", 1L)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mailingJson));


        Mockito.verify(mailingService, Mockito.times(1))
                .getMailing(1L);

    }

    @Test
    void getHistory() {


    }

    @Test
    void getStatus() {

    }

    @Test
    void updateStatus() {

    }
}