package fr.eql.al35.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.al35.entity.Cart;
import fr.eql.al35.entity.Color;
import fr.eql.al35.entity.CommandLine;
import fr.eql.al35.entity.Product;
import fr.eql.al35.iservice.CartIService;
import fr.eql.al35.repository.ColorIRepository;
import fr.eql.al35.repository.ProductIRepository;

@Service
@Transactional
public class CartService implements CartIService {


	@Autowired
	private ProductIRepository productRepo;

	@Autowired
	private ColorIRepository colorRepo;


	//tester un cart en dur :
	@Override
	public Cart generateFakeCart() {
		Cart cart = new Cart();
		Product product1 = productRepo.findById(1).get();
		Product product2 = productRepo.findById(2).get();
		Color color1 = colorRepo.findById(1).get();
		Color color2 = colorRepo.findById(2).get();
		createNewCommandLine(cart, product1, 2, color1);
		createNewCommandLine(cart, product2, 3, color2);
		updateCart(cart);
		return cart;
	}

	//methodes services refaites par : Floriane

	//méthodes publiques : 

	@Override
	public CommandLine addProductToCommandLine(Integer id, CommandLine commandLine) {
		try {
			Product product = productRepo.findById(id).get();
			if (product != null) {
				commandLine.setProduct(product);
			} 
		} catch (Exception e) {
			System.out.println("addProduct, commandLineService, Product not found");
		}
		return commandLine;
	}

	@Override
	public Cart updateCommandLine(Cart cart, CommandLine commandLine) {
		for (CommandLine cartCommandLine : cart.getCommandLines()) {
			if (cartCommandLine.getProduct().getId() == commandLine.getProduct().getId() && cartCommandLine.getColor().getId() == commandLine.getColor().getId()) {
				cartCommandLine.setProductQuantity(cartCommandLine.getProductQuantity() + commandLine.getProductQuantity());
				break;
			} else {
				Set<CommandLine> newCommandLines = cart.getCommandLines();
				newCommandLines.add(commandLine);
				cart.setCommandLines(newCommandLines);
				break;
			}
		}
		updateCart(cart);
		return cart;
	}


	@Override
	public Product getProduct(Cart cart, int index) {
		// TODO a implémenter
		//Floriane : trouver à quoi sert cette méthode 
		//et comment l'adapter avec la nouvelle structure
		return null;
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


	//utile pour le fakeCart
	private CommandLine createNewCommandLine(Cart cart, Product product, int quantity, Color color) {
		CommandLine commandLine = new CommandLine();
		commandLine.setProduct(product);
		commandLine.setProductQuantity(quantity);
		commandLine.setColor(color);
		Set<CommandLine> commandLines = new HashSet<CommandLine>();
		if (cart.getCommandLines()!= null) {
			commandLines = cart.getCommandLines();	
		}
		commandLines.add(commandLine);
		cart.setCommandLines(commandLines);
		return commandLine;
	}








	//vieilles :
	/*
	@Override
	public CommandLine addProduct(Cart cart, Product product, int quantity, Color color) {
		//TODO : a tester(Floriane)
		CommandLine commandLine = new CommandLine();
		if (identifyExistingCommandLine(cart, product) != null) {
			commandLine = identifyExistingCommandLine(cart, product);
			commandLine.setProductQuantity(commandLine.getProductQuantity() + quantity);

		} else {
			commandLine = createNewCommandLine(cart, product, quantity, color);
		}
		System.out.println("avant updateCart");
		updateCart(cart);
		return commandLine;
	}


		@Override
	public void removeCommandLine(Cart cart, CommandLine commandLine) {
		// TODO a implémenter
		//Floriane : trouver à quoi sert cette méthode 
		//et comment l'adapter avec la nouvelle structure
	}
	 */

}
