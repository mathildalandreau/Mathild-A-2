package fr.eql.al35.service;


import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.eql.al35.entity.Cart;
import fr.eql.al35.iservice.CartIService;

@Service
@Transactional
public class CartService implements CartIService {

	@Override
	public int getCartProductsQuantity(Cart cart) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTotalPriceCart(Cart cart) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * ancienne méthode Favori(te)
	@Override
	public int getCartProductsQuantity(Cart cart) {

		Set<Article> articles = cart.getArticles();
		int articlesQuantity = 0;

		for (Article article : articles) {
			articlesQuantity += article.getQuantity();
		}

		return articlesQuantity;
	}

	@Override
	public double getTotalPriceCart(Cart cart) {
		Set<Article> articles = cart.getArticles();
		double total = 0.0;
		double sousTotal = 0.0;
		for (Article article : articles) {
			sousTotal = article.getProduct().getPrice() * article.getQuantity();
			total = total + sousTotal;
		}		
		return total;
	}


	@Override
	public void addArticle(Cart cart, Article article) {
		cart.getArticles().add(article);
		cart.setArticlesQuantity(cart.getArticlesQuantity()+article.getQuantity());
		cart.setPrice(cart.getPrice()+article.getPrice()*article.getQuantity());
	}

	@Override
	public Article getArticle(Cart cart, int index) {
		ArrayList<Article> articles = new ArrayList<>(cart.getArticles());
		return articles.get(index);
	}

	@Override
	public void removeArticle(Cart cart, int index) {
		Article article = this.getArticle(cart, index);
		cart.getArticles().remove(article);
		cart.setArticlesQuantity(cart.getArticlesQuantity()-article.getQuantity());
		cart.setPrice(cart.getPrice()-article.getPrice()*article.getQuantity());
	}

	@Override
	public boolean enoughInStock(Article article, Product product) {
		boolean inStock = false;
		for (Stock stock : product.getStocks()) {
			if (stock.getSize().getLabel().equals(article.getSize().getLabel())){
				if (stock.getQuantity()>=article.getQuantity()) {
					inStock=true;
				}
			}
		}
		return inStock;
	}
	 */
}
