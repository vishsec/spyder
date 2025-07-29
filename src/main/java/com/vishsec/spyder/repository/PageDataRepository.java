package com.vishsec.spyder.repository;

import com.vishsec.spyder.model.PageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageDataRepository extends JpaRepository<PageData, Long> {
    Optional<PageData> findByUrl(String url);
}

