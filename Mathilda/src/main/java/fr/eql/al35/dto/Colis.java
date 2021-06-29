package fr.eql.al35.dto;

import lombok.Data;

@Data
public class Colis {

	private long id;	
	private Double initialWeight;
	private Double finalWeight;
	public Parcel parcel;
}
