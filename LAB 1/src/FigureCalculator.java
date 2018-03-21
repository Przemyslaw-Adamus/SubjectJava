
//package menu_switch;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class FigureCalculator {

    public static int menu(){
        System.out.println();
        System.out.println("     ****************************************");
        System.out.println("     *                 MENU                 *");
        System.out.println("     ****************************************");
        System.out.println("     1. CREATE FIGURE");
        System.out.println("     2. CALCULATE THE AREA");
        System.out.println("     3. CALCULATE THE PERIMETER");
        System.out.println("     0. EXIT");

        Scanner in = new Scanner(System.in);
        try {
            int choice = in.nextInt();
            return choice;
        }catch (InputMismatchException e){
            System.err.println("INPUT ERROR ! EXIT" + e.getMessage());
            System.exit(1);
        }
        return 4;
    }
    public static int menuFigure(){
        System.out.println();
        System.out.println("            ****************************************");
        System.out.println("            *                 MENU FIGURE          *");
        System.out.println("            ****************************************");
        System.out.println("            1. TRIANGLE");
        System.out.println("            2. SQUARE");
        System.out.println("            3. CIRCLE");
        System.out.println("            OTHERS. BACK");

        Scanner in = new Scanner(System.in);
        try {
            int choice = in.nextInt();
            return choice;
        }catch (InputMismatchException e){
            System.err.println("INPUT ERROR ! EXIT" + e.getMessage());
            System.exit(1);
        }
        return 4;

    }

    public static void main(String[] args)throws IOException {
        Triangle triangle=new Triangle(1,1,1);
        Square square=new Square(1,1);
        Circle circle=new Circle(1);
        Scanner in = new Scanner(System.in);
        int choice = menu();
        int selectionFigure=4;
        while(choice!=0){

            switch(choice){
                case 1:
                    selectionFigure=menuFigure();
                    switch(selectionFigure){
                        case 1:
                            triangle = new Triangle();
                            triangle.print();
                            break;
                        case 2:
                            square=new Square();
                            square.print();
                            break;
                        case 3:
                            circle=new Circle();
                            circle.print();
                            break;
                    }
                    break;
                case 2:
                    switch(selectionFigure){
                        case 1:
                            System.out.println("AREA "+triangle.name+" : "+triangle.calculateArea());
                            break;
                        case 2:
                            System.out.println("AREA "+square.name+" : "+square.calculateArea());
                            break;
                        case 3:
                            System.out.println("AREA "+circle.name+" : "+circle.calculateArea());
                            break;
                    }
                    break;
                case 3:
                    switch(selectionFigure){
                        case 1:
                            System.out.println("PERIMETER "+triangle.name+" : "+triangle.calculatePerimeter());
                            break;
                        case 2:
                            System.out.println("PERIMETER "+square.name+" : "+square.calculatePerimeter());
                            break;
                        case 3:
                            System.out.println("PERIMETER "+circle.name+" : "+circle.calculatePerimeter());
                            break;
                    }
                    break;
            }

            System.out.println("\nWciśnij Enter, aby kontynuować...");
            System.in.read();
            choice = menu();
        }
        System.out.println("     ****************************************");
        System.out.println("\n     Koniec programu\n\n");
    }

}
