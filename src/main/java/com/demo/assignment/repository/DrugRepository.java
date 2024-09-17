package com.demo.assignment.repository;

import com.demo.assignment.dao.CustomDrug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author rahimi.riduan
 */

public interface DrugRepository extends JpaRepository<CustomDrug, UUID> {

    Page<CustomDrug> findByManufacturerNameContainingIgnoreCase(String manufacturerName, Pageable pageable);
}
