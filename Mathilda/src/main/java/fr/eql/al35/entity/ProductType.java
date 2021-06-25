package fr.eql.al35.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

//pas de changement since Favori(te)

@Data
@Entity
public class ProductType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	@OneToMany(mappedBy = "productType",  cascade=CascadeType.ALL)
	private Set<Product> products;

}
