package babysitter;

import java.util.Arrays;

public class BabySitterJob { String[] validStartTimes = {"5pm","6pm","7pm","8pm","9pm","10pm","11pm","12am","1am","2am","3am"}; String[] validEndTimes = {"6pm","7pm","8pm","9pm","10pm","11pm","12am","1am","2am","3am","4am"};
double startTime,endTime,bedTime,errorCode = 99;
public BabySitterJob() {
    // TODO Auto-generated constructor stub

}

public BabySitterJob(String startTime)
{
    this.startTime = Arrays.asList(validStartTimes).indexOf(startTime)!= -1 ? Arrays.asList(validStartTimes).indexOf(startTime) : errorCode ;
}

public BabySitterJob(String startTime,String endTime)
{
    this.startTime = Arrays.asList(validStartTimes).indexOf(startTime)!= -1 ? Arrays.asList(validStartTimes).indexOf(startTime) : errorCode ;
    this.endTime = Arrays.asList(validEndTimes).indexOf(endTime)!= -1 ? Arrays.asList(validEndTimes).indexOf(endTime) : errorCode;
}
public BabySitterJob(String startTime, String endTime, String bedTime) {
    // TODO Auto-generated constructor stub
    this.startTime = Arrays.asList(validStartTimes).indexOf(startTime)!= -1 ? Arrays.asList(validStartTimes).indexOf(startTime) : errorCode ;
    this.endTime = Arrays.asList(validEndTimes).indexOf(endTime)!= -1 ? Arrays.asList(validEndTimes).indexOf(endTime) : errorCode;
    this.bedTime =(Arrays.asList(validStartTimes).indexOf(bedTime) >= Arrays.asList(validStartTimes).indexOf(startTime)) && (Arrays.asList(validEndTimes).indexOf(bedTime) <= Arrays.asList(validEndTimes).indexOf(endTime)) ? Arrays.asList(validStartTimes).indexOf(bedTime) : errorCode;
    this.bedTime = Arrays.asList(validStartTimes).indexOf(bedTime) < Arrays.asList(validStartTimes).indexOf("12am") ? Arrays.asList(validStartTimes).indexOf(bedTime) : errorCode;
    //System.out.println(this.startTime + " " + this.endTime + " " + this.bedTime);
    if(this.startTime == errorCode)
    {
        System.out.println("Enter Valid StartTime");
        System.exit(0);
    }
    else if (this.endTime == errorCode)
    {
        System.out.println("Enter Valid EndTime");
        System.exit(0);
    }
    else if (this.bedTime == errorCode)
    {
        System.out.println("Enter Valid BedTime");
        System.exit(0);
    }
}

public double  calculateTotalCharge()
{
    return startTimeToBedTime() + bedTimeToMidnight() + midnightToEndTime();
}

public double startTimeToBedTime()
{
    if (Arrays.asList(validStartTimes).indexOf(bedTime) > Arrays.asList(validStartTimes).indexOf(startTime)) { 
       return (Arrays.asList(validStartTimes).indexOf(startTime) - Arrays.asList(validStartTimes).indexOf(startTime)) * 12; 
    } 
    return 0; 
}

public double bedTimeToMidnight() 
{ 
    if (Arrays.asList(validStartTimes).indexOf("12am") > Arrays.asList(validStartTimes).indexOf(bedTime) && Arrays.asList(validEndTimes).indexOf(endTime) >Arrays.asList(validStartTimes).indexOf(bedTime)) { 
       return (Arrays.asList(validStartTimes).indexOf("12am") - Arrays.asList(validStartTimes).indexOf(bedTime)) * 8; 
     } 
     return 0; 
} 

public double midnightToEndTime() { 
          if (Arrays.asList(validEndTimes).indexOf(endTime) > Arrays.asList(validStartTimes).indexOf("12am")) { 
            return (Arrays.asList(validEndTimes).indexOf(endTime) - Arrays.asList(validStartTimes).indexOf("12am")) * 16; 
          } 
          return 0; 
}

public static void main(String[] args) {

    //System.out.println("hello baby ");
BabySitterJob a = new BabySitterJob("9pm","12am","9pm");
double ans = a.calculateTotalCharge();
System.out.println(ans);

}
}