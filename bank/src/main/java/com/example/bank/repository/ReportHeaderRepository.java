package com.example.bank.repository;
import com.example.bank.model.ReportHeader;
import com.example.bank.model.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface ReportHeaderRepository extends JpaRepository<ReportHeader, Long> {
    List<ReportHeader> findAllByType(ReportType type);
    boolean existsByTypeAndCreationDate(ReportType type, Date creationDate);
    boolean existsById(Long id);
}
