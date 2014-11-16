
package imageProcessing;


import edu.princeton.cs.introcs.Picture;

/*************************************************************************
 
 * The <tt>ConnectedComponentImageInterface</tt> class
 *
 * 
 * @author 
 *************************************************************************/
public interface ConnectedComponentImagingInterface {

	/**
	 * Returns the number of components identified in the image.
	 * 
	 * @return the number of components (between 1 and N)
	 */
	public int countComponents();


	/**
	 * Returns the original image with each object bounded by a red box.
	 * 
	 * @return a picture object with all components surrounded by a red box
	 */
	public Picture identifyComonentImage();


	/**
	 * Returns a picture with each object updated to a random colour.
	 * 
	 * @return a picture object with all components coloured.
	 */
	public Picture colourComponentImage() ;


	/**
	 * @return Returns a picture
	 */
	public Picture getPicture();


	/**
	 * Returns a binarised version of the original image
	 * 
	 * @return a picture object with all components surrounded by a red box
	 */
	public Picture binaryComponentImage();

}
