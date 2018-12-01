package it.unibs.dii.pajc.briscola.client;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class JPanelSchermataIniziale extends JPanel 
{
	BufferedImage immagineCarte = null;
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		FontRenderContext context = g2.getFontRenderContext();
	
		//Disegna l'immagine di copertina
		caricaRisorse();		
		int imageWidth = immagineCarte.getWidth(this);
		int imageHeight = immagineCarte.getWidth(this);		
		g.drawImage(immagineCarte, (getWidth() - imageWidth) / 2, (getHeight() - imageHeight) / 3, this);
		
		//Scrive il titolo BRISCOLA centrato
		String titolo = "BRISCOLA";
		//Font del titolo
		Font fontTitolo = new Font("Times New Roman", Font.BOLD, 72);
		g2.setFont(fontTitolo);		
		//Misurazione della larghezza della stringa
		Rectangle2D boundsTitolo = fontTitolo.getStringBounds(titolo, context);	
		//Setto l'angolo superiore sinistro della stringa
		double xTitolo = (getWidth() - boundsTitolo.getWidth())/2;
		double yTitolo = (getHeight() - boundsTitolo.getHeight())/2;		
		//Necessario per compensare il fatto che la la baseline è presa come punto di riferimento
		double ascent = -boundsTitolo.getY();
		double baseY = yTitolo + ascent;		
		g2.drawString(titolo, (int)xTitolo , (int)baseY);	

		//Scrive *Tap any key to start*
		String messaggio = "In attesa di altri giocatori";
		Font fontMessaggio = new Font("Times New Roman", Font.BOLD, 48);
		g2.setFont(fontMessaggio);		
		Rectangle2D boundsMessaggio = fontMessaggio.getStringBounds(messaggio, context);
		double xMessaggio = (getWidth() - boundsMessaggio.getWidth())/2;
		double yMessaggio = baseY + baseY/2;		
		g2.drawString(messaggio, (int)xMessaggio , (int)yMessaggio);
	}
	
	private void caricaRisorse()
	{
		try
		{
			immagineCarte = ImageIO.read(getClass().getResource("/immagini/carte.jpg"));
		}
		catch (IOException e)
		{
			System.out.println("Immagine alla posizione" + "/immagini/carte.jpg" + "caricata non correttamente");
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
		}
	}
	
	
}