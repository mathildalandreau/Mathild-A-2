package fr.eql.al35.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.al35.entity.Color;
import fr.eql.al35.iservice.ColorIService;
import fr.eql.al35.repository.ColorIRepository;

@Service
@Transactional
public class ColorService implements ColorIService {

	@Autowired
	ColorIRepository colorRepository;

	@Override
	public List<Color> displayAllColors() {
		return (List<Color>) colorRepository.findAll();
	}

}
