package BatteryInfo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Battery
{

    private  static final String fileName="D:/battery_log.txt";
    private static boolean proceed;
    public static void stopLoggin()
    {
        System.out.println("Stop thread");
        proceed=false;
    }

    public static  void loggingBattery()
    {
        {
            proceed=true;
            Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime time = LocalDateTime.now();
            int percent = 0;
            BufferedWriter bw = null;
            FileWriter fw = null;
            LocalTime ltime;
            try {
                fw=new FileWriter(fileName);
                bw=new BufferedWriter(fw);
                while (proceed)
                {
                    Thread.sleep(1000);
                    ltime=time.now().toLocalTime();
                    int h=ltime.getHour();
                    int m=ltime.getMinute();
                    int s=ltime.getSecond();
                    Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
                    if(percent!=batteryStatus.BatteryLifePercent)
                    {
                        bw.write(batteryStatus.BatteryLifePercent+"\t"+h+":"+m+":"+s+"\n");
                        bw.flush();
                        percent=batteryStatus.BatteryLifePercent;
                        System.out.println(batteryStatus.BatteryLifePercent+"\t"+h+":"+m+":"+s); // Shows result of toString() method.
                    }



                }
            }
            catch (Exception e){}
            finally
            {
                try {
                    bw.close();fw.close();
                    System.out.println("close file");
                }
                catch (IOException e) {System.out.println(e);}
            }

        }
    }


    public static void main( final String[] args ) {
    }

}




