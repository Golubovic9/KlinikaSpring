package com.example.demo.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.KorisnikUlogaRepository;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.UlogaRepository;


import model.Korisnik;
import model.KorisnikUloga;
import model.Lekar;
import model.Pacijent;
import model.Uloga;


@Controller
@ControllerAdvice
@RequestMapping(value = "auth")
public class LoginController {

	@Autowired
	UlogaRepository r;
	@Autowired
	KorisnikRepository ur;
	
	@Autowired
	KorisnikUlogaRepository buk;
	
	@Autowired
	PacijentRepository pr;
	
	@Autowired
	LekarRepository lr;
	
//	@ModelAttribute
//	public void getRoles(Model model) {
//		List<Uloga> roles=r.findAll();
//		model.addAttribute("roles", roles);
//		
//	}
	    
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	   @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	    public String loginPage() {    
	    	return "index";
	     
	    }
	   
	   @RequestMapping(value = "/login", method = RequestMethod.POST)
	    public String loginHello() {    
	    	return "hello";
	     
	    }
	   
	    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
	    public String deniedPage() {    
	          return "access_denied";
	     
	    }
	   
	    @RequestMapping(value = "registerUser", method = RequestMethod.GET)
		public String newUser(Model model) {
			Korisnik u = new Korisnik();
			model.addAttribute("user", u);
			List<Uloga> roles= r.findAll();
			model.addAttribute("roles", roles);
			return "register";
		}
	    
	    public boolean postojiUsername(String username) {
	    	List<Korisnik> korisnici = ur.findAll();
	    	for(Korisnik k : korisnici) {
	    		if(k.getKorisnickoIme().equals(username))
	    			return true;
	    	}
	    	return false;
	    }
	 
	   @RequestMapping(value = "register", method = RequestMethod.POST)
		public String saveUser(@ModelAttribute("user") Korisnik u,Model m,Integer uloga,Date datum,String adresa,String spec ) {
	    	if(postojiUsername(u.getKorisnickoIme())) {
	    		String poruka = "morate izabrati drugacije korisnicko ime";
	    		m.addAttribute("postoji", poruka);
	    		return newUser(m);
	    	}
		   	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	     	u.setLozinka(passwordEncoder.encode(u.getLozinka()));
			List<KorisnikUloga> lista= new ArrayList<KorisnikUloga>();
					Uloga role =  r.findById(uloga).get();
					KorisnikUloga bkr=new KorisnikUloga();
					bkr.setUloga(role);
					bkr.setKorisnik(u);
					
					lista.add(bkr);
			
			u.setKorisnikUlogas(lista);
	    	ur.save(u);
	    	buk.save(bkr);
	    	
	    	if(role.getNaziv().equals("PACIJENT")) {
		    	Pacijent p = new Pacijent();
		    	p.setIme(u.getIme());
		    	p.setPrezime(u.getPrezime());
		    	p.setAdresa(adresa);
		    	p.setDatumRodjenja(datum);
		    	pr.save(p);
	    	}else {
	    		Lekar l = new Lekar();
	    		l.setIme(u.getIme());
	    		l.setPrezime(u.getPrezime());
	    		l.setSpecijalnost(spec);
	    		lr.save(l);
	    	}
	    	
	    	
	    	try {
	    		
	    		FileWriter fw = new FileWriter("/home/milos/Korisnici.txt", true);
		    	BufferedWriter bw = new BufferedWriter(fw);
		     
		    	if(role.getNaziv().equals("PACIJENT")) {
		    		bw.write(ur.findAll().get(ur.findAll().size()-1).getIdKorisnik() +";" + pr.findAll().get(pr.findAll().size()-1).getIdPacijent());
		    	}else {
		    		bw.write(ur.findAll().get(ur.findAll().size()-1).getIdKorisnik() +";" + lr.findAll().get(lr.findAll().size()-1).getIdLekar());
		    	}
		    	bw.newLine();
		    	bw.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    	
			return "index";

		}
	   
	   @RequestMapping(value="/pocetna", method = RequestMethod.GET)
	    public String getPocetna (){
	       
	        return "pocetna";
	    }
	    
	    @RequestMapping(value="/logout", method = RequestMethod.GET)
	    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null){    
	            SecurityContextHolder.getContext().setAuthentication(null);
	        }
	        return "redirect:/auth/loginPage";
	    }
	    
	   
	    
	    
}
