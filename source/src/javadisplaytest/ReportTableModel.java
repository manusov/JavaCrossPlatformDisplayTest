/*
Java Display Test and Benchmark Utility. (C)2021 IC Book Labs
--------------------------------------------------------------
Table model for tables, especially generated for text report save.
*/

package javadisplaytest;

public class ReportTableModel extends ApplicationTableModel
{
private String[]   columnsNames;
private String[][] rowsValues;

@Override public String[] getColumnsNames()          { return columnsNames; }
public void setColumnsNames( String[] s )            { columnsNames = s;    }
@Override public String[][] getRowsValues()          { return rowsValues;   }
@Override public void setRowsValues( String[][] s )  { rowsValues = s;      }
}
