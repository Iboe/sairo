package de.fhb.sailboat.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JDialog;

/**
 * TODO Add comment/ author
 * @author ???
 *
 */
public class RootDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * TODO Add comment.
	 */
	public RootDialog() {
		super();
	}

	/**
	 * TODO Add comment.
	 */
	public RootDialog(Frame frame, boolean modal) {
		super( frame, modal );
	}

	/* (non-Javadoc)
	 * @see java.awt.Window#setBounds(int, int, int, int)
	 */
	@Override
	/**
	 * TODO Add comment or commented reference to setBounds.
	 */
	public void setBounds(int x, int y, int width, int height) {
		Point p = calculateCenter( getParent(), new Dimension( width, height ) );
		System.out.println( p + ", " + new Dimension( width, height ) );
		super.setBounds( p.x, p.y, width, height );
	}

	/**
	 * @param parent
	 * @return
	 */
	public static Point calculateCenter(Container parent, Dimension size) {
		while ( parent.getParent() != null ) {
			parent = parent.getParent();
		}
		return calculateCenter( new Rectangle( parent.getLocation(), parent.getSize() ), size );
	}

	/**
	 * TODO Add comment.
	 */
	public static Point calculateCenter(Rectangle screen, Dimension windowSize) {
		Point p = screen.getLocation();
		Dimension m = screen.getSize();
		//SwingUtilities.convertPointToScreen( p, parent );

		p.translate( (int) m.width / 2, (int) m.height / 2 );
		p.translate( windowSize.width / -2, windowSize.height / -2 );

		return p;
	}

}