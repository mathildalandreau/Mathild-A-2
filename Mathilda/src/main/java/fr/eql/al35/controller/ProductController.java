package fr.eql.al35.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.eql.al35.entity.CommandLine;
import fr.eql.al35.entity.ProductType;
import fr.eql.al35.iservice.ColorIService;
import fr.eql.al35.iservice.ProductIService;

@Controller
public class ProductController {

	@Autowired
	private ProductIService productService;
	
	@Autowired
	private ColorIService colorService;


	@GetMapping("/products/all")
	public String displayAllProducts(Model model) {
		model.addAttribute("products", productService.displayAvailableProducts());
		model.addAttribute("categories", productService.displayAllCategories());
		ProductType productType = new ProductType();
		productType.setName("");
		model.addAttribute("productType", productType);
		return "showcase";
	}


	@GetMapping("/products/{category}/{id}")
	public String displayProduct(@PathVariable String category, @PathVariable Integer id, Model model) {
		
		CommandLine commandLine = new CommandLine();
		
		model.addAttribute("colors", colorService.displayAllColors());
		model.addAttribute("product", productService.displayProductById(id));
		model.addAttribute("categories", productService.displayAllCategories());
		model.addAttribute("commandLine", commandLine);
		return "productSheet";
	}

	@GetMapping("/products/{productType}")
	public String displayProductsByType(@PathVariable ProductType productType, Model model) {
		model.addAttribute("categories", productService.displayAllCategories());
		model.addAttribute("products", productService.displayByProductType(productType));
		model.addAttribute("productType", productType);
		return "showcase";
	}






}
