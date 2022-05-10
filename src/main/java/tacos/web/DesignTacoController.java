package tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@Service
public class DesignTacoController {
//	@ModelAttribute
////	public void addIngredientsToModel(Model model) {
////		List<Ingredient> ingredients = Arrays.asList(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
////				new Ingredient("COTO", "Corn Tortilla", Type.WRAP), new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
////				new Ingredient("CARN", "Carnitas", Type.PROTEIN),
////				new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES), new Ingredient("LETC", "Lettuce", Type.VEGGIES),
////				new Ingredient("CHED", "Cheddar", Type.CHEESE), new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
////				new Ingredient("SLSA", "Salsa", Type.SAUCE), new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
////		Type[] types = Ingredient.Type.values();
////		for (Type type : types) {
////			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
////		}
////	}
//	private final IngredientRepository ingredientRepo;
//
//	@GetMapping
//	public String showDesignForm(Model model) {
//		model.addAttribute("taco", new Taco());
//		return "design";
//	}
//
////	@PostMapping
////	public String processDesign(Taco taco) {
////		// Save the taco design...
////		// We'll do this later
////		log.info("Processing design: " + taco);
////		return "redirect:/orders/current";
////	}
//	@PostMapping
//	public String processDesign(@Valid Taco taco, Errors errors) {
//	if (errors.hasErrors()) {
//	return "design";
//	}
//	// Save the taco design...
//	// We'll do this in later
//	log.info("Processing design: " + taco);
//	return "redirect:/orders/current";
//	}
//
//	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
//		List<Ingredient> ingrList = new ArrayList<Ingredient>();
//		for (Ingredient ingredient : ingredients) {
//			if (ingredient.getType().equals(type))
//				ingrList.add(ingredient);
//		}
//		return ingrList;
//	}
	@Autowired
	private IngredientRepository ingredientRepo;
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private TacoRepository tacoRepo;
//	private TacoRepository designRepo;

	
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(ingredients::add);
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}
	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));

		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}

		return "design";
	}
	// end::showDesignForm[]

	// tag::processDesign[]
	@PostMapping
	public String processDesign(@Valid Taco taco, Errors errors) {
		if (errors.hasErrors()) {
			return "design";
		}
		
		Taco saved = tacoRepo.save(taco);
		orderRepo.save(null);
		log.info("Processing design: " + taco);
		return "redirect:/orders/current";
		
	}
	

	// end::processDesign[]

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}

	/*
	 * //tag::classShell[]
	 * 
	 * ...
	 * 
	 * //end::classShell[]
	 */
	// tag::classShell[]
}
