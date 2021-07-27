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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eql.al35.dto.Colis;
import fr.eql.al35.dto.PointRelais;
import fr.eql.al35.dto.Tarif;
import fr.eql.al35.entity.Cart;
import fr.eql.al35.entity.Command;
import fr.eql.al35.entity.User;
import fr.eql.al35.iservice.AccountIService;
import fr.eql.al35.iservice.ColisService;
import fr.eql.al35.iservice.CommandIService;
import fr.eql.al35.iservice.PointRelaisService;


@Controller
public class PaymentController {

	static Logger log = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	CommandIService cmdService;

	@Autowired
	AccountIService accountService;

	@Autowired
	ColisService colisServiceDelegate;

	@Autowired
	PointRelaisService pointRelaisServiceDelegate;


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
		Tarif tarif = colisServiceDelegate.getTarif(idTarif);
		sessionCart.setSendingPrice(tarif.getPrice());
		sessionCart.setTransporteur(tarif.getTransporteur().getName());
		String urlPtRelaisAngular ="http://localhost:4200/";
		return "redirect:" + urlPtRelaisAngular; //ok ? 
	}

	//a changer

	@GetMapping("/payment/{id}")
	public String displayPayment(Model model, HttpSession session, @PathVariable Integer id) {
		Command command = new Command();
		PointRelais pointRelais = pointRelaisServiceDelegate.getPointRelais(id);
		model.addAttribute("command", command);
		Cart sessionCart = (Cart) session.getAttribute("sessionCart");
		sessionCart.setPointRelais(pointRelais);
		return "payment"; 
	}

	@PostMapping("/newCommand") 
	public String createNewCommand(Model model, HttpSession session,
			@ModelAttribute("command") Command command) {
		Cart sessionCart = (Cart) session.getAttribute("sessionCart");
		User sessionUser = (User) session.getAttribute("sessionUser");
		try {
			command.getDeliveryAddress().setUser(sessionUser);
			command.getFacturationAddress().setUser(sessionUser);
		} catch (Exception e) {
			System.out.println("paymentController, adresse non remplie");
		}
		command = cmdService.createCommand(command, sessionCart, sessionUser); 
		cmdService.saveUser(sessionUser); 
		model.addAttribute("sessionCart", new Cart());

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			Thread.currentThread().interrupt();
		}
		return "redirect:home";
	}

	//a changer

}
