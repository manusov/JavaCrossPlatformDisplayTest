/* 
Java Display Test and Benchmark Utility. (C)2019 IC Book Labs
--------------------------------------------------------------
Application main class.
*/

package javadisplaytest;

import javadisplaytest.geometry.Geometry;
import javadisplaytest.performance.Performance;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class JavaDisplayTest 
{
public static void main(String[] args) 
    {
    /*
    Static GUI settings    
    */
    JFrame.setDefaultLookAndFeelDecorated( true );
    JDialog.setDefaultLookAndFeelDecorated( true );
    /*
    Initialize classes
    */
    Geometry geometry = new Geometry();
    Performance performance = new Performance();
    Application application = new Application( geometry, performance );
    /*
    Configure application
    */
    application.add( application.getApplicationPanel() );
    application.setDefaultCloseOperation( EXIT_ON_CLOSE );
    application.setLocationRelativeTo( null );
    application.setSize( application.getApplicationDimension() );
    application.setResizable( false );
    application.setVisible( true );
    }
}
