package fr.eql.al35.wsrest.transport.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.al35.wsrest.transport.dao.TarifDao;
import fr.eql.al35.wsrest.transport.dao.TransporteurDao;
import fr.eql.al35.wsrest.transport.entity.Tarif;
import fr.eql.al35.wsrest.transport.entity.Transporteur;

@Service
@Transactional //aller verifier pq là ya un transactionnal 
public class TransportService implements TransportIService {

	@Autowired
	private TransporteurDao transporteurDao;
	
	@Autowired
	private TarifDao tarifDao;
	
	public Transporteur findById(Integer id) {
		return transporteurDao.findById(id).get();
	}

	@Override
	public Set<Tarif> calculateTarifs(Double weight) {
		Set<Tarif> allTarifs = (Set<Tarif>) tarifDao.findAll();
		Set<Tarif> tarifs = new HashSet<Tarif>();
		for (Tarif tarif : allTarifs) {
			if (weight > tarif.getMinWeight() && weight < tarif.getMaxWeight()) {
				tarifs.add(tarif);
			}
		}
		return tarifs;
	}

}
