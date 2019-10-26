/*
Java Display Test and Benchmark Utility. (C)2019 IC Book Labs
--------------------------------------------------------------
Video performance class.
*/

package javadisplaytest.performance;

import java.awt.image.BufferedImage;
import javadisplaytest.About;
import javadisplaytest.StatisticEntry;
import javax.swing.JOptionPane;

public class Performance 
{
/*
Visual mode option enumeration    
*/
public enum VisualMode    
    { 
    TIMER_MODE   
        { @Override public String getName() { return "GUI timer ticks"; } },
    THREAD_MODE  
        { @Override public String getName() { return "Parallel thread"; } };
    public abstract String getName();
    }
/*
Visual picture select option enumeration    
*/
public enum VisualPicture 
    { 
    DOG_PICTURE 
        { @Override public String getName() { return "Dog"; } }, 
    CAT_PICTURE
        { @Override public String getName() { return "Cat"; } }; 
    public abstract String getName();
    };
/*
Options default settings, useable for debug.
*/
private final static int           VISUAL_COLUMNS = 3;
private final static int           VISUAL_ROWS = 2;
private final static int           DURATION_SECONDS = 50;
private final static double        ANGLE_DELTA = 0.5;
private final static boolean       ANGLE_CHANGE = false;
private final static VisualMode    VISUAL_MODE = VisualMode.THREAD_MODE;
private final static int           SPEED_LIMIT_MS = 0;
private final static VisualPicture VISUAL_PICTURE = VisualPicture.DOG_PICTURE;
private final static boolean       PICTURE_CHANGE = true;
/*
Communication between constructor and run method.
*/
private final boolean status;
private final BufferedImage imageDog;
private final BufferedImage imageCat;

public Performance()
    {
    StoreImages storeImages = new StoreImages();
    boolean b = storeImages.loadPictures();
    if ( b )
        {
        imageDog = storeImages.getPicture( VisualPicture.DOG_PICTURE );
        imageCat = storeImages.getPicture( VisualPicture.CAT_PICTURE );
        if ( ( imageDog == null )||( imageCat == null ) ) b = false;
        }
    else
        {
        imageDog = null;
        imageCat = null;
        }
    status = b;
    }
/*
Run benchmark performance scenario, no parameters: use internal defaults
*/
public void runScenario()
    {
    runScenario( VISUAL_COLUMNS, VISUAL_ROWS, DURATION_SECONDS, 
                 ANGLE_DELTA, ANGLE_CHANGE, VISUAL_MODE, SPEED_LIMIT_MS,
                 VISUAL_PICTURE, PICTURE_CHANGE );
    }
/*
Run benchmark performance scenario, use input parameters (options)
*/
public void runScenario( int visualColumns, int visualRows, int durationSeconds,
                         double angleDelta, boolean angleChange,
                         VisualMode visualMode,  int speedLimitMs,
                         VisualPicture visualPicture, boolean pictureChange )
    {
    taskInterrupt = false;
    taskDone = false;
    
    String applicationName = About.getLongName();
    if ( !status )
        {
        String s1 = "Load pictures failed:\r\n";
        String s2 = null; // storeImages.getStatusString();
        if ( s2 == null ) s2 = "Unknown error.";
        JOptionPane.showMessageDialog
            ( null, s1 + s2, applicationName, JOptionPane.ERROR_MESSAGE ); 
        }
    else
        {
        PerformanceFrame performanceFrame = new PerformanceFrame
            ( this,
              applicationName, imageDog, imageCat,
              visualColumns, visualRows, durationSeconds, 
              angleDelta, angleChange,
              visualMode, speedLimitMs,
              visualPicture, pictureChange );
        
        boolean b = performanceFrame.runBenchmark();
        if ( !b )
            {
            String s1 = "Benchmark performance failed:\r\n";
            String s2 = performanceFrame.getStatusString();
            if ( s2 == null ) s2 = "Unknown error.";
            JOptionPane.showMessageDialog
                ( null, s1 + s2, applicationName, JOptionPane.ERROR_MESSAGE ); 
            }
        }
    }

/*
Parent and child (this) threads communication support
*/
private boolean taskInterrupt;
private boolean taskDone;
private StatisticEntry statisticEntry;
private double[] logArray;

public void setTaskInterrupt( boolean b )  { taskInterrupt = b;    }
public boolean getTaskInterrupt()          { return taskInterrupt; }

public boolean getTaskDone()               { return taskDone;      }
void setTaskDone( boolean b )              { taskDone = b;         }

public synchronized StatisticEntry getStatisticEntry()
    {
    return statisticEntry;
    }

public synchronized void setStatisticEntry( StatisticEntry se )
    {
    statisticEntry = se;
    }

void setLogArray( double[] a )
    {
    logArray = a;
    }

public double[] getLogArray()
    {
    return logArray;
    }

}
