package com.example.bank.dto;

import com.example.bank.model.ReportType;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class ReportHeaderDto {

    private String title;
    private ReportType type;
    private Date creationDate;
    private int creationUser;

}