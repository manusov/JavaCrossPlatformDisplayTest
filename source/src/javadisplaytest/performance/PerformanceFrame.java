/*
Java Display Test and Benchmark Utility. (C)2019 IC Book Labs
--------------------------------------------------------------
This class is JFrame object for benchmarks performance,
JFrame plus application-specific functionality.
*/

package javadisplaytest.performance;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javadisplaytest.StatisticEntry;
import javadisplaytest.StatisticUtil;
import javadisplaytest.performance.Performance.VisualMode;
import javadisplaytest.performance.Performance.VisualPicture;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class PerformanceFrame extends JFrame
{
/*
Fields required for benchmark performance.
*/
private final JFrame frame;
private final JPanel performancePanel;
private final JPanel allPanel;
private final StatisticsPanel statisticsPanel;
private final int panelCount;
private final PerformancePanelTimerMode[] performancePanels;
private final GridLayout performanceLayout;
private final BoxLayout allLayout;
private final ArrayList<Double> statistics;
private int seconds;
private int secondsSkip;
private final int secondsStop;
/*
seconds for pre-heat, yet constant.
*/
private final static int SECONDS_SKIP = 1;
/*
Support status information about benchmark performance errors.
*/
private boolean status = false;
private String statusString = null;
boolean getStatus()       { return status;       }
String getStatusString()  { return statusString; }

/*
Support thread management communications
*/
private final Performance performance;    

PerformanceFrame( Performance p,
                  String applicationName, 
                  BufferedImage imageDog, BufferedImage imageCat,
                  int visualColumns, int visualRows, int durationSeconds,
                  double angleDelta, boolean angleChess,
                  VisualMode visualMode,  int speedLimitMs,
                  VisualPicture visualPicture, boolean pictureChess )
    {
    super( applicationName );
    frame = this;
    status = true;
    performance = p;
    
    secondsStop = durationSeconds;
    statistics = new ArrayList();
    seconds = 0;
    secondsSkip = SECONDS_SKIP;
    
    performance.setStatisticEntry( null );
        
    panelCount = visualColumns * visualRows;
    performancePanels = new PerformancePanelTimerMode[panelCount];
    performanceLayout = new GridLayout( visualRows, visualColumns, 1, 1 );
    performancePanel = new JPanel( performanceLayout );
    statisticsPanel = new StatisticsPanel();
    statisticsPanel.setPreferredSize( new Dimension( 460, 30 ) );
    allPanel = new JPanel( );
    allLayout = new BoxLayout( allPanel, BoxLayout.Y_AXIS );
    allPanel.setLayout( allLayout );
    allPanel.add( performancePanel );
    allPanel.add( statisticsPanel );
    
    for( int i=0; i<panelCount; i++ )
        {
        BufferedImage image;
        switch ( visualPicture )
            {
            case DOG_PICTURE:
                image = imageDog;
                break;
            case CAT_PICTURE:
                image = imageCat;
                break;
            default:
                image = null;
                break;
            }
            
        switch ( visualMode )
            {
            case TIMER_MODE:
                performancePanels[i] = new PerformancePanelTimerMode
                    ( angleDelta, speedLimitMs, image );
                break;
            case THREAD_MODE:    
                performancePanels[i] = new PerformancePanelThreadMode
                    ( angleDelta, speedLimitMs, image );
                break;
            }
        
        status &= performancePanels[i].getStatus();
        statusString = performancePanels[i].getStatusString();
        
        if ( !status ) return;
        
        performancePanel.add( performancePanels[i] );
        performancePanels[i].runPerformance();
        if ( angleChess ) angleDelta = -angleDelta;
        if ( pictureChess )
            {
            if ( visualPicture == VisualPicture.CAT_PICTURE ) 
                 visualPicture = VisualPicture.DOG_PICTURE;
            else visualPicture = VisualPicture.CAT_PICTURE;
            }
        }
    }

/*
FPS statistics timer, one tick per second
*/
private Timer statisticTimer;
        
/*
Run benchmark performance, 
contains some operations, not recommended in constructor.
*/
boolean runBenchmark()
    {
    if ( status )
        {
        add ( allPanel );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        addWindowListener( new UserCloseWindowListener() );
        pack();
        setLocationRelativeTo( null );
        setVisible( true );
        statisticTimer = new Timer( 1000, new StatisticTimerListener() );
        statisticTimer.start();
        }
    return status;
    }

/*
Listener for timer ticks, F = 1Hz
This timer ticks one time per second for FPS measurement.
*/
private class StatisticTimerListener implements ActionListener
    {
    @Override public void actionPerformed( ActionEvent event )
        {
        double fps = 0.0;
        for( int i=0; i<panelCount; i++ )
            {
            fps += performancePanels[i].getAndClearFramesCounter();
            }
        fps /= panelCount;

        if ( secondsSkip > 0 )
            {
            secondsSkip--;
            }
        else
            {
            /*
            Support statistics    
            */
            StringBuilder sb = 
                new StringBuilder ( String.format( "Second=%d ", seconds ) );
            sb.append( String.format( " FPS=%.1f", fps ) );
            statistics.add( fps );
            seconds = statistics.size();
            double[] array = new double[seconds];
            for( int i=0; i<seconds; i++ )
                {
                array[i] = statistics.get( i );
                }
            StatisticEntry entry = 
                StatisticUtil.getStatistic( array, seconds, secondsStop );
            double min = entry.min;
            double max = entry.max;
            double average = entry.average;
            double median = entry.median;
            sb.append( String.format
                ( "   min=%.1f  max=%.1f  average=%.1f  median=%.1f",
                  min, max, average, median ) );
            statisticsPanel.setStatisticsString( sb.toString() );
            statisticsPanel.repaint();
            /*
            Send data to higher level
            */
            performance.setStatisticEntry( entry );
            performance.setLogArray( array );
            /*
            Support session termination by count done
            */
            if ( seconds > secondsStop )
                {
                helperClosing( true );
                }
            }
        /*
        Support session termination by interrupt signal from parent window
        */
        if ( performance.getTaskInterrupt() || performance.getTaskDone() )
            {
            helperClosing( true );
            }
        }
    }

/*
Listener for performance window close by [X]
*/
private class UserCloseWindowListener extends WindowAdapter 
    {
    @Override public void windowClosing( WindowEvent e )
        {
        helperClosing( false );
        performance.setTaskDone( true );
        boolean doneAll = ( seconds > secondsStop );
        performance.setTaskInterrupt( ! doneAll );
        }
    }

/*
Helper for closing window
*/
private void helperClosing( boolean softClose )
    {
    statisticTimer.stop();
    for( int i=0; i<panelCount; i++ )
        {
        performancePanels[i].stopPerformance();
        }
    if ( softClose )
        {
        frame.dispatchEvent
            ( new WindowEvent( frame, WindowEvent.WINDOW_CLOSING ) );
        }
    try 
        { 
        Thread.sleep( 80 );  // safe delay 
        }
    catch ( InterruptedException ex ) 
        {
        status = false;
        statusString = ex.getMessage();
        }
    performance.setTaskDone( true );
    }

}
