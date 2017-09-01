package imdb.controller;

import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import imdb.bindingModel.FilmBindingModel;
import imdb.entity.Film;
import imdb.repository.FilmRepository;

import java.util.List;

@Controller
public class FilmController {

	private final FilmRepository filmRepository;

	@Autowired
	public FilmController(FilmRepository filmRepository) {
		this.filmRepository = filmRepository;
	}

	@GetMapping("/")
	public String index(Model model) {
		List<Film> films = filmRepository.findAll();

		model.addAttribute("films", films);
		model.addAttribute("view", "film/index");

		return "base-layout";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("view","film/create");
		return "base-layout";
	}

	@PostMapping("/create")
	public String createProcess(Model model, FilmBindingModel filmBindingModel) {
		Film filmToAdd = new Film(
				filmBindingModel.getName(),
				filmBindingModel.getDirector(),
				filmBindingModel.getGenre(),
				filmBindingModel.getYear());
		filmRepository.saveAndFlush(filmToAdd);
			return "redirect:/";

	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		Film filmToEdit = filmRepository.findOne(id);
		model.addAttribute("film",filmToEdit);
		model.addAttribute("view","film/edit");
		return "base-layout";

	}

	@PostMapping("/edit/{id}")
	public String editProcess(Model model, @PathVariable int id, FilmBindingModel filmBindingModel) {
		Film filmToEdit = filmRepository.findOne(id);
		filmToEdit.setDirector(filmBindingModel.getDirector());
		filmToEdit.setGenre(filmBindingModel.getGenre());
		filmToEdit.setName(filmBindingModel.getName());
		filmToEdit.setYear(filmBindingModel.getYear());

		filmRepository.saveAndFlush(filmToEdit);
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable int id) {

		Film film = filmRepository.findOne(id);
		if(film == null){
			return "redirect:/";
		}
		model.addAttribute("film",film);
		model.addAttribute("view","film/delete");
		return "base-layout";
	}

	@PostMapping("/delete/{id}")
	public String deleteProcess(@PathVariable int id) {

		Film film = filmRepository.findOne(id);
		if(filmRepository.exists(film.getId())){
			filmRepository.delete(film);
		}
		return "redirect:/";
	}
}
