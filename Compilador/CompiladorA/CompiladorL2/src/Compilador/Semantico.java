package Compilador;

public class Semantico {

	public Semantico(){
		
		}
	public boolean EsEntero(String cadena) {
        boolean resultado;
        if(cadena.indexOf(".")==-1){//No trae punto es entero
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        }else
        	resultado=false;
        return resultado;
    }
	
	public boolean EsDouble(String cadena) {
        boolean resultado;
        if(cadena.indexOf(".")!=-1){//trae punto es decimal
         try {
            Double.parseDouble(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        }else
        	resultado=false;
        return resultado;
    }
	
	public boolean EsBooleana(String cadena) {
		boolean resultado;
       if(cadena.equals("true")){
    	   resultado=true;
       }
       else if (cadena.equals("false")){
    	   resultado = true;
       }
       else
    	   resultado=false;
        return resultado;
    }
	
	public int buscaLinea(Identificador ide){
		int linea=0;
		for(int i=0;i<Lexico.tokenAnalizados.size();i++){
			if(Lexico.tokenAnalizados.get(i).getValor().equals(ide.getNombre())
					&& Lexico.tokenAnalizados.get(i+1).getValor().equals("=")
					&& Lexico.tokenAnalizados.get(i+2).getValor().equals(ide.getValor())){
				linea=Lexico.tokenAnalizados.get(i).getLinea();
			}
		}
		return linea;
	}
	
	public int buscaLineaE(Identificador ide, String e){
		int linea=0;
		for(int i=0;i<Lexico.tokenAnalizados.size();i++){
			if(Lexico.tokenAnalizados.get(i).getValor().equals(ide.getNombre())
					&& Lexico.tokenAnalizados.get(i+1).getValor().equals("=")
					&& Lexico.tokenAnalizados.get(i+2).getValor().equals(e)){
				linea=Lexico.tokenAnalizados.get(i).getLinea();
			}
		}
		return linea;
	}
	public boolean revisaOperandos(String operando, String tipo){
		boolean tipoCorrecto=false;
		if(tipo.equals("int")){
		if(EsEntero(operando)){//verificamos que sea numero entero
			tipoCorrecto=true;
		} else{
			if(EsDouble(operando)){//verificamos que sea numero entero
				tipoCorrecto=true;
			}
		}
	}
		return tipoCorrecto;
	}
}
