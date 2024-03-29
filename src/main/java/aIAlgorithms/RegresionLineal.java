package aIAlgorithms;

import interfaces.Algorithm;
import table.Table;

import java.util.List;

public class RegresionLineal implements Algorithm<Table,Double,Double> {
    double a, b;

    private double media(List<Double> listaNumeros){
        if (listaNumeros.size()==0)
                throw new IndexOutOfBoundsException();
        int sumatorio = 0;
        for(Double numero:listaNumeros){
            sumatorio += numero;
        }
        return (double) sumatorio / listaNumeros.size();
    }

    public void train(Table tabla){
        List<Double> X = tabla.getColumAt(0), Y = tabla.getColumAt(1);
        double mediaX = media(X), mediaY = media(Y), difX, numerador = 0, denominador = 0;
        for(int i = 0; i < X.size(); i++){
            difX = (X.get(i)-mediaX);
            numerador += difX * (Y.get(i) - mediaY);
            denominador += difX * difX;
        }
        a = numerador / denominador;
        b = mediaY - a * mediaX;
    }

    public Double estimate(Double sample){
        return a * sample + b;
    }
    public Double getA(){return a;}
    public Double getB(){return b;}
}

