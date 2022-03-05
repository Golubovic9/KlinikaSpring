package com.example.demo.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Pregled;
public interface PregledRepository extends JpaRepository<Pregled,Integer>{

	@Query("select p from Pregled p where p.datum like :date and p.lekar.idLekar like :id")
	public List<Pregled> getPreglede(@Param("date")Date datum,@Param("id")Integer idl);
}
