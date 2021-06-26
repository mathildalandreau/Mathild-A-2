package fr.eql.al35.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.al35.entity.Cart;
import fr.eql.al35.entity.Command;
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

	@Override
	public Command createCommand(Cart cart) {
		Command command = new Command();
		setInfosCommand(command);


		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 	@Override
	public Command createCommand(Cart cart, Command command) {
		command.setArticles(cart.getArticles());
		command.setTaxOutPrice(cart.getPrice());
		command.setTaxOutPrice((double) (Math.round(cart.getPrice()*100) / 100));
		return command;
	}
	 */

	@Override
	public Command saveCommand(Command command) {
		// TODO Auto-generated method stub
		return null;
	}


	private void setInfosCommand(Command command) {
		Optional<Vat> vat = vatRepo.findById(5); //en dur global pour la command, a modifier pour chaque article plus tard
		if(vat.isPresent()) {
			command.setVat(vat.get());
			command.setTaxInPrice(command.getTaxOutPrice() + command.getTaxOutPrice()*vat.get().getRate());
			command.setCreationDate(LocalDateTime.now());
			Optional<PayMode> paymode = payModeRepo.findById(1);
			command.setPayMode(paymode.isPresent() ? paymode.get() : null);
			Optional<Status> status = statusRepo.findById(1);
			command.setStatus(status.isPresent() ? status.get() : null);
		}
	}

	/*
	 * 	@Override
	public Command saveCommand(Command command) {
		setInfosCommand(command);
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

	@Override
	public List<Command> findByUser(Integer user) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 	@Override
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
	 */

	@Override
	public Command displaybyId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * 	@Override
	public Command displaybyId(Integer id) {
		Optional<Command> command = cmdRepo.findById(id);
		return command.isPresent() ? command.get() : null;
	}
	 */

	@Override
	public List<Command> displayAllCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 	@Override
	public List<Command> displayAllCommands() {
		return (List<Command>) cmdRepo.findAll();
	}
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

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub

	}
	/*
	@Override
	public void saveUser(User user) {
		userRepo.save(user);
	}
	 */

	@Override
	public Command createCommand(Cart cart, Command command) {
		// TODO Auto-generated method stub
		return null;
	}


}
