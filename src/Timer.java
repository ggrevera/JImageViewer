/**
    \file   Timer.java
    \brief  contains Timer class definition.
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

//----------------------------------------------------------------------
/** \brief this class can be used to time operations.
 */
public class Timer {
  long  mStartMillis;  ///< starting time in milliseconds
  long  mStartNano;    ///< starting time in nanoseconds
  //----------------------------------------------------------------------
  /** \brief Construct an instance of the Timer class.
   *  \returns nothing (ctor)
   */
  Timer ( ) {
      reset();
  }
  //----------------------------------------------------------------------
  /** \brief reset the timer
   *  \returns nothing (void)
   */
  void reset ( ) {
      mStartMillis = System.currentTimeMillis();
      mStartNano   = System.nanoTime();
  }
  //----------------------------------------------------------------------
  /** \brief get the elapsed time.  note that the timer continues to run.
   *  \returns elapsed time in seconds (with accuracy to milliseconds)
   */
  double getElapsedTime ( ) {
      final long  endMillis = System.currentTimeMillis();
      return (endMillis-mStartMillis) / 1E3;
  }
  //----------------------------------------------------------------------
  /** \brief get the elapsed time.  note that the timer continues to run.
   *  \returns elapsed time in seconds (with accuracy to nanoseconds)
   */
  double getElapsedTimeNano ( ) {
      final long  endNano = System.nanoTime();
      return (endNano-mStartNano) / 1E9;
  }

}
//----------------------------------------------------------------------

