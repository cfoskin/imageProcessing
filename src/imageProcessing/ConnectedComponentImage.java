package imageProcessing;



import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import edu.princeton.cs.introcs.Picture;
import edu.princeton.cs.introcs.StdOut;

/*************************************************************************
 * Compilation: javac ConnectedComponentImage.java
 * 
 * @author Colum Foskin
 * version 1.0
 *************************************************************************/
public class ConnectedComponentImage implements ConnectedComponentImagingInterface {
	private static int THRESHOLD = 128;
	private int width;
	private int height;
	private String fileLocation;
	private ArrayList<Component> components;
	private ArrayList <Integer> roots;

	/**	 
	 * @param fileLocation constructs a new ConnectedComponentImage object
	 */

	public ConnectedComponentImage (String fileLocation) {
		this.fileLocation = fileLocation;
		components = new ArrayList<Component>();
		roots = new ArrayList<Integer>();
	}

	/**
	 * @return returns the original Picture object
	 */
	public Picture getPicture() {
		Picture p = new Picture(this.fileLocation);
		return p;
	}

	/**
	 * @return returns a grayScaled version of a Picture object 
	 * This picture can then be used to convert to a binary image
	 */
	public Picture grayScaleImage()
	{
		Picture p = new Picture(fileLocation);
		this.width = p.width();
		this.height = p.height();
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				Color cl = p.get(x, y);
				Color gray = Luminance.toGray(cl);
				p.set(x, y, gray);
			}
		}
		return p;
	}

	/**
	 * @return
	 * Returns a binarised version of the original image by taking in the picture 
	 * from the grayscale method and uses this to convert
	 */
	public Picture binaryComponentImage()
	{
		Picture p = new Picture(grayScaleImage());
		this.width = p.width();
		this.height = p.height();
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				Color cl = p.get(x, y);
				double lumValue = Luminance.lum(cl);	
				if (lumValue >= THRESHOLD)
				{
					p.set(x, y, Color.BLACK);
				}
				else
				{
					p.set(x, y, Color.WHITE);
				}
			}
		}
		return p;
	}

	/**
	 * @return
	 * This private method just returns a new random color for use in the 
	 * colorComponentImage method 
	 */
	public Color getRandomColor(){
		Random randomGenerator = new Random();
		int red = randomGenerator.nextInt(255);
		int green = randomGenerator.nextInt(255);
		int blue = randomGenerator.nextInt(255);
		return new Color(red,green,blue);
	}

	/**
	 * 
	 * @return Returns a picture with each object updated to a random color
	 * This includes the background color
	 */
	public Picture colourComponentImage() {

		Picture  p = binaryComponentImage();
		WeightedQuickUnionUF wqu = checkUnion(p);
		this.width = p.width();
		this.height = p.height();
		HashMap<Integer, Color> cl = new HashMap<Integer, Color>();
		for (int x = 0; x < width; x++)
		{
			for(int y=0; y< height; y++)
			{
				int root = wqu.find(index(x, y));
				if(cl.get(root) == null){//just checking if there is no color there for the root already
					Color randomColor = getRandomColor();
					cl.put(root, randomColor);
					p.set(x,y, randomColor);
				}
				else
				{
					p.set(x, y, cl.get(root));// set the random color anyway
				}
			}
		}
		return p;
	}

	/**
	 * @param x
	 * @param y
	 * @return This private method returns the location of each pixel when used in the 
	 * checkUnion method.
	 */
	private int index(int x, int y)
	{
		return ((y*width)+x);
	}

	/**
	 * @return 
	 *This method returns a weightedQuickUnionUF object.
	 *This method takes in a binary image and creates a new wqu
	 *object of the picsize. I am the  doing a check for each pixel to check if
	 *the color of each one matches any that are next to it or over it and if so union it.
	 */
	private WeightedQuickUnionUF checkUnion(Picture p)
	{
		p = new Picture(binaryComponentImage());
		this.width = p.width() ; 
		this.height = p.height() ;
		int picSize = (width*height);
		WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(picSize);

		for(int x=0; x<width; x++)
		{
			for(int y=0; y<height; y++)
			{
				if ((y+1 < height) && p.get(x, y).equals(p.get(x, y+1)))
				{
					wqu.union(index(x, y), index(x, y+1));	
				}
				if ((x+1 < width) && p.get(x, y).equals(p.get(x+1, y)))
				{
					wqu.union(index(x, y),index(x+1, y));
				}
				if ((x+1 < width && (y+1 < height)) && p.get(x, y).equals(p.get(x+1, y+1)))
				{
					wqu.union(index(x, y), index(x+1, y+1));
				}
			}
		}
		return wqu;
	}

	/**
	 * @return Returns the number of components identified in the image.
	 * this number is then decremented by 1 to not include the background
	 */
	public int countComponents() {

		WeightedQuickUnionUF wqu = checkUnion(binaryComponentImage());
		return wqu.count()-1;// decrementing to not include in the backGround here.
	}

	/** 
	 * @return a picture object with all components surrounded by a red box.
	 * this method 
	 */
	public Picture identifyComonentImage() {
		Picture  p = binaryComponentImage();
		Picture original = new Picture(fileLocation);
		WeightedQuickUnionUF wqu = checkUnion(p);
		int width = p.width();
		int height = p.height();
		
		// Creating list of components from roots
		for (int x = 0; x < width; x++)
		{
			for(int y=0; y< height; y++)
			{
				int root = wqu.find(index(x, y));	
				if(!(roots.contains(root))&& root !=0)//ensuring here that no duplicates are added to the array 
					// while also excluding the background
				{
					roots.add(root);
					Component c = new Component(root);
					components.add(c);
				}
			}
		}
		
		// Creating an list that contains all the pixels within a component
		for (int x = 0; x < width; x++)
		{
			for(int y=0; y< height; y++)
			{
				for(int i=0;i< components.size();i++)
				{
					Component component = components.get(i);
					int root = wqu.find(index(x, y));	
					int componentId = component.getId();
					if(root == componentId)//a check to see if the root matches the id of component
					{
						component.addPixelsToComponent(index(x, y));//if so add to the components array of pixels
					}	
				}
			}
		}

		// Draw red box around component
		for(Component c : components)	
		{
			int largestX = c.largestXInArray(width);
			int smallestX = c.smallestXInArray(width);
			int largestY = c.largestYInArray(width);
			int smallestY = c.smallestYInArray(width);

			if (smallestX > largestX || smallestY > largestY) 
			{
				StdOut.println("It's All White Pixels!!!");
			}
			else 
			{
				for (int x = smallestX; x <= largestX; x++) 
				{
					original.set(x, smallestY, Color.RED);
					original.set(x, largestY, Color.RED);
				}
				for (int y = smallestY; y <= largestY; y++) 
				{
					original.set(smallestX, y, Color.RED);
					original.set(largestX, y, Color.RED);
				}
			}
		}
		return original;
	}

	/**
	 * displays the original picture with all the components surrounded by red boxes
	 * returned in the identifyComponentImage method
	 */
	public void displayRedBoxedPic()
	{
		Picture p = new Picture(identifyComonentImage());
		p.show();
	}

	/**
	 * displays the binarized picture returned by the getPicture method
	 * @param value
	 */
	public void displayBinaryPic(int value)
	{
		THRESHOLD = value;
		Picture p = new Picture(binaryComponentImage());
		p.show();
	}

	/**
	 * displays the gray-scaled picture returned by the grayScale method
	 */
	public void displayGrayscalePic()
	{
		Picture p = new Picture(grayScaleImage());
		p.show();
	}

	/**
	 * displays the gray-scaled picture returned by the grayScale method
	 */
	public void displayColourComponentImage()
	{
		Picture p = new Picture(colourComponentImage());
		p.show();
	}

	/**
	 * displays the original picture returned by the getPicture method
	 */
	public void displayOriginal()
	{
		Picture p = getPicture();
		p.show();
	}

}