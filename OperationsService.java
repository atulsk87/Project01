package main.java.com.atul.spring.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.java.com.atul.spring.mvc.exception.RecordNotFoundException;
import main.java.com.atul.spring.mvc.model.OperationEntity;
import main.java.com.atul.spring.mvc.repository.OperationRepository;

@Service
public class OperationsService {
	
	     
	    @Autowired
	    OperationRepository repository;
	     
	    public List<OperationEntity> getAllOperations()
	    {
	        List<OperationEntity> operationsList = repository.findAll();
	         
	        if(operationsList.size() > 0) {
	            return operationsList;
	        } else {
	            return new ArrayList<OperationEntity>();
	        }
	    }
	     
	    public OperationEntity addOperations(OperationEntity entity) throws RecordNotFoundException
	    {
	            entity = repository.save(entity);
	             
	            return entity;
	    }

}
