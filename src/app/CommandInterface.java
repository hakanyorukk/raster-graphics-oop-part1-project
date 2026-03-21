package app;

import exceptions.InvalidImageName;
import image.Image;
import session.SessionManager;
import transformations.Grayscale;
import transformations.Monochrome;
import transformations.Negative;
import transformations.Rotate;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CommandInterface {
    private Scanner scan;
    private SessionManager sessions;

    public CommandInterface(Scanner scanner) {
        this.scan = scanner;
        this.sessions = new SessionManager();
    }

    public void start() {
        System.out.println("---------------------------------------");
        System.out.println("\t\tWelcome to the Image Reader.");
        System.out.println("---------------------------------------");
        System.out.println("Type 'help' for more information");

        while(true) {
            System.out.print("> ");
            String input = scan.nextLine();

            String[] parts = input.split(" ");
            String command = parts[0];
            if(command.equals("help")) {
                System.out.println("The following commands are supported: ");
                System.out.println("open <file> -> opens <file>");
                System.out.println("close -> closes currently opened file");
                System.out.println("save -> saves the currently open file");
                System.out.println("savesas <file> -> saves the currently open file in <file>");
                System.out.println("help -> prints this information");
                System.out.println("exit -> exists the program");
            }
            if(command.equals("exit")){
                System.out.println("Exiting program...");
                break;
            }

            if(command.equals("load")) {
                sessions.createSession();
                System.out.println("Session with ID: " + sessions.getCurrentSession().getId() + " started");
                for(int i = 1; i < parts.length; i++) {
                    try {
                        loadImage(parts[i]);
                    } catch (InvalidImageName e) {
                        System.out.println(e.getMessage());
                        System.out.println("Try again!");
                    }
                }
            }

            if(command.equals("add")) {
                if (parts.length < 2) {
                    System.out.println("Usage: add <imageName>");
                    return;
                }

                try {
                    loadImage(parts[1]);
                } catch (InvalidImageName e) {
                    System.out.println(e.getMessage());
                    System.out.println("Try again!");
                }
            }

            if(command.equals("grayscale")) {
                sessions.getCurrentSession().addTransformation(new Grayscale());
            }
            if(command.equals("monochrome")) {
                sessions.getCurrentSession().addTransformation(new Monochrome());
            }
            if(command.equals("negative")) {
                sessions.getCurrentSession().addTransformation(new Negative());
            }
            if(command.equals("rotate")) {
                if(parts.length < 2) {
                    System.out.println("Missing rotation direction");
                    break;
                }

                String direction = parts[1];

                if(direction.equals("left") || direction.equals("right")) {
                    sessions.getCurrentSession().addTransformation(new Rotate(direction));
                } else {
                    System.out.println("Invalid direction. Use left or right");
                }
            }

            if(input.equals("session info")) {
                System.out.println(sessions.getSessionInfo());
            }

            if(command.equals("switch")) {
                int sessionId = Integer.parseInt(parts[1]);
                sessions.switchSession(sessionId);

                System.out.println("You switched to session with ID: " + sessions.getCurrentSession().getId() + "!");
                System.out.println(sessions.getSessionInfo());
            }

            // save all images with transformation
            if(command.equals("save")) {
                sessions.getCurrentSession().applyTransformations();
            }

            // saveas saves under a new name only the image that was loaded first

            if(command.equals("saveas")) {
                if(parts.length < 2) {
                    System.out.println("Usage: saveas <filename>");
                    return;
                }

                sessions.getCurrentSession().saveAs(parts[1]);
            }
        }
    }

    public void validateImageName(String imageName) throws InvalidImageName {
        if(imageName == null || !imageName.contains(".")) {
            throw new InvalidImageName("Invalid image name");
        }

        String extension = imageName.substring(imageName.lastIndexOf(".") + 1);

       Set<String> validExtensions = Set.of("ppm", "pgm", "pbm");
       if(!validExtensions.contains(extension)) {
           throw new InvalidImageName("Invalid extension: " + extension);
       }
    }

    public void loadImage(String imageName) throws InvalidImageName{
        validateImageName(imageName);

        checkImageAdded(imageName);
        Image img = new Image(imageName);
        sessions.getCurrentSession().addImage(img);
        System.out.println(img.printImage());
        System.out.println("Image '" + imageName + "' added");
    }

    public void checkImageAdded (String imageName) throws InvalidImageName {
       List<Image> images = sessions.getCurrentSession().getImages();

        for(Image img : images) {
            System.out.println(img.getName());
            if(img.getName().equals(imageName)) {
                throw new InvalidImageName("Image already added!");
            }
        }
    }
}
