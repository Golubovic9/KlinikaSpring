package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import com.example.demo.repository.UlogaRepository;

import model.Uloga;



public class UlogaConverter implements Converter<String,Uloga>{

	@Autowired
	UlogaRepository br;
	
	public UlogaConverter(UlogaRepository b) {
		this.br= b;
	}
	
	@Override
	public Uloga convert(String source) {
		// TODO Auto-generated method stub
		int idb= -1;
		try {
			idb = Integer.parseInt(source);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), 
					TypeDescriptor.valueOf(Uloga.class), idb, null);
		}
		Uloga bib = br.findById(idb).get();
		return bib;
	}

}
