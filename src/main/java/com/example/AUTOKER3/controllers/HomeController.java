package com.example.AUTOKER3.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.AUTOKER3.beans.Car;
import com.example.AUTOKER3.beans.Role;
import com.example.AUTOKER3.database.DatabaseAccess;
import com.example.AUTOKER3.service.EmailSenderService;
import com.example.AUTOKER3.service.UserServiceImpl;

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
	@GetMapping("/")
	public String gotoWelcomePage() {
		return "loginPage.html";
	}

	@GetMapping("/loginPage")
	public String login() {
		return "loginPage.html";
	}

	@GetMapping("/home")
	public String gotoHomePage() {
		if (userOrAdmin().equals("ROLE_USER")) {
			return "home.html";
		} else if(userOrAdmin().equals("ROLE_COMPANY")) {
			return "companyHome.html";
		} else {
			return "adminHome.html";
		}

	}

	/**
	 * Directs to add page through which user can add a car
	 * 
	 * @return A HTML page which acts as add car page
	 */
	@GetMapping("/list")
	public String goToAddCarPage(Model model) {
		model.addAttribute("car", new Car());
		if (userOrAdmin().equals("ROLE_USER")) {
			return "addCar.html";
		}else if(userOrAdmin().equals("ROLE_COMPANY")) {
			return "companyAddCar.html";
		} else {
			return "adminAddCar.html";
		}

	}

	@GetMapping("/contact")
	public String goToContactCarPage(Model model) {
		if (userOrAdmin().equals("ROLE_USER")) {
			return "contact.html";
		} else {
			return "companyContact.html";
		}
		
	}

	@GetMapping("/mail")
	public String goToContactMailCarPage(Model model, @RequestParam String name, @RequestParam String telNumber,
			@RequestParam String subject, @RequestParam String message) throws MessagingException {

		senderService.sendSimpleEmail(name + " " + telNumber + " " + subject, message);
		return "mail.html";
	}

	@GetMapping("/logout") // jobbra kene igazitani
	public String goToLoginCarPage(Model model) {
		return "loginPage.html";
	}

	/**
	 * Adds the car to the database
	 * 
	 * @return A HTML page which acts as add car page
	 */
	@RequestMapping(path = "/add", method=RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String addCar(Model model, @ModelAttribute Car car) {

//		Object attribute = model.getAttribute("file");
//		System.out.println(attribute);
//		car.setFile("FILE"+car.getVin());

		dataObj.addCar(car);
		model.addAttribute("car", new Car());
		
		if (userOrAdmin().equals("ROLE_USER")) {
			return "addCar.html";
		} else if(userOrAdmin().equals("ROLE_COMPANY")) {
			return "companyAddCar.html";
		} else {
			return "adminAddCar.html";
		}
	}

	/**
	 * Views/Returns all cars present in database
	 * 
	 * @return A HTML page which acts as view car page
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@GetMapping("/view")
	public String viewCars(Model model) {

		model.addAttribute("carsFromD1", dataObj.getCars("Eagle_Garage"));
		model.addAttribute("carsFromD2", dataObj.getCars("Giant_Garage"));
		model.addAttribute("carsFromD3", dataObj.getCars("Super_Garage"));

		if (userOrAdmin().equals("ROLE_USER")) {
			return "viewCar.html";
		}else if(userOrAdmin().equals("ROLE_COMPANY")) {
			return "companyViewCar.html";
		}  else {
			return "adminViewCar.html";
		}

	}

	@GetMapping("/map")
	public String goToMap(Model model) {
		if (userOrAdmin().equals("ROLE_USER")) {
			return "map.html";
		} else if(userOrAdmin().equals("ROLE_COMPANY")) {
			return "companyMap.html";
		} else {
			return "adminMap.html";
		}
	}

	private String userOrAdmin() {
		String name = "";
		Collection<Role> roles = UserServiceImpl.roles;
		for (Role role : roles) {
			name = role.getName();
		}
		return name;
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
		if (userOrAdmin().equals("ROLE_USER")) {
			return "searchCar.html";
		} else if(userOrAdmin().equals("ROLE_COMPANY")) {
			return "companySearchCar.html";
		}  else {
			return "adminSearchCar.html";
		}
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

		if (userOrAdmin().equals("ROLE_USER")) {
			return "viewCar.html";
		} else if(userOrAdmin().equals("ROLE_COMPANY")) {
			return "companyViewCar.html";
		} else {
			return "adminViewCar.html";
		}
		
	}

	// -------------------------------------------------------------------------------------------

	/**
	 * Return a receipt page of the car and delete the car from database
	 * 
	 * @return A HTML page which acts as receipt page for car
	 */
	@GetMapping("/info/{dealership}/{id}")
	public String infoCar(Model model, @PathVariable String dealership, @PathVariable int id) {
		Car car = dataObj.getCarById(id, dealership);

		Double taxes = car.getPrice() * 0.13;
		BigDecimal bd1 = new BigDecimal(taxes).setScale(2, RoundingMode.HALF_UP);
		double newTaxes = bd1.doubleValue();

		String value = String.valueOf(car.getPrice() + taxes);
		Double price = Double.valueOf(value);
		BigDecimal bd2 = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
	    double newPrice = bd2.doubleValue();

		car.setPrice(newPrice);
		model.addAttribute("car", car);
		model.addAttribute("taxes", newTaxes);
//		dataObj.deleteCar(id, dealership);
		
		if (userOrAdmin().equals("ROLE_USER")) {
			return "receipt.html";
		} else if(userOrAdmin().equals("ROLE_COMPANY")) {
			return "companyReceipt.html";
		} else {
			return "adminReceipt.html";
		}
		
	}
	
	@GetMapping("/buy/{dealership}/{id}")
	public String buyCar() {
		if (userOrAdmin().equals("ROLE_USER")) {
			return "PayPalHome.html";
		} else if(userOrAdmin().equals("ROLE_COMPANY")) {
			return "companyPayPalHome.html";
		} else {
			return "adminPayPalHome.html";
		}
	}
}
