package it.unibs.dii.pajc.briscola.utility;

public class MessaggiDiServizio 
{
	//Errori Client
	
	//ThreadMain
	public static final String ERRORE_MAIN_CLIENT   = "ERRORE-CLIENT-MAIN-COD0 : Errore in ThreadClient";
	
	//GestoreClient
	//Errori
	public static final String ERRORE_CHIUSURA_STREAM_CLIENT = "ERROR-CLIENT-THREAD-COD1 : Chiusura Stream fallita";
	public static final String ERRORE_INVIO_COMUNICAZIONE_C_S = "ERROR-CLIENT-THREAD-COD2: Impossibilità di inviare la comunicazione al Server";
	public static final String ERRORE_INVIO_MESSAGGIO_DATI_C_S = "ERROR-CLIENT-THREAD-COD3: Impossibilità di inviare il messaggio dati al Server";
	//Log
	public static final String LOG_GESTORECLIENT_STREAM_CREATI = "CLIENT-LOG-GESTORECLIENT : Stream creati";
	public static final String LOG_GESTORECLIENT_CHIUSURA_CONNESSIONE = "CLIENT-LOG-GESTORECLIENT : Connesione con il Server Interrotta";
	//JLayeredPanelPartita
	public static final String ERRORE_OVERFLOW_TURNI = "ERROR-CLIENT-PANEL_PARTITA-COD4 - Overflow numero turni";
	public static final String ERRORE_CARICAMENTO_RETRO_IMMAGINI = "ERROR-CLIENT-PANEL_PARTITA-COD5 : Errore nel caricamento retro carte da gioco";
	
	
	
	//Errori Server
	
	//MainServer
	public static final String ERRORE_SOCKET = "ERROR-SERVER-MAIN-COD1: MALFUNZIONAMENTO SOCKET";
	public static final String ERRORE_CREAZIONE_GUI   = "ERROR-SERVER-MAIN-COD2 : Fallita creazione interfaccia grafica";
	//ThreadGiocatore
	public static final String ERRORE_CHIUSURA_STREAM_SERVER = "ERROR-SERVER-GIOCATORE-COD3 : Chiusura Stream fallita";
	public static final String ERRORE_INVIO_MESSAGGIO_DATI_S_C = "ERROR-SERVER-GIOCATORE-COD4 : Impossibilità di inviare il messaggio dati al Client";
	public static final String ERRORE_RICEZIONE_MESSAGGI = "ERROR-SERVER-GIOCATORE-COD5 : Errore nel ricevere il messaggio dal Client";
	public static final String ERRORE_INVIO_COMUNICAZIONE_S_C = "ERROR-SERVER-GIOCATORE-COD6 : Impossibilità di inviare la comunicazione al Client";	
	public static final String ERRORE_WAIT = "ERROR-SERVER-PARTITA-COD7 : Wait ha generato un errore";
	//ThreadGiocatore
	//Log
	public static final String LOG_MESSAGGIO_RICEVUTO_DA_G1 = "LOG-PARTITA : Messaggio arrivato dal giocatore 1 : ";
	public static final String LOG_MESSAGGIO_RICEVUTO_DA_G2 = "LOG-PARTITA : Messaggio arrivato dal giocatore 2 : ";

	//Errori
	public static final String ERRORE_INVIO_MESSAGGI = "ERROR-SERVER-PARTITA-COD8 : Fallimento invio messaggi a causa interrupt() dei ThreadGiocatore";
	//Errori
	//Comunicazioni di servizio
	public static final String PARTITA_INIZIATA = "S_C COD1" ;
	public static final String CHIUSURA_CONNESSIONE = "COD1 - Chiusura Connessione";
}
