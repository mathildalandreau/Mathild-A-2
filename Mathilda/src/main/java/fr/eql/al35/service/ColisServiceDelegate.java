package fr.eql.al35.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.eql.al35.dto.Colis;
import fr.eql.al35.dto.Tarif;
import fr.eql.al35.iservice.ColisService;

@Service
public class ColisServiceDelegate implements ColisService {

	private RestTemplate restTemplate;
	private String baseUrlWsTransport = "http://localhost:8088/transport-rest";
	private String  baseUrlWsPoids = "http://localhost:8087/api-colis/weight/";
	private URI url = URI.create(baseUrlWsPoids);


	public ColisServiceDelegate() {
		restTemplate = initRestTemplate();
	}

	public RestTemplate initRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		}
		interceptors.add(new RestTemplateHeaderModifierInterceptor());
		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}

	@Override
	public List<Tarif> displayAllTarifs(Double poidsColis) {
		String url = baseUrlWsTransport + "/allTarifs/" + poidsColis;
		Tarif[] tabTarifs = restTemplate.getForObject(url, Tarif[].class);
		return Arrays.asList(tabTarifs);
	}
	
	//envoi d'une requête POST au WS pour récupérer le poids final du colis
	@Override
	public Colis getPoids(Colis colis) {
		colis =restTemplate.postForObject(url, colis, Colis.class);	
		return colis; 
	}
}
