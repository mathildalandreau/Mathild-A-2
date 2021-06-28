package fr.eql.al35.wsrest.transport.service;

import java.util.Set;

import fr.eql.al35.wsrest.transport.entity.Tarif;

public interface TransportIService {
	
	public Set<Tarif> calculateTarifs(Double weight);

}
