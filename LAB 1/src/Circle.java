import java.util.InputMismatchException;
import java.util.Scanner;

public class Circle extends Figure implements Print {
    public String name;
    public double radius;

    public Circle(double radius) {
        this.name = "CIRCLE";
        this.radius = radius;
    }

    public Circle() {
        this.name = "CIRCLE";
        try{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter side radius: ");
        double r= in.nextDouble();
        if(r<0) {
            System.err.println("INPUT ERROR !");
        }
        else {
            this.radius=radius;
        }
        } catch(InputMismatchException e){
            System.err.println("INPUT ERROR ! EXIT" + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public double calculateArea() {
        return Math.PI*radius*radius;
    }

    @Override
    public double calculatePerimeter() {
        return Math.PI*2*radius;
    }

    @Override
    public void print() {
        System.out.println("     ****************************************");
        System.out.println("     NAME: "+name);
        System.out.println("     RADIUS: "+radius);
        System.out.println("     ****************************************");
    }
}
