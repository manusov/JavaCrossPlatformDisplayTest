/*
Java Display Test and Benchmark Utility. (C)2019 IC Book Labs
--------------------------------------------------------------
Handler for "Run" and "Stop" buttons. Test executed at separate thread.
Measurement iterations executed in this class.
*/

package javadisplaytest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javadisplaytest.performance.Performance;
import javadisplaytest.performance.Performance.VisualMode;
import javadisplaytest.performance.Performance.VisualPicture;
import javadisplaytest.performance.PerformanceTableModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

class ActionRun extends Thread 
{
/*
Scenario input parameters    
*/
private final Performance performance;
private final int columns;
private final int rows;
private final int duration;
private final double angle;
private final boolean angleChg;
private final VisualMode mode;
private final int delay;
private final VisualPicture picture;
private final boolean pictureChg;
/*
GUI components and report data
*/
private final PerformanceTableModel tModel;
private final DefaultBoundedRangeModel pModel;
private final JProgressBar pBar;
private final JButton rsButton;
private final ActionListener rsListener;
private final JComponent[] disComp;
private final JFrame appFrame;
/*
Operation variables
*/
private boolean running;
private String rsName;
private double percentage;
private boolean errorFlag;
private String errorString;

public boolean getErrorFlag()  { return errorFlag; }
public String getErrorString() { return errorString; }

/*
Get running status
*/
public boolean getRunning() 
    {
    return running; 
    }
/*
Get log of benchmark performance
*/
public double[] getLogArray()
    {
    return performance.getLogArray();
    }

public StatisticEntry getStatisticEntry()
    {
    return performance.getStatisticEntry();
    }

/*
Constructor used for save all options parameters of operation,
1) benchmark pattern transit parameters, used for called method
2) GUI and report parameters, used by this class
*/
ActionRun( // benchmark pattern transit parameters
           Performance benchmarkPerformance,
           int visualColumns, int visualRows, int durationSeconds,
           double angleDelta, boolean angleChange,
           VisualMode visualMode,  int speedLimitMs,
           VisualPicture visualPicture, boolean pictureChange,
           // GUI and report parameters
           PerformanceTableModel tableModel,
           DefaultBoundedRangeModel progressModel,
           JProgressBar progressBar,
           JButton runStopButton, ActionListener runStopListener,
           JComponent[] disabledComponents, JFrame applicationFrame
         )
    {
    // benchmark pattern parameters
    performance = benchmarkPerformance;
    columns     = visualColumns;
    rows        = visualRows;
    duration    = durationSeconds;
    angle       = angleDelta;
    angleChg    = angleChange;
    mode        = visualMode;
    delay       = speedLimitMs;
    picture     = visualPicture;
    pictureChg  = pictureChange;
    // GUI and report parameters
    tModel      = tableModel;
    pModel      = progressModel;
    pBar        = progressBar;
    rsButton    = runStopButton;
    rsListener  = runStopListener;
    disComp     = disabledComponents;
    appFrame    = applicationFrame;
    }

/*
Entry point for run performance benchmark as separate thread
*/
@Override public void run()
    {
    /*
    Setup GUI state before run benchmark performance scenario    
    */
    running = true;
    errorFlag = false;
    errorString = "N/A";
    rsName = rsButton.getText();
    rsButton.setText( "Stop" );
    ActionListener stopListener = new LstStop();
    rsButton.removeActionListener( rsListener );
    rsButton.addActionListener( stopListener );
    helperDisableEnable( false );
    appFrame.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
    percentage = 0.0;
    pModel.setValue( (int)percentage );
    pBar.setString ( pModel.getValue() + "%" );
    pBar.repaint();
    helperBlankTable( "-" );
    tModel.fireTableDataChanged();
    /*
    Run benchmark performance scenario
    */
    performance.runScenario( columns, rows, duration, angle, angleChg,
                             mode, delay, picture, pictureChg );
    /*
    Wait for benchmark performance scenario termination
    */
    while ( ! performance.getTaskDone() )
        {
        /*
        Revisual statistics table and progress indicator    
        */
        StatisticEntry se = performance.getStatisticEntry();
        if ( se != null )
            {
            percentage = se.percentage;
            String sMedian = String.format    ( " %.2f", se.median  );
            String sMedianMin = String.format ( " %.2f", se.median1 );
            String sMedianMax = String.format ( " %.2f", se.median2 );
            String sAverage   = String.format ( " %.2f", se.average );
            String sMin       = String.format ( " %.2f", se.min     );
            String sMax       = String.format ( " %.2f", se.max     );
            tModel.setValueAt ( sMedian,    0, 1 );
            tModel.setValueAt ( sMedianMin, 0, 2 );
            tModel.setValueAt ( sMedianMax, 0, 3 );
            tModel.setValueAt ( sAverage,   1, 1 );
            tModel.setValueAt ( sMin,       1, 2 );
            tModel.setValueAt ( sMax,       1, 3 );
            tModel.fireTableDataChanged();
            }
        else
            {
            percentage = 0.0;
            }
        pModel.setValue( (int)percentage );
        pBar.setString ( pModel.getValue() + "%" );
        pBar.repaint();
        /*
        Delay 50 milliseconds    
        */
        try 
            {
            sleep( 50 );
            }
        catch( InterruptedException e )
            {
            errorFlag = true;
            errorString = e.getMessage();
            }
        }
    /*
    Write "skipped" if interrupted by user click "Stop"
    */
    if ( performance.getTaskInterrupt() )
        {
        helperBlankTable( "skipped" );
        tModel.fireTableDataChanged();
        }
    /*
    Restore GUI state after run benchmark performance scenario    
    */
    percentage = 100.0;
    pModel.setValue( (int)percentage );
    pBar.setString ( pModel.getValue() + "%" );
    pBar.repaint();
    rsButton.setText( rsName );
    rsButton.removeActionListener( stopListener );
    rsButton.addActionListener( rsListener );
    helperDisableEnable( true );
    appFrame.setDefaultCloseOperation( EXIT_ON_CLOSE );
    running = false;
    }

/*
Handler for "Stop" button, redefined from "Run" button when test in-progress
*/
private class LstStop implements ActionListener
    {
    @Override public void actionPerformed (ActionEvent e)
        {
        performance.setTaskInterrupt( true );
        }
    }
/*
Helper for blank table
*/
private void helperBlankTable( String s )
    {
    int cc = tModel.getColumnCount();
    int rc = tModel.getRowCount();
    for( int i=0; i<rc; i++ )
        {
        for( int j=1; j<cc; j++ )
            {
            tModel.setValueAt( s, i, j );
            }
        }
    }

private void helperDisableEnable( boolean b )
    {
    for( JComponent item : disComp )
        {
        item.setEnabled( b );
        item.repaint();
        item.revalidate();
        }
    }
}
