package fr.eql.al35.wsrest.transport.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.al35.wsrest.transport.dao.TransporteurDao;
import fr.eql.al35.wsrest.transport.entity.Transporteur;

@Service
@Transactional //aller verifier pq là ya un transactionnal 
public class TransportService implements TransportIService {

	@Autowired
	private TransporteurDao transporteurDao;

	public Transporteur findById(Integer id) {
		return transporteurDao.findById(id).get();
	}

}
