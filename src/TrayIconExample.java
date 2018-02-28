import BatteryInfo.Battery;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.net.URL;

import javax.swing.JOptionPane;

public class TrayIconExample {
    public static void main(String[] args)throws Exception
    {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");


            return;
        }

        //start
        Thread thr= new Thread(){
            @Override
            public void run()
            {
                Battery.loggingBattery();
            }
        };
        thr.start();

        SystemTray tray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        URL imageUrl=ClassLoader.getSystemResource("bat.png");
        Image image = toolkit.getImage(imageUrl);//"res/bat.png"

        PopupMenu menu = new PopupMenu();

        MenuItem messageItem = new MenuItem("Show Message");
        messageItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "message - "+imageUrl));
        menu.add(messageItem);

        MenuItem closeItem = new MenuItem("Close");
        closeItem.addActionListener(e ->
        {
           //
            Battery.stopLoggin();
            try {
               while(thr.isAlive()) Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.exit(0);
        });
        menu.add(closeItem);


        TrayIcon icon = new TrayIcon(image, "SystemTray Demo", menu);
        icon.setImageAutoSize(true);

        tray.add(icon);
    }
}