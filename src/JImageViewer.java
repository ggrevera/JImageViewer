/**
    \file   JImageViewer.java
    \brief  contains JImageViewer class definition.
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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
//----------------------------------------------------------------------
/** \brief instantiate this class to display an image */
public class JImageViewer extends JFrame implements ActionListener {

    JMenuBar    mMenuBar    = new JMenuBar();                   ///< menu bar
    JMenu       mFile       = new JMenu( "File" );           ///< file menu item
    JMenuItem   mOpen       = new JMenuItem( "Open" );     ///< open menu item
    JMenuItem   mClose      = new JMenuItem( "Close" );    ///< close menu item
    JMenuItem   mSave       = new JMenuItem( "Save" );     ///< save menu item
    JMenuItem   mSaveAs     = new JMenuItem( "Save As" );  ///< save as menu item
    JMenuItem   mExit       = new JMenuItem( "Exit" );     ///< exit menu item
    ImagePanel  mImagePanel = new ImagePanel( this );       ///< panel in which an image may be displayed
    ImageData   mImage;                                        ///< actual image data
    //----------------------------------------------------------------------
    /** \brief Ctor that simply creates an empty window.
     *  \returns nothing (ctor)
     */
    public JImageViewer ( ) {
        try {
            init( null );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //----------------------------------------------------------------------
    /** \brief Ctor that given the name of an image file, displays that
     *  image in a window.
     *  \param fname name of input image file
     *  \returns nothing (ctor)
     */
    public JImageViewer ( String fname ) {
        try {
            init( fname );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //----------------------------------------------------------------------
    /** \brief Load and display an image.
     *  \param fn name of input image file
     *  \returns nothing (void)
     */
    private void init ( String fn ) {
        setupMenu();
        //was a file name specified?
        if (fn != null) {
            System.out.println( "Loading file. Please wait." );
            Timer  t = new Timer();  //see how long this takes
            mImage = ImageData.load( fn );
            System.out.println( "Loading this image file required "
                              + t.getElapsedTimeNano() + " seconds." );
        }
        //getContentPane().add( new JScrollPane(mImagePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) );
        getContentPane().add( new JScrollPane(mImagePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) );
        if (fn == null) {
            setSize( 800, 600 );
            setPreferredSize( new Dimension(800,600) );
            setTitle( "JImageViewer: <empty>" );
        } else {
            //setSize( mImage.mW + 100, mImage.mH + 100 );
            setSize( 800, 600 );
            setPreferredSize( new Dimension(800,600) );
            setTitle( "JImageViewer: " + fn );
        }
        setLocation( 50, 50 );
        setVisible( true );
    }
    //----------------------------------------------------------------------
    /** \brief Respond to menu actions.
     *  \param e action event
     *  \returns nothing (void)
     */
    public void actionPerformed ( ActionEvent e ) {
        if (e.getSource() == mExit) {
            System.exit(0);
        } else if (e.getSource() == mOpen) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "image & audio files",
                    "bmp", "gif", "ico", "jpg", "png", "pgm", "pnm", "ppm", "tif", "wav"
            );
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter( filter );
            int ret = chooser.showOpenDialog( this );
            if (ret == JFileChooser.APPROVE_OPTION) {
                new JImageViewer( chooser.getSelectedFile().getAbsolutePath() );
            }
        } else {
            /** \todo handle Close, Save, Save As, etc.
             */
            System.out.println( "Sorry.\nThis action is not yet handled." );
            JOptionPane.showMessageDialog( null,
                "Sorry.\n\nThis action is not yet handled.\n ",
                "Information", JOptionPane.INFORMATION_MESSAGE );
        }
    }
    //----------------------------------------------------------------------
    /** \brief Set up the menu bar.
     *  \returns nothing (void)
     */
    private void setupMenu ( ) {
        setJMenuBar( mMenuBar );

        mMenuBar.add( mFile );
        mFile.add( mOpen );
        mFile.add( mClose );
        mFile.add( mSave );
        mFile.add( mSaveAs );
        mFile.addSeparator();
        mFile.add( mExit );

        mSave.setEnabled( false );
        mSaveAs.setEnabled( false );

        mOpen.addActionListener( this );
        mClose.addActionListener( this );
        mSave.addActionListener( this );
        mSaveAs.addActionListener( this );
        mExit.addActionListener( this );
    }

}
//----------------------------------------------------------------------

