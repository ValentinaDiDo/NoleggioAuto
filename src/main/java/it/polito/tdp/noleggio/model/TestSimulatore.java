  package it.polito.tdp.noleggio.model;

public class TestSimulatore {

	public static void main(String[] args) {
		//PASSO IL NUMERO DI AUTO CHE VOGLIO SIMULARE
		/* - 6 CLIENTI ALL'ORA
		   - 2 ORE DI NOLEGGIO IN MEDIA
		12 AUTO SONO UN NUMERO RAGIONEVOLE*/
		System.out.println("BU");
		
		Simulatore sim = new Simulatore(12);

		sim.caricaEventi();
		sim.run();
		System.out.println(
				"Clienti: " + sim.getnClientiTot() + " di cui " + 
				sim.getnClientiInsoddisfatti() + " insoddisfatti\n");
		
	}

}
