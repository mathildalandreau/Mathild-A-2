package fr.eql.al35.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return (List<Product>)productRepository.listAvailableProducts();
	}


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
		Product recupProduct = productRepository.findById(id).get();
		product.setId(id);
		product.setRefCreationDate(recupProduct.getRefCreationDate());
		product.setRefDeletionDate(recupProduct.getRefDeletionDate());
		product.setDimension(recupProduct.getDimension());
		product.setPattern(recupProduct.getPattern());
		product.setCustomsProducts(recupProduct.getCustomsProducts());
		product.setPhotos(recupProduct.getPhotos());
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		//LocalDateTime formatDateTime = LocalDateTime.parse(now, formatter);
		return productRepository.save(product);
	}


	@Override
	public void setDeleteProduct(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			product.get().setRefDeletionDate(LocalDateTime.now());
		}
		productRepository.save(product.get());
	}

	@Override
	public void setUndeleteProduct(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			product.get().setRefDeletionDate(null);
		}
		productRepository.save(product.get());
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
