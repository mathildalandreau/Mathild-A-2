package fr.eql.al35.iservice;

import fr.eql.al35.entity.Cart;
import fr.eql.al35.entity.Product;


public interface CartIService {

	//methodes services refaites par : Floriane
	public void addProduct(Cart cart, Product product, int quantity);
	public Product getProduct(Cart cart, int index);
	public void removeCommandLine(Cart cart, Product product);
}
