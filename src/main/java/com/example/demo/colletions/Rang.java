package com.example.demo.colletions;

import model.Lekar;

// Klasa koriscena da kao datasource prilikom kreiranja jasper izvestaja u metodi
// oceneSvihLekara()  pacijent controlera
public class Rang {
	private Lekar l ;
	private float rang;
	
	
	public Rang(Lekar l, float rang) {
		super();
		this.l = l;
		this.rang = rang;
	}
	public Lekar getL() {
		return l;
	}
	public void setL(Lekar l) {
		this.l = l;
	}
	public float getRang() {
		return rang;
	}
	public void setRang(float rang) {
		this.rang = rang;
	}
	
	
}
