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
public class EnergiaThread extends Thread {

    private Date timeProp;
    private SimpleDateFormat df;
    private Mascota mascota;
    private JProgressBar pb;
    private JLabel lb;
    private long localMS;
    private long propMS;

    public EnergiaThread(String timeProp, Mascota mascota, JProgressBar pb, JLabel lb) throws ParseException {
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

                // Cada 30 Minutos Baja la energía y cada dos horas todo queda en 0
                if (localMS - propMS >= TimeUnit.HOURS.toMillis(10)) {
                    mascota.setEnergia(0);
                    pb.setValue(0);
                    lb.setText(pb.getValue() + "%");
                } else if (localMS - propMS >= TimeUnit.MINUTES.toMillis(30)) {
                    mascota.setEnergia(mascota.getEnergia() - 1);
                    pb.setValue(pb.getValue() - 1);
                    lb.setText(pb.getValue() + "%");
                    timeProp.setTime(propMS + TimeUnit.MINUTES.toMillis(30));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
