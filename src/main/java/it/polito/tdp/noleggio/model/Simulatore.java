package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.PriorityQueue;

import it.polito.tdp.noleggio.model.Event.EventType;

public class Simulatore {
	
	//PARAMETRI IN INGRESSO
	
	private int NC; //NUMERO CARS
	private Duration T_NC = Duration.ofMinutes(10); //tempo arrivo tra un cliente e l'altro
	private Duration T_TRAVEL = Duration.ofHours(1); //1 2 o 3 volte tanto 
	
	//VALORI CALCOLATI IN USCITA
	private int nClientiTot;
	private int nClientiInsoddisfatti;
	
	//VARIABILI CHE RAPPRESENTANO LO STATO DEL SISTEMA -> UTILE PER SAPERE QUANTE AUTO HO IN OGNI ISTANTE
	private int autoDisponibili;
	//CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;
	
	public Simulatore(int NC) {
		this.NC = NC;
		this.queue = new PriorityQueue<Event>();
		this.autoDisponibili = NC;
	}

	public void run() { //ESEGUO  LA SIMULAZIONE
		//FINCHE' LA CODA NON E' VUOTA HO QUALCOSA DA FARE
		while(!queue.isEmpty()) {
			Event e = this.queue.poll(); //ottiene e rimuove la TESTA della coda (NULL se è vuota)
			processEvent(e); //LAVORA SULL'EVENTO
		}
	}
	
	//PRIMA DI PROCESSARE TUTTO POPOLO LA CODA DEGLI EVENTI
	public void caricaEventi() {
		LocalTime ora = LocalTime.of(8, 0); //inserisco eventi a partir da un'ora di partenza: 8 del mattino
		while(ora.isBefore(LocalTime.of(16,0))){
			//finché l'ora è precedente all'orario di chiusura aggiungo un cliente
			this.queue.add(new Event(ora, EventType.NUOVO_CLIENTE)); // FINCHE' SONO NEL RANGE TEMPORALE, AGGIUNGO UN NUOVO CLIENTE 
			//INSERISCO COME TIPO DI EVENTO: NUOVO_CLIENTE
			
		}
	}
	
	
	private void processEvent(Event e) {
		//IL TEMPO PUO' ESSERE DI DUE TIPI QUINDI USO LO SWITCH PER EFFETTUARE OPERAZIONI DIVERSE IN BASE AL TIPO
		switch(e.getType()) {
		 
		case NUOVO_CLIENTE: //NON SO SE HO O MENO UN'AUTO DA DARGLI -> DIPENDE DALLO STATO DEL MONDO
			if(this.autoDisponibili>0) {
				this.autoDisponibili--;
				
				int ore = (int)(Math.random()*3)+1; //VOGLIO UN NUMERO TRA 1 E 3 (Math.random() MI RESTITUISCE UN NUMERO TRA 0 E 1) -> 0/1 * 3 + 1 MI DA UN MINUMO DI 1 E UN MAX DI 4
				
				LocalTime oraRientro = e.getTime().plus(Duration.ofHours(ore*T_TRAVEL.toHours())); //prendo la durata in ore che ho calcolato casualmente moltiplicando l'ora per 1/2/3
				this.queue.add(new Event(oraRientro, EventType.AUTO_RESTITUITA));
				//SE HO UN AUTO DA DARGLI -> NUMERO CLIENTI SODDISFATTI AUMENTA 
			}
			break;
			
		case AUTO_RESTITUITA:
			this.autoDisponibili++;
			break;
		}
		
	}

	public int getNC() {
		return NC;
	}

	public void setNC(int nC) {
		NC = nC;
	}

	public Duration getT_NC() {
		return T_NC;
	}

	public void setT_NC(Duration t_NC) {
		T_NC = t_NC;
	}

	public Duration getT_TRAVEL() {
		return T_TRAVEL;
	}

	public void setT_TRAVEL(Duration t_TRAVEL) {
		T_TRAVEL = t_TRAVEL;
	}

	public int getnClientiTot() {
		return nClientiTot;
	}

	public int getnClientiInsoddisfatti() {
		return nClientiInsoddisfatti;
	}

	public int getAutoDisponibili() {
		return autoDisponibili;
	}

	public PriorityQueue<Event> getQueue() {
		return queue;
	}

}
