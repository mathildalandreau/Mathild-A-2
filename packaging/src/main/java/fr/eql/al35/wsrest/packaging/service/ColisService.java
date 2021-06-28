package fr.eql.al35.wsrest.packaging.service;

import org.springframework.beans.factory.annotation.Autowired;

import fr.eql.al35.wsrest.packaging.dao.ColisIRepository;
import fr.eql.al35.wsrest.packaging.entity.Colis;

public class ColisService implements ColisIService {
	
	@Autowired
	private ColisIRepository colisIRepository;

	@Override
	public Colis getColisById(Integer id) {
		return colisIRepository.findById(id).get();
	}

}
