package com.example.niepewnosci.pomiarowe;

import java.io.Serializable;

public class Ziarenko implements Serializable{
	private static final long serialVersionUID = -5435670920302756945L;
	public String liczba;
	public Ziarenko(){}
	
	public Ziarenko(String liczba){
		this.liczba = liczba;
	}
}
