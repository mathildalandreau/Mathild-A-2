package fr.eql.al35.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class CustomsProducts implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name="custom_id")
	private Custom custom;
	
	@ManyToOne( cascade=CascadeType.ALL)
	@JoinColumn(name="product_id")
	private Product product;
	
}
