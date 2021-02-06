/*
Java Display Test and Benchmark Utility. (C)2021 IC Book Labs
--------------------------------------------------------------
Panel for benchmark performance: picture rotation.
This class for visualization mode = GUI timer ticks.
This class is parent for PerformancePanelThreadMode,
different organization of image update parallel threads.
*/

package javadisplaytest.performance;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;

class PerformancePanelTimerMode extends JPanel
{
/*
Fields required for benchmark performance.    
*/
double angle;
double angleDelta;
int speedLimitMs;
BufferedImage originalImage;
BufferedImage rotatedImage;
int imageWidth = 0;
int imageHeight = 0;
int framesCounter;

/*
Support status information about benchmark performance errors.
*/
boolean status = false;
String statusString = null;
boolean getStatus()       { return status;       }
String getStatusString()  { return statusString; }

PerformancePanelTimerMode
    ( double angleDelta, int speedLimitMs, BufferedImage image )
    {
    if ( image == null )
        {
        status = false;
        }
    else
        {
        this.angleDelta = angleDelta;
        this.speedLimitMs = speedLimitMs;
        this.originalImage = image;
        imageWidth = originalImage.getWidth();
        imageHeight = originalImage.getHeight();
        rotatedImage = helperRotate( originalImage, 0.0 );
        status = true;
        }
    }

/*
Timer for benchmark performance.    
*/
private Timer performanceTimer;
    
boolean runPerformance()
    {
    framesCounter = 0;
    angle = 0.0;
    int a = speedLimitMs;
    if ( a == 0 ) a++;
    if ( originalImage != null )
        {
        performanceTimer = new Timer( a, new RotorTimerListener() );
        performanceTimer.start();
        status = true;
        }
    else
        {
        status = false;
        }
    return status;
    }

void stopPerformance()
    {
    performanceTimer.stop();
    }

public synchronized int getAndClearFramesCounter()
    {
    int a = framesCounter;
    framesCounter = 0;
    return a;
    }

synchronized void incrementFramesCounter()
    {
    framesCounter++;
    }

@Override public Dimension getPreferredSize()
    {
    int width = 200;
    int height = 200;
    if ( ( originalImage != null )&&( imageWidth > 0 )&&( imageHeight > 0 ) )
        {
        width = height = Math.max( imageWidth, imageHeight );
        }
    return new Dimension( width, height );
    }

@Override protected void paintComponent( Graphics g ) 
    {
    super.paintComponent( g );
    if ( rotatedImage != null )
        {
        Graphics2D g2d = (Graphics2D) g.create();
        int x = ( getWidth() - rotatedImage.getWidth() ) / 2 ;
        int y = ( getHeight() - rotatedImage.getHeight() ) / 2;
        g2d.drawImage( rotatedImage, x, y, this );
        g2d.dispose();
        }
    }

private class RotorTimerListener implements ActionListener
    {
    @Override public void actionPerformed( ActionEvent e )
        {
            angle += angleDelta;
            rotatedImage = helperRotate( originalImage, angle );
            repaint();
            incrementFramesCounter();
        }
    }

final BufferedImage helperRotate( BufferedImage image, double angle ) 
    {
    double radians = Math.toRadians( angle );
    double sin = Math.abs( Math.sin( radians ) );
    double cos = Math.abs( Math.cos( radians ) );
    int width  = image.getWidth();
    int height = image.getHeight();
    
    int rWidth  = (int) Math.floor( width * cos + height * sin );
    int rHeight = (int) Math.floor( height * cos + width * sin );
    BufferedImage rImage = 
        new BufferedImage( rWidth, rHeight, BufferedImage.TYPE_INT_ARGB );
    Graphics2D g2d = rImage.createGraphics();
    
    AffineTransform transform = new AffineTransform();
    transform.translate( ( rWidth - width ) / 2, ( rHeight - height ) / 2 );
    int x = width / 2;
    int y = height / 2;
    transform.rotate( radians, x, y );
    g2d.setTransform( transform );
    
    g2d.drawImage( image, 0, 0, this );
    g2d.dispose();
    return rImage;
    }
}
