package sample;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.util.Random;


public class DrawerTask extends Task {
    private int nrPoint;
    private PointListener listener;


    public DrawerTask(int nrPoint ) {
        this.nrPoint=nrPoint;
    }

    public void setListener(PointListener listener) {
        this.listener = listener;
    }

    @Override
    protected Object call() throws Exception {
       // BufferedImage bi= new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
       // gc.setFill(Color.BLUEVIOLET);
     //   gc.fillRect(gc.getCanvas().getLayoutX(), gc.getCanvas().getLayoutY(), gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        double x=0.0;
        double y=0.0;
       // double pointX=0;
       // double pointY=0;
        int hit=0;

        for(int i=0; i<nrPoint;i++)
        {
            System.out.println(nrPoint);
            Random random = new Random();
            x=(16*random.nextDouble())-8.0;
            y=(16*random.nextDouble())-8.0;
            //pointX=((gc.getCanvas().getWidth())*(x+8)/16);
           // pointY=((gc.getCanvas().getHeight())*(y+8)/16);
          //  pointY=400-pointY;

            if(Equation.calc(x,y)){
                //bi.setRGB((int)pointX, (int)pointY,java.awt.Color.YELLOW.getRGB());
                listener.pointFound(new PointEvent(x, y, true, i));
                hit++;
                System.out.println("Trafiony"+i);
            }
            else{
                listener.pointFound(new PointEvent(x, y, false, i));
               // bi.setRGB((int)pointX, (int)pointY,java.awt.Color.BLUE.getRGB());
                System.out.println("pudło"+i);

            }
           // if(i%1000==0){
                //gc.drawImage(SwingFXUtils.toFXImage(bi,null),0,0);
            //}
            //System.out.println(i);
            updateProgress(i, nrPoint);
            if(isCancelled())
                break;
        }
        return (16.0*16.0*hit/nrPoint);
    }
}

