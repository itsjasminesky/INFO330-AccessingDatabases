import java.sql.*;
//java build path
//add external jars
//sqlite-jdbc-3.23.1.jar
//sqlite-jdbc-3.23.1.jar
public class TeamAnalyzer {
    // All the "against" column suffixes
    static String[] types = {
        "bug","dark","dragon","electric","fairy","fight",
        "fire","flying","ghost","grass","ground","ice","normal",
        "poison","psychic","rock","steel","water"
    };

    public static void main(String... args) throws Exception {
        
        // Take six command-line parameters
        if (args.length < 6) {
            print("You must give me six Pokemon to analyze");
            System.exit(-1);
        }

    // This bit of JDBC magic I provide as a free gift :-)
        // The rest is up to you.
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:../pokemon.sqlite")) {
            for (String arg : args) {
                print("Analyzing " + arg);
                // check if number or name
                //check if number 
                int pokedex_number = -1;
                try {
                    pokedex_number = Integer.parseInt(arg);
                }
                // do nothing if not number
                catch (NumberFormatException e) {
                } 
                
                String number_arg; 
                if(pokedex_number >=1 && pokedex_number <= 801) {
                    // Analyze the pokemon whose pokedex_number is in "arg"
                     number_arg = "SELECT * FROM pokemon WHERE pokedex_number = '" + pokedex_number+ "'";
                } else{
                    number_arg = "SELECT * FROM pokemon WHERE name = '" + arg + "'";
                }
            System.out.println(number_arg);
                    // You will need to write the SQL, extract the results, and compare
                    try(Statement statement = con.createStatement()) {
                        try(ResultSet results = statement.executeQuery(number_arg)) {
                            while(results.next()) {
                    String name = results.getString("name");

                    if(pokedex_number >=1 && pokedex_number <= 801) {
                     String number_arg1 = "Select * FROM pokemon_types_battle_view WHERE type1name = '" + name + "'"; 
                     try(ResultSet results1 = statement.executeQuery(number_arg1)) {
                     //print name here
                    System.out.println(name + "Pokemon");
                     }
                    }
                    // Remember to look at those "against_NNN" column values; greater than 1
                    // means the Pokemon is strong against that type, and less than 1 means
                            // the Pokemon is weak against that type
                        for(String type : types) {
                            double against = results.getDouble("against_" + type);
                            if(against > 1) {
                             System.out.println("Strong against " + type);
                             }
                            else if(against < 1) {
                            System.out.println("Weak against " + type);
                            }
                    
                        }
                    
                // Analyze the pokemon whose pokedex_number is in "arg"
                String number_argument = "SELECT * FROM pokemon WHERE pokedex_number = '" + arg + "'";
                System.out.println(number_argument);
                // You will need to write the SQL, extract the results, and compare
                try(Statement statement1 = con.createStatement()) {
                    try(ResultSet resultsArg = statement1.executeQuery(number_argument)) {
                        while(resultsArg.next()) {
                    String name3 = resultsArg.getString("name");

                     String number_arg2 = "Select * FROM pokemon_types_battle_view WHERE type1name = '" + arg + "'"; 
                     try(ResultSet results1 = statement.executeQuery(number_arg2)) {
                     //print name here
                    System.out.println(name + "Pokemon");
                     }
                    }
                }
            }
                    // Remember to look at those "against_NNN" column values; greater than 1
                    // means the Pokemon is strong against that type, and less than 1 means
                            // the Pokemon is weak against that type
                        for(String type : types) {
                            double against = results.getDouble("against_" + type);
                            if(against > 1) {
                             System.out.println("Strong against " + type);
                             }
                            else if(against < 1) {
                            System.out.println("Weak against " + type);
                            }
                    
                        }
                    }
                }
            }
        }
                    
                
            
            

            String answer = input("Would you like to save this team? (Y)es or (N)o: ");
            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
                String teamName = input("Enter the team name: ");

                // Write the pokemon team to the "teams" table
                try(Statement statement2 = con.createStatement()) {
                    String insert = "INSERT INTO teams (name, pokemon) VALUES ('" 
                        + teamName + "', " + String.join(", ", args) + ")";

                    statement2.executeUpdate(insert);
                }
                print("Saving " + teamName + " ...");
            }
            else {
                print("Bye for now!");
            }
            }
            }
       

    

    /*
     * These are here just to have some symmetry with the Python code
     * and to make console I/O a little easier. In general in Java you
     * would use the System.console() Console class more directly.
     */
    public static void print(String msg) {
        System.console().writer().println(msg);
    }

    /*
     * These are here just to have some symmetry with the Python code
     * and to make console I/O a little easier. In general in Java you
     * would use the System.console() Console class more directly.
     */
    public static String input(String msg) {
        return System.console().readLine(msg);
    }
}
