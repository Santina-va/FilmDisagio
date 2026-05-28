package models;

import java.time.LocalDate;
import java.time.LocalTime;

import abstractClass.Proiezione;

public class ProiezioneStandard extends Proiezione{
	
	public ProiezioneStandard(int id, Film film, Sala sala, LocalDate data, LocalTime oraInizio, double prezzoBase) {
		super(id, film, sala, data, oraInizio, prezzoBase);
	}

	@Override
	public double calcolaPrezzoFinale() {
		double prezzoFinale = prezzoBase;
		if (isNelWeekend()) {
			prezzoFinale += 2.00;
		}
		
		if (isSerale()) {
			prezzoFinale += 1.50;
		}
		
		return prezzoFinale;
	}

	@Override
	public String getTipoProiezione() {
		return "standard";
	}
	
	

}
