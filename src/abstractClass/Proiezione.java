package abstractClass;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import interfaces.Identificabile;
import interfaces.Prezzabile;
import interfaces.Programmabile;
import models.Film;
import models.Sala;

public abstract class Proiezione  implements Identificabile, Programmabile, Prezzabile{
	private int id;
	private Film film;
	private Sala sala;
	private LocalDate data;
	private LocalTime oraInizio;
	protected double prezzoBase;
	private Set<String> tag;
	
	public Proiezione(int id, Film film, Sala sala, LocalDate data, LocalTime oraInizio, double prezzoBase) {
		this.id = id;
		this.film = film;
		this.sala = sala;
        this.data = (data != null) ? data : LocalDate.now();
        this.oraInizio = (oraInizio != null) ? oraInizio : LocalTime.of(20, 0);
        this.prezzoBase = (prezzoBase > 0) ? prezzoBase : 7.00;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public LocalDate getData() {
		return data;
	}

	@Override
	public LocalTime getOraInizio() {
		return oraInizio;
	}

	@Override
	public LocalDateTime getDataOraInizio() {
		return LocalDateTime.of(data,  oraInizio);
	}

	public Film getFilm() {
		return film;
	}
	
	public Sala getSala() {
		return sala;
	}
	
	public double getPrezzoBase() {
		return prezzoBase;
	}
	
	public Set<String> getTag() {
		return tag;
	}
	
	public void aggiungiTag(String t) {
		if (t != null && !t.trim().isEmpty()) {
			tag.add(t.trim().toLowerCase());
		}
	}
	
	public boolean contieneTag(String t) {
		if (t == null) return false;
		return tag.contains(t.trim().toLowerCase());
	}
	
	public LocalDateTime getDataOraFine() {
		return getDataOraInizio().plusMinutes(film.getDurataMinuti());
	}
	
	public boolean isOggi() {
		return data.equals(LocalDate.now());
	}
	
	public boolean isNelWeekend() {
		DayOfWeek dow = data.getDayOfWeek();
		return (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY);
	}
	
	public boolean isSerale() {
		return !oraInizio.isBefore(LocalTime.of(20,  0));
	}
	
	public boolean isTerminata() {
		return getDataOraFine().isBefore(LocalDateTime.now());
	}
	
	public String getDettagliBase() {
		return String.format("ID: %d | Film: %s | Sala: %s | Data: %s %s | Prezzo Base: %.2f€", id, film.getTitolo(), sala.getNome(), data, oraInizio, prezzoBase);
	}
	
	public abstract String getTipoProiezione();
	

}
