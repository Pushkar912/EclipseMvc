package com.bharat.location.controllers;

import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bharat.location.entities.Location;
import com.bharat.location.repos.LocationRepository;
import com.bharat.location.service.LocationService;
import com.bharat.location.util.EmailUtil;
import com.bharat.location.util.ReportUtil;

@Controller
public class LocationController {

	@Autowired
	LocationService locationService;
	
	@Autowired
	LocationRepository locationRepository;
	
	@Autowired
	ReportUtil reportUtil;
	
	@Autowired
	EmailUtil emailUtil;

	@Autowired
	ServletContext servletContext;
	
	@RequestMapping("/showCreate")
	public String showCreate() {
		System.out.println("Inside show create");
		return "createLocation";
	}
	
	@RequestMapping("/saveLoc")
	public String saveLocation(@ModelAttribute("location")Location location,ModelMap modelMap) {
		System.out.println("Inside save location");
		Location locationSaved=locationService.saveLocation(location);
		String message="Location saved with id:"+locationSaved.getId();
		modelMap.addAttribute("msg",message);
		emailUtil.sendEmail("pushkar4a@gmail.com", "Location Saved", message);
		return "createLocation";
	}
	
	@RequestMapping("/displayLocations")
	public String displayLocations(ModelMap modelMap) {
		List<Location> locations=locationService.getAllLocations();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	
	@RequestMapping("/deleteLocation")
	public String deleteLocation(@RequestParam int id,ModelMap modelMap) {
		Location location= locationService.getLocation(id);
		locationService.deleteLocation(location); 
		
		List<Location> locations=locationService.getAllLocations();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	
	@RequestMapping("/showUpdate")
	public String showUpdate(@RequestParam int id,ModelMap modelMap) {
		Location location= locationService.getLocation(id);
		modelMap.addAttribute("location", location);
		return "updateLocation";
	}
	
	@RequestMapping("/updateLoc")
	public String updateLocation(@ModelAttribute("location")Location location,ModelMap modelMap) {
		Location locationUpdated= locationService.updateLocation(location);
		
		List<Location> locations=locationService.getAllLocations();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	
	@RequestMapping("/generateReport")
	public String generateReport(@ModelAttribute("location")Location location,ModelMap modelMap) {
		String path=servletContext.getRealPath("/");		
		List<Object[]> objects=locationRepository.findTypeAndTypeCount();
		reportUtil.generatePieChart(path, objects);
		return "report";
	}
	
}
