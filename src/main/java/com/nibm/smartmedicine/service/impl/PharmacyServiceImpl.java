package com.nibm.smartmedicine.service.impl;

import com.nibm.smartmedicine.entity.Pharmacy;
import com.nibm.smartmedicine.repository.PharmacyRepository;
import com.nibm.smartmedicine.repository.specification.PharmacySpecification;
import com.nibm.smartmedicine.service.PharmacyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository repository;

    public PharmacyServiceImpl(PharmacyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Pharmacy> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Pharmacy> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Pharmacy> getPage(PageRequest of, String searchKey) {

        Specification<Pharmacy> specification = Specification.where(
               PharmacySpecification.search(searchKey == null ? null : searchKey.trim()));

        return repository.findAll(specification, of);
    }

    @Override
    public Pharmacy save(Pharmacy pharmacy) {
        return repository.save(pharmacy);
    }
}
