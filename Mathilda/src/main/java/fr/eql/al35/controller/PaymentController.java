package fr.eql.al35.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

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


	@GetMapping("/choixTransporteur")
	public String displayTransporteur(Model model, HttpSession session) {
		Cart sessionCart = (Cart) session.getAttribute("sessionCart");
		Colis colis = new Colis();
		Double initialWeight = cmdService.calculateInitialWeight(sessionCart);
		colis.setInitialWeight(initialWeight);
		colis = colisServiceDelegate.getPoids(colis);
		sessionCart.setPoidsColis(colis.getFinalWeight());
		// peut etre pas necessaire model.addAttribute("colis", colis);
		try {
			List<Tarif> tarifs = colisServiceDelegate.displayAllTarifs(colis.getFinalWeight()); 
			model.addAttribute("tarifs", tarifs);
		} catch (Exception e) {
			System.out.println("WS transport HS ..?");
		}
		return "choixTransporteur";
	}

	@PostMapping("/goToPayment")
	public String postMappingChoixTransporteur(Model model, HttpSession session,
			@RequestParam("idTarif") Integer idTarif,
			@ModelAttribute("tarifs") ArrayList<Tarif> tarifs) {
		Cart sessionCart = (Cart) session.getAttribute("sessionCart");
		System.out.println(" ds postMapping choixTransporteur : idTarif : " + idTarif);
		Tarif tarif = colisServiceDelegate.getTarif(idTarif);
		sessionCart.setSendingPrice(tarif.getPrice());
		sessionCart.setTransporteur(tarif.getTransporteur().getName());
		return "redirect:payment";
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

		command = cmdService.createCommand(sessionCart, sessionUser); 
		cmdService.saveUser(sessionUser); 

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			Thread.currentThread().interrupt();
		}
		return "redirect:home";
	}

	//temporaire pour tester, à enlever ensuite
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

}
