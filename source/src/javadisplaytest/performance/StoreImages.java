/*
Java Display Test and Benchmark Utility. (C)2019 IC Book Labs
--------------------------------------------------------------
This class give access to pictures images as BufferedImage objects.
*/

package javadisplaytest.performance;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javadisplaytest.performance.Performance.VisualPicture;

import javax.imageio.ImageIO;

class StoreImages 
{
/*
Paths and names for JAR file resources.    
*/
private static final String FILE_PATH = "/javadisplaytest/resources/";
private static final String FILE_NAME_DOG = "image_dog.png";
private static final String FILE_NAME_CAT = "image_cat.png";

/*
Support status information about file I/O errors.
*/
private boolean status = false;
private String statusString = null;
boolean getStatus()       { return status;       }
String getStatusString()  { return statusString; }

/*
Target images, returned to caller by getPicture() method.
*/
private BufferedImage imageDog = null, imageCat = null;

/*
Get picture BufferedImage. 
Note picture must be pre-loaded, call this after loadPicture() success call.
*/
BufferedImage getPicture( VisualPicture vp )
    {
    BufferedImage image;
    switch ( vp )
        {
        case DOG_PICTURE:
            image = imageDog;
            break;
        case CAT_PICTURE:
            image = imageCat;
            break;
        default:
            image = null;
            break;
        }
    return image;
    }

/*
Load pictures from JAR resource to BufferedImage
*/
boolean loadPictures()
    {
    status = true;
    imageDog = helperLoad( FILE_NAME_DOG );
    if ( ( status )&&( imageDog != null ) )
        {
        imageCat = helperLoad( FILE_NAME_CAT );
        }
    return status;
    }

/*
Helper for load one resource file,
note this method update variables: status and statusString.
*/
private BufferedImage helperLoad( String name )
    {
    BufferedImage image = null;
    try
        {
        InputStream stream = 
            getClass().getResourceAsStream( FILE_PATH + name );
        image = ImageIO.read( stream );
        }
    catch ( IOException ex )
        {
        status = false;
        statusString = "Load image error: " + ex.getMessage();
        }
    return image;
    }
}
