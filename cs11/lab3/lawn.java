import java.util.Scanner;

class lawn {
  public static void main(String[] args) {
    double lawnlength, lawnwidth, houselength, housewidth, area, lawnarea, housearea, rate, time;
    int hours, minutes, seconds;
    Scanner sc = new Scanner(System.in);

    lawnlength = sc.nextDouble();
    lawnwidth = sc.nextDouble();
    lawnarea = lawnlength*lawnwidth;

    houselength = sc.nextDouble();
    housewidth = sc.nextDouble();
    housearea = houselength*housewidth;

    area = lawnarea-housearea;
    System.out.println("The lawn area is "+area+" square feet.");

    rate = sc.nextDouble();

    time = area/rate;
    
    int roundedtime = (int)Math.round(time);
    minutes = roundedtime/60;
    hours = minutes/60;
    minutes = minutes%60;
    seconds = roundedtime%60;

    if (hours==1) {
      System.out.print("The mowing time is "+hours+" hour ");
      }else{
      System.out.print("The mowing time is "+hours+" hours ");
    }
    if (minutes==1) {
      System.out.print(minutes+" minute ");
      }else{
      System.out.print(minutes+" minutes ");
    }
    if (seconds==1) {
      System.out.println(seconds+" second.");
      }else{
      System.out.println(seconds+" seconds.");
    }
  }
}
