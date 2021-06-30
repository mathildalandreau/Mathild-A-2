package fr.eql.al35.iservice;

import java.util.List;

import fr.eql.al35.dto.Colis;
import fr.eql.al35.dto.Tarif;

public interface ColisService {

	public List<Tarif> displayAllTarifs(Double poidsColis);
	public Colis getPoids(Colis colis);
	public Tarif getTarif(Integer id);
}
