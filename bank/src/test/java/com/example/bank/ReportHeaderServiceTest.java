package com.example.bank;

import com.example.bank.model.ReportHeader;
import com.example.bank.model.ReportType;
import com.example.bank.repository.ReportHeaderRepository;
import com.example.bank.service.ReportHeaderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportHeaderServiceTest {
    @Mock
    private ReportHeaderRepository reportHeaderRepository;

    @InjectMocks
    private ReportHeaderService reportHeaderService;

    public ReportHeaderServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddReportHeader_Success() {
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.setType(ReportType.BALANCE_SHEET);
        reportHeader.setCreationDate(new Date());

        when(reportHeaderRepository.existsByTypeAndCreationDate(ReportType.BALANCE_SHEET, reportHeader.getCreationDate()))
                .thenReturn(false);

        reportHeaderService.addReportHeader(reportHeader);

        verify(reportHeaderRepository, times(1)).save(reportHeader);
    }

    @Test
    public void testAddReportHeader_DuplicateBalanceSheet() {
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.setType(ReportType.BALANCE_SHEET);
        reportHeader.setCreationDate(new Date());

        when(reportHeaderRepository.existsByTypeAndCreationDate(ReportType.BALANCE_SHEET, reportHeader.getCreationDate()))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reportHeaderService.addReportHeader(reportHeader);
        });

        assertEquals("There is already a BALANCE_SHEET report for the specified date", exception.getMessage());
        verify(reportHeaderRepository, never()).save(reportHeader);
    }

    @Test
    public void testDeleteReportHeader_Success() {
        Long reportHeaderId = 1L;

        when(reportHeaderRepository.existsById(reportHeaderId)).thenReturn(true);

        reportHeaderService.deleteReportHeader(reportHeaderId);

        verify(reportHeaderRepository, times(1)).deleteById(reportHeaderId);
    }

    @Test
    public void testDeleteReportHeader_NotFound() {
        Long reportHeaderId = 1L;

        when(reportHeaderRepository.existsById(reportHeaderId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reportHeaderService.deleteReportHeader(reportHeaderId);
        });

        assertEquals("The report header does not exist", exception.getMessage());
        verify(reportHeaderRepository, never()).deleteById(reportHeaderId);
    }

    @Test
    public void testGetAllReportHeaders_NullType() {
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.setType(ReportType.BALANCE_SHEET);

        List<ReportHeader> reportHeaders = Collections.singletonList(reportHeader);

        when(reportHeaderRepository.findAll()).thenReturn(reportHeaders);

        List<ReportHeader> result = reportHeaderService.getAllReportHeaders(null);

        assertEquals(reportHeaders, result);
    }

    @Test
    public void testGetAllReportHeaders_NotNullType() {
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.setType(ReportType.BALANCE_SHEET);

        List<ReportHeader> reportHeaders = Collections.singletonList(reportHeader);

        when(reportHeaderRepository.findAllByType(ReportType.BALANCE_SHEET)).thenReturn(reportHeaders);

        List<ReportHeader> result = reportHeaderService.getAllReportHeaders(ReportType.BALANCE_SHEET);

        assertEquals(reportHeaders, result);
    }
}
