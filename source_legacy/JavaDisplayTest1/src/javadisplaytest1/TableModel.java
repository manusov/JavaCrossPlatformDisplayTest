// Table model for screen parameters view.

package javadisplaytest1;

import javax.swing.table.AbstractTableModel;

// RTM, NIO Benchmark Report Table Model, used for options values add to report
public class TableModel extends AbstractTableModel
    {
    // table model class fields
    private String[] columnsNames;
    private String[][] rowsValues;
    // table model this application-specific public methods
    public void setColumnsNames(String[] s)  { columnsNames = s; }
    public void setRowsValues(String[][] s)  { rowsValues = s; }
    public String[] getColumnsNames()        { return columnsNames; }
    public String[][] getRowsValues()        { return rowsValues; }
    // table model standard required public methods
    @Override public int getColumnCount()    { return columnsNames.length; }
    @Override public int getRowCount()       { return rowsValues.length; }
    @Override public String getColumnName(int column)
        { return columnsNames[column]; }
    @Override public String getValueAt( int row, int column )
        { 
        if ( row<rowsValues.length ) 
             { return rowsValues[row][column]; }
        else { return ""; }
        }
    };
