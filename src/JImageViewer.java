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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.Preferences;
import javax.swing.*;
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

    //better to migrate to file as opposed to using windows registry.
    // see http://www.davidc.net/programming/java/java-preferences-using-file-backing-store
    private static Preferences prefs = Preferences.userRoot();  ///< for user preferences ("dir" is last dir)
    private static int windowPosition = 50;
    //----------------------------------------------------------------------
    /** \brief Ctor that simply creates an empty window.
     *  \returns nothing (ctor)
     */
    public JImageViewer ( ) {
        init( null );
    }
    //----------------------------------------------------------------------
    /** \brief Ctor that given the name of an image file, displays that
     *  image in a window.
     *  \param fname name of input image file
     *  \returns nothing (ctor)
     */
    public JImageViewer ( String fname ) {
        init( fname );
    }
    //----------------------------------------------------------------------
    /** \brief Load and display an image.
     *  \param fn name of input image file
     *  \returns nothing (void)
     */
    private void init ( String fn ) {
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        setupMenu();
        //was a file name specified?
        if (fn != null) {
            System.out.println( "Loading file. Please wait." );
            Timer  t = new Timer();  //see how long this takes
            mImage = ImageData.load( fn );
            System.out.println( "Loading this image file required "
                              + t.getElapsedTimeNano() + " seconds." );
            mImagePanel.setPreferredSize( new Dimension(mImage.mW,mImage.mH) );
            setTitle( "JImageViewer: " + fn   );
        } else {
            setTitle( "JImageViewer: <empty>" );
        }
        JScrollPane jsp = new JScrollPane( mImagePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        jsp.setDoubleBuffered( true );
        add( jsp );
        setSize( 800, 600 );
        setPreferredSize( new Dimension(800,600) );
        //make sure windows do not overlay each other
        setLocation( windowPosition, windowPosition );
        windowPosition += 50;
        windowPosition %= 800;
        pack();
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

            //set default dir to previous one (if any)
            String d = prefs.get( "dir", null );
            JFileChooser chooser;
            if (d == null)    chooser = new JFileChooser();
            else              chooser = new JFileChooser( d );

            chooser.setMultiSelectionEnabled( true );  //multiple (more than 1)
            chooser.setFileFilter( filter );
            int ret = chooser.showOpenDialog( this );
            if (ret == JFileChooser.APPROVE_OPTION) {
                //update only if changed
                String newD = chooser.getCurrentDirectory().getAbsolutePath();
                if (!newD.equals(d))    prefs.put( "dir", newD );

                //single file selection:
                //new JImageViewer( chooser.getSelectedFile().getAbsolutePath() );

                //handle multiple file selection
                File[] f = chooser.getSelectedFiles();
                for (int i=0; i<f.length; i++) {
                    new JImageViewer( f[i].getAbsolutePath() );
                }
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

