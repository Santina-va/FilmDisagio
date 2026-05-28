package models;

import java.util.HashSet;
import java.util.Set;

import interfaces.Identificabile;

public class Sala implements Identificabile {

	private int id;
	private String nome;
	private int numeroPosti;
	private boolean supporta3D;
	private Set<String> caratteristiche;

	public Sala(int id, String nome, int numeroPosti, boolean supporta3D) {
		this.id = id;

		this.nome = (nome != null && !nome.trim().isEmpty()) ? nome : "Sala anonima";
		this.numeroPosti = numeroPosti > 0 ? numeroPosti : 1;
		this.supporta3D = supporta3D;
		this.caratteristiche = new HashSet<>();

	}

	@Override
	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getNumeroPosti() {
		return numeroPosti;
	}

	public boolean isSupporta3D() {
		return supporta3D;
	}

	public Set<String> getCaratteristiche() {
		return caratteristiche;
	}

	public void aggiungiCaratteristica(String caratteristica) {
		if (caratteristica != null && !caratteristica.trim().isEmpty()) {
			caratteristiche.add(caratteristica.trim());
		}
	}

	public boolean haCaratteristica(String caratteristica) {
		if (caratteristica == null)
			return false;
		return caratteristiche.contains(caratteristica.trim());
	}

	public String getDescrizione() {
		return String.format("Sala: %s (Posti: %d, Supporto 3D: %b, Servizi: %s)", nome, numeroPosti, supporta3D,
				caratteristiche);
	}

}
