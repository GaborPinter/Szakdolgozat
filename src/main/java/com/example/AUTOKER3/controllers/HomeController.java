package com.example.AUTOKER3.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.AUTOKER3.beans.Car;
import com.example.AUTOKER3.database.DatabaseAccess;
import com.example.AUTOKER3.service.EmailSenderService;

@Controller
public class HomeController {

	@Autowired
	private DatabaseAccess dataObj;

	@Autowired
	private EmailSenderService senderService;

	/**
	 * Directs to home page of application
	 * 
	 * @return A HTML page which acts as home page
	 */
//	@GetMapping("/")
//	public String goHome(Model model)
//	{
//		return "home.html";
//	}

	/**
	 * Directs to add page through which user can add a car
	 * 
	 * @return A HTML page which acts as add car page
	 */
	@GetMapping("/list")
	public String goToAddCarPage(Model model) {
		model.addAttribute("car", new Car());
		return "addCar.html";
	}

	@GetMapping("/contact")
	public String goToContactCarPage(Model model) {
		return "contact.html";
	}

	@GetMapping("/mail")
	public String goToContactMailCarPage(Model model, @RequestParam String name, @RequestParam String telNumber,
			@RequestParam String subject, @RequestParam String message) throws MessagingException {

		senderService.sendSimpleEmail(name+ " " +telNumber + " " + subject, message);
		return "mail.html";
	}
	
	@GetMapping("/logout")					//jobbra kene igazitani
	public String goToLoginCarPage(Model model) {
		return "home.html";
	}

	/**
	 * Adds the car to the database
	 * 
	 * @return A HTML page which acts as add car page
	 */
	@GetMapping("/add")
	public String addCar(Model model, @ModelAttribute Car car) {
		dataObj.addCar(car);
		model.addAttribute("car", new Car());
		return "addCar.html";
	}

	/**
	 * Views/Returns all cars present in database
	 * 
	 * @return A HTML page which acts as view car page
	 */
	@GetMapping("/view")
	public String viewCars(Model model) {
		model.addAttribute("carsFromD1", dataObj.getCars("Eagle_Garage"));
		model.addAttribute("carsFromD2", dataObj.getCars("Giant_Garage"));
		model.addAttribute("carsFromD3", dataObj.getCars("Super_Garage"));
		return "viewCar.html";
	}

	/**
	 * Directs to edit car page for editing the properties of car
	 * 
	 * @return A HTML page which acts as edit car page
	 */
	@GetMapping("/edit/{dealership}/{id}")
	public String goToEditPage(Model model, @PathVariable String dealership, @PathVariable int id) {
		Car car = dataObj.getCarById(id, dealership);
		car.setNewDealership(dealership);
		model.addAttribute("car", car);
		return "editCar.html";
	}

	/**
	 * Saves the edited properties of a car to database
	 * 
	 * @return (Redirects to) A HTML page which acts as view car page
	 */
	@GetMapping("/edit")
	public String editCar(Model model, @ModelAttribute Car car) {
		boolean checkTransfer = dataObj.editCar(car);
		if (checkTransfer == true) {
			dataObj.deleteCar(car.getId(), car.getDealership());
		}
		return "redirect:/view";
	}

	/**
	 * Delete the car from database
	 * 
	 * @return (Redirects to) A HTML page which acts as view car page
	 */
	@GetMapping("/delete/{dealership}/{id}")
	public String deleteCar(Model model, @PathVariable String dealership, @PathVariable int id) {
		dataObj.deleteCar(id, dealership);
		return "redirect:/view";
	}

	/**
	 * Directs to search page for searching a car
	 * 
	 * @return A HTML page which acts as search car page
	 */
	@GetMapping("/search")
	public String goToSearchPage() {
		return "searchCar.html";
	}

	/**
	 * Searches the car by ID and returns the result
	 * 
	 * @return A HTML page which acts as view car page
	 */

	// ----------------------------------------------------------------------------------------------

	/**
	 * Searches the car by price range (min-max) and returns the result
	 * 
	 * @return A HTML page which acts as view car page
	 */
	@GetMapping("/searchParameters")
	public String searchCarPrice(Model model, @RequestParam String make, @RequestParam String modelForCar,
			@RequestParam String min, @RequestParam String max) {
		boolean makeEmpty = make.isEmpty();
		boolean modelEmpty = modelForCar.isEmpty();

		String makeValue = make.toString();
//		System.out.println(makeValue);

		String modelValue = modelForCar.toString();
//		System.out.println(modelValue);

		// search by make
		ArrayList<Car> searchCarByMakeFromD1 = dataObj.searchCarByMake(make, "Eagle_Garage");
		ArrayList<Car> searchCarByMakeFromD2 = dataObj.searchCarByMake(make, "Giant_Garage");
		ArrayList<Car> searchCarByMakeFromD3 = dataObj.searchCarByMake(make, "Super_Garage");

		// search by model
		ArrayList<Car> searchCarByModelFromD1 = dataObj.searchCarByModel(modelForCar, "Eagle_Garage");
		ArrayList<Car> searchCarByModelFromD2 = dataObj.searchCarByModel(modelForCar, "Giant_Garage");
		ArrayList<Car> searchCarByModelFromD3 = dataObj.searchCarByModel(modelForCar, "Super_Garage");

		if (min.equals("")) {
			min = "0";
		}

		if (max.equals("")) {
			max = "1000000";
		}

		// search by price
		ArrayList<Car> searchCarByPriceFromD1 = dataObj.searchCarByPrice(Double.valueOf(min), Double.valueOf(max),
				"Eagle_Garage");
		ArrayList<Car> searchCarByPriceFromD2 = dataObj.searchCarByPrice(Double.valueOf(min), Double.valueOf(max),
				"Giant_Garage");
		ArrayList<Car> searchCarByPriceFromD3 = dataObj.searchCarByPrice(Double.valueOf(min), Double.valueOf(max),
				"Super_Garage");

		ArrayList<Car> listaD1 = dataObj.getCars("Eagle_Garage");
//		System.out.println(listaD1.size());

		List<Car> collectAllD1 = new ArrayList<>();

		if (!makeValue.equals("") && !modelValue.equals("")) {
			for (int i = 0; i < listaD1.size(); i++) {
				if (listaD1.get(i).getMake().equals(makeValue) && listaD1.get(i).getModel().equals(modelValue)
						&& listaD1.get(i).getPrice() > Integer.valueOf(min)
						&& listaD1.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD1.add(listaD1.get(i));
				}
			}
		}

		if (makeValue.equals("") && !modelValue.equals("")) {
			for (int i = 0; i < listaD1.size(); i++) {
				if (listaD1.get(i).getModel().equals(modelValue) && listaD1.get(i).getPrice() > Integer.valueOf(min)
						&& listaD1.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD1.add(listaD1.get(i));
				}
			}
		}

		if (!makeValue.equals("") && modelValue.equals("")) {
			for (int i = 0; i < listaD1.size(); i++) {
				if (listaD1.get(i).getMake().equals(makeValue) && listaD1.get(i).getPrice() > Integer.valueOf(min)
						&& listaD1.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD1.add(listaD1.get(i));
				}
			}
		}

		if (makeValue.equals("") && modelValue.equals("")) {
			for (int i = 0; i < listaD1.size(); i++) {
				if (listaD1.get(i).getPrice() > Integer.valueOf(min)
						&& listaD1.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD1.add(listaD1.get(i));
				}
			}
		}

		ArrayList<Car> listaD2 = dataObj.getCars("Giant_Garage");
		List<Car> collectAllD2 = new ArrayList<>();

		if (!makeValue.equals("") && !modelValue.equals("")) {
			for (int i = 0; i < listaD2.size(); i++) {
				if (listaD2.get(i).getMake().equals(makeValue) && listaD2.get(i).getModel().equals(modelValue)
						&& listaD2.get(i).getPrice() > Integer.valueOf(min)
						&& listaD2.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD2.add(listaD2.get(i));
				}
			}
		}

		if (makeValue.equals("") && !modelValue.equals("")) {
			for (int i = 0; i < listaD2.size(); i++) {
				if (listaD2.get(i).getModel().equals(modelValue) && listaD2.get(i).getPrice() > Integer.valueOf(min)
						&& listaD2.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD2.add(listaD2.get(i));
				}
			}
		}

		if (!makeValue.equals("") && modelValue.equals("")) {
			for (int i = 0; i < listaD2.size(); i++) {
				if (listaD2.get(i).getMake().equals(makeValue) && listaD2.get(i).getPrice() > Integer.valueOf(min)
						&& listaD2.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD2.add(listaD2.get(i));
				}
			}
		}

		if (makeValue.equals("") && modelValue.equals("")) {
			for (int i = 0; i < listaD2.size(); i++) {
				if (listaD2.get(i).getPrice() > Integer.valueOf(min)
						&& listaD2.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD2.add(listaD2.get(i));
				}
			}
		}

		ArrayList<Car> listaD3 = dataObj.getCars("Super_Garage");
		List<Car> collectAllD3 = new ArrayList<>();

		if (!makeValue.equals("") && !modelValue.equals("")) {
			for (int i = 0; i < listaD3.size(); i++) {
				if (listaD3.get(i).getMake().equals(makeValue) && listaD3.get(i).getModel().equals(modelValue)
						&& listaD3.get(i).getPrice() > Integer.valueOf(min)
						&& listaD3.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD3.add(listaD3.get(i));
				}
			}
		}

		if (makeValue.equals("") && !modelValue.equals("")) {
			for (int i = 0; i < listaD3.size(); i++) {
				if (listaD3.get(i).getModel().equals(modelValue) && listaD3.get(i).getPrice() > Integer.valueOf(min)
						&& listaD3.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD3.add(listaD3.get(i));
				}
			}
		}

		if (!makeValue.equals("") && modelValue.equals("")) {
			for (int i = 0; i < listaD3.size(); i++) {
				if (listaD3.get(i).getMake().equals(makeValue) && listaD3.get(i).getPrice() > Integer.valueOf(min)
						&& listaD3.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD3.add(listaD3.get(i));
				}
			}
		}

		if (makeValue.equals("") && modelValue.equals("")) {
			for (int i = 0; i < listaD3.size(); i++) {
				if (listaD3.get(i).getPrice() > Integer.valueOf(min)
						&& listaD3.get(i).getPrice() < Integer.valueOf(max)) {
					collectAllD3.add(listaD3.get(i));
				}
			}
		}

		model.addAttribute("carsFromD1", collectAllD1);
		model.addAttribute("carsFromD2", collectAllD2);
		model.addAttribute("carsFromD3", collectAllD3);
		return "viewCar.html";
	}

	// -------------------------------------------------------------------------------------------

	/**
	 * Return a receipt page of the car and delete the car from database
	 * 
	 * @return A HTML page which acts as receipt page for car
	 */
	@GetMapping("/purchase/{dealership}/{id}")
	public String purchaseCar(Model model, @PathVariable String dealership, @PathVariable int id) {
		Car car = dataObj.getCarById(id, dealership);

//		DecimalFormat decimalFormat = new DecimalFormat("#########.##");
		Double taxes = car.getPrice() * 0.13;
		if (String.valueOf(taxes).length() > 7) {
			String substring = taxes.toString().substring(0, 7);
			taxes = Double.valueOf(substring);
		}

		String value = String.valueOf(car.getPrice() + taxes);
		Double price = Double.valueOf(value.substring(0, 6));

		car.setPrice(price);

		model.addAttribute("car", car);
		model.addAttribute("taxes", taxes);
		dataObj.deleteCar(id, dealership);
		return "receipt.html";
	}
}
