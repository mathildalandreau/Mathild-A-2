package fr.eql.al35.iservice;

import org.springframework.web.client.RestTemplate;

import fr.eql.al35.dto.PointRelais;

public interface PointRelaisService {

	public RestTemplate initRestTemplate();
	public PointRelais getPointRelais(Integer id);
}
