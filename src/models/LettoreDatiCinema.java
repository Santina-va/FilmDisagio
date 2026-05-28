package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

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
	                
	                // Salta righe vuote o commenti
	                if (riga.isEmpty() || riga.startsWith("#")) {
	                    continue;
	                }
	                
	                String[] campi = riga.split(";");
	                
	                // Verifica che ci siano almeno 6 campi
	                if (campi.length < 6) {
	                    System.out.println("[ERRORE] Riga " + rigaNumero
	                                               + " non valida (campi insufficienti): " + riga);
	                    continue;
	                }
	                
	                try {
	                    int id                 = Integer.parseInt(campi[0].trim());
	                    String titolo          = campi[1].trim();
	                    String regista         = campi[2].trim();
	                    int durataMinuti       = Integer.parseInt(campi[3].trim());
	                    LocalDate dataUscita   = LocalDate.parse(campi[4].trim());
	                    String[] generiStringa = campi[5].trim().split(",");
	                    
	                    // Validazioni base
	                    if (titolo.isEmpty()) {
	                        System.out.println("[ERRORE] Riga " + rigaNumero
	                                                   + ": il titolo non può essere vuoto.");
	                        continue;
	                    }
	                    if (regista.isEmpty()) {
	                        System.out.println("[ERRORE] Riga " + rigaNumero
	                                                   + ": il regista non può essere vuoto.");
	                        continue;
	                    }
	                    if (durataMinuti <= 0) {
	                        System.out.println("[ERRORE] Riga " + rigaNumero
	                                                   + ": la durata deve essere maggiore di 0.");
	                        continue;
	                    }
	                    
	                    Film film = new Film(id, titolo, regista, durataMinuti, dataUscita);
	                    
	                    // Aggiunge i generi, ignorando quelli non riconosciuti
	                    for (String g : generiStringa) {
	                        String nomeGenere = g.trim().toUpperCase();
	                        try {
	                            GenereFilm genere = GenereFilm.valueOf(nomeGenere);
	                            film.aggiungiGenere(genere);
	                        } catch (IllegalArgumentException e) {
	                            System.out.println("[AVVISO] Riga " + rigaNumero
	                                                       + ": genere non riconosciuto ignorato: " + nomeGenere);
	                        }
	                    }
	                    
	                    gestore.aggiungiFilm(film);
	                    caricati++;
	                    System.out.println("[OK] Film.txt caricato: " + titolo + " (id=" + id + ")");
	                    
	                } catch (NumberFormatException e) {
	                    System.out.println("[ERRORE] Riga " + rigaNumero
	                                               + ": valore numerico non valido: " + riga);
	                } catch (DateTimeParseException e) {
	                    System.out.println("[ERRORE] Riga " + rigaNumero
	                                               + ": data non valida (formato atteso: yyyy-MM-dd): " + riga);
	                }
	            }
	            
	        } catch (IOException e) {
	            System.out.println("[ERRORE] Impossibile leggere il file: " + percorsoFile
	                                       + " — " + e.getMessage());
	        }
	        
	        System.out.println("Film.txt caricati: " + caricati + " su " + (rigaNumero) + " righe elaborate.");
	        System.out.println();
	    }
	    
	    // -------------------------------------------------------------------------
	    // Caricamento sale
	    // -------------------------------------------------------------------------
	    
	    /**
	     * Legge il file sale.txt e aggiunge ogni sala valida al gestore.
	     *
	     * Formato atteso per ogni riga:
	     * id;nome;numeroPosti;supporta3D;caratteristiche
	     *
	     * Esempio:
	     * 1;Sala IMAX;220;true;IMAX,DOLBY_ATMOS,SCHERMO_GRANDE
	     *
	     * Le caratteristiche multiple sono separate da virgola.
	     * Le righe vuote o non valide vengono ignorate con un messaggio di errore.
	     *
	     * @param percorsoFile path del file sale.txt
	     * @param gestore      istanza di GestoreCinema a cui aggiungere le sale
	     */
	    public void caricaSale(String percorsoFile, GestoreCinema gestore) {
	        System.out.println("=== Caricamento sale da: " + percorsoFile + " ===");
	        int rigaNumero = 0;
	        int caricate = 0;
	        
	        try (BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))) {
	            String riga;
	            
	            while ((riga = reader.readLine()) != null) {
	                rigaNumero++;
	                riga = riga.trim();
	                
	                if (riga.isEmpty() || riga.startsWith("#")) {
	                    continue;
	                }
	                
	                String[] campi = riga.split(";");
	                
	                // Almeno 5 campi richiesti
	                if (campi.length < 5) {
	                    System.out.println("[ERRORE] Riga " + rigaNumero
	                                               + " non valida (campi insufficienti): " + riga);
	                    continue;
	                }
	                
	                try {
	                    int id            = Integer.parseInt(campi[0].trim());
	                    String nome       = campi[1].trim();
	                    int numeroPosti   = Integer.parseInt(campi[2].trim());
	                    boolean supporta3D = Boolean.parseBoolean(campi[3].trim());
	                    String[] caratteristicheStringa = campi[4].trim().split(",");
	                    
	                    // Validazioni base
	                    if (nome.isEmpty()) {
	                        System.out.println("[ERRORE] Riga " + rigaNumero
	                                                   + ": il nome della sala non può essere vuoto.");
	                        continue;
	                    }
	                    if (numeroPosti <= 0) {
	                        System.out.println("[ERRORE] Riga " + rigaNumero
	                                                   + ": il numero posti deve essere maggiore di 0.");
	                        continue;
	                    }
	                    
	                    Sala sala = new Sala(id, nome, numeroPosti, supporta3D);
	                    
	                    // Aggiunge le caratteristiche, ignora quelle vuote
	                    for (String c : caratteristicheStringa) {
	                        String car = c.trim();
	                        if (!car.isEmpty() && !car.equals("-")) {
	                            sala.aggiungiCaratteristica(car);
	                        }
	                    }
	                    
	                    gestore.aggiungiSala(sala);
	                    caricate++;
	                    System.out.println("[OK] Sala caricata: " + nome + " (id=" + id + ")");
	                    
	                } catch (NumberFormatException e) {
	                    System.out.println("[ERRORE] Riga " + rigaNumero
	                                               + ": valore numerico non valido: " + riga);
	                }
	            }
	            
	        } catch (IOException e) {
	            System.out.println("[ERRORE] Impossibile leggere il file: " + percorsoFile
	                                       + " — " + e.getMessage());
	        }
	        
	        System.out.println("Sale caricate: " + caricate + " su " + rigaNumero + " righe elaborate.");
	        System.out.println();
	    }
	    
	    // -------------------------------------------------------------------------
	    // Caricamento proiezioni
	    // -------------------------------------------------------------------------
	    
	    /**
	     * Legge il file proiezioni.txt e aggiunge ogni proiezione valida al gestore.
	     *
	     * ATTENZIONE: film e sale devono essere già stati caricati nel gestore,
	     * perché ogni proiezione fa riferimento a un filmId e a un salaId esistenti.
	     *
	     * Formato atteso per ogni riga:
	     * id;tipo;filmId;salaId;data;oraInizio;prezzoBase;extra1;extra2;extra3;tag
	     *
	     * Valori del campo tipo:
	     *   STANDARD  → ProiezioneStandard  (extra1,extra2,extra3 = "-")
	     *   TRE_D     → Proiezione3D        (extra1=supplemento3D, extra2=occhialiInclusi, extra3="-")
	     *   EVENTO    → EventoSpeciale       (extra1=nomeEvento, extra2=ospite, extra3=postiLimitati)
	     *
	     * Esempi:
	     * 1;STANDARD;1;1;2026-06-10;21:30;10.00;-;-;-;SERALE,WEEKEND
	     * 2;TRE_D;1;3;2026-06-11;20:15;12.00;3.00;true;-;TRE_D,SERALE
	     * 3;EVENTO;2;1;2026-06-12;19:00;14.00;Anteprima speciale;Regista ospite;true;EVENTO,ANTEPRIMA
	     *
	     * @param percorsoFile path del file proiezioni.txt
	     * @param gestore      istanza di GestoreCinema a cui aggiungere le proiezioni
	     */
	    public void caricaProiezioni(String percorsoFile, GestoreCinema gestore) {
	        System.out.println("=== Caricamento proiezioni da: " + percorsoFile + " ===");
	        int rigaNumero = 0;
	        int caricate = 0;
	        
	        try (BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))) {
	            String riga;
	            
	            while ((riga = reader.readLine()) != null) {
	                rigaNumero++;
	                riga = riga.trim();
	                
	                if (riga.isEmpty() || riga.startsWith("#")) {
	                    continue;
	                }
	                
	                // Minimo 11 campi: id;tipo;filmId;salaId;data;ora;prezzoBase;extra1;extra2;extra3;tag
	                String[] campi = riga.split(";");
	                if (campi.length < 11) {
	                    System.out.println("[ERRORE] Riga " + rigaNumero
	                                               + " non valida (campi insufficienti, attesi 11): " + riga);
	                    continue;
	                }
	                
	                try {
	                    int id           = Integer.parseInt(campi[0].trim());
	                    String tipo      = campi[1].trim().toUpperCase();
	                    int filmId       = Integer.parseInt(campi[2].trim());
	                    int salaId       = Integer.parseInt(campi[3].trim());
	                    LocalDate data   = LocalDate.parse(campi[4].trim());
	                    LocalTime ora    = LocalTime.parse(campi[5].trim());
	                    double prezzoBase = Double.parseDouble(campi[6].trim());
	                    String extra1    = campi[7].trim();
	                    String extra2    = campi[8].trim();
	                    String extra3    = campi[9].trim();
	                    String[] tagStringa = campi[10].trim().split(",");
	                    
	                    // Recupera film e sala dagli archivi del gestore
	                    Film film = gestore.getArchivioFilm().cercaPerId(filmId);
	                    Sala sala = gestore.getArchivioSale().cercaPerId(salaId);
	                    
	                    if (film == null) {
	                        System.out.println("[ERRORE] Riga " + rigaNumero
	                                                   + ": film con id=" + filmId + " non trovato. Riga saltata.");
	                        continue;
	                    }
	                    if (sala == null) {
	                        System.out.println("[ERRORE] Riga " + rigaNumero
	                                                   + ": sala con id=" + salaId + " non trovata. Riga saltata.");
	                        continue;
	                    }
	                    
	                    Proiezione proiezione = null;
	                    
	                    switch (tipo) {
	                        case "STANDARD":
	                            proiezione = new ProiezioneStandard(id, film, sala, data, ora, prezzoBase);
	                            break;
	                        
	                        case "TRE_D":
	                            if (extra1.equals("-") || extra2.equals("-")) {
	                                System.out.println("[ERRORE] Riga " + rigaNumero
	                                                           + ": TRE_D richiede extra1=supplemento3D e extra2=occhialiInclusi.");
	                                continue;
	                            }
	                            double supplemento3D   = Double.parseDouble(extra1);
	                            boolean occhialiInclusi = Boolean.parseBoolean(extra2);
	                            proiezione = new Proiezione3D(id, film, sala, data, ora, prezzoBase,
	                                    supplemento3D, occhialiInclusi);
	                            break;
	                        
	                        case "EVENTO":
	                            if (extra1.equals("-")) {
	                                System.out.println("[ERRORE] Riga " + rigaNumero
	                                                           + ": EVENTO richiede almeno extra1=nomeEvento.");
	                                continue;
	                            }
	                            String nomeEvento   = extra1;
	                            // ospite può essere "-" (nessun ospite)
	                            String ospite       = extra2.equals("-") ? "" : extra2;
	                            boolean postiLimitati = Boolean.parseBoolean(extra3);
	                            proiezione = new EventoSpeciale(id, film, sala, data, ora, prezzoBase,
	                                    nomeEvento, ospite, postiLimitati);
	                            break;
	                        
	                        default:
	                            System.out.println("[ERRORE] Riga " + rigaNumero
	                                                       + ": tipo proiezione non riconosciuto: " + tipo);
	                            continue;
	                    }
	                    
	                    // Aggiunge i tag alla proiezione
	                    for (String t : tagStringa) {
	                        String tag = t.trim();
	                        if (!tag.isEmpty() && !tag.equals("-")) {
	                            proiezione.aggiungiTag(tag.toLowerCase());
	                        }
	                    }
	                    
	                    gestore.aggiungiProiezione(proiezione);
	                    caricate++;
	                    System.out.println("[OK] Proiezione caricata: tipo=" + tipo
	                                               + ", film=" + film.getTitolo()
	                                               + ", sala=" + sala.getNome()
	                                               + " (id=" + id + ")");
	                    
	                } catch (NumberFormatException e) {
	                    System.out.println("[ERRORE] Riga " + rigaNumero
	                                               + ": valore numerico non valido: " + riga);
	                } catch (DateTimeParseException e) {
	                    System.out.println("[ERRORE] Riga " + rigaNumero
	                                               + ": data o orario non validi (formati attesi: yyyy-MM-dd / HH:mm): " + riga);
	                }
	            }
	            
	        } catch (IOException e) {
	            System.out.println("[ERRORE] Impossibile leggere il file: " + percorsoFile
	                                       + " — " + e.getMessage());
	        }
	        
	        System.out.println("Proiezioni.txt caricate: " + caricate + " su " + rigaNumero + " righe elaborate.");
	        System.out.println();
	    }

}
