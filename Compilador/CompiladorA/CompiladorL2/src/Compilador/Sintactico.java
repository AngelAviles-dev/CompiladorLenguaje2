package Compilador;

import java.util.ArrayList;
import java.util.Arrays;

public class Sintactico {
	int iniciador;
	boolean error=false,errorS,banLlave=false,banPar=false;
	static  ArrayList<Identificador> TablaSimbolos;
	public Sintactico() {
		TablaSimbolos = new ArrayList<Identificador>();
		iniciador=0;
		errorS=false;
		if(!Lexico.tokenAnalizados.get(Lexico.tokenAnalizados.size()-1).getValor().equals("}")) {
			Lexico.errores.add("Error al colocar la llave final del programa");
			return;
		}
		analisis();
		for(int i=0; i<Lexico.tokenAnalizados.size();i++) {
			if(Lexico.tokenAnalizados.get(i).getTipo().equals("Tipo de dato")) {
				if(i+1>Lexico.tokenAnalizados.size()-1) {
					Lexico.errores.add("Error sintactico declaracion de variable invalida");
					break;
				}
				if(Lexico.tokenAnalizados.get(i+1).getTipo().equals("Identificador")) {
					if(i+2>Lexico.tokenAnalizados.size()-1) {
						Lexico.errores.add("Error sintactico declaracion de variable invalida");
						break;
					}
					if(Lexico.tokenAnalizados.get(i+1).getValor().equals(";")) {
						Lexico.errores.add("Error sintactico declaracion de variable invalida");
						break;
					}
				}
			}
		}
		if(Lexico.errores.get(Lexico.errores.size()-1).equals("No hay errores lexicos"))
			Lexico.errores.add("No hay errores sintacticos");
		for (Identificador identificador : TablaSimbolos) {
			if (identificador.getTipo().equals("")) {
				String x =buscar(identificador.getNombre());
			identificador.setTipo(x);
			}
			System.out.println(identificador);
		}
	}
	public void analisis() {
		int cant = Lexico.tokenAnalizados.size();
		while(iniciador<cant){
			if(Lexico.tokenAnalizados.get(iniciador).getTipo().equals("Modificador"))
			{
				if(!Lexico.tokenAnalizados.get(iniciador+1).getTipo().equals("Tipo de datos") && !Lexico.tokenAnalizados.get(iniciador+1).getTipo().equals("Clase"))
				{
					Lexico.errores.add("Error Sintactico se esperaba un identificador o clase.");
					errorS=true;
				}
				iniciador++;
				continue;
			}
			if(Lexico.tokenAnalizados.get(iniciador).getTipo().equals("Identificador"))
			{
				if(!Arrays.asList("{","=",";",")",">","<","<=",">=").contains(Lexico.tokenAnalizados.get(iniciador+1).getValor()))
				{
					Lexico.errores.add("Error Sintactico se esperaba un simbolo.");
					errorS=true;
				}
				if(Lexico.tokenAnalizados.get(iniciador-1).getTipo().equals("Clase"))
					TablaSimbolos.add(new Identificador(Lexico.tokenAnalizados.get(iniciador).getValor(),"","Clase",0,""));
				iniciador++;
				continue;
			}
			if(Lexico.tokenAnalizados.get(iniciador).getTipo().equals("Clase") || Lexico.tokenAnalizados.get(iniciador).getTipo().equals("Tipo de datos"))
			{
				if(!Lexico.tokenAnalizados.get(iniciador-1).getTipo().equals("Modificador") && Lexico.tokenAnalizados.get(iniciador).getTipo().equals("Tipo de datos"))
				{
					if(!Lexico.tokenAnalizados.get(iniciador+1).getTipo().equals("Identificador"))
					{
						Lexico.errores.add("Error Sintactico se esperaba un identificador.");
						errorS=true;
					}
				}
				else if(Lexico.tokenAnalizados.get(iniciador-1).getTipo().equals("Modificador"))
				{
					if(!Lexico.tokenAnalizados.get(iniciador+1).getTipo().equals("Identificador"))
					{
						Lexico.errores.add("Error Sintactico se esperaba un identificador.");
						errorS=true;
					}
				}
				else
				{
					Lexico.errores.add("Error Sintactico se esperaba un modificador.");
					errorS=true;
				}
				
				iniciador++;
				continue;
			}
			if(Lexico.tokenAnalizados.get(iniciador).getTipo().equals("Simbolo"))
			{
				if(Arrays.asList("{","}").contains(Lexico.tokenAnalizados.get(iniciador).getValor()))
				{
					if(cuentaLlaves())
					{
						banLlave = true;
						errorS=true;
					}
				}
				else if(Arrays.asList("(",")").contains(Lexico.tokenAnalizados.get(iniciador).getValor()))
				{
					if(cuentaParentesis())
					{						
						banPar = true;
						errorS=true;
					}
				}
				else if(Lexico.tokenAnalizados.get(iniciador).getValor().equals("=") || Lexico.tokenAnalizados.get(iniciador).getValor().equals("Identificador") )
				{
					if(Lexico.tokenAnalizados.get(iniciador-1).getTipo().equals("Identificador"))
					{
						if(Lexico.tokenAnalizados.get(iniciador+1).getTipo().equals("Constantes") || Lexico.tokenAnalizados.get(iniciador+1).getTipo().equals("Identificador")||Lexico.tokenAnalizados.get(iniciador+1).getTipo().equals("Simbolo"))
						{
							String a=Lexico.tokenAnalizados.get(iniciador-2).getValor(),iden=Lexico.tokenAnalizados.get(iniciador-1).getValor();
							if(!a.equals("int")&&!a.equals("double")&&!a.equals("String")&&!a.equals("boolean")) {
								a="Asignacion";
							}
							String valor="";
							iniciador++;
							while(!Lexico.tokenAnalizados.get(iniciador).getValor().equals(";")) {
								valor+=Lexico.tokenAnalizados.get(iniciador).getValor();
								iniciador++;
							}
							TablaSimbolos.add(new Identificador(iden,valor,a,0,""));
						}else {
							Lexico.errores.add("Error Sintactico se esperaba una constante o variable.");
							errorS=true;
						}
					}
					else
					{
						Lexico.errores.add("Error Sintactico se esperaba un identificador.");
						errorS=true;
					}
				}
				iniciador++;
				continue;
			}
			if(Lexico.tokenAnalizados.get(iniciador).getTipo().equals("Constantes"))
			{
				if(Lexico.tokenAnalizados.get(iniciador-1).getValor().equals("="))
				{
					if(!Lexico.tokenAnalizados.get(iniciador+1).getValor().equals(";"))
					{
						Lexico.errores.add("Error Sintactico asignacion invalida.");
						errorS=true;
					}
				}
				iniciador++;
				continue;
			}
			if(Lexico.tokenAnalizados.get(iniciador).getTipo().equals("Palabra Reservada"))
			{
				if(Lexico.tokenAnalizados.get(iniciador).getValor().equals("if"))
				{
					if(!Lexico.tokenAnalizados.get(iniciador+1).getValor().equals("("))
					{
						Lexico.errores.add("Error Sintactico se esperaba un (");
						errorS=true;
					}
				}
				iniciador++;
				continue;
			}
			if(Lexico.tokenAnalizados.get(iniciador).getTipo().equals("Operador Relacional"))
			{
				if(!Lexico.tokenAnalizados.get(iniciador-1).getTipo().equals("Constantes") && !Lexico.tokenAnalizados.get(iniciador-1).getTipo().equals("Identificador"))
				{
					Lexico.errores.add("Error Sintactico se esperaba una constante o identificador");
					errorS=true;
				}
				if(!Lexico.tokenAnalizados.get(iniciador+1).getTipo().equals("Constantes") && !Lexico.tokenAnalizados.get(iniciador-1).getTipo().equals("Identificador"))
				{
					Lexico.errores.add("Error Sintactico se esperaba una constante o identificador");
					errorS=true;
				}
				iniciador++;
				continue;
			}else
			iniciador++;
		}
		if(banLlave)
			Lexico.errores.add("Error Sintactico falta de llave.");
		if(banPar)
			Lexico.errores.add("Error Sintactico falta de parentesis.");
	}
	public boolean cuentaParentesis(){
		int cant1=0,cant2=0;
		for(int i = 0;i<Lexico.tokenAnalizados.size();i++)
		{
			if(Lexico.tokenAnalizados.get(i).getValor().equals("("))
				cant1++;
			if(Lexico.tokenAnalizados.get(i).getValor().equals(")"))
				cant2++;
		}
		if(cant1==cant2)
			return false;
		return true;
	}
	public boolean cuentaLlaves(){
		int cant1=0,cant2=0;
		for(int i = 0;i<Lexico.tokenAnalizados.size();i++)
		{
			if(Lexico.tokenAnalizados.get(i).getValor().equals("{"))
				cant1++;
			if(Lexico.tokenAnalizados.get(i).getValor().equals("}"))
				cant2++;
		}
		if(cant1==cant2)
			return false;
		return true;
	}
	private String buscar(String id)
	{
		for (int i = TablaSimbolos.size()-1; i >=0; i--) {
			Identificador identificador = TablaSimbolos.get(i);
			if(identificador.getNombre().equals(id))
				return identificador.getTipo();
		}
		return "";
	}
}
