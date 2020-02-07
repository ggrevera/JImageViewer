/**
    \file   GrayImageData.java 
    \brief  contains GrayImageData class definition.
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

import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
//----------------------------------------------------------------------
/** \brief class containing the actual pixel data for a gray image
 *  (one value per pixel)
 */
public class GrayImageData extends ImageData {
    /** \brief Gray image data represented as a 2D array.
     *  \todo allocate and initialize to speed up 2D array indexing.
     */
    //----------------------------------------------------------------------
    /** \brief Given a buffered image, this ctor reads the image data, stores
     *  the raw pixel data in an array, and creates a displayable version of
     *  the image.  Note that this ctor is protected.  The user should only
     *  use ImageData.load( fileName ) to instantiate an object of this type.
     *  \param bi buffered image used to construct an instance of this class
     *  \param w image width
     *  \param h image height
     *  \returns nothing (ctor)
     */
    protected GrayImageData ( BufferedImage bi, int w, int h ) {
        mOriginalData = bi.getData().getPixels( 0, 0, w, h, (int[])null );
        init( w, h );
    }
    //----------------------------------------------------------------------
    /** \brief This ctor constructs a GrayImageData object from an array
     *  of gray pixel values and the width and height of the image.
     *  \param unpacked gray values used to construct an instance of this class
     *  \param w image width
     *  \param h image height
     *  \returns nothing (ctor)
     */
    public GrayImageData ( int[] unpacked, int w, int h ) {
        mOriginalData = unpacked;
        init( w, h );
    }
    //----------------------------------------------------------------------
    /**
     * this function copies the original data to the (unpacked) display data,
     * determines the min and max values,
     * @param w is the image width
     * @param h is the image height
     */
    private void init ( int w, int h ) {
        mW = w;
        mH = h;
        mIsColor = false;

        //determine the min and max values
        mMin = mMax = mOriginalData[ 0 ];
        if (mDisplayData == null)
            mDisplayData = new int[ mOriginalData.length ];
        for (int i=0; i<mOriginalData.length; i++) {
            if (mOriginalData[i] < mMin)    mMin = mOriginalData[i];
            if (mOriginalData[i] > mMax)    mMax = mOriginalData[i];
            mDisplayData[i] = mOriginalData[i];
        }

        if (mDisplayImage == null)
            mDisplayImage = new BufferedImage( mW, mH, BufferedImage.TYPE_INT_RGB );
        unpackedGray2packedRGB( mDisplayData );
        System.out.println( "min=" + mMin + ", max=" + mMax );
    }
    //----------------------------------------------------------------------
    /** \brief This function converts the raw gray pixel data values <b>in
     *  the specified unpacked array parameter</b> to rgb values and creates a
     *  displayable image.  This is required because only color (rgb) images
     *  can be displayed/drawn in a window.  The gray values in unpacked will
     *  be converted to clamped rgb values.  This is required because each
     *  individual r, g, or b component must be in the range of [0..255].
     *  \param unpacked - unpacked gray input values
     *  \returns nothing (void)
     */
    public void unpackedGray2packedRGB ( int[] unpacked ) {
        assert unpacked != null;
        assert !mIsColor;
        assert unpacked.length == mW*mH;

        int[] packed = new int[ unpacked.length ];
        for (int i=0; i<unpacked.length; i++) {
            int g = unpacked[i];
            if (g < 0)      g = 0;
            if (g > 255)    g = 255;
            packed[i] = (g<<16) | (g<<8) | g;
        }
        mDisplayImage.setRGB( 0, 0, mW, mH, packed, 0, mW );
    }
    //----------------------------------------------------------------------
    /** \brief Given a pixel's row and column location, this function
     *  \param row image row
     *  \param col image column
     *  \returns the pixel's gray value.
     */
    public int getGray ( int row, int col ) {
        int  offset = row*mW + col;
        return mOriginalData[ offset ];
    }

}
//----------------------------------------------------------------------

