package fr.eql.al35.entity;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
public class Cart {

	private int articlesQuantity;
	private Double price;
	Set<CommandProduct> commandProducts = new HashSet<CommandProduct>();
	Set<CommandCustomProduct> commandCustomProducts = new HashSet<CommandCustomProduct>();

}
