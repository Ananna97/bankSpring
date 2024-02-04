package com.example.bank.service;

import com.example.bank.model.ReportHeader;
import com.example.bank.model.ReportType;
import com.example.bank.repository.ReportHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportHeaderService {
    @Autowired
    private ReportHeaderRepository reportHeaderRepository;

    public void addReportHeader(ReportHeader reportHeader) {
        if (reportHeader.getType() == ReportType.BALANCE_SHEET &&
                reportHeaderRepository.existsByTypeAndCreationDate(ReportType.BALANCE_SHEET, reportHeader.getCreationDate())) {
            throw new IllegalArgumentException("There is already a BALANCE_SHEET report for the specified date");
        }

        reportHeaderRepository.save(reportHeader);
    }

    public void deleteReportHeader(Long id) {
        if (!reportHeaderRepository.existsById(id)) {
            throw new IllegalArgumentException("The report header does not exist");
        }
        reportHeaderRepository.deleteById(id);
    }

    public List<ReportHeader> getAllReportHeaders(ReportType type) {
        if (type != null) {
            return reportHeaderRepository.findAllByType(type);
        } else {
            return reportHeaderRepository.findAll();
        }
    }
}
