/*
Java Display Test and Benchmark Utility. (C)2019 IC Book Labs
--------------------------------------------------------------
Main GUI frame of application include buttons listeners.
*/

package javadisplaytest;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javadisplaytest.geometry.Geometry;
import javadisplaytest.geometry.GeometryTableModel;
import javadisplaytest.performance.Performance;
import javadisplaytest.performance.Performance.VisualMode;
import javadisplaytest.performance.Performance.VisualPicture;
import javadisplaytest.performance.PerformanceTableModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class Application extends JFrame
{
/*
Global objects
*/
private final Geometry geometry;
private final Performance performance;
/*
sizes of GUI components
*/
private final static Dimension SIZE_WINDOW     = new Dimension ( 410, 450 );
private final static Dimension SIZE_COMBO      = new Dimension ( 124, 21 );
private final static Dimension SIZE_COMBO_HALF = new Dimension ( 124, 21 );
private final static Dimension SIZE_PROGRESS   = new Dimension ( 189, 21 );
private final static Dimension SIZE_BUTTON     = new Dimension ( 87, 24 );
private final static Dimension SIZE_RUN        = new Dimension ( 78, 24 );
private final static Dimension SIZE_LABEL      = new Dimension ( 85, 21 );
/*
GUI components
*/
private final SpringLayout sl;
private final JPanel p;
private final GeometryTableModel geometryTableModel;
private final PerformanceTableModel performanceTableModel;
private final JTable geometryTable;
private final JTable performanceTable;
private final JScrollPane sg;
private final JScrollPane sp;
private final DefaultTableCellRenderer geometryTableRenderer;
private final DefaultTableCellRenderer performanceTableRenderer;
private final JProgressBar pb;
private final DefaultBoundedRangeModel progressRangeModel;
private final JLabel l1, l2, l3, l4, l5, l6;
private final JComboBox c1, c2, c3, c4, c5, c6, c7, c8, c9;
private final JButton b1, b2, b3, b4, b5;
private static final int NUM_OPTIONS_VERTICAL = 5;
/*
Options values for combo boxes
*/
private final static int COMBO_COLUMNS[] = { 1, 2, 3, 4, 5 };
private final static int COMBO_ROWS[] = { 1, 2, 3 };
private final static int COMBO_DURATION[] = { 10, 20, 40, 60, 120, 180, 300 };
private final static double COMBO_ANGLE[] = 
    { 0.1, 0.3, 0.5, 1.0, 5.0, 10.0, -0.1, -0.3, -0.5, -1.0, -5.0, -10.0 };
private final static boolean COMBO_ANGLE_CHANGE[] = { false, true };
private final static VisualMode COMBO_MODE[] = 
    { VisualMode.TIMER_MODE, VisualMode.THREAD_MODE };
private final static int COMBO_DELAY[] = { 0, 1, 3, 5, 10, 20, 30, 40, 50 };
private final static VisualPicture COMBO_PICTURE[] =
    { VisualPicture.DOG_PICTURE, VisualPicture.CAT_PICTURE };
private final static boolean COMBO_PICTURE_CHANGE[] = { false, true };
/*
Supports buttons press events
*/
private final LstRun      lst1;
private final LstAbout    lst2;
private final LstReport   lst3;
private final LstDefaults lst4;
private final LstCancel   lst5;

private final JFrame frame;

Application( Geometry geometry, Performance performance )
    {
    super( About.getShortName() );
    frame = this;
    this.geometry = geometry;
    this.performance = performance;
    sl = new SpringLayout();
    p = new JPanel( sl );
    /*
    Build components
    */
    geometryTableModel = this.geometry.getModel( frame );
    geometryTable = new JTable( geometryTableModel );
    performanceTableModel = new PerformanceTableModel();
    performanceTable = new JTable( performanceTableModel );
    sg = new JScrollPane( geometryTable );
    sp = new JScrollPane( performanceTable );
    geometryTableRenderer = new DefaultTableCellRenderer();
    performanceTableRenderer = new DefaultTableCellRenderer();
    geometryTableRenderer.setHorizontalAlignment( SwingConstants.CENTER );
    geometryTable.getColumnModel().getColumn(1).
        setCellRenderer( geometryTableRenderer );
    performanceTableRenderer.setHorizontalAlignment( SwingConstants.CENTER );
    for ( int i=0; i<performanceTable.getColumnCount(); i++ )
        { performanceTable.getColumnModel().getColumn(i).
            setCellRenderer( performanceTableRenderer ); }
    l1 = new JLabel( "Matrix"      );
    l2 = new JLabel( "Duration"    );
    l3 = new JLabel( "Angle delta" );
    l4 = new JLabel( "Render mode" );
    l5 = new JLabel( "Picture"     );
    l6 = new JLabel( "Run"         );
    l1.setPreferredSize( SIZE_LABEL );
    l2.setPreferredSize( SIZE_LABEL );
    l3.setPreferredSize( SIZE_LABEL );
    l4.setPreferredSize( SIZE_LABEL );
    l5.setPreferredSize( SIZE_LABEL );
    l6.setPreferredSize( SIZE_LABEL );
    c1 = new JComboBox();
    c2 = new JComboBox();
    c3 = new JComboBox();
    c4 = new JComboBox();
    c5 = new JComboBox();
    c6 = new JComboBox();
    c7 = new JComboBox();
    c8 = new JComboBox();
    c9 = new JComboBox();
    c1.setPreferredSize( SIZE_COMBO_HALF );
    c2.setPreferredSize( SIZE_COMBO_HALF );
    c3.setPreferredSize( SIZE_COMBO );
    c4.setPreferredSize( SIZE_COMBO_HALF );
    c5.setPreferredSize( SIZE_COMBO_HALF );
    c6.setPreferredSize( SIZE_COMBO_HALF );
    c7.setPreferredSize( SIZE_COMBO_HALF );
    c8.setPreferredSize( SIZE_COMBO_HALF );
    c9.setPreferredSize( SIZE_COMBO_HALF );
    /*
    Add items to combos
    */
    for( int i=0; i<COMBO_COLUMNS.length; i++ )
        {
        int n = COMBO_COLUMNS[i];
        String s = ( n == 1 ) ? "column" : "columns";
        c1.addItem( String.format( " %d %s", n, s ) );
        }
    for( int i=0; i<COMBO_ROWS.length; i++ )
        {
        int n = COMBO_ROWS[i];
        String s = ( n == 1 ) ? "row" : "rows";
        c2.addItem( String.format( " %d %s", n, s ) );
        }
    for( int i=0; i<COMBO_DURATION.length; i++ )
        {
        c3.addItem( String.format( " %d seconds", COMBO_DURATION[i] ) );
        }
    for( int i=0; i<COMBO_ANGLE.length; i++ )
        {
        c4.addItem( String.format( " %.1f degree", COMBO_ANGLE[i] ) );
        }
    for( int i=0; i<COMBO_ANGLE_CHANGE.length; i++ )
        {
        String s = COMBO_ANGLE_CHANGE[i]
            ? " change polarity" : " same polarity";
        c5.addItem( s );
        }
    for ( VisualMode item : COMBO_MODE )
        {
        c6.addItem( " " + item.getName());
        }
    for( int i=0; i<COMBO_DELAY.length; i++ )
        {
        int n = COMBO_DELAY[i];
        String s = ( n == 0 ) 
            ? "no delay" : String.format("+%d ms", n );
        c7.addItem( " " + s );
        }
    for ( VisualPicture item : COMBO_PICTURE )
        {
        c8.addItem( " " + item.getName());
        }
    for( int i=0; i<COMBO_PICTURE_CHANGE.length; i++ )
        {
        String s = COMBO_PICTURE_CHANGE[i]
            ? " change picture" : " same picture";
        c9.addItem( s );
        }
    /*
    Set defaults for combo options
    */
    helperDefaults();
    /*
    Continue build GUI components, progress indicator and buttons
    */
    progressRangeModel = new DefaultBoundedRangeModel( 0, 0, 0, 100 );
    pb = new JProgressBar( progressRangeModel );
    pb.setPreferredSize( SIZE_PROGRESS );
    pb.setStringPainted( true );
    pb.setString( "Please run..." );
    b1 = new JButton( "Run"      );
    b2 = new JButton( "About"    );
    b3 = new JButton( "Report"   );
    b4 = new JButton( "Defaults" );
    b5 = new JButton( "Cancel"   );
    b1.setPreferredSize( SIZE_RUN    );
    b2.setPreferredSize( SIZE_BUTTON );
    b3.setPreferredSize( SIZE_BUTTON );
    b4.setPreferredSize( SIZE_BUTTON );
    b5.setPreferredSize( SIZE_BUTTON );
    /*
    Create and add buttons press events listeners
    */
    lst1 = new LstRun();
    lst2 = new LstAbout();
    lst3 = new LstReport();
    lst4 = new LstDefaults();
    lst5 = new LstCancel();
    b1.addActionListener( lst1 );
    b2.addActionListener( lst2 );
    b3.addActionListener( lst3 );
    b4.addActionListener( lst4 );
    b5.addActionListener( lst5 );
    /*
    Make layout, start setup components coordinates and sizes,
    geometry and results tables
    */
    sl.putConstraint ( SpringLayout.NORTH, sg,   1, SpringLayout.NORTH, p  );
    sl.putConstraint ( SpringLayout.SOUTH, sg, 135, SpringLayout.NORTH, p  );
    sl.putConstraint ( SpringLayout.WEST,  sg,   1, SpringLayout.WEST,  p  );
    sl.putConstraint ( SpringLayout.EAST,  sg,   0, SpringLayout.EAST,  p  );
    sl.putConstraint ( SpringLayout.NORTH, sp,   3, SpringLayout.SOUTH, sg );
    sl.putConstraint ( SpringLayout.SOUTH, sp,  57, SpringLayout.SOUTH, sg );
    sl.putConstraint ( SpringLayout.WEST,  sp,   1, SpringLayout.WEST,  p  );
    sl.putConstraint ( SpringLayout.EAST,  sp,   0, SpringLayout.EAST,  p  );
    /*
    Make layout for left labels
    */
    sl.putConstraint ( SpringLayout.NORTH, l1,  21, SpringLayout.SOUTH, sp );
    sl.putConstraint ( SpringLayout.WEST,  l1,  22, SpringLayout.WEST,  p  );
    sl.putConstraint ( SpringLayout.NORTH, l2,   6, SpringLayout.SOUTH, l1 );
    sl.putConstraint ( SpringLayout.WEST,  l2,  22, SpringLayout.WEST,  p  );
    sl.putConstraint ( SpringLayout.NORTH, l3,   6, SpringLayout.SOUTH, l2 );
    sl.putConstraint ( SpringLayout.WEST,  l3,  22, SpringLayout.WEST,  p  );
    sl.putConstraint ( SpringLayout.NORTH, l4,   6, SpringLayout.SOUTH, l3 );
    sl.putConstraint ( SpringLayout.WEST,  l4,  22, SpringLayout.WEST,  p  );
    sl.putConstraint ( SpringLayout.NORTH, l5,   6, SpringLayout.SOUTH, l4 );
    sl.putConstraint ( SpringLayout.WEST,  l5,  22, SpringLayout.WEST,  p  );
    sl.putConstraint ( SpringLayout.NORTH, l6,   6, SpringLayout.SOUTH, l5 );
    sl.putConstraint ( SpringLayout.WEST,  l6,  22, SpringLayout.WEST,  p  );
    /*
    Make layout for combo boxes
    */
    sl.putConstraint ( SpringLayout.NORTH, c1,  20, SpringLayout.SOUTH, sp );
    sl.putConstraint ( SpringLayout.WEST,  c1,   1, SpringLayout.EAST,  l1 );
    sl.putConstraint ( SpringLayout.NORTH, c2,   0, SpringLayout.NORTH, l1 );
    sl.putConstraint ( SpringLayout.WEST,  c2,  10, SpringLayout.EAST,  c1 );
    sl.putConstraint ( SpringLayout.NORTH, c3,   6, SpringLayout.SOUTH, c2 );
    sl.putConstraint ( SpringLayout.WEST,  c3,   0, SpringLayout.WEST,  c1 );
    sl.putConstraint ( SpringLayout.NORTH, c4,   6, SpringLayout.SOUTH, c3 );
    sl.putConstraint ( SpringLayout.WEST,  c4,   0, SpringLayout.WEST,  c3 );
    sl.putConstraint ( SpringLayout.NORTH, c5,   0, SpringLayout.NORTH, c4 );
    sl.putConstraint ( SpringLayout.WEST,  c5,  10, SpringLayout.EAST,  c4 );
    sl.putConstraint ( SpringLayout.NORTH, c6,   6, SpringLayout.SOUTH, c4 );
    sl.putConstraint ( SpringLayout.WEST,  c6,   0, SpringLayout.WEST,  c4 );
    sl.putConstraint ( SpringLayout.NORTH, c7,   0, SpringLayout.NORTH, c6 );
    sl.putConstraint ( SpringLayout.WEST,  c7,  10, SpringLayout.EAST,  c6 );
    sl.putConstraint ( SpringLayout.NORTH, c8,   6, SpringLayout.SOUTH, c6 );
    sl.putConstraint ( SpringLayout.WEST,  c8,   0, SpringLayout.WEST,  c6 );
    sl.putConstraint ( SpringLayout.NORTH, c9,   0, SpringLayout.NORTH, c8 );
    sl.putConstraint ( SpringLayout.WEST,  c9,  10, SpringLayout.EAST,  c8 );
    /*
    Make layout for progress indicator
    */
    sl.putConstraint ( SpringLayout.NORTH, pb,  12, SpringLayout.SOUTH, c9 );
    sl.putConstraint ( SpringLayout.WEST,  pb,   1, SpringLayout.EAST,  l6 );
    /*
    Make layout for run button
    */
    sl.putConstraint ( SpringLayout.NORTH, b1,  -1, SpringLayout.NORTH, pb );
    sl.putConstraint ( SpringLayout.WEST,  b1,   8, SpringLayout.EAST,  pb );
    /*
    Make layout for 4 down buttons from left to right
    */
    sl.putConstraint ( SpringLayout.SOUTH, b5,  -3, SpringLayout.SOUTH, p  );
    sl.putConstraint ( SpringLayout.EAST,  b5,  -3, SpringLayout.EAST,  p  );
    sl.putConstraint ( SpringLayout.SOUTH, b4,  -3, SpringLayout.SOUTH, p  );
    sl.putConstraint ( SpringLayout.EAST,  b4,  -3, SpringLayout.WEST,  b5 );
    sl.putConstraint ( SpringLayout.SOUTH, b3,  -3, SpringLayout.SOUTH, p  );
    sl.putConstraint ( SpringLayout.EAST,  b3,  -3, SpringLayout.WEST,  b4 );
    sl.putConstraint ( SpringLayout.SOUTH, b2,  -3, SpringLayout.SOUTH, p  );
    sl.putConstraint ( SpringLayout.EAST,  b2,  -3, SpringLayout.WEST,  b3 );
    /*
    Components build and layout done, add it to panel.
    */
    p.add( sg );
    p.add( sp );
    p.add( l1 );
    p.add( l2 );
    p.add( l3 );
    p.add( l4 );
    p.add( l5 );
    p.add( l6 );
    p.add( c1 );
    p.add( c2 );
    p.add( c3 );
    p.add( c4 );
    p.add( c5 );
    p.add( c6 );
    p.add( c7 );
    p.add( c8 );
    p.add( c9 );
    p.add( pb );
    p.add( b1 );
    p.add( b2 );
    p.add( b3 );
    p.add( b4 );
    p.add( b5 );
    }

Dimension getApplicationDimension()
    {
    return SIZE_WINDOW;
    }
   
JPanel getApplicationPanel()
    {
    return p;
    }

/*
Buttons listeners, handlers for buttons press
*/

/*
RUN button
*/
private class LstRun implements ActionListener
    {
    @Override public void actionPerformed ( ActionEvent e )
        {
        /*
        Extract options values from GUI combo components
        */
        int columns           = COMBO_COLUMNS        [ c1.getSelectedIndex() ];
        int rows              = COMBO_ROWS           [ c2.getSelectedIndex() ];
        int duration          = COMBO_DURATION       [ c3.getSelectedIndex() ];
        double angle          = COMBO_ANGLE          [ c4.getSelectedIndex() ];
        boolean angleChange   = COMBO_ANGLE_CHANGE   [ c5.getSelectedIndex() ];
        VisualMode mode       = COMBO_MODE           [ c6.getSelectedIndex() ];
        int delay             = COMBO_DELAY          [ c7.getSelectedIndex() ];
        VisualPicture picture = COMBO_PICTURE        [ c8.getSelectedIndex() ];
        boolean pictureChange = COMBO_PICTURE_CHANGE [ c9.getSelectedIndex() ];
        /*
        Build list of disabled GUI
        */
        JComponent disabledComponents[] = new JComponent[]
            { c1, c2, c3, c4, c5, c6, c7, c8, c9, b2, b3, b4, b5 };
        /*
        Create thread and run benchmark performance, 
        include open new window frame
        */    
        ActionRun ar = new ActionRun
            ( // benchmark pattern parameters
              performance, columns, rows, duration, angle, angleChange, 
              mode, delay, picture, pictureChange,
              // GUI and report parameters
              performanceTableModel, progressRangeModel, pb,
              b1, lst1, disabledComponents, frame
            );
        // setup done, run benchmark performance
        ar.start();
        lastAction = ar;
        }
    }

/*
ABOUT button
*/
private class LstAbout implements ActionListener
    {
    @Override public void actionPerformed ( ActionEvent e )
        {
        ActionAbout ab = new ActionAbout();
        JDialog dialog = ab.createDialog
            ( frame, About.getLongName(), About.getVendorName() );
        dialog.setLocationRelativeTo( null );
        dialog.setVisible( true );
        }
    }

/*
REPORT button and required data
*/
private final static String LOG_NAME = "\r\nDetail log.\r\n" +
                                       "FPS = Frames per Second.\r\n" +
                                       "Tag notes: M = Median\r\n\r\n";
private final String[] LOG_COLUMNS   = { "FPS", "Tag" };
private ActionRun lastAction = null;

private class LstReport implements ActionListener
    {
    @Override public void actionPerformed ( ActionEvent e )
        {
        /*
        Build report table for test options
        */
        String[] s1 = new String[] { "Option" , "Value" };
        String[][] s2 = new String[NUM_OPTIONS_VERTICAL][2];
        s2[0][0] = l1.getText();
        s2[1][0] = l2.getText();
        s2[2][0] = l3.getText();
        s2[3][0] = l4.getText();
        s2[4][0] = l5.getText();
        s2[0][1] = ( (String) c1.getSelectedItem() ).trim() + " x " +
                   ( (String) c2.getSelectedItem() ).trim();
        s2[1][1] = ( (String) c3.getSelectedItem() ).trim();
        s2[2][1] = ( (String) c4.getSelectedItem() ).trim() + " , " +
                   ( (String) c5.getSelectedItem() ).trim();
        s2[3][1] = ( (String) c6.getSelectedItem() ).trim() + " , " +
                   ( (String) c7.getSelectedItem() ).trim();
        s2[4][1] = ( (String) c8.getSelectedItem() ).trim() + " , " +
                   ( (String) c9.getSelectedItem() ).trim();
        ReportTableModel optionsTableModel = new ReportTableModel();
        optionsTableModel.setColumnsNames( s1 );
        optionsTableModel.setRowsValues( s2 );
        /*
        Build report table for benchmark performance log
        */
        ReportTableModel logTableModel = null;
        String logHeader = null;
        if ( lastAction != null )
            {
            double[] logArray = lastAction.getLogArray();
            StatisticEntry statisticEntry = lastAction.getStatisticEntry();
            if ( ( logArray != null )&&( statisticEntry != null )&&
                 ( logArray.length > 0 ) )
                {
                logHeader = LOG_NAME;
                s1 = LOG_COLUMNS;
                s2 = new String[ logArray.length ][2];
                for( int i=0; i< logArray.length; i++ )
                    {
                    s2[i][0] = String.format( "%.1f" , logArray[i] );
                    boolean b = ( statisticEntry.medianIndex1 == i ) ||
                                ( statisticEntry.medianIndex2 == i ) ||
                                ( statisticEntry.medianIndex3 == i );
                    s2[i][1] = b ? "M" : " ";
                    }
                logTableModel = new ReportTableModel();
                logTableModel.setColumnsNames( s1 );
                logTableModel.setRowsValues( s2 );
                }
            }
        /*
        Build report dialogue and write text file to log
        */
        ActionReport ar = new ActionReport();
        ar.createDialogRT
            ( frame,
              geometryTableModel, optionsTableModel, performanceTableModel,
              About.getLongName(), About.getVendorName(), 
              logHeader, logTableModel );
        }
    }

/*
DEFAULTS button
*/
private class LstDefaults implements ActionListener
    {
    @Override public void actionPerformed ( ActionEvent e )
        {
        helperDefaults();
        }
    }

/*
CANCEL button
*/
private class LstCancel implements ActionListener
    {
    @Override public void actionPerformed ( ActionEvent e )
        {
        System.exit(0);
        }
    }

/*
Helper for default settings
Note zero (default matching) items required for set defaults after edit.
*/
private void helperDefaults()
    {
    c1.setSelectedIndex( 0 );
    c2.setSelectedIndex( 0 );
    c3.setSelectedIndex( 3 );
    c4.setSelectedIndex( 2 );
    c5.setSelectedIndex( 0 );
    c6.setSelectedIndex( 1 );
    c7.setSelectedIndex( 0 );
    c8.setSelectedIndex( 0 );
    c9.setSelectedIndex( 0 );
    }

}
