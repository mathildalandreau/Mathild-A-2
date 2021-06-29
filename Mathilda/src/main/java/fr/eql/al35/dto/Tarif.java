package fr.eql.al35.dto;

import lombok.Data;

@Data
public class Tarif {

	private Integer id;
	private Double price;
	private Double minWeight;
	private Double maxWeight;
	private Transporteur transporteur;
}
