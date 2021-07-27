package fr.eql.al35.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.al35.entity.Address;
import fr.eql.al35.entity.Cart;
import fr.eql.al35.entity.City;
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
	public Command createCommand(Command command, Cart cart, User sessionUser) {
		command.setReference(writeReference(sessionUser)); //reference
		setCreationDateAndStatus(command); //creationDate et status
		command.setTaxOutPrice((double) (Math.round(cart.getPrice()*100) / 100)); //taxOutPrice
		setVatAndTaxInPrice(command); //vat and taxPrice
		command.setUser(sessionUser); //user
		command.setCommandLines(cart.getCommandLines()); //commandLines
		command.setSendingPrice(cart.getSendingPrice());
		command.setTransporteur(cart.getTransporteur());
		command.setFinalWeight(cart.getPoidsColis());
		//ajout point relais : 
		Address deliveryAdd = new Address();
		City city = new City();
		city.setName(cart.getPointRelais().getAdresse().getVille());
		city.setZipCode(cart.getPointRelais().getAdresse().getCodePostal());
		deliveryAdd.setStreet(cart.getPointRelais().getAdresse().getRue());
		cityRepo.save(city);
		deliveryAdd.setCity(city);
		command.setDeliveryAddress(deliveryAdd);
		command.setPointRelais(cart.getPointRelais().getName()); 
		
		cmdRepo.save(command); //enregistrer en BDD
		for (CommandLine cmdLine : cart.getCommandLines()) {
			cmdLine.setCommand(command);
			cmdLineRepo.save(cmdLine);
		}
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

	@Override
	public Command updateCommand(Command command, Status status) {
		System.out.println(status.toString());
		/*addressRepo.save(command.getDeliveryAddress());
		addressRepo.save(command.getFacturationAddress());
		cityRepo.save(command.getDeliveryAddress().getCity());
		cityRepo.save(command.getFacturationAddress().getCity());
		payModeRepo.save(command.getPayMode());
		vatRepo.save(command.getVat());*/
		command.setStatus(status);
		String statusString = status.toString();
		 switch (statusString) {
         case "En attente" :  ; 
                  break;
         case "Annulée" :  command.setCancelDate(LocalDateTime.now());;
                  break;
         case "Validée" :  ;
                  break;
         case "Envoyée" :  command.setShippingDate(LocalDateTime.now());
                  break;
         case "Reçue" :  command.setDeliveryDate(LocalDateTime.now());
                  break;
         case "Retour-envoyée" :  command.setReturnDate(LocalDateTime.now());
                  break;
         case "Retournée" :  command.setDeliveryReturnDate(LocalDateTime.now());
                  break;
		 }
		//mettre un switch case sur le status avec mise à jour de la date 
		
		//statusRepo.save(command.getStatus());
		return cmdRepo.save(command);

	}

}
