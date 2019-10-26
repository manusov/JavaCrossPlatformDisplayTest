/*
Java Display Test and Benchmark Utility. (C)2019 IC Book Labs
--------------------------------------------------------------
Panel for benchmark performance: picture rotation.
This class for visualization mode = Parallel thread.
This class is child of PerformancePanelTimerMode,
different organization of image update parallel threads.
*/

package javadisplaytest.performance;

import java.awt.image.BufferedImage;

class PerformancePanelThreadMode extends PerformancePanelTimerMode
{
PerformancePanelThreadMode
    ( double angleDelta, int speedLimitMs, BufferedImage image )
    {
    super( angleDelta, speedLimitMs, image );
    }

/*
Flag for benchmark performance    
*/
private boolean performanceFlag;    
    
@Override boolean runPerformance()
    {
    performanceFlag = true;
    framesCounter = 0;
    angle = 0.0;
    if ( originalImage != null )
        {
        RotorThread rotorThread = new RotorThread();
        rotorThread.start();
        status = true;
        }
    else
        {
        status = false;
        }
    return status;
    }

@Override void stopPerformance()
    {
    performanceFlag = false;
    }

private class RotorThread extends Thread
    {
    @Override public void run()
        {
        while( performanceFlag )
            {
            angle += angleDelta;
            rotatedImage = helperRotate( originalImage, angle );
            repaint();
            
            if ( speedLimitMs != 0 )
                {
                try 
                    {
                    Thread.sleep( speedLimitMs ); 
                    }
                catch ( InterruptedException ex ) { }
                }
            incrementFramesCounter();
            }
        }
    }
}
