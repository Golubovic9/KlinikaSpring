package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.OcenaRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.PregledRepository;

import model.Korisnik;
import model.Lekar;
import model.Ocena;
import model.Pacijent;
import model.Pregled;

@Controller
@RequestMapping(value="/lekar")
public class LekarController {

	HashMap<Integer,Integer> mapa = new HashMap<Integer,Integer>();
	
	@Autowired
	LekarRepository lr;
	
	@Autowired
	OcenaRepository or;
	
	@Autowired
	PacijentRepository pr;
	
	@Autowired
	KorisnikRepository kr;
	
	@Autowired
	PregledRepository pregledRep;
	
	@RequestMapping(value="/getDoctors", method=RequestMethod.GET)
	public String getDoctors(HttpServletRequest ht) {
		List<Lekar> doctors = lr.findAll();
		ht.getSession().setAttribute("doctors", doctors);
		return "unos/unosLekara";
	}
	
//	@RequestMapping(value="/chooseDoctor", method=RequestMethod.POST)
//	public String chooseDoctor() {
//		
//	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping(value="/getApointment",method=RequestMethod.GET)
	public String zakazi(HttpServletRequest ht) {
		List<Lekar> doctors = lr.findAll();
		ht.setAttribute("lekari", doctors);
		return "zakazivanjePregleda";
	}
	
	@RequestMapping(value="/zakaziPregled",method=RequestMethod.POST)
	public String zakaziPregled(Integer doc,Date datum) throws IOException {
		Pregled p = new Pregled();
		p.setDatum(datum);
		p.setLekar(lr.findById(doc).get());
		Pacijent pacijent = getLogedPacijent();
		p.setPacijent(pacijent);
		pregledRep.save(p);
		return "pocetna";
	}
	
	@RequestMapping(value="/showDoctors", method=RequestMethod.GET)
	public String showDoctors(HttpServletRequest ht) throws IOException {
		List<Lekar> doctors = lr.findAll();
		ht.setAttribute("lekari", doctors);
		return "prikazLekara";
	}
	
	@RequestMapping(value="/oceni", method=RequestMethod.POST)
	public String oceni(String rate,Integer doc,Model m) throws IOException {
		Lekar l = lr.findById(doc).get();
		Pacijent p = getLogedPacijent();
		Ocena o = new Ocena();
		o.setOcena(Integer.parseInt(rate));
		o.setLekar(l);
		o.setPacijent(p);
		or.save(o);
		
		return "pocetna";
	}
	
	@RequestMapping(value="/chooseDoctor",method=RequestMethod.POST)
	public String choose(Integer doc,Model m ) throws IOException {
		Pacijent p = getLogedPacijent();
		p.getLekars().add(lr.findById(doc).get());
		List<Lekar> izabrani = p.getLekars();
		m.addAttribute("izabrani", izabrani);
		return "unos/unosLekara";
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
