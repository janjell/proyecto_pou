package cl.org.thread;

import cl.org.model.Mascota;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Jorge A
 */
public class LimpiezaThread extends Thread {

    private Date timeProp;
    private SimpleDateFormat df;
    private Mascota mascota;
    private JProgressBar pb;
    private JLabel lb;
    private long localMS;
    private long propMS;

    public LimpiezaThread(String timeProp, Mascota mascota, JProgressBar pb, JLabel lb) throws ParseException {
        this.df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.mascota = mascota;
        this.timeProp = df.parse(timeProp);
        this.pb = pb;
        this.lb = lb;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(5);

                localMS = System.currentTimeMillis();
                propMS = timeProp.getTime();

                // Cada 1 Hora Baja 1 de Limpieza (Lo que menos baja en el juego) *ES PROVISIONAL*
                if (localMS - propMS >= TimeUnit.HOURS.toMillis(10)) {
                    mascota.setLimpieza(0);
                    pb.setValue(0);
                    lb.setText(pb.getValue() + "%");
                } else if (localMS - propMS >= TimeUnit.MINUTES.toMillis(60)) {
                    mascota.setLimpieza(mascota.getLimpieza() - 1);
                    pb.setValue(pb.getValue() - 1);
                    lb.setText(pb.getValue() + "%");
                    timeProp.setTime(propMS + TimeUnit.MINUTES.toMillis(60));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
