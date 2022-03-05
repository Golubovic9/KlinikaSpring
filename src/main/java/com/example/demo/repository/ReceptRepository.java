package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import model.Recept;
public interface ReceptRepository extends JpaRepository<Recept,Integer> {

}
