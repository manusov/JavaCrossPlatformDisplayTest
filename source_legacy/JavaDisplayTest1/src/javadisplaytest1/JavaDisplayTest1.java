// Screen resolution and DPI test.

package javadisplaytest1;

import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class JavaDisplayTest1 
{
private static final int BOX_X = 350;
private static final int BOX_Y = 200;
private static final int RIGHT_MIN_X = 100;
private static final int RIGHT_MAX_X = 150;
    
private static final String TEST_NAME = "Display test 1 (v0.0)";
private static final String[] PARMS_NAMES = 
    { "Parameter" , "Value" };
private static final String[][] PARMS_VALUES = 
    {
        { "Screen size X"       , "n/a" },
        { "Screen size Y"       , "n/a" },
        { "Dots per inch (DPI)" , "n/a" },
        { ""                    , ""    },
        { "Application area size X"       , "n/a" },
        { "Application area size Y"       , "n/a" },
        { "Application area upper-left X" , "n/a" },
        { "Application area upper-left Y" , "n/a" }
        
    };

public static void main(String[] args) 
    {
    // Set global option for GUI style
    JFrame.setDefaultLookAndFeelDecorated(true);
    
        // Build GUI box
    GuiBox guiBox = new GuiBox(TEST_NAME);
    guiBox.setDefaultCloseOperation(EXIT_ON_CLOSE);
    guiBox.setSize(BOX_X, BOX_Y);
    guiBox.setLocationRelativeTo(null);
    
    // Build display data block, OS parameters
    GuiData guiData = new GuiData();
    GuiDescriptor guiDesc = guiData.getGuiDescriptor();
    int xSize = guiDesc.screenSize.width;
    int ySize = guiDesc.screenSize.height;
    int dpi = guiDesc.screenResolution;
    PARMS_VALUES[0][1] = String.format("%d", xSize);
    PARMS_VALUES[1][1] = String.format("%d", ySize);
    PARMS_VALUES[2][1] = String.format("%d", dpi);
    
    // Build display data block, GUI component parameters
    GraphicsConfiguration grapConf = guiBox.getGraphicsConfiguration();
    Rectangle rect = grapConf.getBounds();
    int appXsize = rect.width;
    int appYsize = rect.height;
    int appXpos  = rect.x;
    int appYpos  = rect.y;
    PARMS_VALUES[4][1] = String.format("%d", appXsize);
    PARMS_VALUES[5][1] = String.format("%d", appYsize);
    PARMS_VALUES[6][1] = String.format("%d", appXpos);
    PARMS_VALUES[7][1] = String.format("%d", appYpos);
    
    // Build table
    TableModel tableModel = new TableModel();
    tableModel.setColumnsNames(PARMS_NAMES);
    tableModel.setRowsValues(PARMS_VALUES);
    JTable table = new JTable(tableModel);
    JScrollPane scroll = new JScrollPane(table);
    
    // Adjust table
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setHorizontalAlignment(SwingConstants.CENTER);
    table.getColumnModel().getColumn(1).setCellRenderer(renderer);
    table.getColumnModel().getColumn(1).setMinWidth(RIGHT_MIN_X);
    table.getColumnModel().getColumn(1).setMaxWidth(RIGHT_MAX_X);

    // Output GUI box
    guiBox.add(scroll);
    guiBox.setVisible(true);
    }
    
}
