package fr.eql.al35.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.eql.al35.dto.Tarif;
import fr.eql.al35.iservice.ColisService;

@Service
public class ColisServiceDelegate implements ColisService {
	
	private RestTemplate restTemplate;
	private String baseUrlWsTransport = "http://localhost:8086/transport-rest";
	
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

}
