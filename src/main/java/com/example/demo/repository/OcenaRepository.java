package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Ocena;

public interface OcenaRepository extends JpaRepository<Ocena,Integer> {

	@Query("select o from Ocena o where o.lekar.idLekar like :id")
	public List<Ocena> getOcene(@Param("id")Integer ajdi );
	
	@Query("select avg(o.ocena) from Ocena o where o.lekar.idLekar like :ajdi")
	public Float getProsek(@Param("ajdi")Integer ajdi );
	
}
