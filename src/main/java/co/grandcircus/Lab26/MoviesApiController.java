package co.grandcircus.Lab26;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.grandcircus.Lab26.dao.MovieDao;
import co.grandcircus.Lab26.entities.Movie;

@RestController
public class MoviesApiController {
	
	@Autowired
	private MovieDao dao;


	// movies and ?category and ?title
	@GetMapping("/movies")
	public List<Movie> listMovies(
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "title", required = false) String title) {

		if (category == null || category.isEmpty()) {

			if (title == null || title.isEmpty()) {
				return dao.findAll();
			} else {
				return dao.findByTitleContainsIgnoreCase(title);
			}
	} else {
			return dao.findByCategoryContainsIgnoreCase(category);
	}

	}


	// Id
	@GetMapping("/movies/{id}")
	public Movie getMovie(@PathVariable("id") Long id) {
		return dao.findById(id).get();
	}


	// RandomList // What is quantity?

	@GetMapping("/movies/random-list")
	public List<Movie> listRandomCat(@RequestParam(value = "quantity", required = false) int quantity) {

		int full = dao.findAll().size();

		List<Movie> random = new ArrayList<>();
		Movie movies = dao.findById((long) (Math.random() * full) + 1).get();

		for (int i = 0; i < quantity; i++) {
			random.add(movies);
			return random;
		}

	}

	// Random Id Category

	@GetMapping("/movies/random")
	public Movie getRandomMovieCat(@RequestParam(value = "category", required = false) String category) {
		int full = dao.findAll().size();
		int num = dao.findByCategoryContainsIgnoreCase(category).size();

		if (category == null || category.isEmpty()) {
			Movie random = dao.findById((long) (Math.random() * full) + 1).get();
			System.out.println(random);
			return random;


		} else {

			Movie m = dao.findById((long) (Math.random() * num) + 1).get();
			System.out.println(m);
			return m;
		}


	}
	 
	  // Category
	  
	  @GetMapping("/categories") 
	public List<String> listCategories() {
		List<String> stringCat = new ArrayList<>();
		List<Movie> movies = dao.findAll();
		for (Movie m : movies) {
			m.getCategory();
			stringCat.add(m.getCategory());
		}
		return stringCat;
	  }
	 
	/*
	 * // Random Id
	 * 
	 * @GetMapping("/movies/random") public Movie getRandomMovie() { int full =
	 * dao.findAll().size(); return dao.findById((long) (Math.random() * full) +
	 * 1).get(); }
	 */
}
