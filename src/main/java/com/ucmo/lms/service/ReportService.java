package com.ucmo.lms.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucmo.lms.dao.ArtifactRepository;
import com.ucmo.lms.dao.LoanHistoryRepository;
import com.ucmo.lms.dao.ReserveQueueRepository;

@Service
public class ReportService {
	@Autowired
	ReserveQueueRepository reserveQueueRepository;

	@Autowired
	ArtifactRepository artifactRepository;

	@Autowired
	LoanHistoryRepository loanHistoryRepository;

	public LocalDateTime today = LocalDate.now().atStartOfDay().minusSeconds(1);
	public LocalDateTime thisMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay()
			.minusSeconds(1);
	public LocalDateTime thisYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay()
			.minusSeconds(1);

	public BigDecimal fine(LocalDateTime fromDate) {
		return loanHistoryRepository.sumFineByIssuedOnAfter(fromDate);
	}

	public Integer totalArtifacts(LocalDateTime fromDate) {
		return artifactRepository.countByCreatedOnAfter(fromDate);
	}

	public Integer artifactsIssued(LocalDateTime fromDate) {
		return loanHistoryRepository.countByIssuedOnAfter(fromDate);
	}

	public Integer artifactsReturned(LocalDateTime fromDate) {
		return loanHistoryRepository.countByReturnedOnAfter(fromDate);
	}

	public Integer artifactsLost(LocalDateTime fromDate) {
		return loanHistoryRepository.countByIssuedOnAfterAndWasLostTrue(fromDate);
	}
}