package fr.eql.al35.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//pas de changement since Favori(te)

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class ProductType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String name;
	private Double width;
	private Double height;
	private Double weight;

	@OneToMany(mappedBy = "productType",  cascade=CascadeType.ALL)
	private Set<Product> products;

	@Override
	public String toString() {
		return  name ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductType other = (ProductType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
