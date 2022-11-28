package com.example.AUTOKER3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.AUTOKER3.beans.Car;
import com.example.AUTOKER3.controllers.HomeController;
import com.example.AUTOKER3.database.DatabaseAccess;
import com.example.AUTOKER3.login.AuthenticationService;

@SpringBootTest
class Autoker3ApplicationTests {
	
	private Car car=new Car();
	
	@Autowired
	private HomeController homeController=new HomeController();
	
	@Autowired
	private DatabaseAccess databaseAccess=new DatabaseAccess();
	
	@Autowired
	private AuthenticationService authenticationService=new AuthenticationService();

	@Test
	public void testDealerShipsValue() {
		String[] dealership = car.getDealerships();
//		System.out.println(Arrays.toString(dealership));
		int length=dealership.length;
		
		assertEquals(3, length);
	}
	
	@Test
	public void testColourGetAndSetMethods() {
		car.setColour("Black");
		
		assertEquals("Black", car.getColour());
	}
	
	@Test
	public void testVinGetAndSetMethods() {
		car.setVin("12345ABC");
		String vin = car.getVin();
//		System.out.println(vin);
		assertNotNull(vin);
	}
	
	@Test
	public void testHomeControllerNotNull() {
		assertThat(homeController).isNotNull();
		assertThat(databaseAccess).isNotNull();
		assertThat(authenticationService).isNotNull();
	}
	
	@Test
	public void testAuthenticationService() {
		boolean authenticate1 = authenticationService.authenticate("username", "password");
		boolean authenticate2 = authenticationService.authenticate("dummy", "test");
		assertTrue(authenticate1);
		assertFalse(authenticate2);
	}
	
}
