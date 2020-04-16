package main.java.com.atul.spring.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.java.com.atul.spring.mvc.model.OperationEntity;

 
@Repository
public interface OperationRepository
        extends JpaRepository<OperationEntity, Long> {
 
}

