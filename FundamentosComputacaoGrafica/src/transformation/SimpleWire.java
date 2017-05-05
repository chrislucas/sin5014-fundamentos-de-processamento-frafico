package transformation;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Locale;
import javax.media.j3d.Node;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * This uses the Box utility class to build a simple cube. The cube is then
 * displayed in as a wireframe by setting the polygon attributes accordingly.
 *
 * @author I.J.Palmer
 * @version 1.0
 */
public class SimpleWire extends Frame implements ActionListener {
    protected Canvas3D myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

    protected Button myButton = new Button("Exit");

    /**
     * This function builds the view branch of the scene graph. It creates a
     * branch group and then creates the necessary view elements to give a
     * useful view of our content.
     *
     * @param c
     *            Canvas3D that will display the view
     * @return BranchGroup that is the root of the view elements
     */
    protected BranchGroup buildViewBranch(Canvas3D c) {
        BranchGroup viewBranch = new BranchGroup();
        Transform3D viewXfm = new Transform3D();
        viewXfm.set(new Vector3f(0.0f, 0.0f, 5.0f));
        TransformGroup viewXfmGroup = new TransformGroup(viewXfm);
        ViewPlatform myViewPlatform = new ViewPlatform();
        PhysicalBody myBody = new PhysicalBody();
        PhysicalEnvironment myEnvironment = new PhysicalEnvironment();
        viewXfmGroup.addChild(myViewPlatform);
        viewBranch.addChild(viewXfmGroup);
        View myView = new View();
        myView.addCanvas3D(c);
        myView.attachViewPlatform(myViewPlatform);
        myView.setPhysicalBody(myBody);
        myView.setPhysicalEnvironment(myEnvironment);
        return viewBranch;
    }

    /**
     * This builds the content branch of our scene graph. It uses the buildCube
     * function to create the actual shape, adding to to the transform group so
     * that the shape is slightly tilted to reveal its 3D shape.
     *
     * @param shape
     *            Node that represents the geometry for the content
     * @return BranchGroup that is the root of the content branch
     */
    protected BranchGroup buildContentBranch(Node shape) {
        BranchGroup contentBranch = new BranchGroup();
        Transform3D rotateCube = new Transform3D();
        rotateCube.set(new AxisAngle4d(1.0, 1.0, 0.0, Math.PI / 4.0));
        TransformGroup rotationGroup = new TransformGroup(rotateCube);
        contentBranch.addChild(rotationGroup);
        rotationGroup.addChild(shape);
        return contentBranch;
    }

    /**
     * This constructs a cube using the Box utility class. The attributes are
     * set so that the lines are drawn and not the faces.
     *
     * @return Node that is the cube
     */
    protected Node buildShape() {
        Appearance app = new Appearance();
        //Create the polygon attributes
        PolygonAttributes polyAttr = new PolygonAttributes();
        //Set them so that the draw mode is polygon line
        polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        //Use these in the appearance
        app.setPolygonAttributes(polyAttr);
        return new Box(1.0f, 1.0f, 1.0f, app);
    }

    /**
     * Handles the exit button action to quit the program.
     */
    public void actionPerformed(ActionEvent e) {
        dispose();
        System.exit(0);
    }

    public SimpleWire() {
        VirtualUniverse myUniverse = new VirtualUniverse();
        Locale myLocale = new Locale(myUniverse);
        myLocale.addBranchGraph(buildViewBranch(myCanvas3D));
        myLocale.addBranchGraph(buildContentBranch(buildShape()));
        setTitle("SimpleWire");
        setSize(400, 400);
        setLayout(new BorderLayout());
        add("Center", myCanvas3D);
        add("South", myButton);
        myButton.addActionListener(this);
        setVisible(true);
    }

    public static void main(String[] args) {
        SimpleWire sw = new SimpleWire();
    }
}
