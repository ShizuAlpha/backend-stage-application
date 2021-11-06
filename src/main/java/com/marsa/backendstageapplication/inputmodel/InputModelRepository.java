package com.marsa.backendstageapplication.inputmodel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputModelRepository extends JpaRepository<InputModel, Long> {
}
