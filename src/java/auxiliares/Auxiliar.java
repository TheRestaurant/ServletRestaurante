package auxiliares;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Auxiliar {

    public static void limitaCaracteres(JTextField txt, KeyEvent evt, int pValor) {
        if (txt.getText().length() >= pValor) {
            evt.consume();
        }
    }

    public static void limitaNumeros(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
    }

    public static void mensajeError(String titulo, String texto) {
        JOptionPane op = new JOptionPane(texto, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = op.createDialog(titulo);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    public static void mensajeInformacion(String titulo, String texto) {
        JOptionPane op = new JOptionPane(texto, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = op.createDialog(titulo);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    public static boolean mensajeConfirmacion(String titulo, String texto, Component componente) {
        JOptionPane optionPane = new JOptionPane(texto, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(componente, titulo);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(true);
        dialog.pack();
        dialog.setVisible(true);
        if (optionPane.getValue().toString().equals("0")) {
            return true;
        }
        return false;
    }

    public static String encriptarPass(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
    }

    public static String encripta(String s) {
        char array[] = s.toCharArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = (char) (array[i] + (char) 5);
        }
        String encriptado = String.valueOf(array);
        return encriptado;
    }

    public static String desEncripta(String s) {
        char arrayD[] = s.toCharArray();
        for (int i = 0; i < arrayD.length; i++) {
            arrayD[i] = (char) (arrayD[i] - (char) 5);
        }
        String desencriptado = String.valueOf(arrayD);
        return desencriptado;
    }

     //Metodo para generar String sin caracteres especiales para imprimir
    public static String limpiarString(String producto) {
        producto = producto.replace("á", "a");
        producto = producto.replace("é", "e");
        producto = producto.replace("í", "i");
        producto = producto.replace("ó", "o");
        producto = producto.replace("ú", "u");
        producto = producto.replace("ñ", "n");
        producto = producto.replace("Á", "A");
        producto = producto.replace("É", "E");
        producto = producto.replace("Í", "I");
        producto = producto.replace("Ó", "O");
        producto = producto.replace("Ú", "U");
        return producto;
    }

    public static void cerrarTeclado() {
        String[] cmd = {"cmd.exe", "/C", "taskkill /IM osk.exe"};
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
            Logger.getLogger(Auxiliar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void abrirCajon(String puerto) {
        try {
            FileWriter imp = new FileWriter(puerto);
            imp.write(27);
            imp.write(112);
            imp.write(0);
            imp.write(50);
            imp.write(250);
            imp.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void imprimir(String impresion) {
        try {
            /*
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            DocPrintJob job = service.createPrintJob();
            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            SimpleDoc doc = new SimpleDoc(new MyPrintable(), flavor, null);
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            job.print(doc, aset);
            */
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            DocPrintJob job = service.createPrintJob();

            byte[] bytes = impresion.getBytes();

            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            SimpleDoc doc = new SimpleDoc(bytes, flavor, null);
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            job.print(doc, aset);

            FileWriter imp = new FileWriter("LPT1");
            imp.write(27);
            imp.write(105);
            imp.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    static class MyPrintable implements Printable {

        ImageIcon printImage = new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo2.jpg"));

        public int print(Graphics g, PageFormat pf, int pageIndex) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate((int) (pf.getImageableX()), (int) (pf.getImageableY()));
            if (pageIndex == 0) {
                double pageWidth = pf.getImageableWidth();
                double pageHeight = pf.getImageableHeight();
                double imageWidth = printImage.getIconWidth();
                double imageHeight = printImage.getIconHeight();
                double scaleX = pageWidth / imageWidth;
                double scaleY = pageHeight / imageHeight;
                double scaleFactor = Math.min(scaleX, scaleY);
                g2d.scale(scaleFactor, scaleFactor);
                g2d.drawImage(printImage.getImage(), 0, 0, null);
                return Printable.PAGE_EXISTS;
            }
            return Printable.NO_SUCH_PAGE;
        }
    }
}
