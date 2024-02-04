package com.example.bank.controller;

import com.example.bank.dto.ReportHeaderDto;
import com.example.bank.model.ReportHeader;
import com.example.bank.model.ReportType;
import com.example.bank.service.ReportHeaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportHeaderController {
    @Autowired
    private ReportHeaderService reportHeaderService;

    @PostMapping("/header")
    public ResponseEntity<String> addReportHeader(@RequestBody ReportHeaderDto reportHeaderDTO) {
        try {
            ReportHeader reportHeader = new ReportHeader();
            reportHeader.setTitle(reportHeaderDTO.getTitle());
            reportHeader.setType(reportHeaderDTO.getType());
            reportHeader.setCreationDate(reportHeaderDTO.getCreationDate());
            reportHeader.setCreationUser(reportHeaderDTO.getCreationUser());
            reportHeaderService.addReportHeader(reportHeader);
            return ResponseEntity.ok("Report header added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/header/{id}")
    public ResponseEntity<Void> deleteReportHeader(@PathVariable Long id) {
        try {
            reportHeaderService.deleteReportHeader(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/headers")
    public ResponseEntity<List<ReportHeader>> getAllReportHeaders(@RequestParam(required = false) ReportType type) {
        List<ReportHeader> reportHeaders = reportHeaderService.getAllReportHeaders(type);
        return ResponseEntity.ok(reportHeaders);
    }
}
