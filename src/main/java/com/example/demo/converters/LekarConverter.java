package com.example.demo.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import com.example.demo.repository.LekarRepository;

import model.Lekar;
import model.Uloga;

public class LekarConverter implements Converter<String,Lekar>{

	@Autowired
	LekarRepository lr;
	
	public LekarConverter(LekarRepository l) {
		this.lr = l;
	}
	
	@Override
	public Lekar convert(String source) {
		// TODO Auto-generated method stub
		int idb= -1;
		try {
			idb = Integer.parseInt(source);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), 
					TypeDescriptor.valueOf(Uloga.class), idb, null);
		}
		Lekar lekar = lr.findById(idb).get();
		return lekar;
		
	}

}
