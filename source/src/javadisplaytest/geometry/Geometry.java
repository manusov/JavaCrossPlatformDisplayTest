/*
Java Display Test and Benchmark Utility. (C)2021 IC Book Labs
--------------------------------------------------------------
Support information about system display and application screen parameters.
*/

package javadisplaytest.geometry;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Geometry 
{
public GeometryTableModel getModel( JFrame frame )
    {
    /*
    Get screen parameters    
    */
    GeometryTableModel model = new GeometryTableModel();
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    int screenResolution = toolkit.getScreenResolution();
    /*
    Get application frame parameters
    */
    GraphicsConfiguration cfg = frame.getGraphicsConfiguration();
    Rectangle rect = cfg.getBounds();
    int appXsize = rect.width;
    int appYsize = rect.height;
    int appXpos  = rect.x;
    int appYpos  = rect.y;
    /*
    Write parameters to model and return model
    */
    model.setValueAt( String.format( "%d", screenSize.width  ), 0, 1 );
    model.setValueAt( String.format( "%d", screenSize.height ), 1, 1 );
    model.setValueAt( String.format( "%d", screenResolution  ), 2, 1 );
    model.setValueAt( String.format( "%d", appXsize          ), 3, 1 );
    model.setValueAt( String.format( "%d", appYsize          ), 4, 1 );
    model.setValueAt( String.format( "%d", appXpos           ), 5, 1 );
    model.setValueAt( String.format( "%d", appYpos           ), 6, 1 );
    return model;
    }
}
