package com.ssmedia.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssmedia.model.KYCCustomer;
import com.ssmedia.model.KYCUser;
import com.ssmedia.rest.Response;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("kyc")
public class KYCController { 
	 
	
	@GetMapping("test")
	public String test() {
		return "KYC Service is up and running!";
	}

	
	@PostMapping("authenticate")
	public Response login(@RequestBody KYCUser req) {
		KYCUser user = new KYCUser("admin", null, "kycAdmin", "admin@kyc.com","8553577356");
		return req.email().equals("admin") ? new Response(user, 200, "success") : new Response("Authentication Failure" ,500,null);
	}
	
	@PostMapping("customer-search")
	public Response getAllCustomers(@RequestBody KYCCustomer req) {
	   
		
		List<KYCCustomer> customers = List.of(
			    new KYCCustomer(1001L, "Ravi Kumar",   "9876543210", "APPROVED", "KYC verified"),
			    new KYCCustomer(1002L, "Anil Sharma",  "9123456789", "PENDING",  "Documents pending"),
			    new KYCCustomer(1003L, "Neha Verma",   "9988776655", "REJECTED", "Photo mismatch"),
			    new KYCCustomer(1004L, "Suresh Reddy", "9012345678", "APPROVED", "Verified successfully"),
			    new KYCCustomer(1005L, "Pooja Singh",  "9090909090", "PENDING",  "Address proof required"),
			    new KYCCustomer(1006L, "Amit Patel",   "9345678901", "APPROVED", "All checks passed"),
			    new KYCCustomer(1007L, "Kiran Rao",    "9567890123", "REJECTED", "Invalid ID proof"),
			    new KYCCustomer(1008L, "Priya Nair",   "9786543210", "PENDING",  "Manual review"),
			    new KYCCustomer(1009L, "Vikram Mehta", "9654321098", "APPROVED", "KYC completed"),
			    new KYCCustomer(1010L, "Sneha Iyer",   "9898989898", "PENDING",  "Waiting for approval")

			);
		return new Response(customers, 200, "success");
	}
	
	
	
}
