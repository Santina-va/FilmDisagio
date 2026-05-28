package models;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

import enumeration.GenereFilm;
import interfaces.Identificabile;

public class Film implements Identificabile {
	private int id;
	private String titolo;
	private String regista;
	private int durataMinuti;
	private LocalDate dataUscita;
	private Set<GenereFilm> generi;
	
	public Film(int id, String titolo, String regista, int durataMinuti, LocalDate dataUscita, Set<GenereFilm> generi) {
		super();
		this.id = id;
		this.titolo = titolo;
		this.regista = regista;
		this.durataMinuti = durataMinuti;
		this.dataUscita = dataUscita;
		this.generi = generi;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public String getregista() {
		return regista;
	}
	
	public int getDurataMinuti() {
		return durataMinuti;
	}
	
	public LocalDate getDataUscita() {
		return dataUscita;
	}
	
	public Set<GenereFilm> getGeneri() {
		return generi;
	}
		
	public void aggiungiGenere(GenereFilm genere) {
		if (genere != null) {
			generi.add(genere);
		}
	}
	
	public void rimuoviGenere(GenereFilm genere) {
		generi.remove(genere);
	}
	
	public boolean contieneGenere(String genere) {
		try {
			return generi.contains(GenereFilm.valueOf(genere.toUpperCase().trim()));
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public int getAnniDaUscita() {
		return Period.between(dataUscita, LocalDate.now()).getYears();
	}
	
	public boolean isFilmRecente() {
		return getAnniDaUscita() <= 2;
	}
	
	public String getDescrizione() {
		return String.format("%s (Regia: %s, Durata: %d min, Uscito il: %s, Generi: %s)", titolo, regista, durataMinuti, dataUscita, generi);
	}

}
