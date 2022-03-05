package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.AnketaRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.PacijentRepository;

import model.Anketa;
import model.Korisnik;
import model.Pacijent;

@Controller
@RequestMapping(value="/feedback")
public class AnketaController {
	
	HashMap<Integer,Integer> mapa = new HashMap<Integer,Integer>();
	
	@Autowired
	PacijentRepository pr;
	
	@Autowired
	KorisnikRepository kr;
	
	@Autowired
	AnketaRepository ar;

	@RequestMapping(value="/utisak", method=RequestMethod.GET)
	public String utisak() {
		
		return "utisak";
	}
	
	@RequestMapping(value="/leaveFeedback", method= RequestMethod.POST)
	public String leaveFeedback(String feedback,Model m) throws IOException {
		Pacijent p = getLogedPacijent();
		Anketa a = new Anketa();
		a.setOdgovor(feedback);
		a.setPacijent(p);
		ar.save(a);
		boolean flag = true;
		m.addAttribute("succes", flag);
		return "utisak";
	}
	
	public Pacijent getLogedPacijent() throws IOException {
		Object au = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if(au instanceof UserDetails) {
			 username = ((UserDetails)au).getUsername();
			 
		}else {
			 username = au.toString();
		}
		Korisnik k = kr.findByKorisnickoIme(username);
		
		File fajl = new File("/home/milos/Korisnici.txt");
		BufferedReader br = new BufferedReader (new FileReader(fajl));
		String line;
		while((line=br.readLine())!=null) {
			String[] elements = line.split(";");
			mapa.put(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]));
		}
		br.close();
		
		
		
		Pacijent p = pr.findById(mapa.get(k.getIdKorisnik())).get();
		return p;
	}
	
}
