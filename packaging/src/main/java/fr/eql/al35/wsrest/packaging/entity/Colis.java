package fr.eql.al35.wsrest.packaging.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Colis implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;	
	private Double initialWeight;
	private Double finalWeight;
	
	@OneToOne(mappedBy = "colis",cascade=CascadeType.ALL)
	public Parcel parcel;
}

