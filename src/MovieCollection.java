import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> genres = new ArrayList<String>();
    private ArrayList<Movie> highestRated = new ArrayList<Movie>();
    private ArrayList<Movie> highestRevenue = new ArrayList<Movie>();
    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    public static void selectionSortWordList(List<String> words)
    {
        for(int n = 0; n < words.size() - 1; n++)
        {
            int minInd = n;
            for(int m = n + 1; m < words.size(); m++)
            {
                if(words.get(m).compareTo(words.get(minInd)) < 0)
                {
                    minInd = m;
                }
            }
            String temp = words.get(n);
            words.set(n,words.get(minInd));
            words.set(minInd,temp);
        }
    }

    private void searchCast()
    {
        System.out.print("Enter a person to search for: ");
        String searchTerm = scanner.nextLine();
        String all = "";
        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();


        for(int n = 0; n < movies.size(); n++)
        {
            all += movies.get(n).getCast();
            all += "|";
        }

        all = all.toLowerCase();

        String[] a = all.split("\\|");
        ArrayList<String> results = new ArrayList<String>(Arrays.asList(a));
        String appearances = "";
        for(int l = 0; l < results.size(); l++)
        {
            if(results.get(l).indexOf(searchTerm) != -1)
            {
                if(appearances.indexOf(results.get(l)) == -1)
                {
                    appearances += "," + results.get(l);
                }
                else
                {
                    results.remove(l);
                    l--;
                }
            }
            else
            {
                results.remove(l);
                l--;
            }
        }

        selectionSortWordList(results);

        for (int i = 0; i < results.size(); i++)
        {
            String actor = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + actor);
        }

        System.out.println("Which actor would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String actor = results.get(choice - 1);

        ArrayList<Movie> m = new ArrayList<Movie>();

        for(int k = 0; k < movies.size(); k++)
        {
            if(movies.get(k).getCast().toLowerCase().indexOf(actor) != -1)
            {
                m.add(movies.get(k));
            }
        }

        // sort the results by title
        sortResults(m);

        // now, display them all to the user
        for (int i = 0; i < m.size(); i++)
        {
            String title = m.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice2 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = m.get(choice2 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeyword = movies.get(i).getKeywords();
            movieKeyword = movieKeyword.toLowerCase();

            if (movieKeyword.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void findGenres()
    {
        String all = "";
        //get every movies' genre
        for(int n =  0; n < movies.size(); n++)
        {
            all += movies.get(n).getGenres();
            all += "|";
        }
        //make the string of every genre into an array
        String[] a = all.split("\\|");

        //add every element in the array to the genres
        for(int j = 0; j < a.length; j++)
        {
            genres.add(a[j]);
        }
        //removes every repeating genre from the arraylist genres
        for(int k = 0; k < genres.size(); k++)
        {
            for(int l = k + 1; l < genres.size(); l++)
            {
                if(genres.get(l).equals(genres.get(k)))
                {
                    genres.remove(l);
                    l--;
                }
            }
        }
        Collections.sort(genres);
    }

    private void listGenres()
    {
        if(genres.size() == 0)
        {
            findGenres();
        }
        for (int i = 0; i < genres.size(); i++)
        {
            String g = genres.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + g);
        }

        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String genreChoice = genres.get(choice - 1);
        ArrayList<Movie> moviesWithGenre = new ArrayList<Movie>();
        for(int n = 0; n < movies.size(); n++)
        {
            if(movies.get(n).getGenres().indexOf(genreChoice) != -1)
            {
                moviesWithGenre.add(movies.get(n));
            }
        }
        // sort the results by title
        sortResults(moviesWithGenre);

        // now, display them all to the user
        for (int i = 0; i < moviesWithGenre.size(); i++)
        {
            String title = moviesWithGenre.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice2 = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = moviesWithGenre.get(choice2 - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void findHighestRated()
    {
        for(int i = 0; i < movies.size(); i++)
        {
            highestRated.add(movies.get(i));
        }

        for (int j = 0; j < highestRated.size() - 1; j++)
        {
            int minIndex = j;
            for (int k = j + 1; k < highestRated.size(); k++)
            {
                if (highestRated.get(k).getUserRating() < highestRated.get(minIndex).getUserRating())
                {
                    minIndex = k;
                }
            }
            Movie temp = highestRated.get(j);
            highestRated.set(j,highestRated.get(minIndex));
            highestRated.set(minIndex,temp);
        }
    }

    private void listHighestRated()
    {
        if(highestRated.size() == 0)
        {
            findHighestRated();
        }

        int count = 0;
        for (int i = highestRated.size() - 1; i > highestRated.size() - 51; i--)
        {
            String h = highestRated.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!

            int choiceNum = count + 1;

            System.out.println("" + choiceNum + ". " + h + " Rating: " + highestRated.get(i).getUserRating());
            count++;
        }

        System.out.println("Which Movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = highestRated.get(highestRated.size() - choice);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void findHighestRevenue()
    {
        for(int i = 0; i < movies.size(); i++)
        {
            highestRevenue.add(movies.get(i));
        }

        for (int j = 0; j < highestRevenue.size() - 1; j++)
        {
            int minIndex = j;
            for (int k = j + 1; k < highestRevenue.size(); k++)
            {
                if (highestRevenue.get(k).getRevenue() < highestRevenue.get(minIndex).getRevenue())
                {
                    minIndex = k;
                }
            }
            Movie temp = highestRevenue.get(j);
            highestRevenue.set(j,highestRevenue.get(minIndex));
            highestRevenue.set(minIndex,temp);
        }

    }
    private void listHighestRevenue()
    {
        if(highestRevenue.size() == 0)
        {
            findHighestRevenue();
        }

        int count = 0;
        for (int i = highestRevenue.size() - 1; i > highestRevenue.size() - 51; i--)
        {
            String h = highestRevenue.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!

            int choiceNum = count + 1;

            System.out.println("" + choiceNum + ". " + h + " Revenue: $" + highestRevenue.get(i).getRevenue());
            count++;
        }

        System.out.println("Which Movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = highestRevenue.get(highestRevenue.size() - choice);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}