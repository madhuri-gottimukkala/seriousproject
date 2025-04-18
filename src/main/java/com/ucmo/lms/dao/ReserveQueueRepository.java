package com.ucmo.lms.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ucmo.lms.entity.Member;
import com.ucmo.lms.entity.ReserveQueue;

@Repository
public interface ReserveQueueRepository extends JpaRepository<ReserveQueue, Long> {
	/* @Query("SELECT RQ " + "FROM ReserveQueue RQ " + "WHERE "
	  + "(RQ.artifact.id = ?1 or UPPER(RQ.artifact.title) like UPPER(CONCAT('%', UPPER(?2), '%')) or UPPER(RQ.isbn) like UPPER(CONCAT('%', UPPER(?2), '%'))) and "
	  + "(RQ.memberId = ?3 or UPPER(RQ.member.fullName) like UPPER(CONCAT('%', UPPER(?4), '%')) or UPPER(RQ.member.email) like UPPER(CONCAT('%', UPPER(?4), '%'))) and "
	  + "(RQ.expiredOn between ?5 and ?6)")
	Page<ReserveQueue> findAllByArtifactAndMemberAndBothDatesAndStatus(Long artifactId, String artifactDetails,
	  Long memberId, String memberDetails, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable); */

	@Query("SELECT RQ " + "FROM ReserveQueue RQ " + "WHERE "
			+ "(RQ.artifact.id = ?1 or UPPER(RQ.artifact.title) like UPPER(CONCAT('%', UPPER(?2), '%')) or UPPER(RQ.isbn) like UPPER(CONCAT('%', UPPER(?2), '%'))) and "
			+ "(RQ.memberId = ?3 or UPPER(RQ.member.fullName) like UPPER(CONCAT('%', UPPER(?4), '%')) or UPPER(RQ.member.email) like UPPER(CONCAT('%', UPPER(?4), '%'))) and "
			+ "(RQ.expiredOn between ?5 and ?6) " + "ORDER BY RQ.artifact.id ASC, RQ.id ASC")
	Page<ReserveQueue> findAllByArtifactAndMemberAndBothDates(Long artifactId, String artifactDetails, Long memberId,
			String memberDetails, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);

	@Query("SELECT RQ.artifact.id, MIN(RQ.id) " + "FROM ReserveQueue RQ " + "WHERE "
			+ "(RQ.artifact.id = ?1 or UPPER(RQ.artifact.title) like UPPER(CONCAT('%', UPPER(?2), '%')) or UPPER(RQ.isbn) like UPPER(CONCAT('%', UPPER(?2), '%'))) and "
			+ "(RQ.memberId = ?3 or UPPER(RQ.member.fullName) like UPPER(CONCAT('%', UPPER(?4), '%')) or UPPER(RQ.member.email) like UPPER(CONCAT('%', UPPER(?4), '%'))) and "
			+ "(RQ.expiredOn between ?5 and ?6) " + "GROUP BY RQ.artifact.id")
	List<Object> findAllFirstPositionInQueueByArtifact(Long artifactId, String artifactDetails, Long memberId,
			String memberDetails, LocalDateTime dateFrom, LocalDateTime dateTo);

	Page<ReserveQueue> findByExpiredOnBetween(LocalDateTime expiredOnFrom, LocalDateTime expiredOnTo, Pageable pageable);

	ReserveQueue findFirstByIsbnOrderById(String isbn);

	boolean existsByIsbnAndMemberId(String isbn, Long memberId);

	boolean existsByIsbn(String isbn);

	boolean existsByArtifactId(String artifactId);

	boolean existsByMemberId(Long memberId);

	Integer countByMemberId(Long memberId);

	List<ReserveQueue> findByMemberId(Long memberId);

	List<ReserveQueue> findByMember(Member member);
}
