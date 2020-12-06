package com.stackroute.userprofile.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.userprofile.model.UserProfile;
import com.stackroute.userprofile.service.UserProfileService;
import com.stackroute.userprofile.util.exception.UserProfileAlreadyExistsException;
import com.stackroute.userprofile.util.exception.UserProfileNotFoundException;


/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

@RestController
@RequestMapping("/api/v1")
public class UserProfileController {

	/*
	 * Autowiring should be implemented for the UserService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	private static Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	private UserProfileService userProfileService;

	@Autowired
	public UserProfileController(UserProfileService userProfileService) {
		super();
		this.userProfileService = userProfileService;
	}
	
	
	@PostMapping("/user")
	public ResponseEntity<?> registerUser(@RequestBody UserProfile user) {
		ResponseEntity<?> entity;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (!user.getUserId().isEmpty() && !user.getEmail().isEmpty() && !user.getFirstName().isEmpty()
					&& !user.getLastName().isEmpty() && !user.getContact().isEmpty()) {
				userProfileService.registerUser(user);
				map.put("message", "User Registration Successfull");
				logger.info(" User Registration Successfull ");
				entity = new ResponseEntity<>(map, HttpStatus.CREATED);
			} else {
				map.put("message", "fields should not be empty");
				logger.debug(" error :",map );
				entity = new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
			}
		} catch (UserProfileAlreadyExistsException e) {
			map.put("message", "User is already registered");
			entity = new ResponseEntity<>(map, HttpStatus.CONFLICT);
		}
		return entity;
	}

	


	/*
	 * Define a handler method which will create a specific userprofile by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 201(CREATED) - If the userprofile created successfully. 
	 * 2. 409(CONFLICT) - If the userId conflicts with any existing user, return the 
	 * UserProfileAlreadyExistsException along with the status
	 * 
	 * This handler method should map to the URL "/api/v1/user" using HTTP POST method
	 */
   
	   @PutMapping("/userprofile/{userId}")
		public ResponseEntity<?> updateUser(@PathVariable("userId") String userId, @RequestBody UserProfile user) {
		   
	    	ResponseEntity<?> entity;
	    	
		try {
	             UserProfile updatedUser = userProfileService.updateUser(userId, user);
	             logger.info(" User Updated successfully");
	             entity = new ResponseEntity<>(userProfileService.getUserById(userId), HttpStatus.OK);
			} catch (UserProfileNotFoundException e) {
				entity = new ResponseEntity<>("[ User Profile Not Found ]", HttpStatus.NOT_FOUND);
				logger.info(" User not found");
			}
			return entity;
		}

	   
	   

	/*
	 * Define a handler method which will update a specific userprofile by reading the
	 * Serialized object from request body and save the updated user details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the userprofile is updated successfully.
	 * 2. 404(NOT FOUND) - If the userprofile with specified userId is not found,return the 
	 * UserProfileNotFoundException along with the status
	 * 
	 * This handler method should map to the URL "/api/v1/userprofile/{userid}" using HTTP PUT method.
	 */

	   
	   @DeleteMapping("/userprofile/{userId}")
		public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {
	    	
	    	ResponseEntity<?> entity;
			try {
				boolean b = userProfileService.deleteUser(userId);
				logger.debug("User deleted",b);
				entity = new ResponseEntity<>("[ UserProfile Deleted successfully ]", HttpStatus.OK);
			} catch (UserProfileNotFoundException e) {
				entity = new ResponseEntity<>("[ User profile Not Found ]", HttpStatus.NOT_FOUND);
			}
			return entity;
		}


	   
	   
	/*
	 * Define a handler method which will delete an userprofile from a database.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the userprofile is deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the user with specified userId is not found, return 
	 * the UserProfileNotFoundException along with the status.
	 *
	 * This handler method should map to the URL "/api/v1/userprofile/{userId}" using 
	 * HTTP Delete method where "userId" should be replaced by a valid userId
	 * 
	 */

	   @GetMapping("/userprofile/{userId}")
		public ResponseEntity<?> getUserById(@PathVariable("userId") String userId) {
		HashMap<String, List<UserProfile>> map = new HashMap<>();
			ResponseEntity<?> entity;
			List<UserProfile> ls = new LinkedList<>();
			UserProfile u =null;
			try {
				 u = userProfileService.getUserById(userId);
				logger.debug("User profile found",u);
				ls.add(u);
				map.put("userprofile", ls);
				entity = new ResponseEntity<>(map, HttpStatus.OK);
			} catch (UserProfileNotFoundException e) {
				ls.add(u);
				map.put("userprofile", ls);
				entity = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
			return entity;

		}

	   
	/*
	 * Define a handler method which will show details of a specific user. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the userprofile found successfully. 
	 * 2. 404(NOT FOUND) - If the userprofile with specified userId is not found, return 
	 * UserProfileNotFoundException message along with the status.
	 * This handler method should map to the URL "/api/v1/userprofile/{userId}" using 
	 * HTTP GET method where "id" should be replaced by a valid userId without {}.
	 */



}
