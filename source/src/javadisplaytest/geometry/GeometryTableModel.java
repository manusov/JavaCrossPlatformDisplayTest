/*
Java Display Test and Benchmark Utility. (C)2021 IC Book Labs
--------------------------------------------------------------
Display geometry parameters representation as table model,
used for GUI visualization and text report saving.
Unify data representation by AbstractTableModel.
*/

package javadisplaytest.geometry;

import javadisplaytest.ApplicationTableModel;

public class GeometryTableModel extends ApplicationTableModel
{
/*
table model class fields
*/
private final static String[] COLUMNS_NAMES = 
    { "Parameter" , "Value" };
private static String[][] rowsValues =
    { { "Screen size X"                 , "n/a" } ,
      { "Screen size Y"                 , "n/a" } ,
      { "Dots per inch (DPI)"           , "n/a" } ,
      { "Application area size X"       , "n/a" } ,
      { "Application area size Y"       , "n/a" } ,
      { "Application area upper-left X" , "n/a" } ,
      { "Application area upper-left Y" , "n/a" } };
/*
table model this application-specific public methods
*/
@Override public String[] getColumnsNames()          { return COLUMNS_NAMES; }
@Override public String[][] getRowsValues()          { return rowsValues;    }
@Override public void setRowsValues( String[][] s )  { rowsValues = s;       }
/*
customize one of table model standard required public methods
*/
@Override public String getValueAt( int row, int column )
    {
    return "  " + super.getValueAt( row, column );
    }
}
