package tests;

import static org.junit.Assert.*;
import java.awt.Color;
import java.util.ArrayList;

import imageProcessing.ConnectedComponentImage;
import imageProcessing.Luminance;
import imageProcessing.WeightedQuickUnionUF;
import org.junit.Before;
import org.junit.Test;
import edu.princeton.cs.introcs.Picture;

/**
 * @author Colum Foskin
 * version 1.0
 *
 */
public class ConnectedComponentImageTest {


	ConnectedComponentImage c;
	String fileLocation;
	@Before
	public void setUp() throws Exception {

		c = new ConnectedComponentImage("src/images/shapes.bmp");
	}

	/**
	 * this method is  to test that union find is unioning correctly
	 */
	@Test
	public void testWQU() {
		WeightedQuickUnionUF qf = new WeightedQuickUnionUF(10);
		qf.union(1, 2);
		qf.union(2, 3);
		qf.union(6, 8);
		qf.union(3, 6);
		assertTrue(qf.connected(1, 8));
	}
	/**
	 * this method is testing that the connectedComponentImage object created exists
	 */
	@Test
	public void testCCI() {
		assertTrue (c != null);
	}

	/**
	 * testing the grayscaled image
	 */
	@Test
	public void TestGrayscale()
	{
		Picture original = c.getPicture();
		int originaWidth = original.width();
		int originalHeight = original.height();

		Picture pGray = c.grayScaleImage();
		int newPicWidth = pGray.width();
		int newPicHeight =pGray.height();
		ArrayList<Color> colors = new ArrayList<Color>(); //an array of colors so i can check that the image doesnt contain 
		// some incorrect colors after grayscaling it. 
		
		for(int x =0; x<newPicWidth; x++)
		{
			for(int y =0; y<newPicHeight; y++)
			{
				Color cl = pGray.get(x, y);
				colors.add(cl);	
				Color gray = Luminance.toGray(cl);
				assertFalse(colors.contains(Color.RED) || colors.contains(Color.YELLOW) ||
						colors.contains(Color.GREEN) || colors.contains(Color.PINK) || colors.contains(Color.BLUE));
				assertTrue(colors.contains(gray));
			}
		}
		assertTrue(originaWidth == newPicWidth); 	// checking the pictures dimensions are correct after converting	
		assertTrue(originalHeight == newPicHeight);
	}

	/**
	 * testing the binary picture method
	 */
	@Test
	public void TestBinary()
	{
		Picture original = c.getPicture();
		int originaWidth = original.width();
		int originalHeight = original.height();

		Picture p = c.binaryComponentImage();
		int newPicWidth = p.width();
		int newPicHeight =p.height();
		for(int x =0; x<newPicWidth; x++)
		{
			for(int y =0; y<newPicHeight; y++)
			{
				Color cl = p.get(x, y);
				assertTrue(cl.equals(Color.BLACK) || cl.equals(Color.WHITE)); //checking that the binary image only has black or white pixels.
			}
		}
		assertTrue(originaWidth == newPicWidth);// checking the pictures dimensions are correct after converting	 		
		assertTrue(originalHeight == newPicHeight);
	}

	/**
	 * testing the random colored picture method
	 */
	@Test
	public void TestRandomColoredImage()
	{
		Picture original = c.getPicture();
		int originaWidth = original.width();
		int originalHeight = original.height();
		ArrayList<Color> originalColors =new ArrayList<Color>(); //array of colors form original pic
		for(int x =0; x<originaWidth; x++)
		{
			for(int y =0; y<originalHeight; y++)
			{
				Color cl = original.get(x, y);
				originalColors.add(cl);
			}
		}

		Picture randomColoredPic = c.colourComponentImage();
		int newPicWidth = randomColoredPic.width();
		int newPicHeight =randomColoredPic.height();
		ArrayList<Color> newColors =new ArrayList<Color>();//new array of colors from the random colored pic.
		for(int x =0; x<newPicWidth; x++)
		{
			for(int y =0; y<newPicHeight; y++)
			{
				Color cl = randomColoredPic.get(x, y);
				newColors.add(cl);
			}
		}

		for(int i= 0; i<originalColors.size(); i++)
		{
			for(int j =0; j< newColors.size();j++)
			{
				assertFalse(originalColors.get(i)== newColors.get(j)); //checking the colors for both pics are different.
			}
		}
		assertTrue(originaWidth == newPicWidth); // checking the pictures dimensions are correct after converting			
		assertTrue(originalHeight == newPicHeight);
	}

	/**
	 * testing the random colors method
	 */
	@Test
	public void testRandomColorMethod()
	{
		Color firstColor = c.getRandomColor();
		Color secondColor = c.getRandomColor();
		assertFalse(firstColor == secondColor);
	}

	/**
	 * testing the count method
	 */
	@Test
	public void testCount()
	{
		int noOfShapes = 3; // this is the number of shapes in the picture I am using for testing purposes
		int noOfObjects =  c.countComponents();
		assertTrue(noOfShapes == noOfObjects);
	}

	/**
	 * testing tehe redboxed method
	 */
	@Test
	public void testIdentifyComonentImage()
	{
		Picture original = c.getPicture();
		int originaWidth = original.width();
		int originalHeight = original.height();

		Picture redBoxedPic = c.identifyComonentImage();
		int newPicWidth = redBoxedPic.width();
		int newPicHeight =redBoxedPic.height();
		ArrayList<Color> colors =new ArrayList<Color>();

		for(int x =0; x<newPicWidth; x++)
		{
			for(int y =0; y<newPicHeight; y++)
			{
				Color cl = redBoxedPic.get(x, y);
				colors.add(cl);
			}
		}

		assertTrue(colors.contains(Color.RED));//checking to ensure the new image containd red pixels
		assertFalse(redBoxedPic.equals(original));//checking the new image is different from original
		assertTrue(originaWidth == newPicWidth); // checking the pictures dimensions are correct after converting			
		assertTrue(originalHeight == newPicHeight);
	}
}



















