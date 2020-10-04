package Compilador;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Lexico
{
	static int renglon=1;
	int columna=0;
	static ArrayList<Token> tokenAnalizados;
	static ArrayList<String> errores;
	String aux="";
	boolean ban=false;
	public Lexico(String nom) {
		procesoAnalisis(nom);
	}
	public void procesoAnalisis(String nom) {
		String linea="", aux="";
		StringTokenizer tokenizer;
		try{
	          FileReader file = new FileReader(nom);
	          BufferedReader archivoEntrada = new BufferedReader(file);
	          linea = archivoEntrada.readLine();
	          tokenAnalizados=new ArrayList<Token>();
	          errores=new ArrayList<String>();
	          while (linea != null){
	        	    columna=0;
	        	    linea = colocaEspacios(linea);
	                tokenizer = new StringTokenizer(linea);
	                int cont=tokenizer.countTokens();
	                for(int i=0; i<cont; i++) {
	                columna++;
	                aux = tokenizer.nextToken();
	                checarToken(aux);
	                }
	                linea=archivoEntrada.readLine();
	                renglon++;
	          }
	          if(errores.size()==0)
	        	  errores.add("No hay errores lexicos");
	          archivoEntrada.close();
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null,"No se encontro el archivo favor de verificar la ruta","Alerta",JOptionPane.ERROR_MESSAGE);
		}
	}
	public void checarToken(String token) {
		if(esModificador(token))
			return;
		if(esPalabraReservada(token))
			return;
		if(esTipoDato(token))
			return;
		if(esSimbolo(token))
			return;
		if(esOperadorRelacional(token))
			return;
		if(esOperadorAritmetico(token))
			return;
		if(esCadena(token))
			return;
		if(esValor(token))
			return;
		if(esClase(token))
			return;
		Pattern pat = Pattern.compile("^[a-zA-Z0-9]+$");//Checo si cumple con esta expresion regular
		Matcher mat = pat.matcher(token);
		if(mat.find()) {//Si la expresion cumple con las reglas del identificador
			tokenAnalizados.add(new Token("Identificador",token,renglon));//Guardo el token analizado
		}else {
			errores.add("Error token no valido: Renglon "+renglon+" Columna "+columna+" "+token);//Mando un error de token en que renglon fue ocasionado y columna
		}
	}
	public boolean esCadena(String token) {
		//Como partimos el token por espacios al extraer la informacion del archivo el valor estara separado
		//Por lo que lo pegamos
		if(Pattern.matches("^['][A-Za-z0-9]+[']$",token)) {
			tokenAnalizados.add(new Token("Cadena",token,renglon));//Guardo el token analizado
			return true;
		}
		if(Pattern.matches("^['][A-Za-z0-9]+$",token)) {
			ban=true;
			aux+=token;
			return true;
		}
		if(Pattern.matches("^[A-Za-z0-9]+$",token) && ban) {
			aux+=" "+token;
			return true;
		}
		if(Pattern.matches("^[A-Za-z0-9]+[']$",token)) {
			aux+=" "+token;
			tokenAnalizados.add(new Token("Cadena",aux,renglon));
			aux="";
			ban=false;
			return true;
		}
		return false;
	}
	public boolean esValor(String token) {
		if(Arrays.asList("true","false").contains(token)) {
			tokenAnalizados.add(new Token("Constantes",token,renglon));
			return true;
		}
		if(Pattern.matches("^(\\d+)$",token)||Pattern.matches("(^[0-9]+([.][0-9]+)?$)",token)) {
			tokenAnalizados.add(new Token("Constantes",token,renglon));
			return true;
		}
		return false;
	}
	public boolean esModificador(String token) {
		if(token.equals("public")||token.equals("private")||token.equals("protected")) {
			tokenAnalizados.add(new Token("Modificador",token,renglon));
			return true;
		}else 
			return false;
	}
	public boolean esPalabraReservada(String token) {
		if(token.equals("else")||token.equals("if")) {
			tokenAnalizados.add(new Token("Palabra Reservada",token,renglon));
			return true;
		}else 
			return false;
	}
	public boolean esTipoDato(String token) {
		if(token.equals("int")||token.equals("double")||token.equals("String")||token.equals("boolean")) {
			tokenAnalizados.add(new Token("Tipo de datos",token,renglon));
			return true;
		}else
			return false;
	}
	public boolean esSimbolo(String token) {
		if(token.equals("(")||token.equals(")")||token.equals("{")||token.equals("}")||token.equals(";")||token.equals("=")) {
			tokenAnalizados.add(new Token("Simbolo",token,renglon));
			return true;
		}else
			return false;
	}
	public boolean esOperadorRelacional(String token) {
		if(token.equals("<")||token.equals("<=")||token.equals(">=")||token.equals("==")||token.equals("!")||token.equals(">")) {
			tokenAnalizados.add(new Token("Operador Relacional",token,renglon));
			return true;
		}else
			return false;
	}
	public boolean esOperadorAritmetico(String token) {
		if(token.equals("+")||token.equals("-")||token.equals("*")||token.equals("/")) {
			tokenAnalizados.add(new Token("Operador Aritmetico",token,renglon));
			return true;
		}else
			return false;
	}
	public boolean esClase(String token) {
		if(token.equals("class")) {
			tokenAnalizados.add(new Token("Clase",token,renglon));
			return true;
		}else
			return false;
	}
	public static String colocaEspacios(String linea){
		for (String string : Arrays.asList("(",")","{","}","=",";","*","+","/","-")) {
			if(string.equals("=")) {
				if(linea.indexOf(">=")>=0) {
					linea = linea.replace(">=", " >= ");
					break;
				}
				if(linea.indexOf("<=")>=0) {
					linea = linea.replace("<=", " <= ");
					break;
				}
				if(linea.indexOf("==")>=0){
					linea = linea.replace("==", " == ");
					break;
				}
				if(linea.indexOf("<")>=0) {
					linea = linea.replace("<", " < ");
					break;
				}
				if(linea.indexOf(">")>=0) {
					linea = linea.replace(">", " > ");
					break;
				}
			}
			if(linea.contains(string))
				linea = linea.replace(string, " "+string+" ");
		}
		return linea;
	}
}
