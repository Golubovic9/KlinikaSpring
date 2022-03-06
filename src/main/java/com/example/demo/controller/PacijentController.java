package com.example.demo.controller;

import java.io.BufferedReader;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.colletions.Apointment;
import com.example.demo.colletions.Rang;
import com.example.demo.repository.AnketaRepository;
import com.example.demo.repository.KartonRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.OcenaRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.PregledRepository;
import com.example.demo.repository.ReceptRepository;

import model.Karton;
import model.Korisnik;
import model.Lekar;
import model.Ocena;
import model.Pacijent;
import model.Pregled;
import model.Recept;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value="/pacijent")
public class PacijentController {

	HashMap<Integer,Integer> mapa = new HashMap<Integer,Integer>();
	
	@Autowired
	PacijentRepository pr;
	
	@Autowired
	KartonRepository kartonRep;
	
	@Autowired
	KorisnikRepository kr;
	
	@Autowired
	LekarRepository lr;
	
	@Autowired
	AnketaRepository ar;
	
	@Autowired
	OcenaRepository or;
	
	@Autowired
	PregledRepository pregledRep;
	
	@Autowired
	ReceptRepository receptRep;
	
	@RequestMapping(value="/showPatients",method=RequestMethod.GET)
	public String showPatients(HttpServletRequest ht) {
		List<Pacijent> pacijenti = pr.findAll();
		ht.setAttribute("pacijenti", pacijenti);
		return "prikazPacijenata";
	}
	
	@RequestMapping(value="/dijagnoza",method=RequestMethod.GET)
	public String dijagnoza(Integer idp,HttpServletRequest ht ) {
		ht.getSession().setAttribute("idp", idp);
		return "unos/unosDijagnoze";
	}
	
	@RequestMapping(value="/dijagnostifikuj",method=RequestMethod.POST)
	public String dijagnostifikuj(String dijagnoza,HttpServletRequest ht) {
		Integer idp = (Integer)ht.getSession().getAttribute("idp");
		Karton k = new Karton();
		k.setDijagnoza(dijagnoza);
		k.setPacijent(pr.findById(idp).get());
		kartonRep.save(k);
		return showPatients(ht);
	}
	
	@RequestMapping(value="/pregledaj",method=RequestMethod.GET)
	public String pregledaj(HttpServletRequest ht) throws IOException {
		Date sad = new Date();
		Lekar l = getLogedLekar();
		List<Pregled> pregledi = pregledRep.getPreglede(sad,l.getIdLekar());
		ht.setAttribute("pregledi",pregledi );
		return "pregledi";
	}
	
	@RequestMapping(value="/napisiRecept",method=RequestMethod.GET)
	public String napisiRecept(HttpServletRequest ht,Integer pregled) {
		ht.getSession().setAttribute("pregled", pregled);
		return "recept";
	}
	
	@RequestMapping(value="/izdajRecept",method=RequestMethod.POST)
	public void izdajRecept(HttpServletRequest ht,HttpServletResponse res,String naziv,Integer kolicina) throws Exception {
		Recept r = new Recept();
		r.setNazivLeka(naziv);
		r.setKolicina(kolicina);
		Pregled p = pregledRep.findById((Integer)ht.getSession().getAttribute("pregled")).get();
		r.setPregled(p);
		receptRep.save(r);
		
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/recept.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("naziv",naziv);
		params.put("kolicina", kolicina.toString());
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,new JREmptyDataSource(1));
		inputStream.close();
		
		
		res.setContentType("application/x-download");
		res.addHeader("Content-disposition", "attachment; filename=Recept.pdf");
		OutputStream out = res.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
		
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String search(String ime,String prezime,HttpServletRequest ht) {
		List<Pacijent> patients = pr.pretrazi(ime, prezime);
		ht.setAttribute("patients", patients);
		return "pocetna";
	}
	
	@RequestMapping(value="/getRate",method=RequestMethod.GET)
	public String rate(HttpServletRequest ht) throws IOException {
		Lekar l = getLogedLekar();
		List<Ocena> ocene = or.getOcene(l.getIdLekar());
		int suma=0;
		Float prosek;
		int counter=0;
		for(Ocena o: ocene) {
			suma += o.getOcena();
			counter++;
		}
		if(suma==0) {
		    prosek = null;
			ht.setAttribute("prosecna", prosek);
		}else {
			prosek = (float)suma/(float)counter;
			double prosjek = Math.round(prosek * 100.0)/100.0;
			ht.setAttribute("prosecna", prosjek);
		}
		
		return "ocena";
	}
	
	@RequestMapping(value="/getKarton.pdf", method=RequestMethod.GET)
	public void showReport(HttpServletResponse response,String ime,String prezime,Integer idp) throws Exception{
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(kartonRep.getSveDijagnoze(idp));
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/karton.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ime", ime);
		params.put("prezime", prezime);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		inputStream.close();
		
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=Karton.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	
	@RequestMapping(value="/izvestaji",method = RequestMethod.GET)
	public String izvestaji() {
		return "izvestaji";
	}
	
	@RequestMapping(value="/getAnkete.pdf", method=RequestMethod.GET)
	public void showReport(HttpServletResponse response) throws Exception{
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ar.findAll());
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/anketareportjrxml.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		Map<String, Object> params = new HashMap<String, Object>();
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		inputStream.close();
		
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=Anketa.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	
	@RequestMapping(value="/oceneSvihLekara.pdf", method=RequestMethod.GET)
	public void oceneSvihLekara(HttpServletResponse response) throws IOException, JRException {
		List<Lekar> lekari= lr.findAll();
		List<Rang> rangovi = new ArrayList<Rang>();
		for(Lekar l: lekari) {
				if(or.getProsek(l.getIdLekar()) != null) {
					Float prosek = or.getProsek(l.getIdLekar());
					Rang rang = new Rang(l,prosek);
					rangovi.add(rang);
				}
		}
		List<Rang> rangs= rangovi.stream().sorted(Comparator.comparing(Rang::getRang))
										  .collect(Collectors.toList());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rangs);
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/ocenereport.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		Map<String, Object> params = new HashMap<String, Object>();
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,dataSource);
		inputStream.close();
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=OceneLekara.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	
	
	@RequestMapping(value="/preglediUMesecu.pdf", method=RequestMethod.GET)
	public void preglediUMesecu(HttpServletResponse response) throws IOException, JRException {
	    List<Pregled> pregledi = pregledRep.findAll();
	    List<Apointment> apointments = new ArrayList<Apointment>();
		Map<String,Long> preglediPoMesecu = pregledi.stream().collect(Collectors
																		.groupingBy(Pregled::proveriDatum,Collectors.counting()));
	    preglediPoMesecu.forEach((k,v)->apointments.add(new Apointment(k,v)));
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(apointments);
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/preglediPoMesecu.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		Map<String, Object> params = new HashMap<String, Object>();
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,dataSource);
		inputStream.close();
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=PreglediPoMesecu.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
	}
	
	
//	public void printRecept(HttpServletResponse response,String naziv,Integer kolicina) throws Exception{
//		
//		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/recept.jrxml");
//		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("nazic", naziv);
//		params.put("kolicina", kolicina);
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params);
//		inputStream.close();
//		
//		
//		response.setContentType("application/x-download");
//		response.addHeader("Content-disposition", "attachment; filename=Recept.pdf");
//		OutputStream out = response.getOutputStream();
//		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
//	}
	
	
// Zbog nacina na koji sam modelirao bazu podataka logovanog korisnika sam dobijao ovom metodom
// u fajl Korisnici.txt sam prilikom registracije korisnika
// u jednu liniju upisivao id Korisnika i id Pacijenta ili Lekara 
// u zavisnosti od uloge i to u formatu "idKorisnik;idLekar/idPacijent"
// preko principle objekta sam dobijao idKorisnik i na osnovu toga dovlacio 
// iz fajla idLekar/idPacijent i dobijao odgovarajuci objekat klase Lekar ili Pacijent
	public Lekar getLogedLekar() throws IOException {
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
		
		
		
		Lekar p = lr.findById(mapa.get(k.getIdKorisnik())).get();
		return p;
	}
	
}
