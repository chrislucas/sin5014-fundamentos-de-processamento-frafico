package views.samples;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3f;
import java.awt.*;

/**
 * Created by C.Lucas on 16/04/2017.
 */
public class Cube3D {

    /**
     * http://www.java2s.com/Code/Jar/j/Downloadj3dcore131jar.htm
     * http://www.java2s.com/Code/Java/3D/ThisusestheBoxutilityclasstobuildasimplecube.htm
     * */
    private Canvas3D canvas3D;

    public static class WireCube extends JFrame {

        private Canvas3D canvas3D;

        WireCube(Canvas3D canvas3D) {
            this.canvas3D = canvas3D;
        }


        public Canvas3D getCanvas3D() {
            return canvas3D;
        }

        private BranchGroup buildViewBranch() {
            Canvas3D canvas3D = getCanvas3D();
            BranchGroup branchGroup = new BranchGroup();
            Transform3D viewTransform3D = new Transform3D();
            viewTransform3D.set(new Vector3f(0.0f, 0.0f, 0.5f));
            TransformGroup viewTransformGroup = new TransformGroup(viewTransform3D);
            ViewPlatform viewPlatform = new ViewPlatform();
            PhysicalBody physicalBody = new PhysicalBody();
            PhysicalEnvironment physicalEnvironment = new PhysicalEnvironment();
            viewTransformGroup.addChild(viewPlatform);
            branchGroup.addChild(viewTransformGroup);
            View view = new View();
            view.addCanvas3D(canvas3D);
            view.attachViewPlatform(viewPlatform);
            view.setPhysicalBody(physicalBody);
            view.setPhysicalEnvironment(physicalEnvironment);
            return branchGroup;
        }

        private BranchGroup buildContentBranch(Node shape) {
            BranchGroup contentBranchGroup = new BranchGroup();
            Transform3D rotateCube = new Transform3D();
            rotateCube.set(new AxisAngle4d(1.0,1.0,0.0, Math.PI / 4.0));
            TransformGroup  rotationGroup = new TransformGroup(rotateCube);
            contentBranchGroup.addChild(rotationGroup);
            rotationGroup.addChild(shape);
            return contentBranchGroup;
        }

        private Node buildShape() {
            Appearance appearance = new Appearance();
            PolygonAttributes polygonAttributes = new PolygonAttributes();
            polygonAttributes.setPolygonMode(PolygonAttributes.POLYGON_LINE);
            appearance.setPolygonAttributes(polygonAttributes);
            return new Box(1.0f, 1.0f, 1.0f, appearance);
        }

        public void display() {
            final WireCube frame = this;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Canvas3D canvas3D = getCanvas3D();
                    Dimension dimension = new Dimension(800, 600);
                    SimpleUniverse virtualUniverse = new SimpleUniverse();
                    Locale locale = new Locale(virtualUniverse);
                    locale.addBranchGraph(buildViewBranch());
                    locale.addBranchGraph(buildContentBranch(buildShape()));

                    frame.add(canvas3D);
                    frame.pack();

                    frame.setTitle("WireCube");
                    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    frame.setSize(dimension);
                    frame.setVisible(true);
                    frame.setResizable(false);
                }
            });
        }
    }

    public static void main(String[] args) {
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        Dimension dimension = new Dimension(300,300);
        canvas3D.setSize(dimension);
        Cube3D.WireCube wireCube = new WireCube(canvas3D);
        wireCube.display();
    }
}
