package fr.eql.al35.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import fr.eql.al35.dto.PointRelais;
import fr.eql.al35.iservice.PointRelaisService;

@Service
public class PointRelaisServiceDelegate implements PointRelaisService {

	private RestTemplate restTemplate;
	private String baseUrlWSPointRelais = "http://localhost:3000/pointsRelais";

	public PointRelaisServiceDelegate() {
		restTemplate = initRestTemplate();
	}

	@Override
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
	public PointRelais getPointRelais(Integer id) {
		String url = baseUrlWSPointRelais + "/" + id;
		return restTemplate.getForObject(url, PointRelais.class);
	}



}
