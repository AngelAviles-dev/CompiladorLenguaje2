package Compilador;
import java.util.ArrayList;

public class Ensamblador {
	static ArrayList<String> lineas;
	public Ensamblador(){
		lineas = new ArrayList<String>();
		for(int i=0; i<Intermedio.Tabla2.size();i+=4){
			if(!(Intermedio.Tabla2.get(i).equals("="))){
				lineas.add("MOV AL,"+Intermedio.Tabla2.get(i+1));
				lineas.add("MOV BL,"+Intermedio.Tabla2.get(i+2));
			}else
			{
				lineas.add("MOV "+Intermedio.Tabla2.get(i+3)+", "+Intermedio.Tabla2.get(i+1));
			}
			if(Intermedio.Tabla2.get(i).equals("+")){
				lineas.add("ADD AL, BL");
				lineas.add("MOV "+Intermedio.Tabla2.get(i+3)+", AL");
			}else if(Intermedio.Tabla2.get(i).equals("-")){
				lineas.add("SUB AL, BL");
				lineas.add("MOV "+Intermedio.Tabla2.get(i+3)+", AL");
			}else if(Intermedio.Tabla2.get(i).equals("/")){
				lineas.add("MUL BL");
				lineas.add("MOV "+Intermedio.Tabla2.get(i+3)+", AX");
			}else if(Intermedio.Tabla2.get(i).equals("*")){
				lineas.add("DIV BL");
				lineas.add("MOV "+Intermedio.Tabla2.get(i+3)+", AX");
			}
		}
	}
	
}
