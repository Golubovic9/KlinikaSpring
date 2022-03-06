package com.example.demo.colletions;


// Klasa koriscena kao datasource prilikom kreiranja jasper izvestaja u metodi
// preglediUMesecu()  pacijent controlera; izvestaj pokazuje broj zakazanih pregleda u svakom
// mesecu
public class Apointment {
		private String datum;
		private Long broj;
		public Apointment(String datum, Long broj) {
			super();
			this.datum = datum;
			this.broj = broj;
		}
		public String getDatum() {
			return datum;
		}
		public void setDatum(String datum) {
			this.datum = datum;
		}
		public Long getBroj() {
			return broj;
		}
		public void setBroj(Long broj) {
			this.broj = broj;
		}
		
		
}
