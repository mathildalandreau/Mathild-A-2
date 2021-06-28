package fr.eql.al35.wsrest.transport.service;

import java.util.List;

import fr.eql.al35.wsrest.transport.entity.Tarif;

public interface TransportIService {

	public List<Tarif> calculateTarifs(Double weight);

}
