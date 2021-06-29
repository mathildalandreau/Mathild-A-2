package fr.eql.al35.wsrest.testsTransport;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.eql.al35.wsrest.transport.TransportApplication;
import fr.eql.al35.wsrest.transport.entity.Tarif;
import fr.eql.al35.wsrest.transport.service.TransportService;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TransportApplication.class})
class GetTransporter {
	
	@Autowired
	private TransportService transportService; 
	
	@Test
	@DisplayName("TU Calcul fees for an initial weight of 0.4 kg")
	void TU_CalculFees_04() {
		List<Tarif> list = transportService.calculateTarifs(0.4);
		assertEquals("La Poste", list.get(0).getTransporteur().getName()); 
		}

		
	}

