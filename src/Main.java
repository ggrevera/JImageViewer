/**
 \file   Main.java
 \brief  Contains Main class definition.
 \author George J. Grevera, Ph.D., ggrevera@sju.edu

 Copyright (C) 2010, George J. Grevera

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
/** \brief This class contains tyhe main() method. */
public class Main {
    /** \brief Main application entry point.
     *  \param args Each image file name in args will cause that image to be
     *              displayed in a window.
     */
    public static void main ( String[] args ) {
        if (args.length==0) {
            new JImageViewer();
        } else {
            for (int i = 0; i < args.length; i++)
                new JImageViewer( args[i] );
        }
    }
}
