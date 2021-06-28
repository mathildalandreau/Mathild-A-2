package fr.eql.al35.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.al35.entity.Photo;
import fr.eql.al35.entity.Product;
import fr.eql.al35.entity.ProductType;
import fr.eql.al35.iservice.ProductIService;
import fr.eql.al35.repository.ProductIRepository;
import fr.eql.al35.repository.ProductTypeIRepository;

@Service
@Transactional
public class ProductService implements ProductIService {

	@Autowired
	private ProductIRepository productRepository;

	@Autowired
	private ProductTypeIRepository productTypeRepository;

	//Méthode vérifiée par Mathilda
	@Override
	public List<Product> displayAllProducts() {
		return (List<Product>) productRepository.findAll();
	}

	//Méthode vérifiée par Mathilda
	@Override
	public List<Product> displayAvailableProducts() {
	
		/*List<Product> listProducts = (List<Product>)productRepository.listAvailableProducts();
		int size = listProducts.size();
		for (int i = 0; i < size; i++) {
			Set<Photo> photos = sortedPhoto(listProducts.get(i).getPhotos());
			listProducts.get(i).setPhotos(photos);
			productRepository.save(listProducts.get(i));
		}
		*/
		return (List<Product>)productRepository.listAvailableProducts();
	}

	/*private Set<Photo> sortedPhoto(Set<Photo> photos){
		System.out.println("Liste photos avant treeset" + photos);
		
		for (Photo photo : photos) {
			
			
			
		}
		System.out.println("Liste photos après treeset" + listPhotos);
		
		
		
		return listPhotos;
	}*/
	
	//Méthode vérifiée par Mathilda
	@Override
	public Product displayProductById(int id) {
		Optional<Product> product = productRepository.findById(id);
		return product.isPresent() ? product.get() : null;
	}

	//Méthode vérifiée par Mathilda
	@Override
	public List<ProductType> displayAllCategories() {
		return (List<ProductType>)productTypeRepository.findAll();
	}

	//Méthode vérifiée par Mathilda
	@Override
	public List<Product> displayByProductType(ProductType productType) {
		return (List<Product>) productRepository.findByProductType(productType);
	}

	//Méthode ADMIN NON PRIORITAIRE 
	@Override
	public Product upDate(Integer id, Product product) {
		product.setId(id);
		String now = "2021-01-01 10:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime formatDateTime = LocalDateTime.parse(now, formatter);
		product.setRefCreationDate(formatDateTime);
		return productRepository.save(product);
	}

	//Méthode ADMIN NON PRIORITAIRE 
	@Override
	public void setDeleteProduct(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			product.get().setRefDeletionDate(LocalDateTime.now());
		}
	}

	//Méthode ADMIN NON PRIORITAIRE 
	@Override
	public Product addProduct(Product product) {
		/* ancien code Favori(te)
		product.setRefCreationDate(LocalDateTime.now());
		Set<Photo> photos = new HashSet<Photo>();
		Photo photoPantalon = new Photo();
		photoPantalon.setPath("PANTALON_BEIGE_1.jpg");
		photoPantalon.setDescription("PANTALON_BEIGE_1");
		photos.add(photoPantalon);
		product.setPhotos(photos);
		return productRepository.save(product);
		 */
		return null;
	}
}
