package com.example.demo.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PetEntity;
import com.example.demo.service.RestService;

@RestController
@RequestMapping("api")
public class Controller {
	@Autowired
	RestService service;
	
	@GetMapping(path = "/pets/")
	
	public ResponseEntity<List<PetEntity>> getPets(@RequestParam(name = "name", required = false) String name) {
		List<PetEntity> pets = service.pets(name);
		return new ResponseEntity<List<PetEntity>>(pets, HttpStatus.OK);
	}
	
	@GetMapping(path = "/pokemon/")
	public ResponseEntity<String> getPokemon() {
		ResponseEntity<String> pokemonRes = service.pokemon();
		return pokemonRes;
	}
	
	@GetMapping(path = "/encrypt/")
	public ResponseEntity<String> encript(@RequestParam(name = "text") String text) {
		String encrypted;
		try {
			encrypted = service.encrypt(text);
			return new ResponseEntity<String>(encrypted, HttpStatus.OK);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
