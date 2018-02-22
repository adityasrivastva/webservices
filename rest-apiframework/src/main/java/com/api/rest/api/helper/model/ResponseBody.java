package com.api.rest.api.helper.model;

import java.util.ArrayList;
import java.util.List;



public class ResponseBody {
	public String BrandName;
	public String Id;
	public String LaptopName;
	public Features Features;
	
	public String getBrandName() {
		return BrandName;
	}
	public void setBrandName(String brandName) {
		BrandName = brandName;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getLaptopName() {
		return LaptopName;
	}
	public void setLaptopName(String laptopName) {
		LaptopName = laptopName;
	}
	public Features getFeatures() {
		return Features;
	}
	public void setFeatures(Features features) {
		Features = features;
	}
	
	

}

class Features{
	public List<String> Feature = new ArrayList<>(); 
}
