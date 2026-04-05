package app;

import exceptions.FalseUsage;
import exceptions.InvalidDirection;
import exceptions.InvalidImageName;
import image.Image;
import session.SessionManager;
import transformations.*;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CommandInterface {
    private Scanner scan;
    private SessionManager sessions;
    private static String[] parts;

    public CommandInterface(Scanner scanner) {
        this.scan = scanner;
        this.sessions = new SessionManager();
    }

    public void start()  {
        System.out.println("\t\tWelcome to the Image Reader.");
        System.out.println("Type 'help' for more information");

        while(true) {
            System.out.print("> ");
            String input = scan.nextLine();

            parts = input.split(" ");
            String command = parts[0];
            switch(command) {

                case "exit" -> {
                    System.out.println("Exiting the program...");
                    return;
                }

                case "load" -> {
                    try {
                        checkPartsLength(2);
                        sessions.createSession();
                        loadImage(parts[1]);
                        System.out.println("Session with ID: " + sessions.getCurrentSession().getId() + " started");
                    } catch (FalseUsage e) {
                        System.out.println(e.getMessage());
                        System.out.println("Try again!");
                    }
                }

                case "help" -> {
                    help();
                }

                case "add" -> {
                    try {
                        checkPartsLength(2);
                        loadImage(parts[1]);
                    } catch (InvalidImageName | FalseUsage e) {
                        System.out.println(e.getMessage());
                        System.out.println("Try again!");
                    }
                }

                case "grayscale" -> {
                    sessions.getCurrentSession().addTransformation(new Grayscale());
                }
                case "monochrome" -> {
                    sessions.getCurrentSession().addTransformation(new Monochrome());
                }

                case "negative" -> {
                    sessions.getCurrentSession().addTransformation(new Negative());
                }

                case "rotate" -> {
                    try {
                        checkPartsLength(2);
                        String direction = parts[1];
                        checkDirection(direction);
                        sessions.getCurrentSession().addTransformation(new Rotate(direction));
                    } catch (FalseUsage | InvalidDirection e ) {
                        System.out.println(e.getMessage());
                        System.out.println("Usage: rotate <left | right>");
                    }
                }

                case "session" -> {
                    if(parts.length > 1 && parts[1].equals("info")) {
                        System.out.println(sessions.getSessionInfo());
                    } else {
                        System.out.println("Unknown session command");
                    }
                }

                case "switch" -> {
                    try {
                        checkPartsLength(2);
                        int sessionId = Integer.parseInt(parts[1]);
                        sessions.switchSession(sessionId);
                        System.out.println("You switched to session with ID: " + sessions.getCurrentSession().getId() + "!");
                        System.out.println(sessions.getSessionInfo());
                    } catch (FalseUsage e) {
                        System.out.println(e.getMessage());
                    }
                }

                case "save" -> {
                    sessions.getCurrentSession().applyTransformations();
                }

                case "saveas" -> {
                    try {
                        checkPartsLength(2);
                        sessions.getCurrentSession().saveAs(parts[1]);
                    } catch (FalseUsage e) {
                        System.out.println(e.getMessage());
                    }
                }

                case "undo" -> {
                    System.out.println("Transformations before undo: ");
                    for(Transformation t : sessions.getCurrentSession().getTransformations()) {
                        System.out.println(t);
                    }

                    sessions.getCurrentSession().undo();

                    System.out.println("Transformations after undo: ");
                    for(Transformation t : sessions.getCurrentSession().getTransformations()) {
                        System.out.println(t);
                    }
                }

                case "collage" -> {
                    try {
                        checkPartsLength(5);

                        String direction = parts[1];
                        String img1Name = parts[2];
                        String img2Name = parts[3];
                        String outputName = parts[4];

                        checkCollageDirection(direction);
                        validateImageName(img1Name);
                        validateImageName(img2Name);

                        Image image1 = sessions.getCurrentSession().findImage(img1Name);
                        Image image2 = sessions.getCurrentSession().findImage(img2Name);

                        if(image1 == null || image2 == null) {
                            throw new InvalidImageName("One or both images not found in session");
                        }

                        if(!image1.getFormat().equals(image2.getFormat())) {
                            throw new InvalidImageName(
                                    "Cannot make a collage from different types! (." +
                                            image1.getFormat() + " and ." + image2.getFormat() + ")"
                            );
                        }

                        Image result;

                        if(direction.equals("horizontal")) {
                            result = image1.collageHorizontal(image2, outputName);
                        } else {
                            result = image1.collageVertical(image2, outputName);
                        }

                        sessions.getCurrentSession().addImage(result);

                        System.out.println("New collage \"" + outputName + "\" created");

                    } catch (FalseUsage | InvalidDirection | InvalidImageName e) {
                        System.out.println(e.getMessage());
                        //System.out.println("Try again!");
                        //System.out.println("Usage: collage <direction> <image1> <image2> <outimage>");
                    }
                }

                default -> System.out.println("Unknown command: " + input + " Type 'help' for available commands.");
            }
        }
    }

    public void help() {
        System.out.println("The following commands are supported: ");
        System.out.println("open <file> -> opens <file>");
        System.out.println("close -> closes currently opened file");
        System.out.println("save -> saves the currently open file");
        System.out.println("savesas <file> -> saves the currently open file in <file>");
        System.out.println("help -> prints this information");
        System.out.println("exit -> exists the program");
    }

    public void checkPartsLength(int partLength) throws FalseUsage {
        if(parts.length < partLength) {
            throw new FalseUsage("Invalid command, partsLength: " + parts.length + ", expected: " + partLength);
        }
    }

    public void checkDirection(String direction) throws InvalidDirection{
        if(!direction.equals("left") && !direction.equals("right")) {
            throw new InvalidDirection("Invalid direction: " + direction + ", Use left or right");
        }
    }

    public void checkCollageDirection(String direction) throws InvalidDirection{
        if(!direction.equals("horizontal") && !direction.equals("vertical")) {
            throw new InvalidDirection("Invalid direction: " + direction + ", Use horizontal or vertical");
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
        System.out.println("Image '" + imageName + "' added");
    }

    public void checkImageAdded (String imageName) throws InvalidImageName {
       List<Image> images = sessions.getCurrentSession().getImages();

        for(Image img : images) {
            if(img.getName().equals(imageName)) {
                throw new InvalidImageName("Image already added!");
            }
        }
    }

    public void checkImagesFormat(String image1, String image2) throws InvalidImageName {
        String imgExt1 = image1.substring(image1.lastIndexOf(".") + 1);
        String imgExt2 = image2.substring(image2.lastIndexOf(".") + 1);

        if(!imgExt1.equals(imgExt2)) {
            throw new InvalidImageName("Cannot make a collage from different types! ( ." + imgExt1 + " and ." + imgExt2 +")");
        }
    }

}
