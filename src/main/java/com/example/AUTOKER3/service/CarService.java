package com.example.AUTOKER3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AUTOKER3.beans.Car;
import com.example.AUTOKER3.repository.CarRepository;

@Service
public class CarService {

	@Autowired
	CarRepository carRepository;
	
	public List<Car> findAll(){
		return carRepository.findAll();
	}
	
	public void save(Car car) {
		carRepository.save(car);
	}
	
	
}
