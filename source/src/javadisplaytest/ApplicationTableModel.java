/*
Java Display Test and Benchmark Utility. (C)2021 IC Book Labs
--------------------------------------------------------------
AbstractTableModel customization class
for this application-specific requirements.
*/

package javadisplaytest;

import javax.swing.table.AbstractTableModel;

public abstract class ApplicationTableModel extends AbstractTableModel
{
/*
table model this application-specific public methods
*/
abstract public String[] getColumnsNames();
abstract public String[][] getRowsValues();
abstract public void setRowsValues( String[][] s );
/*
table model standard required public methods
*/
@Override public int getColumnCount() { return getColumnsNames().length; }
@Override public int getRowCount()    { return getRowsValues().length;   }
@Override public String getColumnName( int column )
    {
    if ( column < getColumnsNames().length )   
        return getColumnsNames()[column];
    else
        return "?";
    }
@Override public String getValueAt( int row, int column )
    { 
    if ( ( row < getRowsValues().length ) &&
         ( column < getColumnsNames().length ) )
        return getRowsValues()[row][column];
    else
        return "";
    }
@Override public void setValueAt( Object ob, int row, int column )
    {
    if ( ( ob instanceof String ) &&
         ( row < getRowsValues().length ) &&
         ( column < getRowsValues()[row].length ) )
        {
        getRowsValues()[row][column] = (String) ob;
        }
    }
@Override public boolean isCellEditable( int row, int column )
    { return false; }
}
