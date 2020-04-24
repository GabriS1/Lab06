package it.polito.tdp.meteo.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	MeteoDAO meteoDAO;

	private List<Citta> citta = new LinkedList<>(); // PARTENZA, List poichè mi interessa l'ordine delle città-->nella
													// ricorsione devo prenderli in ordine

	int bestSum = 100000;
	int sum = 0;
	List<Citta> soluzione = new LinkedList<>();

	public Model() {
		meteoDAO = new MeteoDAO();
		citta.add(new Citta("Torino"));
		citta.add(new Citta("Milano"));
		citta.add(new Citta("Genova"));
	}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {

		String s;
		String meseDopo;

		if (mese > 9) {
			s = "2013-" + String.valueOf(mese) + "-01";
			meseDopo = "2013-" + String.valueOf(mese) + "-31";
		} else {
			s = "2013-" + "0" + String.valueOf(mese) + "-01";
			meseDopo = "2013-" + "0" + String.valueOf(mese) + "-31";
		}
		citta.get(0).setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(s, "Torino", meseDopo));
		citta.get(1).setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(s, "Milano", meseDopo));
		citta.get(2).setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(s, "Genova", meseDopo));

		String s1 = citta.get(0).getNome() + " " + String.valueOf(citta.get(0).setMedia());
		String s2 = citta.get(1).getNome() + " " + String.valueOf(citta.get(1).setMedia());
		String s3 = citta.get(2).getNome() + " " + String.valueOf(citta.get(2).setMedia());

		return s1 + "\n" + s2 + "\n" + s3;
	}

	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) {

		String s;
		String meseDopo;
		if (mese > 9) {
			s = "2013-" + String.valueOf(mese) + "-01";
			meseDopo = "2013-" + String.valueOf(mese) + "-31";
		} else {
			s = "2013-" + "0" + String.valueOf(mese) + "-01";
			meseDopo = "2013-" + "0" + String.valueOf(mese) + "-31";
		}
		citta.get(0).setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(s, "Torino", meseDopo));
		citta.get(1).setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(s, "Milano", meseDopo));
		citta.get(2).setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(s, "Genova", meseDopo));

		int L = 0;
		List<Citta> parziale = new LinkedList<>();

		ricorsione(L, parziale, citta); // citta==disponibili
		

		return soluzione;
	}

	public void ricorsione(int L, List<Citta> parziale, List<Citta> disponibili) {

		if (parziale.size() > 0) {
			if (parziale.get(parziale.size() - 1).getCounter() > 6) {
				return;
			}
		}
		
		if (L == 15) { // terminazione
			sum = calcolaSomma(parziale);
			if (sum < bestSum) {
				soluzione = new LinkedList<>(parziale);
				bestSum = sum;
			}
			return;
		}


		for (Citta c : disponibili) {
			if (parziale.size() == 0) {
				parziale.add(c);
				c.setCounter(c.getCounter() + 1);
				ricorsione(L + 1, parziale, disponibili);
				parziale.remove(L);
				c.setCounter(c.getCounter() - 1);
			}

			if (parziale.size() != 0) {
				if (parziale.get(parziale.size() - 1).getCounter() < 3) {
					if (parziale.get(parziale.size() - 1).getNome().compareTo(c.getNome()) == 0) { // mi prende la citta
																									// c che è =
																									// all'ultima citta
																									// in parziale
						parziale.add(c);
						c.setCounter(c.getCounter() + 1);
						ricorsione(L + 1, parziale, disponibili);
						parziale.remove(L);
						c.setCounter(c.getCounter() - 1);
					}
				} else {
					parziale.add(c);
					c.setCounter(c.getCounter() + 1);
					ricorsione(L + 1, parziale, disponibili);
					parziale.remove(L);
					c.setCounter(c.getCounter() - 1);
				}
			}
		}

	}

	private int calcolaSomma(List<Citta> parziale) {
		int sum = 0;
		for (int i = 0; i < parziale.size(); i++) {
			sum += parziale.get(i).getRilevamenti().get(i).getUmidita();
		}

		for (int i = 1; i < parziale.size(); i++) {
			if (parziale.get(i).getNome().compareTo(parziale.get(i - 1).getNome()) != 0) {
				sum += 100;
			}
		}

		return sum;
	}

}
