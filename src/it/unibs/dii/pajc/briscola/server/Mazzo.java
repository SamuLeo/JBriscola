package it.unibs.dii.pajc.briscola.server;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import it.unibs.dii.pajc.briscola.utility.Carta;
import it.unibs.dii.pajc.briscola.utility.SemeCarta;

public class Mazzo 
{
	private Stack<Carta> mazzo;

	public Mazzo()
	{
		mazzo = new Stack<>();
	}
	
	public void inizializzaMazzo()
	{		
		for(int i=0 ; i<4 ; i++)
		{	
			for(int j=1 ; j<=10 ; j++)
			{
				if(i == 0)
				{
					if(j == 1) mazzo.push(new Carta(SemeCarta.BASTONI, j, 11));				
					if(j == 2 || (j >= 4 && j <= 7)) mazzo.push(new Carta(SemeCarta.BASTONI, j, 0));
					if(j == 3) mazzo.push(new Carta(SemeCarta.BASTONI, j, 10));	
					if(j == 8) mazzo.push(new Carta(SemeCarta.BASTONI, j, 2));	
					if(j == 9) mazzo.push(new Carta(SemeCarta.BASTONI, j, 4));	
					if(j == 10) mazzo.push(new Carta(SemeCarta.BASTONI, j, 4));	
				}
				
				if(i == 1)
				{
					if(j == 1) mazzo.push(new Carta(SemeCarta.SPADE, j, 11));				
					if(j == 2 || (j >= 4 && j <= 7)) mazzo.push(new Carta(SemeCarta.SPADE, j, 0));
					if(j == 3) mazzo.push(new Carta(SemeCarta.SPADE, j, 10));	
					if(j == 8) mazzo.push(new Carta(SemeCarta.SPADE, j, 2));	
					if(j == 9) mazzo.push(new Carta(SemeCarta.SPADE, j, 4));	
					if(j == 10) mazzo.push(new Carta(SemeCarta.SPADE, j, 4));	
				}
				
				if(i == 2)
				{
					if(j == 1) mazzo.push(new Carta(SemeCarta.COPPE, j, 11));				
					if(j == 2 || (j >= 4 && j <= 7)) mazzo.push(new Carta(SemeCarta.COPPE, j, 0));
					if(j == 3) mazzo.push(new Carta(SemeCarta.COPPE, j, 10));	
					if(j == 8) mazzo.push(new Carta(SemeCarta.COPPE, j, 2));	
					if(j == 9) mazzo.push(new Carta(SemeCarta.COPPE, j, 4));	
					if(j == 10) mazzo.push(new Carta(SemeCarta.COPPE, j, 4));	
				}
				
				if(i == 3)
				{
					if(j == 1) mazzo.push(new Carta(SemeCarta.DENARI, j, 11));				
					if(j == 2 || (j >= 4 && j <= 7)) mazzo.push(new Carta(SemeCarta.DENARI, j, 0));
					if(j == 3) mazzo.push(new Carta(SemeCarta.DENARI, j, 10));	
					if(j == 8) mazzo.push(new Carta(SemeCarta.DENARI, j, 2));	
					if(j == 9) mazzo.push(new Carta(SemeCarta.DENARI, j, 4));	
					if(j == 10) mazzo.push(new Carta(SemeCarta.DENARI, j, 4));	
				}
			}			
		}
		
		mescolaMazzo();
	}
	
	public void mescolaMazzo()
	{
		if(!mazzo.isEmpty())
			Collections.shuffle(mazzo, new Random((long)LocalDateTime.now().getNano()));
	}
	
	public Carta pescaDalMazzo()
	{
		if(!mazzo.isEmpty()) 
			return mazzo.pop();
		else
			return null;
	}
}
