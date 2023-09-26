package com.nibm.smartmedicine.service;

import com.nibm.smartmedicine.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MedicineService {
    List<Medicine> findAll();

    Page<Medicine> getPage(PageRequest of, String searchKey);

    Medicine save(Medicine medicine);

    void remove(Long id);
}
