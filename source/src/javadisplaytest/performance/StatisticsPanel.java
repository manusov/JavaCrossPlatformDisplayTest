/*
Java Display Test and Benchmark Utility. (C)2021 IC Book Labs
--------------------------------------------------------------
Down GUI panel for visual FPS (Frames Per Second) statistics.
*/

package javadisplaytest.performance;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

class StatisticsPanel extends JPanel
{
private String statisticsString = null;

void setStatisticsString( String s )
    {
    statisticsString = s;
    }

@Override protected void paintComponent( Graphics g ) 
    {
    super.paintComponent( g );
    if ( statisticsString != null )
        {
        g.setColor( Color.BLACK );
        g.drawString( statisticsString, 4, 21 );
        }
    }
}
