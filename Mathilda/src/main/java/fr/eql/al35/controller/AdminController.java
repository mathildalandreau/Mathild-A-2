package fr.eql.al35.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eql.al35.entity.Command;
import fr.eql.al35.entity.Photo;
import fr.eql.al35.entity.Product;
import fr.eql.al35.entity.Status;
import fr.eql.al35.entity.User;
import fr.eql.al35.iservice.AccountIService;
import fr.eql.al35.iservice.AdminIService;
import fr.eql.al35.iservice.CommandIService;
import fr.eql.al35.iservice.PhotoIService;
import fr.eql.al35.iservice.ProductIService;
import fr.eql.al35.repository.StatusIRepository;

@Controller
public class AdminController {

	@Autowired
	private ProductIService productService;

	@Autowired
	private CommandIService commandService;

	@Autowired
	private AdminIService adminService;

	@Autowired
	private AccountIService accountService;

	@Autowired
	private PhotoIService photoService;
	
	@Autowired
	private StatusIRepository statusRepository;

	@GetMapping("/admin/product")
	public String displayAdminProduct( Model model) {
		model.addAttribute("products", productService.displayAllProducts());
		return "adminProducts";
	}

	@GetMapping("/admin/users")
	public String displayUsers(Model model) {
		model.addAttribute("users", adminService.displayAllUsers());
		return "adminUsers";
	}

	@GetMapping("/admin/users/{id}")
	public String displayUser(@PathVariable Integer id, Model model) {	
		model.addAttribute("user", adminService.displayUser(id));
		model.addAttribute("genders", accountService.getAllGenders());
		model.addAttribute("userTypes", accountService.getAllUserTypes());
		return "adminUserInfo";
	}

	@PostMapping("/admin/user/{id}/unsubscribe")
	public String unsubscribeUser(@PathVariable Integer id, @ModelAttribute("user")User user, Model model) {
		user.setUnsubscribingDate(LocalDateTime.now());
		adminService.updateUser(user, id);
		return "redirect:/admin/users";
	}

	@PostMapping("/admin/user/{id}/subscribe")
	public String subscribeUser(@PathVariable Integer id, @ModelAttribute("user")User user, Model model) {
		user.setUnsubscribingDate(null);
		System.out.println(user.getUnsubscribingDate());
		adminService.updateUser(user, id);
		return "redirect:/admin/users";
	}

	@PostMapping("/updateUser/{id}")
	public String updateUser(@PathVariable Integer id, 
			@ModelAttribute("user")User user, 
			Model model) {
		adminService.updateUser(user, id);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/commands/{id}")
	public String displayCommand(@PathVariable Integer id, Model model) {
		model.addAttribute("command", commandService.displaybyId(id));
		return "adminCommandInfo";
	}

	@GetMapping("/admin/home")
	public String redirectAdminHome( Model model) {
		return "adminHome";
	}

	@PostMapping("/upDateProducts")
	public String upDateProducts(@ModelAttribute("product")Product product, @RequestParam("idProduct") Integer idProduct, Model model) {
		model.addAttribute("productTypes", productService.displayAllCategories());
		model.addAttribute("product", productService.upDate(idProduct, product));

		return "redirect:/admin/products/"+idProduct;
	}

	/* ancienne méthode Favori(te)
	@PostMapping("/upDateStock")
	public String upDateStock(@ModelAttribute("stock")Stock stock, @RequestParam("idStock") Integer idStock, @RequestParam("idProduct") Integer idProduct,
			@RequestParam String sizeLabel, Model model) {
		stock.setProduct(productService.displayProductById(idProduct));
		Size size = new Size();
		size.setLabel(sizeLabel);
		stock.setSize(size);
		stockService.upDate(idStock, stock);
		Product product = productService.displayProductById(idProduct);
		Integer quantity = 0;
		for (Stock s : product.getStocks()) {
			quantity+=s.getQuantity();
		}
		product.setQuantity(quantity);
		model.addAttribute("productTypes", productService.displayAllCategories());
		model.addAttribute("product", productService.upDate(idProduct, product));

		return "redirect:/admin/products/"+idProduct;
	}
	 */

	@PostMapping("/upDatePhotos")
	public String upDatePhoto(@ModelAttribute("photo")Photo photo, 
			@RequestParam("idPhoto") Integer idPhoto,
			@RequestParam("pathPhoto") String pathPhoto,
			@RequestParam("descriptionPhoto") String descriptionPhoto,
			@RequestParam("idProduct") Integer idProduct, 
			@RequestParam("index") Integer index,
			Model model) {
		model.addAttribute("productTypes", productService.displayAllCategories());
		photoService.upDatePhoto(idPhoto, pathPhoto, descriptionPhoto, idProduct, index);
		model.addAttribute("product", productService.displayProductById(idProduct));
		return "adminProductInfo";
	}

	/*@GetMapping("/admin/command")
	public String displayAdminCommand( Model model) {
		model.addAttribute("status", new Status());
		model.addAttribute("commands", commandService.displayAllCommands());
		model.addAttribute("statusRef", adminService.displayAllStatus());
		model.addAttribute("vatRef", adminService.displayAllVats());
		model.addAttribute("payModeRef", adminService.displayAllPayModes());

		return "adminCommand";
	}*/

	/*
	@PostMapping("/upDateCommands")
	public String upDateCommands(@ModelAttribute("command")Command command, Model model) {
		commandService.updateCommand(command);
		model.addAttribute("command", commandService.updateCommand(command));
		model.addAttribute("commands", commandService.displayAllCommands());
		model.addAttribute("statusRef", adminService.displayAllStatus());
		model.addAttribute("vatRef", adminService.displayAllVats());
		model.addAttribute("payModeRef", adminService.displayAllPayModes());
		return "adminCommand";
	}
*/

	@GetMapping("/admin/products/{id}")
	public String displayProduct(@PathVariable Integer id, Model model) {
	
		model.addAttribute("product", productService.displayProductById(id));
		model.addAttribute("productTypes", productService.displayAllCategories());
		model.addAttribute("index", 0);
		return "adminProductInfo";
	}
	

	@GetMapping("/admin/products/delete/{id}")
	public String deleteProduct(@PathVariable Integer id, Model model) {
		model.addAttribute("products", productService.displayAllProducts());
		productService.setDeleteProduct(id);
		return "adminProducts";
	}
	
	@GetMapping("/admin/products/undelete/{id}")
	public String undeleteProduct(@PathVariable Integer id, Model model) {
		model.addAttribute("products", productService.displayAllProducts());
		productService.setUndeleteProduct(id);
		return "adminProducts";
	}

	@GetMapping("/admin/product/add")
	public String adminAddProduct( Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		model.addAttribute("productTypes", productService.displayAllCategories());
		return "adminAddProduct";
	}

	@PostMapping("/addProduct")
	public String addProduct(@ModelAttribute("product")Product product, Model model) {	
		productService.addProduct(product);
		model.addAttribute("products", productService.displayAllProducts());
		return "adminProducts";
	}
	
	@GetMapping("/adminMyOrders")
	public String userCommands(Model model) {
		model.addAttribute("leStatus", new Status());
		List<Command> commands = commandService.displayAllCommands();
		commands.sort((o2,o1) -> o1.getCreationDate().compareTo(o2.getCreationDate()));
		model.addAttribute("lesStatus", statusRepository.findAll());
		model.addAttribute("commands", commands);
		return "adminMyOrders";
	}
	

	@PostMapping("/upDateStatus")
	public String updateCommand(Model model, @RequestParam("idCommand") Integer id, @RequestParam("idStatus") Integer idstatus) {
		System.out.println("dans updateCommand id statut " + idstatus);
		System.out.println(id);
		Command command = commandService.displaybyId(id);
		//commandService.updateCommand(command, status);
		model.addAttribute("commands", commandService.displayAllCommands());
		model.addAttribute("lesStatus", statusRepository.findAll());
		return "adminMyOrders";
	}

}
