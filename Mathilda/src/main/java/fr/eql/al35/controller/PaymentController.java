package fr.eql.al35.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eql.al35.dto.Colis;
import fr.eql.al35.dto.Tarif;
import fr.eql.al35.entity.Cart;
import fr.eql.al35.entity.Command;
import fr.eql.al35.entity.User;
import fr.eql.al35.iservice.AccountIService;
import fr.eql.al35.iservice.ColisService;
import fr.eql.al35.iservice.CommandIService;

@Controller
public class PaymentController {

	static Logger log = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	CommandIService cmdService;

	@Autowired
	AccountIService accountService;

	@Autowired
	ColisService colisServiceDelegate;

	@GetMapping("/tarifs")
	public String displayTarifs(Model model) {
		try {
			Colis colis = new Colis();
			colis.setInitialWeight(0.6); //poids de la commande en dur en dur
			colis = colisServiceDelegate.getPoids(colis); //ICI OK ON A BIEN RECUP LE POIDS FINAL
			List<Tarif> tarifs = colisServiceDelegate.displayAllTarifs(colis.getFinalWeight()); 
			model.addAttribute("colis", colis);
			System.out.println("Mon colis:" + colis);
			model.addAttribute("tarifs", tarifs);
			System.out.println("taille liste tarifs : " + tarifs.size());
			System.out.println("un tarif " + tarifs.get(0).toString()); //ICI OK ON A BIEN RECUP LE TARIF
		} catch (Exception e) {
			System.out.println("WS transport HS ..?");
		}
		return "tarifs";
	}


	@GetMapping("/payment")
	public String displayPayment(Model model, HttpSession session) {
		Command command = new Command();
		model.addAttribute("command", command);
		return "payment";
	}

	@PostMapping("/newCommand") 
	public String createNewCommand(Model model, HttpSession session,
			@ModelAttribute("command") Command command) {
		Cart sessionCart = (Cart) session.getAttribute("sessionCart");
		User sessionUser = (User) session.getAttribute("sessionUser");

		//Floriane : nouvelle méthode pour créer une command, plus simple :
		command = cmdService.createCommand(sessionCart, sessionUser); 
		System.out.println("command : post Service : " + command.toString());
		cmdService.saveUser(sessionUser); 
		//Floriane : modif méthode service : à priori ne sert plus, tester avec le front
		//	cmdService.saveCommand(command); //stocker en BDD command et addresses

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			Thread.currentThread().interrupt();
		}
		return "redirect:home";
	}


}
