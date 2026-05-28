package archivio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.Identificabile;

public class Archivio <T extends Identificabile> {

	private Map <Integer, T> elementi = new HashMap<>();
	private static int contatoreId = 0;
	
	public void aggiungi(T elemento) {
		if (elemento != null) {
		elementi.put(contatoreId, elemento);
		contatoreId++;
		System.out.println("Elemento aggiunto correttamente.");
		}
		else
			System.out.println("Elemento non valido.");
	}
	
	public T cercaPerId(int id) {
		return elementi.get(id);
	}
	
	public boolean rimuoviPerId(int id) {
		if (elementi.get(id) != null) {
			elementi.remove(id);
			System.out.println("Elemento rimosso correttamente.");
			return true;
		}
		
		System.out.println("Elemento non valido.");
		return false;
	}
	
	public List<T> trovaTutti() {
		return elementi != null ? (List<T>) elementi.values() : new ArrayList<>();
	}
	
	public int contaElementi() {
		return elementi != null ? elementi.size() : 0;
	}
}
