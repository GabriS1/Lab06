package it.polito.tdp.meteo.model;

public class TestModel {

	public static void main(String[] args) {
		
		System.out.println("ciao");
		
		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(7));
		
		System.out.println(m.trovaSequenza(7));
		

	}

}
