package models;

import java.time.LocalDate;
import java.time.LocalTime;

import abstractClass.Proiezione;

public class EventoSpeciale extends Proiezione {
	
	private String nomeEvento;
	private String ospite;
	private boolean postiLimitati;

	public EventoSpeciale(int id, Film film, Sala sala, LocalDate data, LocalTime oraInizio, double prezzoBase,
			String nomeEvento, String ospite, boolean postiLimitati) {
		super(id, film, sala, data, oraInizio, prezzoBase);
		this.nomeEvento = (nomeEvento != null && !nomeEvento.trim().isEmpty()) ? nomeEvento : "Evento Speciale";
		this.ospite = (ospite != null && !ospite.trim().isEmpty()) ? ospite : "Nessuno";
		this.postiLimitati = postiLimitati;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public String getOspite() {
		return ospite;
	}

	public boolean isPostiLimitati() {
		return postiLimitati;
	}

	@Override
	public double calcolaPrezzoFinale() {
		double prezzoFinale = prezzoBase;

		prezzoFinale += 5.00;

		if (postiLimitati) {
			prezzoFinale += 3.00;
		}

		if (isSerale()) {
			prezzoFinale += 1.50;
		}

		return prezzoFinale;
	}

	@Override
	public String getTipoProiezione() {
		return "evento";
	}

}
