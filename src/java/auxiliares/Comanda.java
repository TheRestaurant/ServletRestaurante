package auxiliares;

import java.util.ArrayList;
import java.util.Calendar;

public class Comanda {

    public String contenidoComanda = "\n{{fecha}}\n"
            + "==========================================\n"
            + "{{items}}\n"
            + "==========================================\n";

    public Comanda(ArrayList<Producto> items, int mesa) {
        Calendar cal1 = Calendar.getInstance();

        String dia = "" + cal1.get(Calendar.DATE);
        if (Integer.parseInt(dia) < 10) {
            dia = "0" + dia;
        }

        String mes = "" + cal1.get(Calendar.MONTH);
        if (Integer.parseInt(mes) < 10) {
            mes = "0" + mes;
        }

        String hora = "" + cal1.get(Calendar.HOUR_OF_DAY);
        if (Integer.parseInt(hora) < 10) {
            hora = "0" + hora;
        }

        String minutos = "" + cal1.get(Calendar.MINUTE);
        if (Integer.parseInt(minutos) < 10) {
            minutos = "0" + minutos;
        }

        String fecha = "" + dia + "/" + mes
                + "/" + cal1.get(Calendar.YEAR) + " " + hora
                + ":" + minutos;

        this.contenidoComanda = this.contenidoComanda.replace("{{fecha}}", fecha);
        String pedido="";
        for (int i = 0; i < items.size(); i++) {
            pedido = pedido + "\n" + String.format("%-14.14s", items.get(i).getNombreProducto()) + "  " + String.format("%3d", items.get(i).getCantidad());
        }
        this.contenidoComanda = this.contenidoComanda.replace("{{items}}", pedido);        
    }
}
