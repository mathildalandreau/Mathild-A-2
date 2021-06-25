package fr.eql.al35.wsrest.packaging.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.Data;

@Data
@Entity
public class Parcel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue
	private long id;
	private Double weigth;
	@JoinColumn(name="size_id")
	private String size;		
}
