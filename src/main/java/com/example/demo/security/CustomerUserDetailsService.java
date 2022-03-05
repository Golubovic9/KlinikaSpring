package com.example.demo.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.KorisnikRepository;

import model.Korisnik;

@Transactional
@Service("customUserDetailsService")
public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
    private KorisnikRepository korisnikRepository;  
  
    @Transactional
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Korisnik user = korisnikRepository.findByKorisnickoIme(username);
		UserDetailsImpl userDetails =new UserDetailsImpl();
		userDetails.setUsername(user.getKorisnickoIme());
		userDetails.setPassword(user.getLozinka());
		userDetails.setRoles(user.getKorisnikUlogas());
		return userDetails;
		
    }
	
}
