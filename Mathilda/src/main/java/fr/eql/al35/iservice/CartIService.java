package fr.eql.al35.iservice;

import fr.eql.al35.entity.Cart;
import fr.eql.al35.entity.CommandLine;
import fr.eql.al35.entity.Product;


public interface CartIService {

	//methodes services refaites par : Floriane

	public Cart generateFakeCart();
	public CommandLine addProductToCommandLine(Integer id, CommandLine commandLine);
	public Cart updateCommandLine(Cart cart, CommandLine commandLine);
	public Product getProduct(Cart cart, int index);
	public void removeCommandLine(Cart cart, CommandLine commandLine);

}
