import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * @author Anthony Tang <Anthony.Tang.2013@live.rhul.ac.uk>
 *
 */

public class Chuck {

	static ArrayList<String> storeJokes = new ArrayList<String>();
	/**
	 * Adds joke into ArrayList to form cache
	 * @param joke the string of the joke that will be added
	 * @param nb the id of the joke added to the arraylist
	 */
	public static void addJoke(String joke, int nb){
		storeJokes.add(joke);
		storeJokes.add(Integer.toString(nb));
	}
	/**
	 * Retrieves the joke at a specific id
	 * @param id the number at which the joke is in the Internet database
	 * @return the joke in String form
	 * @throws IOException if there is no output because connecting to URL fails
	 */
	public static String id(int id) throws IOException{
		String temp = null;
		try{
			
			for(int i =0; i< storeJokes.size(); i++){
				if((storeJokes.get(i)).equals(Integer.toString(id))){
					temp = storeJokes.get(i-1);
					System.out.println(temp);
					break;
				}
				
			}
			if(temp == null){
				
			
			URL url = new URL("Http://api.icndb.com/jokes/"+ (Integer.toString(id)));
			URLConnection connection = url.openConnection();
			BufferedReader buffer =
					new BufferedReader(
							new InputStreamReader(
									connection.getInputStream()));
			
			
			StringBuilder response = new StringBuilder();
			while((temp = buffer.readLine()) !=null){
				response.append(temp);
			}
			buffer.close();
		
			
			
				String s = response.toString();
				int start = s.indexOf("joke");
				int last =s.lastIndexOf("categories");
				System.out.println(s.substring(start+8, last-4));
				addJoke(s.substring(start+8, last-4), id);
				
			}
		}catch(Exception e){
				temp = null;
				for(int i =0; i< storeJokes.size(); i++){
				if((storeJokes.get(i)).equals(Integer.toString(id))){
					temp = storeJokes.get(i-1);
					System.out.println(temp);
					break;
				}
				
		}
				if (temp == null){
					System.out.println("Not found in cache");
				}
		}
		return temp;

			
	}
	
	/**
	 * Retrieves a random joke from the Internet database
	 * @return the string of the joke
	 * @throws if there is no output because connecting to URL fails
	 */
	public static String randomJoke() throws IOException {
			URL url = new URL("Http://api.icndb.com/jokes/random");
			URLConnection connection = url.openConnection();
			BufferedReader in = 
					new BufferedReader(
							new InputStreamReader(connection.getInputStream()));
			
			        String inputLine;
			        StringBuilder response = new StringBuilder();
			        while ((inputLine = in.readLine()) != null){
			        	response.append(inputLine);
			        
			        }
		in.close();
		
		String s = response.toString();
		int start = s.indexOf("joke");
		int last =s.lastIndexOf("categories");
		System.out.println(s.substring(start+8, last-4));
		return s;	
	}
	
	/**
	 * gets the number of jokes available from the URL and converts into an integer
	 * 
	 * @return the number jokes
	 * @throws IOException if there is no output because connecting to URL fails
	 */
	public static int JokeCount() throws IOException{
		URL url = new URL("Http://api.icndb.com/jokes/count");
		URLConnection connection = url.openConnection();
		BufferedReader in = 
				new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
		
		        String inputLine;
		        StringBuilder response = new StringBuilder();
		        while ((inputLine = in.readLine()) != null){
		        	response.append(inputLine);	           		        
		        }
		        in.close();
		   
		 
		        String s = response.toString();
				int start = s.indexOf("value");
				int last = s.lastIndexOf("}");
				int count = Integer.parseInt(s.substring(start+8, last-1));
				return count;
			
	}
	
	/**
	 * The welcome screen which prints out jokes according to user input
	 *  
	 * @throws IOException if there is no output because connecting to URL fails
	 */
	public static void welcome() throws IOException{
		
		System.out.println("Welcome to the Internet Chuck Norris database!");
		System.out.println("Joke of the day: ");
		randomJoke();

		
			Scanner scanner = new Scanner(System.in);
			int joke = 0;
			
			while(joke != -1){	

				System.out.println("(-1) Quit (0) Random Joke (n) Joke number n");
				System.out.println("What do you want to do?");
				
				try{
					joke = scanner.nextInt();
					if(joke == 0){
						randomJoke();
					}
					else if(joke == -1){
						System.out.println("Thanks for using the Internet Chuck Norris database!");
					}
					
					else if(joke > 546 || joke < 0){
						System.out.println("Joke does not exist");
					}
					else {
						id(joke);
					}
		}
		catch(InputMismatchException e){
			System.out.println("Invalid input");
			String j = scanner.nextLine();
		}
			}	
			scanner.close();
				
	}
	
	/**
	 * Main method which executes the program
	 * @param args
	 * @throws IOException if there is no output because connecting to URL fails
	 */
	public static void main(String[] args) throws IOException {
		
		welcome();
		
	}

}
