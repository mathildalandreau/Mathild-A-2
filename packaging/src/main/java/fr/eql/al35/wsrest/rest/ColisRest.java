package fr.eql.al35.wsrest.rest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.eql.al35.wsrest.packaging.entity.Colis;
import fr.eql.al35.wsrest.packaging.entity.Parcel;
import fr.eql.al35.wsrest.packaging.request.ColisRequest;
import fr.eql.al35.wsrest.packaging.response.ColisResponse;
import fr.eql.al35.wsrest.packaging.service.ColisIService;

@RestController
@RequestMapping(value="api-colis", headers="Accept=application/json")
public class ColisRest {	
	
	@Autowired
	private ColisIService colisService;

	@GetMapping("/colis/{idColis}") // http://localhost:8087/api-colis/colis/{id}
	public Colis getColis(@PathVariable("idColis") Integer idColis) {
		System.out.println("sysout du getColis");
		return colisService.getColisById(idColis);
	}

	@PostMapping
	public ColisResponse getWeigth(@RequestBody ColisRequest colisRequest) {
		Colis colis = new Colis();
		Parcel parcel = new Parcel();
		BeanUtils.copyProperties(colisRequest, colis);

		if (colis.getInitialWeight() < 0.5) {
			parcel.setSize("small");
			colis.setFinalWeight(colis.getInitialWeight() + parcel.getWeigth());
			ColisResponse response = new ColisResponse();
			BeanUtils.copyProperties(colis, response);

			return response;
		}

		else if (colis.getInitialWeight() < 1) {
			parcel.setSize("medium");
			colis.setFinalWeight(colis.getInitialWeight() + parcel.getWeigth());
			ColisResponse response = new ColisResponse();
			BeanUtils.copyProperties(colis, response);

			return response;
		}

		else if (colis.getInitialWeight() < 2) {
			parcel.setSize("large");
			colis.setFinalWeight(colis.getInitialWeight() + parcel.getWeigth());
			ColisResponse response = new ColisResponse();
			BeanUtils.copyProperties(colis, response);

			return response;
		}

		else if (colis.getInitialWeight() > 2) {
			parcel.setSize("kingsize");
			colis.setFinalWeight(colis.getInitialWeight() + parcel.getWeigth());
			ColisResponse response = new ColisResponse();
			BeanUtils.copyProperties(colis, response);

			return response;
		}

		return null;

	}


	public String getUser() {
		return "get user was called";
	}


}
