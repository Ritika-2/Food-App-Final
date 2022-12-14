package com.org.foodapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.org.foodapp.dao.FoodProductDao;
import com.org.foodapp.dao.MenuDao;
import com.org.foodapp.dto.FoodProduct;
import com.org.foodapp.dto.Menu;
import com.org.foodapp.dto.User;
import com.org.foodapp.util.ResponseStructure;

@Service
public class FoodProductService {
	
	@Autowired
	FoodProductDao foodProductDao;
	@Autowired
	MenuDao menuDao;
	
	public  ResponseEntity<ResponseStructure<FoodProduct>> savefFoodProduct(FoodProduct foodProduct, int menuId) {
		ResponseStructure<FoodProduct> structure = new ResponseStructure<>();
		Optional<Menu> optional = menuDao.getMenuById(menuId); 
		if(optional.isEmpty()) {
			
			structure.setMessage("Id Not Found");
			structure.setError(true);
		}else {
			foodProduct.setMenu(optional.get());
			structure.setError(false);
			structure.setMessage("Food Product Saved");
			structure.setData(foodProductDao.saveFoodProduct(foodProduct));
		}		
		return new ResponseEntity<ResponseStructure<FoodProduct>>(structure, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<FoodProduct>>> getFoodProductsInMenu(int menuId){
		ResponseStructure<List<FoodProduct>> structure = new ResponseStructure<>();
		structure.setError(false);
		structure.setData(foodProductDao.getFoodProductsInMenu(menuId));
		return new ResponseEntity<ResponseStructure<List<FoodProduct>>>(structure, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteFoodProduct(int id){
		ResponseStructure<String> structure = new ResponseStructure<>();
		Optional<FoodProduct> optional = foodProductDao.getFoodProductById(id);
		if(optional.isEmpty()) {
			structure.setError(true);
		}
		else {
			structure.setError(false);
			structure.setMessage("Deleted Food Product");
			foodProductDao.deleteFoodProduct(id);
		}
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);

	}

}
