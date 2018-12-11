import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;



public class Scheduler 
{
	static final int CYLINDERS = 5000;
	static final int REQUESTS = 50;
	
	public static void main (String [] args)
	{
		Scanner keyboard = new Scanner(System.in);							//To read in position of head
        Random rand = new Random();											//To fill in array
        int headPos = 0;													//Position of head
        int seek;															//Seek time
        ArrayList <Integer> queue = new ArrayList <Integer> (REQUESTS); 	//Queue of requests

        //Fills the first 50 positions of the queue with random memory locations
        for(int i=0; i<REQUESTS; i++)			
        {
        	queue.add(rand.nextInt(CYLINDERS));		//range 0-5000
        }
        
		//Read in initial head position from user
        System.out.println("Enter initial head position");
        headPos  = keyboard.nextInt();
        
        //First Come First Serve
        seek = fcfs(headPos, queue);
        System.out.println("Total seek time with FCFS is " +seek);
        System.out.println();
        System.out.println();
        
        //C-LOOK
        seek = c_LOOK (headPos, queue);
        System.out.println("Total seek time with C-LOOk is " +seek);
        System.out.println();
        System.out.println();
        
        //Shortest Seek Time First
        seek = sstf (headPos, queue);
        System.out.println("Total seek time with SSTF is " +seek);

        //close the scanner object
        keyboard.close();
	}
	
	/**
	 * 	fcfs services the first request on the queue every time
	 * @param head initial head position
	 * @param queue the array of requests to be processed
	 * @return int the seek time of the algorithm
	 */
	public static int fcfs (int head, ArrayList <Integer> queue)
	{
        int seek = 0;
        int movement = 0;
        
        System.out.println("First Come First Serve:");
                  
        for(int i=0; i<REQUESTS; i++)
        {
            movement = Math.abs(head - queue.get(i));
            seek += movement;
            System.out.println("Move head from " + head 
            					+ " to " + queue.get(i) 
    							+ " with seek " + movement);
            head = queue.get(i);
        }
        return seek;
    }
	
	/**
	 *  sort the array, find distance between head position and maximum
	 *  and find distance between maximum and minimum, 
	 *  and adds them together to get total distance traveled by the head
	 * @param head initial head position
	 * @param queue the array of requests to be processed
	 * @return int the seek time of the algorithm
	 */
	public static int c_LOOK (int head, ArrayList <Integer> queue)
	{
	    int seek = 0;
	    int max, min, temp;
	
        System.out.println("C-LOOK:");

	    //sort disk locations queue
	    for(int i=0; i<REQUESTS ;i++)    
	    {
			for(int j=i+1 ;j<REQUESTS;j++)
			{
			   if(queue.get(i)>queue.get(j))
			   {
				   temp=queue.get(i);
				   queue.set(i, queue.get(j));
				   queue.set(j, temp);
			   }
			}
	    }	    

	    max = queue.get(REQUESTS-1);
	    min = queue.get(0);
	    
	    System.out.println("Farthest space to the right is: " + max +
	    					", farthest space to the left is: "+min);
	    
	    seek = max - head;		//Distance head travels from initial position to max PLUS
	    seek+= max-min;			//Distance head travels from max back to min, processing all stops on the way
	    return seek;
	}
	
	/**
	 * sstf finds the minimum possible distance between
	 * the head and its next destination, chooses that path,
	 * moves the head to the new destination, deletes
	 * the previous position of head, and shifts all elements
	 * to the left
	 * @param head initial head position
	 * @param queue the array of requests to be processed
	 * @return int the seek time of the algorithm
	 */
	public static int sstf (int head, ArrayList<Integer> queue) 
	{
        int seek = 0;
        
        System.out.println("Shortest Seek Time First:");
        
        for (int i = 0; i < REQUESTS; i++) 
        {
            int pos=0;
            int minVal = Math.abs (head - queue.get(pos));
            for (int j=1; j<queue.size(); j++)
            {
                if(Math.abs(head-queue.get(j)) < minVal)
                {
                	minVal = head - queue.get(j);
                	pos=j;
                }
            }
            System.out.println("Move head from "+ head +
            					" to "+ queue.get(pos)+
            					" with seek " + Math.abs(queue.get(pos)-head));
            seek += Math.abs(queue.get(pos)-head);
           head = queue.remove(pos);
        }
        return seek;
    }
}