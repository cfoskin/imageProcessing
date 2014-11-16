package imageProcessing;
import java.util.ArrayList;

/**
 * @author Colum Foskin 
 * version 1.0
 */
public class Component {
	private int id;
	private ArrayList<Integer> pixels;
	
	/**
	 * @param id constructs a new component object which has an id which is the root.
	 */
	public Component(int id)
	{
		pixels = new ArrayList<Integer>();
		this.id = id;
	}
	
	/**
	 * @return accessor for the id of the component and returns that id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id mutator for the id of the component 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param width
	 * @return method which calculates the largest x in the array of pixels 
	 * for each component and returns that x
	 */
	public int largestXInArray(int width)
	{

		int largestX =pixels.get(0)%width;
		for (int i=0; i<pixels.size();i++)
		{
			int x = pixels.get(i)%width;
			if(x > largestX)
			{
				largestX =x;
			}
		}
		return largestX;
	}

	/**
	 * @param width
	 * @return method which calculates the smallest x in the array of pixels 
	 * for each component and returns that x
	 */
	public int smallestXInArray(int width)
	{
		int smallestX =pixels.get(0)%width;
		for (int i=0; i<pixels.size();i++)
		{
			int x = pixels.get(i)%width;
			if(x < smallestX)
			{
				smallestX =x;
			}
		}
		return smallestX;
	}

	/**
	 * @param width
	 * @return method which calculates the largest y in the array of pixels 
	 * for each component and returns that y
	 */
	public int largestYInArray(int width)
	{
		int largestY =pixels.get(0)/width;
		for (int i=0; i<pixels.size();i++)
		{
			int y = pixels.get(i)/width;
			if(y > largestY)
			{
				largestY =y;
			}
		}
		return largestY;
	}

	/**
	 * @param width
	 * @return method which calculates the smallest y in the array of pixels 
	 * for each component and returns that y
	 */
	public int smallestYInArray(int width)
	{
		int smallestY =pixels.get(0)/width;
		for (int i=0; i<pixels.size();i++)
		{
			int y = pixels.get(i)/width;
			if(y < smallestY)
			{
				smallestY = y;
			}
		}
		return smallestY;
	}

	/**
	 * @param index 
	 * this method is used in the identifyComponentImage method in the ConnectedComponenImage class
	 * where it is used to add pixels to the arrayList of each component
	 */
	public void  addPixelsToComponent(int index) 
	{
		pixels.add(index);
	}

	@Override
	public String toString() {
		return "Component id=" + id ;
	}

}
