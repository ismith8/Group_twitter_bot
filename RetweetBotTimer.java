import java.util.*;
import twitter4j.*;

public class RetweetBotTimer extends Bot {
	
	private Timer retweetTimer;
   private Bot b;
	
   /*
    * initialize internal timer and schedule the timer task for every "sec" seconds
    */
    
    public RetweetBotTimer() { b = new Bot(); }
    
    public void runTimer() throws TwitterException {
      
      System.out.println("Instanciated Retweet Bot");
      retweetTimer = new Timer("Retweet_Timer");
      
      // Setup TimerTasks
      TimerTask retweetTask = new TimerTask() {
         public void run() { 
            try { b.retweetCollege(); }
            catch(Exception e) { 
               System.out.println("Failure");
               return; 
            }
         }
      };
      
           
      System.out.println("Created Retweet Timer and Task");
      System.out.println("Running...");
      
      // Runs every 20 minutes
      retweetTimer.scheduleAtFixedRate(retweetTask, 0, 1200000);    
    }
    
    public static void main(String args[]) throws TwitterException {
      RetweetBotTimer bBot = new RetweetBotTimer();
      bBot.runTimer();
    }
}
