package fr.eql.al35.wsrest.transport.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Transporteur implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer deliveryRange; //délai de livraison

	@JsonIgnore
	@OneToMany(mappedBy = "transporteur",  cascade=CascadeType.ALL)
	private Set<Tarif> tarifs;

}
