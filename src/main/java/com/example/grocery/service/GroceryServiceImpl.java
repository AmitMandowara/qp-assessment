package com.example.grocery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.grocery.entity.BookedGroceriesEntity;
import com.example.grocery.entity.GroceryItemsEntity;
import com.example.grocery.entity.UserDetailsEntity;
import com.example.grocery.exception.InvalidOperationException;
import com.example.grocery.model.BookGrocery;
import com.example.grocery.model.Grocery;
import com.example.grocery.model.ManageGrocery;
import com.example.grocery.repository.BookedGroceriesRepository;
import com.example.grocery.repository.GroceryRepository;
import com.example.grocery.repository.UserDetailsRepository;

@Service("groceryService")
public class GroceryServiceImpl implements GroceryService{
	
	@Autowired
	private GroceryRepository groceryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Autowired
	private BookedGroceriesRepository bookedGroceriesRepository;
	
	@Override
	public void addGroceryItem(Grocery grocery) {
		GroceryItemsEntity groceryItemsEntity =  modelMapper.map(grocery, GroceryItemsEntity.class);
		groceryRepository.save(groceryItemsEntity);
	}
	
	@Override
	public List<Grocery> getAllGroceries() {
		List<GroceryItemsEntity> groceryItemsEntityList = groceryRepository.findAll();
		return groceryItemsEntityList.stream().map(groceryItemsEntity->modelMapper.map(groceryItemsEntity, Grocery.class)).collect(Collectors.toList());
	}
	
	@Override
	public void deleteGrocery(int id) {
		groceryRepository.deleteById(id);
	}
	
	@Override
	public String updateGroceryItem(Grocery grocery, int id) {
		Optional<GroceryItemsEntity> groceryItemsEntityOp = groceryRepository.findById(id);
		if(groceryItemsEntityOp.isPresent()) {
			GroceryItemsEntity groceryItemsEntity = groceryItemsEntityOp.get();
			groceryItemsEntity.setName(grocery.getName());
			groceryItemsEntity.setPricePerUnit(grocery.getPricePerUnit());
			groceryItemsEntity.setUnit(grocery.getUnit());
			groceryItemsEntity.setVendor(grocery.getVendor());
			groceryItemsEntity.setRotten(grocery.getRotten());
			groceryRepository.save(groceryItemsEntity);
			return "Record updated successfully";
		}else {
			return "No grocery record found for given id";
		}
	}
	
	@Override
	public Optional<Grocery> getGroceryItem(int id) {
		Optional<GroceryItemsEntity> groceryItemsEntityOp = groceryRepository.findById(id);
		if(groceryItemsEntityOp.isPresent()) {
			return Optional.of(modelMapper.map(groceryItemsEntityOp.get(), Grocery.class));
		}
		return Optional.empty();
	}
	
	@Override
	public void manageGrocery(ManageGrocery manageGrocery) throws InvalidOperationException {
		Optional<GroceryItemsEntity> groceryItemsEntityOp = groceryRepository.findById(manageGrocery.getId());
		if(groceryItemsEntityOp.isPresent()) {
			GroceryItemsEntity groceryItemsEntity = groceryItemsEntityOp.get();
			if(manageGrocery.isIncrease()) {
				groceryItemsEntity.setUnit(groceryItemsEntity.getUnit() + manageGrocery.getUnits());
			}else {
				if(manageGrocery.getUnits() < groceryItemsEntity.getUnit())
					groceryItemsEntity.setUnit(groceryItemsEntity.getUnit() - manageGrocery.getUnits());
				else
					throw new InvalidOperationException("Grocery items removed are more than grocery items present");
			}
			groceryRepository.save(groceryItemsEntity);
		}
	}
	
	@Override
	public String bookGrocery(List<BookGrocery> bookGroceryList, int userId) {
		Optional<UserDetailsEntity> userDetailsEntityOp = userDetailsRepository.findById(userId);
		if(userDetailsEntityOp.isPresent()) {
			UserDetailsEntity userDetailsEntity = userDetailsEntityOp.get();
			List<BookedGroceriesEntity> bookedGroceryEntitiesList = new ArrayList<>();
			List<GroceryItemsEntity> groceryItemsEntityList = new ArrayList<>();
			for(BookGrocery bookGrocery : bookGroceryList) {
				Optional<GroceryItemsEntity> groceryItemsEntityOp = groceryRepository.findById(bookGrocery.getGroceryId());
				if(groceryItemsEntityOp.isPresent()) {
					GroceryItemsEntity groceryItemsEntity = groceryItemsEntityOp.get();
					if(groceryItemsEntity.getUnit()<bookGrocery.getUnit())
						return "Booked groceries are more than available groceries";
					BookedGroceriesEntity bookedGroceriesEntity = new BookedGroceriesEntity();
					bookedGroceriesEntity.setUnitsBooked(bookGrocery.getUnit());
					bookedGroceriesEntity.setBookedByUserId(userDetailsEntity);
					bookedGroceriesEntity.setGroceryId(groceryItemsEntity);
					bookedGroceriesEntity.setTotalPrice(bookGrocery.getUnit() * (long)groceryItemsEntity.getPricePerUnit());
					bookedGroceryEntitiesList.add(bookedGroceriesEntity);
					groceryItemsEntity.setUnit(groceryItemsEntity.getUnit()-bookGrocery.getUnit());
					groceryItemsEntityList.add(groceryItemsEntity);
				}
			}
			if(groceryItemsEntityList != null && !groceryItemsEntityList.isEmpty())
				groceryRepository.saveAll(groceryItemsEntityList);
			if(bookedGroceryEntitiesList != null && !bookedGroceryEntitiesList.isEmpty())
				bookedGroceriesRepository.saveAll(bookedGroceryEntitiesList);
			return "Groceries booked successfully";
		}
		else
			return "This user is not present in backend. Please save user details in backend first";
	}
}