package com.ucmo.lms.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ucmo.lms.dao.ArtifactRepository;
import com.ucmo.lms.entity.Artifact;

@Service
public class ArtifactService {
	@Autowired
	ArtifactRepository artifactRepository;

	public Optional<Artifact> exists(Long id) {
		Optional<Artifact> artifact = artifactRepository.findById(id);

		return artifact;
	}

	public Page<Artifact> search(String stringToSearch, String type, int pageNum) {
		return this.search(stringToSearch, type, pageNum, Common.PAGINATION_ROWS);
	}

	public Page<Artifact> search(String stringToSearch, String type, int pageNum, int pageSize) {
		Long id = Common.convertStringToLong(stringToSearch);

		Page<Artifact> res = artifactRepository
				.findByIdAndTypeContainsIgnoreCaseOrTitleContainsIgnoreCaseAndTypeContainsIgnoreCaseOrIsbnContainsIgnoreCaseAndTypeContainsIgnoreCaseOrAuthorsContainsIgnoreCaseAndTypeContainsIgnoreCase(
						id, type, stringToSearch, type, stringToSearch, type, stringToSearch, type,
						PageRequest.of(pageNum, pageSize));
		return res;
	}

	public ActionConclusion update(String stringId, String isbn, String type, String genre, String authors, String title,
			String subtitle, String description, String publishers, String publishedOn, String itemPrice, String quantity,
			String totalQuantity, String rackLocation, String thumbnailLink) {
		Long id = Common.convertStringToLong(stringId);

		if (artifactRepository.existsById(id)) {
			Artifact artifact = artifactRepository.getOne(id);
			artifact.setAll(isbn, type, genre, authors, title, subtitle, description, publishers, publishedOn, itemPrice,
					quantity, totalQuantity, rackLocation, thumbnailLink);
			artifact.cascadeISBNManuallyOnEdit();
			artifactRepository.save(artifact);
			return new ActionConclusion(true, "Updated successfully.");
		}
		return new ActionConclusion(false, "Failed to update. Artifact ID does not exist.");
	}

	public ActionConclusion create(String isbn, String type, String genre, String authors, String title, String subtitle,
			String description, String publishers, String publishedOn, String itemPrice, String quantity,
			String totalQuantity, String rackLocation, String thumbnailLink) {
		if (!artifactRepository.existsByIsbn(isbn)) {
			Artifact artifact = new Artifact();
			artifact.setAll(isbn, type, genre, authors, title, subtitle, description, publishers, publishedOn, itemPrice,
					quantity, totalQuantity, rackLocation, thumbnailLink);
			artifactRepository.save(artifact);
			return new ActionConclusion(true, "Created successfully.");
		}
		return new ActionConclusion(false, "Failed to create. Artifact ISBN already exists.");
	}

	public ActionConclusion delete(String stringId) {
		Long id = Common.convertStringToLong(stringId);

		if (artifactRepository.existsById(id)) {
			artifactRepository.deleteById(id);
			return new ActionConclusion(true, "Deleted successfully.");
		}
		return new ActionConclusion(false, "Failed to delete. Artifact ID does not exist.");
	}

	public List<Artifact> getLatestArtifacts() {
		return artifactRepository.findTop6ByOrderByIdDescCreatedOnDesc();
	}

	public List<Artifact> getPopularArtifacts() {
		return artifactRepository.findTop6ByOrderByTotalLoansDesc();
	}

	public void printMe(List<Artifact> arr) {
		System.out.println("\n\nPrinting search result:");
		for (Artifact artifact : arr) {
			System.out.println(artifact);
		}
		;
	}

	public void printAll() {
		System.out.println("\n\nPrinting search result:");
		for (Artifact artifact : artifactRepository.findAll()) {
			System.out.println(artifact);
		}
		;
	}

	public void printAllWithLoanHistory() {
		System.out.println("\n\nPrinting search result:");
		for (Artifact artifact : artifactRepository.findAll()) {
			System.out.println(artifact.toStringWithLoanHistory());
		}
		;
	}

	public void printAllWithReserveQueue() {
		System.out.println("\n\nPrinting search result:");
		for (Artifact artifact : artifactRepository.findAll()) {
			System.out.println(artifact.toStringWithReserveQueue());
		}
		;
	}
}
