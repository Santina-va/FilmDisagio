package models;

import java.time.LocalDate;
import java.time.LocalTime;

import abstractClass.Proiezione;

public class Proiezione3D extends Proiezione{
	private double supplemento3D;
	private boolean occhialiInclusi;
	
	public Proiezione3D(int id, Film film, Sala sala, LocalDate data, LocalTime oraInizio, double prezzoBase, double supplemento3D, boolean occhialiInclusi) {
		super(id, film, sala, data, oraInizio, prezzoBase);
		this.supplemento3D = supplemento3D > 0 ? supplemento3D : 2.00;
		this.occhialiInclusi = occhialiInclusi;
		
		if (sala != null && !sala.isSupporta3D()) {
			System.out.println("LA SALA " + sala.getNome() + "NON SUPPORTA 3D");
		}
	}
	
	public double getSupplemento3D() {
		return supplemento3D;
	}
	
	public boolean isOcchialiInclusi() {
		return occhialiInclusi;
	}

	@Override
	public double calcolaPrezzoFinale() {
		double prezzoFinale = prezzoBase;
		
		prezzoFinale += supplemento3D;
		
		if (isNelWeekend()) {
			prezzoFinale += 2.00;
		}
		
		if (!occhialiInclusi) {
			prezzoFinale += 1.00;
		}
		
		return prezzoFinale;
	}

	@Override
	public String getTipoProiezione() {
		return "TRE_D";
	}

}
