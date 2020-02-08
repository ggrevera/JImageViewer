/**
    \file   ImagePanel.java
    \brief  contains ImagePanel class definition.
    \author George J. Grevera, Ph.D., ggrevera@sju.edu

    Copyright (C) 2006, George J. Grevera

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
    USA or from http://www.gnu.org/licenses/gpl.txt.

    This General Public License does not permit incorporating this
    code into proprietary programs.  (So a hypothetical company such
    as GH (Generally Hectic) should NOT incorporate this code into
    their proprietary programs.)
 */
//package jimageviewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
//----------------------------------------------------------------------
/** \brief class used to display an image (ImageData) in a JImageViewer
 *
 *  Note:  To replace all extraneous ^M's use :%s/^M$//g in vi.
 *  BE SURE YOU MAKE the ^M USING "CTRL-V CTRL-M" NOT BY TYPING "CARROT M"!
 */
class ImagePanel extends JPanel implements MouseMotionListener {
    JImageViewer     mParent;                  ///< reference to JImageViewer in which this ImagePanel appears
    private boolean  mMouseMoveValid = false;  ///< is mouse (x,y) below valid?
    private int      mMouseX;                  ///< mouse movement x position
    private int      mMouseY;                  ///< mouse movement y position
    private Image    mDoubleBuffer = null;     ///< to avoid flicker
    public  double   mZoom = 1.0;              ///< zoom/scale factor
    //----------------------------------------------------------------------
    /** \brief Ctor.
     *  \param p refers to JImageViewer in which this ImagePanel appears
     *  \returns nothing (ctor)
     */
    ImagePanel ( JImageViewer p ) {
        mParent = p;
        setDoubleBuffered( true );
        addMouseMotionListener( this );
    }
    //----------------------------------------------------------------------
    /** \brief Simply call paint.
     *  \param g graphics context
     *  \returns nothing (void)
     */
    public void update ( Graphics g ) {
	paint( g );
    }
    //----------------------------------------------------------------------
    /** \brief Redraw the panel contents.
     *
     *  To avoid flicker (when rapidly redrawing), we will draw everything
     *  into a single image and then draw only that image to the panel.
     *  This is called double buffering.
     *  \param g graphics context
     *  \returns nothing (void)
     */
    public void paint ( Graphics g ) {
        //if the size of the panel has changed, we need a new doublebuffer of
        // the correct size
        Dimension  d = getSize();
        if ( mDoubleBuffer==null || mDoubleBuffer.getWidth(null)!=d.width
          || mDoubleBuffer.getHeight(null)!=d.height ) {
            mDoubleBuffer = createImage( d.width, d.height );
        }

        //draw into the doublebuffer
        Graphics  dbg = mDoubleBuffer.getGraphics();
        dbg.setColor( Color.DARK_GRAY );
        dbg.fillRect( 0, 0, d.width, d.height );
        if (mParent.mImage!=null && mParent.mImage.mDisplayImage!=null) {
            dbg.drawImage( mParent.mImage.mDisplayImage, 0, 0,
                    (int)(mParent.mImage.mW * mZoom + 0.5),
                    (int)(mParent.mImage.mH * mZoom + 0.5), null );
        }

        if (mMouseMoveValid) {
            //report position
            dbg.setColor( Color.BLACK );
            dbg.drawString( "(" + mMouseX + "," + mMouseY + ")", 20, 40 );
            dbg.setColor( Color.WHITE );
            dbg.drawString( "(" + mMouseX + "," + mMouseY + ")", 21, 41 );
        }

        //draw the doublebuffer on the panel
        g.drawImage( mDoubleBuffer, 0, 0, null );
    }
    //----------------------------------------------------------------------
    /** \brief Track mouse movement when button is not down.
     *  \param e mouse event
     *  \returns nothing (void)
     */
    public void mouseMoved ( MouseEvent e ) {
        mMouseMoveValid = true;
        mMouseX = e.getX();
        mMouseY = e.getY();
        repaint();
    }
    //----------------------------------------------------------------------
    /** \brief Track mouse movement when button is down.
     *  \param e mouse event
     *  \returns nothing (void)
     */
    public void mouseDragged ( MouseEvent e ) {
        mMouseMoveValid = true;
        mMouseX = e.getX();
        mMouseY = e.getY();
        repaint();
    }
}
//----------------------------------------------------------------------

