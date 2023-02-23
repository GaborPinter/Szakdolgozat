package com.example.AUTOKER3.beans;

public class Car {

	private int id;
	
	private String make;
	private String model;
	private String colour;
	private double price;
	
	private String vin;
	
	private String dealership;
	
	//To use for moving car from one dealership to another
	private String newDealership;
	
	//Dealership names
	private String[] dealerships = {"Eagle_Garage","Giant_Garage","Super_Garage"};
	
	public Car(){
		
	}
	
	public Car(int id, String make, String model, String colour, double price, String vin, String dealership,
			String newDealership, String[] dealerships) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.colour = colour;
		this.price = price;
		this.vin = vin;
		this.dealership = dealership;
		this.newDealership = newDealership;
		this.dealerships = dealerships;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getDealership() {
		return dealership;
	}

	public void setDealership(String dealership) {
		this.dealership = dealership;
	}

	public String getNewDealership() {
		return newDealership;
	}

	public void setNewDealership(String newDealership) {
		this.newDealership = newDealership;
	}

	public String[] getDealerships() {
		return dealerships;
	}

	public void setDealerships(String[] dealerships) {
		this.dealerships = dealerships;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", make=" + make + ", model=" + model + ", colour=" + colour + ", price=" + price
				+ ", vin=" + vin + ", dealership=" + dealership;
	}
	
	
	
}
