package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;

import abstractClass.Proiezione;
import enumeration.GenereFilm;
import gestoreCinema.GestoreCinema;

public class LettoreDatiCinema {

	public void caricaFilm(String percorsoFile, GestoreCinema gestore) {

		System.out.println("=== Caricamento film da: " + percorsoFile + " ===");

		int rigaNumero = 0;
		int caricati = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))) {

			String riga;

			while ((riga = reader.readLine()) != null) {
				rigaNumero++;
				riga = riga.trim();

				if (riga.isEmpty() || riga.startsWith("#")) {
					continue;
				}

				String[] campi = riga.split(";");

				if (campi.length < 6) {
					System.out.println("[ERRORE] Riga " + rigaNumero + " non valida: " + riga);
					continue;
				}

				try {
					int id = Integer.parseInt(campi[0].trim());
					String titolo = campi[1].trim();
					String regista = campi[2].trim();
					int durata = Integer.parseInt(campi[3].trim());
					LocalDate dataUscita = LocalDate.parse(campi[4].trim());
					String[] generi = campi[5].split(",");

					if (titolo.isEmpty() || regista.isEmpty() || durata <= 0) {
						System.out.println("[ERRORE] Riga " + rigaNumero + " dati non validi");
						continue;
					}

					Film film = new Film(id, titolo, regista, durata, dataUscita, new HashSet<>());

					for (String g : generi) {
						try {
							GenereFilm genere = GenereFilm.valueOf(g.trim().toUpperCase());
							film.aggiungiGenere(genere);
						} catch (IllegalArgumentException e) {
							System.out.println("[AVVISO] genere ignorato: " + g);
						}
					}

					gestore.aggiungiFilm(film);
					caricati++;

				} catch (Exception e) {
					System.out.println("[ERRORE] Riga " + rigaNumero + ": " + riga);
				}
			}

		} catch (IOException e) {
			System.out.println("[ERRORE] File non trovato: " + e.getMessage());
		}

		System.out.println("Film caricati: " + caricati);
	}

	// CARICAMENTO SALE
	public void caricaSale(String percorsoFile, GestoreCinema gestore) {

		System.out.println("=== Caricamento sale ===");

		int rigaNumero = 0;
		int caricate = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))) {

			String riga;

			while ((riga = reader.readLine()) != null) {
				rigaNumero++;
				riga = riga.trim();

				if (riga.isEmpty() || riga.startsWith("#"))
					continue;

				String[] campi = riga.split(";");

				if (campi.length < 5) {
					System.out.println("[ERRORE] Riga " + rigaNumero);
					continue;
				}

				try {
					int id = Integer.parseInt(campi[0].trim());
					String nome = campi[1].trim();
					int posti = Integer.parseInt(campi[2].trim());
					boolean supporta3D = Boolean.parseBoolean(campi[3].trim());
					String[] caratteristiche = campi[4].split(",");

					Sala sala = new Sala(id, nome, posti, supporta3D);

					for (String c : caratteristiche) {
						String car = c.trim();
						if (!car.equals("-") && !car.isEmpty()) {
							sala.aggiungiCaratteristica(car);
						}
					}

					gestore.aggiungeSala(sala);
					caricate++;

				} catch (Exception e) {
					System.out.println("[ERRORE] Riga sale: " + riga);
				}
			}

		} catch (IOException e) {
			System.out.println("[ERRORE] " + e.getMessage());
		}

		System.out.println("Sale caricate: " + caricate);
	}

	// CARICAMENTO PROIEZIONI
	public void caricaProiezioni(String percorsoFile, GestoreCinema gestore) {

		System.out.println("=== Caricamento proiezioni ===");

		int rigaNumero = 0;
		int caricate = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))) {

			String riga;

			while ((riga = reader.readLine()) != null) {
				rigaNumero++;
				riga = riga.trim();

				if (riga.isEmpty() || riga.startsWith("#"))
					continue;

				String[] campi = riga.split(";");

				if (campi.length < 11) {
					System.out.println("[ERRORE] Riga " + rigaNumero);
					continue;
				}

				try {
					int id = Integer.parseInt(campi[0].trim());
					String tipo = campi[1].trim().toUpperCase();
					int filmId = Integer.parseInt(campi[2].trim());
					int salaId = Integer.parseInt(campi[3].trim());
					LocalDate data = LocalDate.parse(campi[4].trim());
					LocalTime ora = LocalTime.parse(campi[5].trim());
					double prezzo = Double.parseDouble(campi[6].trim());

					String extra1 = campi[7].trim();
					String extra2 = campi[8].trim();
					String extra3 = campi[9].trim();
					String[] tags = campi[10].split(",");

					Film film = gestore.cercaFilmPerId(filmId);
					Sala sala = gestore.cercaSalaPerId(salaId);

					if (film == null || sala == null)
						continue;

					Proiezione p;

					switch (tipo) {

					case "STANDARD":
						p = new ProiezioneStandard(id, film, sala, data, ora, prezzo);
						break;

					case "TRE_D":
						p = new Proiezione3D(id, film, sala, data, ora, prezzo, Double.parseDouble(extra1),
								Boolean.parseBoolean(extra2));
						break;

					case "EVENTO":
						p = new EventoSpeciale(id, film, sala, data, ora, prezzo, extra1,
								extra2.equals("-") ? "" : extra2, Boolean.parseBoolean(extra3));
						break;

					default:
						continue;
					}

					for (String t : tags) {
						if (!t.trim().isEmpty()) {
							p.aggiungiTag(t.trim().toLowerCase());
						}
					}

					// ✔ FIX FINALE
					gestore.aggiungiProiezione(p);

					caricate++;

				} catch (Exception e) {
					System.out.println("[ERRORE] Riga proiezione: " + riga);
				}
			}

		} catch (IOException e) {
			System.out.println("[ERRORE] " + e.getMessage());
		}

		System.out.println("Proiezioni caricate: " + caricate);
	}

}
