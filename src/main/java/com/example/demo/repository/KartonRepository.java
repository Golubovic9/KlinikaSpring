package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Karton;
public interface KartonRepository extends JpaRepository<Karton,Integer>{
	
	@Query("select k from Karton k where k.pacijent.idPacijent like :idp ")
	public List<Karton> getSveDijagnoze(@Param("idp")Integer id);
	
}
