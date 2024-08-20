package ru.postal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jooq.db.tables.records.MailingRecord;
import jooq.db.tables.records.OperationRecord;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.postal.dto.MailingDto;
import ru.postal.dto.OperationDto;
import ru.postal.dto.RegMailingDto;
import ru.postal.dto.enums.OperationType;
import ru.postal.service.MailingService;
import ru.postal.service.OperationService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@WebMvcTest(MailingController.class)
class MailingControllerTest {

    @MockBean
    private MailingService mailingService;

    @MockBean
    private OperationService operationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMailing() throws Exception {
        RegMailingDto regMailingDto = new RegMailingDto();

        regMailingDto.setBeneficiaryName("John");
        regMailingDto.setType("1");
        regMailingDto.setBeneficiaryIndex("125478");
        regMailingDto.setBeneficiaryAddress("125478");
        regMailingDto.setOfficeIndex("900500");

        String beneficiaryName = regMailingDto.getBeneficiaryName();
        Long typeId = Long.valueOf(regMailingDto.getType());
        String beneficiaryIndex = regMailingDto.getBeneficiaryIndex();
        String beneficiaryAddress = regMailingDto.getBeneficiaryIndex();
        Long statusId = OperationType.REGISTRATION.getId();
        String officeIndex = regMailingDto.getOfficeIndex();

        Long mailingId = 12L;

        MailingRecord mailingRecord = new MailingRecord();
        mailingRecord.setId(mailingId);
        mailingRecord.setBeneficiaryName(beneficiaryName);
        mailingRecord.setMailingTypeId(typeId);
        mailingRecord.setOfficeIndex(beneficiaryIndex);
        mailingRecord.setBeneficiaryAddress(beneficiaryAddress);
        mailingRecord.setStatusId(statusId);
        mailingRecord.setOfficeIndex(officeIndex);

        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setId(6L);
        operationRecord.setMailingId(mailingId);
        operationRecord.setInOfficeIndex(officeIndex);
        operationRecord.setOutOfficeIndex(officeIndex);
        operationRecord.setOperationTypeId(1L);
        operationRecord.setOperationDate(OffsetDateTime.now());

        String regJson = objectMapper.writeValueAsString(regMailingDto);

        Mockito.when(mailingService.createMailing(regMailingDto))
                .thenReturn(Optional.of(mailingRecord));

        Mockito.when(operationService.createOperation(mailingId, statusId, officeIndex, officeIndex))
                .thenReturn(Optional.of(operationRecord));

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/mailing/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(regJson)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));

        Mockito.verify(mailingService, Mockito.times(1))
                .createMailing(regMailingDto);

        Mockito.verify(operationService, Mockito.times(1))
                .createOperation(mailingId, statusId, officeIndex, officeIndex);

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
                .perform(
                        MockMvcRequestBuilders
                                .get("/mailing/{id}", 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mailingJson));


        Mockito.verify(mailingService, Mockito.times(1))
                .getMailing(1L);

    }

    @Test
    void getHistory() throws Exception {
        List<OperationDto> history = List.of(
                new OperationDto(1L, 1L, "100100",
                        "200200", 1L, OffsetDateTime.now()),
                new OperationDto(2L, 1L, "200200",
                        "300300", 2L, OffsetDateTime.now())
        );

        Mockito.when(operationService.getOperationMovementHistory(1L))
                .thenReturn(history);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/mailing/{id}/history", 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.hasSize(history.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id",
                        Matchers.containsInAnyOrder(1, 2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].inOfficeIndex",
                        Matchers.containsInAnyOrder("100100", "200200")));

        Mockito.verify(operationService, Mockito.times(1))
                .getOperationMovementHistory(1L);
    }

    @Test
    void getStatusArrival() throws Exception {
        MailingDto mailingDto = new MailingDto();

        mailingDto.setId(2L);
        mailingDto.setStatusId(2L);

        String operationName = OperationType.ARRIVAL.getName();

        Mockito.when(mailingService.getStatus(2L))
                .thenReturn(operationName);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/mailing/{id}/status", 2L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(operationName));


        Mockito.verify(mailingService, Mockito.times(1))
                .getStatus(2L);
    }

    @Test
    void getStatusRegistration() throws Exception {
        MailingDto mailingDto = new MailingDto();

        mailingDto.setId(1L);
        mailingDto.setStatusId(1L);

        String operationName = OperationType.REGISTRATION.getName();

        Mockito.when(mailingService.getStatus(1L))
                .thenReturn(operationName);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/mailing/{id}/status", 1L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(operationName));


        Mockito.verify(mailingService, Mockito.times(1))
                .getStatus(1L);
    }

    @Test
    void getStatusDeparture() throws Exception {
        MailingDto mailingDto = new MailingDto();

        mailingDto.setId(3L);
        mailingDto.setStatusId(3L);

        String operationName = OperationType.DEPARTURE.getName();

        Mockito.when(mailingService.getStatus(3L))
                .thenReturn(operationName);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/mailing/{id}/status", 3L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(operationName));


        Mockito.verify(mailingService, Mockito.times(1))
                .getStatus(3L);
    }

    @Test
    void getStatusReceipt() throws Exception {
        MailingDto mailingDto = new MailingDto();

        mailingDto.setId(4L);
        mailingDto.setStatusId(4L);

        String operationName = OperationType.RECEIPT.getName();

        Mockito.when(mailingService.getStatus(4L))
                .thenReturn(operationName);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/mailing/{id}/status", 4L)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(operationName));


        Mockito.verify(mailingService, Mockito.times(1))
                .getStatus(4L);
    }

    @Test
    void updateStatus() throws Exception {
        MailingDto mailingDto = new MailingDto();

        mailingDto.setId(1L);
        mailingDto.setBeneficiaryName("John");
        mailingDto.setMailingTypeId(4L);
        mailingDto.setBeneficiaryOfficeIndex("125478");
        mailingDto.setBeneficiaryAddress("125478");
        mailingDto.setStatusId(3L);
        mailingDto.setOfficeIndex("900500");

        String status = "4";
        String office = "300300";

        mailingDto.setStatusId(Long.valueOf(status));

        Mockito.when(mailingService.updateStatusMailing(1L, Long.valueOf(status), office))
                .thenReturn(mailingDto);

        String mailingJson = objectMapper.writeValueAsString(mailingDto);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/mailing/{id}", 1L)
                                .param("status", status)
                                .param("office", office)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mailingJson));


        Mockito.verify(mailingService, Mockito.times(1))
                .updateStatusMailing(1L, Long.valueOf(status), office);


    }
}