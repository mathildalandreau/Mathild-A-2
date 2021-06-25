package fr.eql.al35.iservice;

import fr.eql.al35.entity.Cart;


public interface CartIService {

	double getTotalPriceCart(Cart cart);
	
	/* ancienne méthode Favori(te)
	 * ajout methodes add
	void addArticle(Cart cart, Article article);
	public Article getArticle(Cart cart, int index);
	public void removeArticle(Cart cart, int index);
	 */
}
