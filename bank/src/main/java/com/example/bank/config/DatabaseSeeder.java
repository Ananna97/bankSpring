package com.example.bank.config;
import com.example.bank.model.ReportHeader;
import com.example.bank.model.ReportType;
import com.example.bank.repository.ReportHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class DatabaseSeeder implements CommandLineRunner{
    @Autowired
    private ReportHeaderRepository reportHeaderRepository;

    @Override
    public void run(String... args) throws Exception {
        seedReportHeaders();
    }

    private void seedReportHeaders() {
        if (reportHeaderRepository.count() == 0) {
            ReportHeader balanceSheet = new ReportHeader();
            balanceSheet.setTitle("Balance Sheet");
            balanceSheet.setType(ReportType.BALANCE_SHEET);
            balanceSheet.setCreationDate(new Date());
            balanceSheet.setCreationUser(1);

            reportHeaderRepository.save(balanceSheet);

            ReportHeader profitAndLoss = new ReportHeader();
            profitAndLoss.setTitle("Profit and Loss Statement");
            profitAndLoss.setType(ReportType.PROFIT_AND_LOSS);
            profitAndLoss.setCreationDate(new Date());
            profitAndLoss.setCreationUser(1);

            reportHeaderRepository.save(profitAndLoss);

            ReportHeader cashFlow = new ReportHeader();
            cashFlow.setTitle("Cash Flow Statement");
            cashFlow.setType(ReportType.CASH_FLOW);
            cashFlow.setCreationDate(new Date());
            cashFlow.setCreationUser(1);

            reportHeaderRepository.save(cashFlow);
        }
    }
}