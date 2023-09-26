package com.nibm.smartmedicine.service;

import com.nibm.smartmedicine.entity.Pharmacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PharmacyService {
    List<Pharmacy> findAll();

    Optional<Pharmacy> findById(Long id);

    Page<Pharmacy> getPage(PageRequest of, String searchKey);

    Pharmacy save(Pharmacy pharmacy);
}
