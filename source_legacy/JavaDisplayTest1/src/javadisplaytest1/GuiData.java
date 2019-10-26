// Get screen parameters.

package javadisplaytest1;

import java.awt.Toolkit;

public class GuiData
{
private final GuiDescriptor guiDescriptor;

public GuiData()
    {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    guiDescriptor = new GuiDescriptor();
    guiDescriptor.screenSize = toolkit.getScreenSize();
    guiDescriptor.screenResolution = toolkit.getScreenResolution();
    }

public GuiDescriptor getGuiDescriptor()
    {
    return guiDescriptor;
    }

}
