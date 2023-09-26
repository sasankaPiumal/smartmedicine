package com.nibm.smartmedicine.service.impl;

import com.nibm.smartmedicine.entity.Medicine;
import com.nibm.smartmedicine.repository.MedicineRepository;
import com.nibm.smartmedicine.repository.specification.MedicineSpecification;
import com.nibm.smartmedicine.service.MedicineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository repository;

    public MedicineServiceImpl(MedicineRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Medicine> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Medicine> getPage(PageRequest of, String searchKey) {

        Specification<Medicine> specification = Specification.where(
                MedicineSpecification.search(searchKey == null ? null : searchKey.trim()));

        return repository.findAll(specification, of);
    }

    @Override
    public Medicine save(Medicine medicine) {
        return repository.save(medicine);
    }

    @Override
    public void remove(Long id) {
       repository.deleteById(id);
    }

}
