package fr.eql.al35.dto;

import lombok.Data;

@Data
public class PointRelais {
	String id;
	String name;
	Adresse adresse;
	String latitude;
	String longitude;

}
