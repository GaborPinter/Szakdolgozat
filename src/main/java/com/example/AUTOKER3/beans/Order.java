package com.example.AUTOKER3.beans;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
public class Order {

	private String price;
	private String currency;
	private String method;
	private String intent;
	private String description;
	
	public Order() {
	}
	
	public Order(String price, String currency, String method, String intent, String description) {
		super();
		this.price = price;
		this.currency = currency;
		this.method = method;
		this.intent = intent;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Order [price=" + price + ", currency=" + currency + ", method=" + method + ", intent=" + intent
				+ ", description=" + description + "]";
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currency, description, intent, method, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(currency, other.currency) && Objects.equals(description, other.description)
				&& Objects.equals(intent, other.intent) && Objects.equals(method, other.method)
				&& Objects.equals(price, other.price);
	}

	

	
	
	

}
