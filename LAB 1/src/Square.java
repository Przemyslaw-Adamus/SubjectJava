import java.util.InputMismatchException;
import java.util.Scanner;

public class Square extends Figure implements Print {
    public String name;
    public double sideA;
    public double sideB;

    public Square(double sideA, double sideB) {
        this.name="SQUARE";
        this.sideA = sideA;
        this.sideB = sideB;
    }

    public Square() {
        Scanner in = new Scanner(System.in);
        this.name="SQUARE";
        try {
            System.out.println("Enter side A: ");
            this.sideA = in.nextDouble();
            System.out.println("Enter side B: ");
            this.sideB = in.nextDouble();
        } catch(InputMismatchException e){
            System.err.println("INPUT ERROR ! EXIT" + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public double calculateArea() {
        return sideA*sideB;
    }

    @Override
    public double calculatePerimeter() {
        return 2*sideA+2*sideB;
    }

    @Override
    public void print() {
        System.out.println("     ****************************************");
        System.out.println("     NAME: "+name);
        System.out.println("     SIDE A: "+sideA);
        System.out.println("     SIDE B: "+sideB);
        System.out.println("     ****************************************");
    }
}
