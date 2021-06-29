package fr.eql.al35.wsrest.testsPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
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

class SizeCalculation {
	
	@Autowired
	private ColisService colisService; 
	
	@Test
	@DisplayName("TU Calcul the parcel size with initial weight 0.4 kg")
	void TU_CalculSize_04_small() {
		Colis colis = new Colis();
		colis.setInitialWeight(0.4);
		assertEquals("small", colisService.getFinalWeight(colis).getParcel().getSize(), "Incorrect parcel size");
	}
	
	@Test
	@DisplayName("TU Calcul the parcel size with initial weight 0.5kg")
	void TU_CalculSize_05_small() {
		Colis colis = new Colis();
		colis.setInitialWeight(0.5);
		assertEquals("small", colisService.getFinalWeight(colis).getParcel().getSize(), "Incorrect parcel size");
	}
	
	@Test
	@DisplayName("TU Calcul the parcel size with initial weight 0.6kg")
	void TU_CalculSize_06_small() {
		Colis colis = new Colis();
		colis.setInitialWeight(0.6);
		assertEquals("medium", colisService.getFinalWeight(colis).getParcel().getSize(), "Incorrect parcel size");
	}
	
	@Test
	@DisplayName("TU Calcul the parcel size with initial weight 0.9kg")
	void TU_CalculSize_09_small() {
		Colis colis = new Colis();
		colis.setInitialWeight(0.9);
		assertEquals("medium", colisService.getFinalWeight(colis).getParcel().getSize(), "Incorrect parcel size");
	}
	
	@Test
	@DisplayName("TU Calcul the parcel size with initial weight 0.9g")
	void TU_CalculSize_1_small() {
		Colis colis = new Colis();
		colis.setInitialWeight(0.9);
		assertEquals("medium", colisService.getFinalWeight(colis).getParcel().getSize(), "Incorrect parcel size");
	}
	
	@Test
	@DisplayName("TU Calcul the parcel size with initial weight 1.0g")
	void TU_CalculSize_11_medium() {
		Colis colis = new Colis();
		colis.setInitialWeight(1.0);
		assertEquals("medium", colisService.getFinalWeight(colis).getParcel().getSize(), "Incorrect parcel size");
	}
	
	@Test
	@DisplayName("TU Calcul the parcel size with initial weight 1.2kg")
	void TU_CalculSize_12_large() {
		Colis colis = new Colis();
		colis.setInitialWeight(1.2);
		assertEquals("large", colisService.getFinalWeight(colis).getParcel().getSize(), "Incorrect parcel size");
	}
	
	@Test
	@DisplayName("TU Calcul the parcel size with initial weight 1.9kg")
	void TU_CalculSize_19_large() {
		Colis colis = new Colis();
		colis.setInitialWeight(1.9);
		assertEquals("large", colisService.getFinalWeight(colis).getParcel().getSize(), "Incorrect parcel size");
	}
	
	@Test
	@DisplayName("TU Calcul the parcel size with initial weight 2.0kg")
	void TU_CalculSize_2_large() {
		Colis colis = new Colis();
		colis.setInitialWeight(2.0);
		assertEquals("large", colisService.getFinalWeight(colis).getParcel().getSize(), "Incorrect parcel size");
	}
	
	@Test
	@DisplayName("TU Calcul the parcel size with initial weight 2.1kg")
	void TU_CalculSize_21_large() {
		Colis colis = new Colis();
		colis.setInitialWeight(2.1);
		assertEquals("kingsize", colisService.getFinalWeight(colis).getParcel().getSize(), "Incorrect parcel size");
	}
}
