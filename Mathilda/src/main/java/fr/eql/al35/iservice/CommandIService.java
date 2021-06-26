package fr.eql.al35.iservice;

import java.util.List;

import fr.eql.al35.entity.Cart;
import fr.eql.al35.entity.Command;
import fr.eql.al35.entity.User;

public interface CommandIService {

	//methodes services refaites par : Floriane

	public Command createCommand(Cart cart, User sessionUser);
	public List<Command> findByUser(Integer user);
	public Command displaybyId(Integer id);
	List<Command> displayAllCommands();
	public void saveUser(User user);
	
	//Floriane : pas encore retouchée
	Command updateCommand(Command command);

	//Floriane : voir si c'est utile de les refaire ou non
	//public Command createCommand(Cart cart, Command command);
	//public Command saveCommand(Command command);
}
