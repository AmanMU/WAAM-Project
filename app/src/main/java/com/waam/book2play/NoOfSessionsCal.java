package com.waam.book2play;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoOfSessionsCal {
    public NoOfSessionsCal() {
    }

    public String calcDif(String time1, String time2) throws Exception {
        String t1, t2;
        int hour1, hour2;

        t1 = time1;
        t2 = time2;

        hour1 = Integer.parseInt(timeConvert(time1));
        hour2 = Integer.parseInt(timeConvert(time2));

        String diff = String.valueOf(hour2 - hour1) + " sessions";

        return diff;
    }

    public String timeConvert(String time) throws Exception {

        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date = parseFormat.parse(time);
        String hour24 = displayFormat.format(date);

        String part[] = hour24.split(":");

        if(Integer.parseInt(part[0]) == 0){
            return "24";
        }

        return part[0];
        //System.out.println(parseFormat.format(date) + " = " + part[0]);
    }
}
