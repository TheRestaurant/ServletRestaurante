package auxiliares;


import java.text.DecimalFormat;
import java.util.Calendar;

public class Ticket {

    public String contenidoTicket = "{{direccion}}\n"
            + "CIF. {{cif}}\n"
            + "{{fecha}}\n"
            + "----------------------------------------\n"
            + "Factura simplificada #{{numeroTicket}}\n"
            + "LE ATENDIO: {{camarero}}\n"
            + "----------------------------------------\n"
            + "Descripcion     Cant    Precio     Importe"
            + "{{items}}\n"
            + "----------------------------------------\n\n"
            + "\t\t   TOTAL(Euros): {{total}}\n\n"
            + "----------------------------------------\n"
            + "     ESPERAMOS SU VISITA NUEVAMENTE\n"
            + "\t      {{nombreBar}}" + " \n" + " \n" + " \n" + " \n" + " \n" + " \n" + " \n" + " \n" + " \n";

    public Ticket(String nombreBar, String direccion, String cif, String numeroTicket, String camarero, String[] items, int[] cantidad, double[] unitario) {
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

        this.contenidoTicket = this.contenidoTicket.replace("{{nombreBar}}", nombreBar);
        this.contenidoTicket = this.contenidoTicket.replace("{{direccion}}", direccion);
        this.contenidoTicket = this.contenidoTicket.replace("{{cif}}", cif);
        this.contenidoTicket = this.contenidoTicket.replace("{{fecha}}", fecha);
        this.contenidoTicket = this.contenidoTicket.replace("{{numeroTicket}}", numeroTicket);
        this.contenidoTicket = this.contenidoTicket.replace("{{camarero}}", camarero);
        String pedido = "";

        DecimalFormat decim = new DecimalFormat("0.00");
        for (int i = 0; i < items.length; i++) {
            pedido = pedido + "\n" + String.format("%-14.14s", items[i]) + "  " + String.format("%3d", cantidad[i]) + "     " + String.format("%6.2f", unitario[i]) + "     " + String.format("%6.2f", (cantidad[i] * unitario[i]));
        }
        this.contenidoTicket = this.contenidoTicket.replace("{{items}}", pedido);

        double total = 0;
        for (int i = 0; i < items.length; i++) {
            total = total + cantidad[i] * unitario[i];
        }

        if (total < 0) {
            total = 0;
        }

        this.contenidoTicket = this.contenidoTicket.replace("{{total}}", "" + String.valueOf(decim.format(total)));
    }
}
