package fr.eql.al35.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.al35.entity.Cart;
import fr.eql.al35.entity.Command;
import fr.eql.al35.entity.CommandLine;
import fr.eql.al35.entity.PayMode;
import fr.eql.al35.entity.Status;
import fr.eql.al35.entity.User;
import fr.eql.al35.entity.Vat;
import fr.eql.al35.iservice.CommandIService;
import fr.eql.al35.repository.AddressIRepository;
import fr.eql.al35.repository.CityIRepository;
import fr.eql.al35.repository.CommandIRepository;
import fr.eql.al35.repository.CommandLineIRepository;
import fr.eql.al35.repository.CustomIRepository;
import fr.eql.al35.repository.PayModeIRepository;
import fr.eql.al35.repository.ProductIRepository;
import fr.eql.al35.repository.StatusIRepository;
import fr.eql.al35.repository.UserIRepository;
import fr.eql.al35.repository.VatIRepository;

@Service
@Transactional
public class CommandService implements CommandIService {

	//methodes services refaites par : Floriane

	@Autowired
	CommandIRepository cmdRepo;

	@Autowired
	CommandLineIRepository cmdLineRepo;

	@Autowired
	StatusIRepository statusRepo;

	@Autowired
	VatIRepository vatRepo;

	@Autowired
	AddressIRepository addressRepo;

	@Autowired
	CityIRepository cityRepo;

	@Autowired
	PayModeIRepository payModeRepo;

	@Autowired
	CustomIRepository customRepo;

	@Autowired
	UserIRepository userRepo;

	@Autowired
	ProductIRepository productRepo;

	//méthode publiques :

	@Override
	public Command createCommand(Cart cart, User sessionUser) {
		Command command = new Command();
		//reference
		command.setReference(writeReference(sessionUser));
		setCreationDateAndStatus(command); //creationDate et status
		command.setTaxOutPrice((double) (Math.round(cart.getPrice()*100) / 100)); //taxOutPrice
		setVatAndTaxInPrice(command); //vat and taxPrice
		command.setUser(sessionUser); //user
		setAddresses(command); // à implémenter: deliveryAddress and facturationAddress
		command.setCommandLines(cart.getCommandLines()); //commandLines
		cmdRepo.save(command); //enregistrer en BDD
		return command;
	}


	@Override
	public List<Command> findByUser(Integer user) {
		List<Command> allCommands = cmdRepo.findByUser(user);
		List<Command> filteredCommands = new ArrayList<Command>();
		for (Command command : allCommands) {
			if (command.getCreationDate().isBefore(LocalDateTime.now())) {
				filteredCommands.add(command);
			}
		}
		return filteredCommands;
	}


	@Override
	public Command displaybyId(Integer id) {
		Optional<Command> command = cmdRepo.findById(id);
		return command.isPresent() ? command.get() : null;
	}

	@Override
	public List<Command> displayAllCommands() {
		return (List<Command>) cmdRepo.findAll();
	}

	@Override
	public void saveUser(User user) {
		userRepo.save(user);
	}


	//méthode privées : 

	private void setCreationDateAndStatus(Command command) {
		command.setCreationDate(LocalDateTime.now());
		Optional<Status> status = statusRepo.findById(1);
		command.setStatus(status.isPresent() ? status.get() : null);
	}

	private void setAddresses(Command command) {
		//a implémenter 
	}


	private void setVatAndTaxInPrice(Command command) {
		Optional<PayMode> paymode = payModeRepo.findById(1);
		command.setPayMode(paymode.isPresent() ? paymode.get() : null);
		Optional<Vat> vat = vatRepo.findById(5); //en dur global pour la command, a modifier pour chaque article plus tard
		if(vat.isPresent()) {
			command.setVat(vat.get());
			command.setTaxInPrice(command.getTaxOutPrice() + command.getTaxOutPrice()*vat.get().getRate());			
		}
	}

	private String writeReference(User user) {
		StringBuilder reference = new StringBuilder();
		reference.append("CMD_");
		reference.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss")));
		reference.append("_Client_");
		reference.append(user.getId()); //a modif avec le n° Client en session
		return reference.toString();
	}

	@Override
	public Double calculateInitialWeight(Cart cart) {
		Double weight=0d;
		for (CommandLine commandLine : cart.getCommandLines()) {
			weight+= commandLine.getProduct().getDimension().getWeight() * commandLine.getProductQuantity();
		}
		return weight;
	}

	/*
	 * Pas encore retouchées :
	 */

	@Override
	public Command updateCommand(Command command) {
		// TODO Auto-generated method stub
		return null;
	}


	/*
	 * 	@Override
	public Command updateCommand(Command command) {
		addressRepo.save(command.getDeliveryAddress());
		addressRepo.save(command.getFacturationAddress());
		cityRepo.save(command.getDeliveryAddress().getCity());
		cityRepo.save(command.getFacturationAddress().getCity());
		payModeRepo.save(command.getPayMode());
		vatRepo.save(command.getVat());
		statusRepo.save(command.getStatus());
		return cmdRepo.save(command);
	}
	 */


	/*
	 * 	@Override
	public Command saveCommand(Command command) {
		articleRepo.saveAll(command.getArticles());	//créer les articles en BDD
		cmdRepo.save(command);
		for (Article article : command.getArticles()) {
			article.setCommand(command);
			updateStock(article);
		}
		articleRepo.saveAll(command.getArticles()); //update la cmd ds les articles		
		return command;
	}

	 */

}
