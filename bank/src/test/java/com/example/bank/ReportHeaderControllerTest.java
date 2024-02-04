package com.example.bank;
import com.example.bank.controller.ReportHeaderController;
import com.example.bank.dto.ReportHeaderDto;
import com.example.bank.model.ReportHeader;
import com.example.bank.model.ReportType;
import com.example.bank.service.ReportHeaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportHeaderControllerTest {
    @Mock
    private ReportHeaderService reportHeaderService;

    @InjectMocks
    private ReportHeaderController reportHeaderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddReportHeader_Success() {
        ReportHeaderDto reportHeaderDto = new ReportHeaderDto();
        reportHeaderDto.setTitle("Test Title");
        reportHeaderDto.setType(ReportType.BALANCE_SHEET);
        reportHeaderDto.setCreationDate(new Date());
        reportHeaderDto.setCreationUser(1);
        doNothing().when(reportHeaderService).addReportHeader(any(ReportHeader.class));
        ResponseEntity<String> response = reportHeaderController.addReportHeader(reportHeaderDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Report header added successfully", response.getBody());
    }

    @Test
    public void testAddReportHeader_Failure() {
        ReportHeaderDto reportHeaderDto = new ReportHeaderDto();

        doThrow(new IllegalArgumentException("Error message")).when(reportHeaderService).addReportHeader(any(ReportHeader.class));
        ResponseEntity<String> response = reportHeaderController.addReportHeader(reportHeaderDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteReportHeader_Success() {
        Long reportHeaderId = 1L;

        doNothing().when(reportHeaderService).deleteReportHeader(reportHeaderId);
        ResponseEntity<Void> response = reportHeaderController.deleteReportHeader(reportHeaderId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    public void testDeleteReportHeader_NotFound() {
        Long reportHeaderId = 1L;

        doThrow(new IllegalArgumentException("Error message")).when(reportHeaderService).deleteReportHeader(reportHeaderId);
        ResponseEntity<Void> response = reportHeaderController.deleteReportHeader(reportHeaderId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    public void testGetAllReportHeaders() {
        ReportHeader reportHeader = new ReportHeader();
        List<ReportHeader> reportHeaders = Collections.singletonList(reportHeader);

        reportHeader.setTitle("Test Title");
        reportHeader.setType(ReportType.BALANCE_SHEET);
        reportHeader.setCreationDate(new Date());
        reportHeader.setCreationUser(1);

        when(reportHeaderService.getAllReportHeaders(null)).thenReturn(reportHeaders);
        ResponseEntity<List<ReportHeader>> response = reportHeaderController.getAllReportHeaders(null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reportHeaders, response.getBody());
    }
}
