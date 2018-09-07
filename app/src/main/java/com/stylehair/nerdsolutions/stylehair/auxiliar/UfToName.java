package com.stylehair.nerdsolutions.stylehair.auxiliar;

public class UfToName {


   private String[] NomeEstados={"Acre" , "Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal","EspiritoSanto",
            "Goiás","Maranhão","Mato Grosso do Sul","Mato Grosso","Minas Gerais","Pará","Paraíba",
            "Paraná","Pernambuco","Piauí","Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul",
            "Rondônia","Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins"};

   private String[] UfEstados={"AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE",
            "PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};


    public String estado(String uf)
    {
        int pos = -1;
        for(int x= 0; x<UfEstados.length;x++)
        {
            if(UfEstados[x].equals(uf))
            {
                pos = x;
            }
        }
        return NomeEstados[pos];
    }

}
