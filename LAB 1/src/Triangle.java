import java.util.InputMismatchException;
import java.util.Scanner;
public class Triangle extends Figure implements Print, Set {
    public String name;
    public double sideA;
    public double sideB;
    public double sideC;

    @Override
    public double calculateArea() {
        double halfPerimeter=calculatePerimeter()/2;
        return Math.sqrt(halfPerimeter*(halfPerimeter-sideA)*(halfPerimeter-sideB)*(halfPerimeter-sideC));
    }

    @Override
    public double calculatePerimeter() {
        return sideA+sideB+sideC;
    }

    public Triangle(double sideA, double sideB, double sideC) {
        this.name="TRIANGLE";
        try {
            this.sideA = sideA;
            this.sideB = sideB;
            this.sideC = sideC;
        } catch(InputMismatchException e){
            System.err.println("INPUT ERROR ! EXIT" + e.getMessage());
            System.exit(1);
        }
    }

    public Triangle(Triangle triangle) {
        this.name="TRIANGLE";
        this.sideA = triangle.sideA;
        this.sideB = triangle.sideB;
        this.sideC = triangle.sideC;
    }

    public Triangle() {
        this.name="TRIANGLE";

        Scanner in = new Scanner(System.in);
        try {
            System.out.println("Enter side A: ");
            this.sideA = in.nextDouble();
            System.out.println("Enter side B: ");
            this.sideB = in.nextDouble();
            System.out.println("Enter side C: ");
        } catch(InputMismatchException e){
            System.err.println("INPUT ERROR ! EXIT" + e.getMessage());
            System.exit(1);
        }

    }

    @Override
    public void print() {
        System.out.println("     ****************************************");
        System.out.println("     NAME: "+name);
        System.out.println("     SIDE A: "+sideA);
        System.out.println("     SIDE B: "+sideB);
        System.out.println("     SIDE C: "+sideC);
        System.out.println("     ****************************************");
    }

    @Override
    public void set(){
    }
}
