package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent,Integer> {
	
	@Query("select p from Pacijent p where p.ime like :firstName or  p.prezime like :lastName")
	public List<Pacijent> pretrazi(@Param("firstName")String ime,@Param("lastName")String prezime);
}
