package fr.eql.al35.service;


import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.eql.al35.entity.Cart;
import fr.eql.al35.entity.CommandLine;
import fr.eql.al35.entity.Product;
import fr.eql.al35.iservice.CartIService;

@Service
@Transactional
public class CartService implements CartIService { 
	//methodes services refaites par : Floriane

	//méthodes publiques : 

	@Override
	public void addProduct(Cart cart, Product product, int quantity) {
		//TODO : a tester(Floriane)
		if (identifyExistingCommandLine(cart, product) != null) {
			CommandLine commandLine = identifyExistingCommandLine(cart, product);
			commandLine.setProductQuantity(commandLine.getProductQuantity() + quantity);
		} else {
			createNewCommandLine(cart, product, quantity);
		}
		updateCart(cart);
	}


	@Override
	public Product getProduct(Cart cart, int index) {
		// TODO a implémenter
		//Floriane : trouver à quoi sert cette méthode 
		//et comment l'adapter avec la nouvelle structure
		return null;
	}

	@Override
	public void removeCommandLine(Cart cart, Product product) {
		CommandLine commandLine = identifyExistingCommandLine(cart, product);
		cart.getCommandLines().remove(commandLine);
		updateCart(cart);
	}



	//méthodes privées : 
	private void updateCart(Cart cart) {
		updateArticleQuantity(cart);
		updateTotalPrice(cart);
	}

	private void updateArticleQuantity(Cart cart) {
		int articleQuantity = 0;
		for (CommandLine commandLine : cart.getCommandLines()) {
			articleQuantity += commandLine.getProductQuantity();
		}
		cart.setArticlesQuantity(articleQuantity);
	}

	private void updateTotalPrice(Cart cart) {
		double total = 0d;
		for (CommandLine commandLine : cart.getCommandLines()) {
			total = total + commandLine.getProduct().getPrice() * commandLine.getProductQuantity();
		}
		cart.setPrice(total);
	}

	private CommandLine identifyExistingCommandLine(Cart cart, Product product) {
		CommandLine foundedCommandLine = null;
		for (CommandLine cartCommandLine : cart.getCommandLines()) {
			if (cartCommandLine.getProduct().getReference() == product.getReference()) {
				foundedCommandLine = cartCommandLine;
				break;
			} 
		}
		return foundedCommandLine;
	}

	private void createNewCommandLine(Cart cart, Product product, int quantity) {
		CommandLine commandLine = new CommandLine();
		commandLine.setProduct(product);
		commandLine.setProductQuantity(quantity);
		cart.getCommandLines().add(commandLine);
	}

}
