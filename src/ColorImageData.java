/**
    \file   ColorImageData.java 
    \brief  contains ColorImageData class definition.
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

import javax.swing.*;
import java.awt.image.BufferedImage;
//----------------------------------------------------------------------
/** \brief class containing the actual pixel data for a color image
 *  (3 values per pixel - red, green, and blue)
 */
public class ColorImageData extends ImageData {
    /** \brief Color image data represented as a 2D array (of rgb values).
     *  \todo allocate and initialize to speed up 2D array indexing.
     */
    //----------------------------------------------------------------------
    /** \brief Given a buffered image, this ctor reads the image data, stores
     *  the raw pixel data in an array, and creates a displayable version of
     *  the image.  Note that this ctor is protected.  The user should only
     *  use ImageData.load( fileName ) to instantiate an object of this type.
     *  \param bi buffered image used to construct this an instance of this class
     *  \param w image width
     *  \param h image height
     *  \returns nothing (ctor)
     */
    protected ColorImageData ( BufferedImage bi, int w, int h ) {
        mOriginalData = bi.getData().getPixels( 0, 0, w, h, (int[])null );
        System.out.println( mOriginalData.length );
        System.out.println( w*h );
        System.out.println( bi.getType() );
        assert bi.getType() == BufferedImage.TYPE_3BYTE_BGR;
        if (bi.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            JOptionPane.showMessageDialog( null,
                    "Warning: \n\nUnsupported image type: " + bi.getType() + ". \n ",
                    "Warning", JOptionPane.WARNING_MESSAGE );
        }
        //BufferedImage.TYPE_BYTE_INDEXED
        init( w, h );
    }
    //----------------------------------------------------------------------
    /** \brief This ctor constructs a ColorImageData object from an array
     *  of rgb pixel values and the width and height of the image.
     *  \param unpacked array of rgb values
     *  \param w image width
     *  \param h image height
     *  \returns nothing (ctor)
     */
    public ColorImageData ( int[] unpacked, int w, int h ) {
        mOriginalData = unpacked;
        init( w, h );
    }
    //----------------------------------------------------------------------
    private void init ( int w, int h ) {
        mW = w;
        mH = h;
        mIsColor = true;

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
        unpackedRGB2packedRGB( mDisplayData );
        System.out.println( "min=" + mMin + ", max=" + mMax );
    }
    //----------------------------------------------------------------------
    /** \brief This function takes an unpacked int array of rgb pixel values
     *  and packs them into a single int and puts the packed value in the
     *  output array.  mDisplayImage is changed to these values as well.
     *  \param unpacked unpacked int array of rgb values
     *  \returns nothing (void)
     */
    public void unpackedRGB2packedRGB ( int[] unpacked ) {
        assert unpacked != null;
        assert mIsColor;
        assert unpacked.length == mW*mH*3;

        int[] packed = new int[ mW*mH ];
        for (int i=0,j=0; i<packed.length; i++) {
            int r = unpacked[j++];
            int g = unpacked[j++];
            int b = unpacked[j++];

            if (r < 0)      r = 0;
            if (r > 255)    r = 255;
            if (g < 0)      g = 0;
            if (g > 255)    g = 255;
            if (b < 0)      b = 0;
            if (b > 255)    b = 255;

            packed[i] = (r<<16) | (g<<8) | b;
        }
        mDisplayImage.setRGB( 0, 0, mW, mH, packed, 0, mW );
    }

    public void old_unpacked2packed ( int[] unpacked, int[] packed ) {
        for (int i=0,j=0; i<packed.length; i++) {
            assert( 0<=unpacked[j] && unpacked[j]<=255 );  //check range
            final int  r = unpacked[j++] & 0xff;

            assert( 0<=unpacked[j] && unpacked[j]<=255 );  //check range
            final int  g = unpacked[j++] & 0xff;

            assert( 0<=unpacked[j] && unpacked[j]<=255 );  //check range
            final int  b = unpacked[j++] & 0xff;

            packed[i] = (r << 16) | (g << 8) | b;
        }
        mDisplayImage.setRGB( 0, 0, mW, mH, packed, 0, mW );
    }
    //----------------------------------------------------------------------
    /** \brief Given a pixel's row and column location, this function
     *  \param row image row
     *  \param col image column
     *  \returns the value of the pixel's red component.
     */
    public int getRed ( int row, int col ) {
        int offset = 3 * (row*mW + col);
        return mOriginalData[ offset ];
    }
    /** \brief Given a pixel's row and column location, this function
     *  \param row image row
     *  \param col image column
     *  \returns the value of the pixel's green component.
     */
    public int getGreen ( int row, int col ) {
        int offset = 3 * (row*mW + col);
        return mOriginalData[ offset+1 ];
    }
    /** \brief Given a pixel's row and column location, this function
     *  \param row image row
     *  \param col image column
     *  \returns the value of the pixel's blue component.
     */
    public int getBlue ( int row, int col ) {
        int offset = 3 * (row*mW + col);
        return mOriginalData[ offset+2 ];
    }

}
//----------------------------------------------------------------------

