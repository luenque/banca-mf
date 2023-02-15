package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.PetEntity;
import com.example.demo.repository.PetRepository;

@Service
public class RestService {
	@Autowired
	PetRepository petRepository;
	
	// Key and initialization vector
	public static String key = "thisabcdefghijklmnopqrstuv012345";
    public static String initVector = "AEIOUaeiou123456";
	
	public List<PetEntity> pets(String name) {
		System.out.println(name);
		List<PetEntity> pets;
		if(name == null || name.isBlank()) {
			pets = petRepository.findAll();
		} else {
			pets = petRepository.filterByName(name);
		}
		return pets;
	}
	
	public ResponseEntity<String> pokemon() {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> res = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon/ditto", String.class);
		
		return res;
	}
	
	public String encrypt(String text) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(
	    		Cipher.ENCRYPT_MODE,
	    		new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"),
                new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8))
	    );
	    byte[] cipherText = cipher.doFinal(text.getBytes());
	    return Hex.encode(cipherText).toString();
	}
}
