/*
Java Display Test and Benchmark Utility. (C)2021 IC Book Labs
--------------------------------------------------------------
Video performance benchmarks results parameters representation
as table model, used for GUI visualization and text report saving.
Unify data representation by AbstractTableModel.
*/

package javadisplaytest.performance;

import javadisplaytest.ApplicationTableModel;

public class PerformanceTableModel extends ApplicationTableModel
{
/*
table model class fields
*/
private final static String[] COLUMNS_NAMES = 
    { "Value, FPS" , "Actual" , "Minimum" , "Maximum" };
private static String[][] rowsValues =
    { { "Median"       , "-" , "-" , "-" } ,
      { "Average"      , "-" , "-" , "-" } };
/*
table model this application-specific public methods
*/
@Override public String[] getColumnsNames()        { return COLUMNS_NAMES; }
@Override public String[][] getRowsValues()        { return rowsValues;    }
@Override public void setRowsValues(String[][] s)  { rowsValues = s;       }
}
