package fr.eql.al35.wsrest.testsPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.eql.al35.wsrest.packaging.PackagingApplication;
import fr.eql.al35.wsrest.packaging.entity.Colis;
import fr.eql.al35.wsrest.packaging.service.ColisService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PackagingApplication.class})
class WeightCalculation {
	
	@Autowired
	private ColisService colisService; 
	
	@Test
	void test04() {
		Colis colis = new Colis();
		colis.setInitialWeight(0.4);
		assertEquals(0.5, colisService.getFinalWeight(colis).getFinalWeight());
	}
	
	@Test
	void test05() {
		Colis colis = new Colis();
		colis.setInitialWeight(0.5);
		assertEquals(0.6, colisService.getFinalWeight(colis).getFinalWeight());
	}
	
	@Test
	void test06() {
		Colis colis = new Colis();
		colis.setInitialWeight(0.6);
		assertEquals(0.85, colisService.getFinalWeight(colis).getFinalWeight());
	}
	
	@Test
	void test09() {
		Colis colis = new Colis();
		colis.setInitialWeight(0.9);
		assertEquals(1.15, colisService.getFinalWeight(colis).getFinalWeight());
	}

	@Test
	void test10() {
		Colis colis = new Colis();
		colis.setInitialWeight(1.0);
		assertEquals(1.25, colisService.getFinalWeight(colis).getFinalWeight());
	}
	
	@Test
	void test11() {
		Colis colis = new Colis();
		colis.setInitialWeight(1.1);
		assertEquals(1.6, colisService.getFinalWeight(colis).getFinalWeight());
	}
	
	@Test
	void test19() {
		Colis colis = new Colis();
		colis.setInitialWeight(1.9);
		assertEquals(2.4, colisService.getFinalWeight(colis).getFinalWeight());
	}
	
	@Test
	void test20() {
		Colis colis = new Colis();
		colis.setInitialWeight(2.0);
		assertEquals(2.5, colisService.getFinalWeight(colis).getFinalWeight());
	}
	
	@Test
	void test21() {
		Colis colis = new Colis();
		colis.setInitialWeight(2.1);
		assertEquals(3.1, colisService.getFinalWeight(colis).getFinalWeight());
	}
	
}
