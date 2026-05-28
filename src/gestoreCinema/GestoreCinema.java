package gestoreCinema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstractClass.Proiezione;
import archivio.Archivio;
import enumeration.GenereFilm;
import interfaces.Filtro;
import models.EventoSpeciale;
import models.Film;
import models.Proiezione3D;
import models.ProiezioneStandard;
import models.Sala;

public class GestoreCinema {

	private Archivio<Film> archivioFilm = new Archivio<>();
	private Archivio<Sala> archivioSale = new Archivio<>();
	private Archivio<Proiezione> archivioProiezione = new Archivio<>();
	private List<Proiezione> programmazione = new ArrayList<>();
	private Map<LocalDate, List<Proiezione>> proiezioniPerData = new HashMap<>();
	private Map<Integer, List<Proiezione>> proiezioniPerSala = new HashMap<>();
	private Map<String, List<Film>> filmPerGenere = new HashMap<>();

	public void aggiungiFilm(Film f) {
		archivioFilm.aggiungi(f);

		for (GenereFilm g : f.getGeneri()) {

			filmPerGenere.computeIfAbsent(g.name(), k -> new ArrayList<>()).add(f);
		}
	}

	public void aggiungeSala(Sala s) {
		archivioSale.aggiungi(s);
	}

	/**public void aggiungiProiezione(Proiezione p, LocalDate d, Integer sala) {
		if (p != null) {
			archivioProiezione.aggiungi(p);
			programmazione.add(p);

			proiezioniPerData.computeIfAbsent(d, k -> new ArrayList<>()).add(p);

			proiezioniPerSala.computeIfAbsent(sala, k -> new ArrayList<>()).add(p);
		}

		else
			System.out.println("Programmazione non valida.");
	}*/
	
	public void aggiungiProiezione(Proiezione p) {
	    if (p != null) {
	        archivioProiezione.aggiungi(p);
	        programmazione.add(p);

	        proiezioniPerData
	            .computeIfAbsent(p.getData(), k -> new ArrayList<>())
	            .add(p);

	        proiezioniPerSala
	            .computeIfAbsent(p.getSala().getId(), k -> new ArrayList<>())
	            .add(p);
	    } else {
	        System.out.println("Programmazione non valida.");
	    }
	}

	public List<Proiezione> cercaProiezioniPerData(LocalDate d) {
		return proiezioniPerData.get(d);
	}

	public List<Proiezione> cercaProiezioniPerSala(int idSala) {
		return proiezioniPerSala.get(idSala);
	}

	public List<Proiezione> cercaProiezioniDiOggi() {
		return proiezioniPerData.get(LocalDate.now());
	}

	public List<Proiezione> cercaProiezioniFuture() {
		return programmazione.stream().filter(p -> !p.isTerminata()).toList();
	}

	public List<Proiezione> cercaProiezioniSerali() {
		return programmazione.stream().filter(p -> p.isSerale()).toList();
	}

	public List<Proiezione> filtraProiezioni(Filtro<Proiezione> filtro) {

		return programmazione.stream().filter(p -> filtro.accetta(p)).toList();
	}

	public List<Film> filtraFilm(Filtro<Film> filtro) {

		return archivioFilm.trovaTutti().stream().filter(f -> filtro.accetta(f)).toList();
	}

	public void stampaDettaglioSpecifico(Proiezione proiezione) {

		if (proiezione == null) {
			System.out.println("Proiezione non valida.");
			return;
		}

		if (proiezione instanceof ProiezioneStandard p) {

			System.out.println("Titolo: " + p.getFilm().getTitolo() + "; sala: " + p.getSala().getNome() + "; data: "
					+ p.getData() + "; ora: " + p.getOraInizio() + "; prezzo: " + p.calcolaPrezzoFinale());

		} else if (proiezione instanceof Proiezione3D p) {

			System.out.println("Titolo: " + p.getFilm().getTitolo() + "; sala: " + p.getSala().getNome()
					+ "; supplemento 3D: " + p.getSupplemento3D() + "; occhiali inclusi: " + p.isOcchialiInclusi()
					+ "; prezzo: " + p.calcolaPrezzoFinale());

		} else if (proiezione instanceof EventoSpeciale e) {

			System.out.println("Evento: " + e.getNomeEvento() + "; ospite: " + e.getOspite() + "; posti limitati: "
					+ e.isPostiLimitati() + "; prezzo: " + e.calcolaPrezzoFinale());
		}
	}

	public void ordinaPerDataOra() {
		programmazione.sort((p1, p2) -> p1.getDataOraInizio().compareTo(p2.getDataOraInizio()));
	}

	public void ordinaPerPrezzo() {
		programmazione.sort((p1, p2) -> Double.compare(p1.calcolaPrezzoFinale(), p2.calcolaPrezzoFinale()));
	}

	public void ordinaPerDurata() {
		archivioFilm.trovaTutti().sort((f1, f2) -> Integer.compare(f2.getDurataMinuti(), f1.getDurataMinuti()));
	}

	public Film cercaFilmPerId(int id) {
		return archivioFilm.cercaPerId(id);
	}

	public Sala cercaSalaPerId(int id) {
		return archivioSale.cercaPerId(id);
	}

}
