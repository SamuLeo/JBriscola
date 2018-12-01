package it.unibs.dii.pajc.briscola.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * Questa classe è una classe di utility del client, il suo scopo è caricare le immagini di tutte le carte, inserendole in un
 * HashMap grazie alla quale è possibile chiedere alla classe tramite il codice l'immagine della carta associata
 * @author Samuele Leone
 *
 */

public class GestoreImmaginiCarte 
{
	HashMap<Integer, BufferedImage> carte = null;
	
	BufferedImage retroCarte = null;
	
	BufferedImage assoDiBastoni = null;
	BufferedImage dueDiBastoni = null;
	BufferedImage treDiBastoni = null;
	BufferedImage quattroDiBastoni = null;
	BufferedImage cinqueDiBastoni = null;
	BufferedImage seiDiBastoni = null;
	BufferedImage setteDiBastoni = null;
	BufferedImage fanteDiBastoni = null;
	BufferedImage cavalloDiBastoni = null;
	BufferedImage reDiBastoni = null;
				
	BufferedImage assoDiSpade = null;
	BufferedImage dueDiSpade = null;
	BufferedImage treDiSpade = null;
	BufferedImage quattroDiSpade = null;
	BufferedImage cinqueDiSpade = null;
	BufferedImage seiDiSpade = null;
	BufferedImage setteDiSpade = null;
	BufferedImage fanteDiSpade = null;
	BufferedImage cavalloDiSpade = null;
	BufferedImage reDiSpade = null;
			
	BufferedImage assoDiCoppe = null;
	BufferedImage dueDiCoppe = null;
	BufferedImage treDiCoppe = null;
	BufferedImage quattroDiCoppe = null;
	BufferedImage cinqueDiCoppe = null;
	BufferedImage seiDiCoppe = null;
	BufferedImage setteDiCoppe = null;
	BufferedImage fanteDiCoppe = null;
	BufferedImage cavalloDiCoppe = null;
	BufferedImage reDiCoppe = null;
			
	BufferedImage assoDiDenari = null;
	BufferedImage dueDiDenari = null;
	BufferedImage treDiDenari = null;
	BufferedImage quattroDiDenari = null;
	BufferedImage cinqueDiDenari = null;
	BufferedImage seiDiDenari = null;
	BufferedImage setteDiDenari = null;
	BufferedImage fanteDiDenari = null;
	BufferedImage cavalloDiDenari = null;
	BufferedImage reDiDenari = null;
	
	/**
	 * Il costruttore della classe carica le carte associandole al corrispettivo oggetto BufferedImage tramite caricaImmaginiCarte(),
	 * inizializza l'HashMap e tramite associaCodiceImmagine() inserisce le BufferedImage nell'HashMap
	 */
	
	public GestoreImmaginiCarte()
	{		
		caricaImmaginiCarte();
		
		carte = new HashMap<>();
		
		associaCodiceImmagine();
	}
	
	/**
	 * Questo metodo inserisce le BufferedImage nell'HashMap, associandole in un ordine condorde a quello dell'Enum SemeCarta
	 */
	
	private void associaCodiceImmagine()
	{	
		carte.put(0, retroCarte);
		
		carte.put(1, assoDiBastoni);
		carte.put(2, dueDiBastoni);
		carte.put(3, treDiBastoni);
		carte.put(4, quattroDiBastoni);
		carte.put(5, cinqueDiBastoni);
		carte.put(6, seiDiBastoni);
		carte.put(7, setteDiBastoni);
		carte.put(8, fanteDiBastoni);
		carte.put(9, cavalloDiBastoni);
		carte.put(10, reDiBastoni);
		
		carte.put(11, assoDiSpade);
		carte.put(12, dueDiSpade);
		carte.put(13, treDiSpade);
		carte.put(14, quattroDiSpade);
		carte.put(15, cinqueDiSpade);
		carte.put(16, seiDiSpade);
		carte.put(17, setteDiSpade);
		carte.put(18, fanteDiSpade);
		carte.put(19, cavalloDiSpade);
		carte.put(20, reDiSpade);
		
		carte.put(21, assoDiCoppe);
		carte.put(22, dueDiCoppe);
		carte.put(23, treDiCoppe);
		carte.put(24, quattroDiCoppe);
		carte.put(25, cinqueDiCoppe);
		carte.put(26, seiDiCoppe);
		carte.put(27, setteDiCoppe);
		carte.put(28, fanteDiCoppe);
		carte.put(29, cavalloDiCoppe);
		carte.put(30, reDiCoppe);
		
		carte.put(31, assoDiDenari);
		carte.put(32, dueDiDenari);
		carte.put(33, treDiDenari);
		carte.put(34, quattroDiDenari);
		carte.put(35, cinqueDiDenari);
		carte.put(36, seiDiDenari);
		carte.put(37, setteDiDenari);
		carte.put(38, fanteDiDenari);
		carte.put(39, cavalloDiDenari);
		carte.put(40, reDiDenari);
	}
	
	/**
	 * Questo metodo inizializza le BufferedImage concordemente al loro nome
	 */
	
	private void caricaImmaginiCarte()
	{	
		retroCarte = caricaImmagine("/immagini/Piacentine/retroCarte.png");
		
		assoDiBastoni = caricaImmagine("/immagini/Piacentine/Bastoni(1).png");
		dueDiBastoni = caricaImmagine("/immagini/Piacentine/Bastoni(2).png");
		treDiBastoni = caricaImmagine("/immagini/Piacentine/Bastoni(3).png");
		quattroDiBastoni = caricaImmagine("/immagini/Piacentine/Bastoni(4).png");
		cinqueDiBastoni = caricaImmagine("/immagini/Piacentine/Bastoni(5).png");
		seiDiBastoni = caricaImmagine("/immagini/Piacentine/Bastoni(6).png");
		setteDiBastoni = caricaImmagine("/immagini/Piacentine/Bastoni(7).png");
		fanteDiBastoni = caricaImmagine("/immagini/Piacentine/Bastoni(8).png");
		cavalloDiBastoni = caricaImmagine("/immagini/Piacentine/Bastoni(9).png");
		reDiBastoni= caricaImmagine("/immagini/Piacentine/Bastoni(10).png");
		
		assoDiSpade = caricaImmagine("/immagini/Piacentine/Spade(1).png");
		dueDiSpade = caricaImmagine("/immagini/Piacentine/Spade(2).png");
		treDiSpade = caricaImmagine("/immagini/Piacentine/Spade(3).png");
		quattroDiSpade = caricaImmagine("/immagini/Piacentine/Spade(4).png");
		cinqueDiSpade = caricaImmagine("/immagini/Piacentine/Spade(5).png");
		seiDiSpade = caricaImmagine("/immagini/Piacentine/Spade(6).png");
		setteDiSpade = caricaImmagine("/immagini/Piacentine/Spade(7).png");
		fanteDiSpade = caricaImmagine("/immagini/Piacentine/Spade(8).png");
		cavalloDiSpade = caricaImmagine("/immagini/Piacentine/Spade(9).png");
		reDiSpade= caricaImmagine("/immagini/Piacentine/Spade(10).png");
		
		assoDiCoppe = caricaImmagine("/immagini/Piacentine/Coppe(1).png");
		dueDiCoppe = caricaImmagine("/immagini/Piacentine/Coppe(2).png");
		treDiCoppe = caricaImmagine("/immagini/Piacentine/Coppe(3).png");
		quattroDiCoppe = caricaImmagine("/immagini/Piacentine/Coppe(4).png");
		cinqueDiCoppe = caricaImmagine("/immagini/Piacentine/Coppe(5).png");
		seiDiCoppe = caricaImmagine("/immagini/Piacentine/Coppe(6).png");
		setteDiCoppe = caricaImmagine("/immagini/Piacentine/Coppe(7).png");
		fanteDiCoppe = caricaImmagine("/immagini/Piacentine/Coppe(8).png");
		cavalloDiCoppe = caricaImmagine("/immagini/Piacentine/Coppe(9).png");
		reDiCoppe= caricaImmagine("/immagini/Piacentine/Coppe(10).png");
		
		assoDiDenari = caricaImmagine("/immagini/Piacentine/Denari(1).png");
		dueDiDenari = caricaImmagine("/immagini/Piacentine/Denari(2).png");
		treDiDenari = caricaImmagine("/immagini/Piacentine/Denari(3).png");
		quattroDiDenari = caricaImmagine("/immagini/Piacentine/Denari(4).png");
		cinqueDiDenari = caricaImmagine("/immagini/Piacentine/Denari(5).png");
		seiDiDenari = caricaImmagine("/immagini/Piacentine/Denari(6).png");
		setteDiDenari = caricaImmagine("/immagini/Piacentine/Denari(7).png");
		fanteDiDenari = caricaImmagine("/immagini/Piacentine/Denari(8).png");
		cavalloDiDenari = caricaImmagine("/immagini/Piacentine/Denari(9).png");
		reDiDenari= caricaImmagine("/immagini/Piacentine/Denari(10).png");
		
		System.out.println("CLIENT-LOG : Immagini delle carte caricate correttamente");
	}
	
	/**
	 * Questo metodo ritorna la BufferedImage associata ad un dato path
	 * @param imagePath percorso in cui si trova l'immagine
	 * @return la BufferedImage associata a imagePath
	 */
	
	private BufferedImage caricaImmagine(String imagePath)
	{
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(getClass().getResource(imagePath));
		}
		catch (IOException e)
		{
			System.out.println("Immagine alla posizione" + imagePath + "caricata non correttamente");
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
		}
		
		return image;
	}
	
	/**
	 * Questo metodo serve ad interrogare l'HashMap tramite il codice carta
	 * @param codiceCarta il codice della carta di cui si vuole ricavare la BufferedImage
	 * @return la BufferedImage associata al codice codiceCarta 
	 */
	
	public BufferedImage getImmagineCarta(int codiceCarta)
	{
		if(codiceCarta < 0 || codiceCarta > 40)
			return null;
		
		if(carte.isEmpty())
			return null;
		
		return carte.get(codiceCarta);
	}
}
