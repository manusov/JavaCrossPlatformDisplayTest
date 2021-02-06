/*
Java Display Test and Benchmark Utility. (C)2021 IC Book Labs
--------------------------------------------------------------
Class for return detail info with array statistics values.
Note. Lower, higher and center median elements required for
marking in the text report table.
*/

package javadisplaytest;

public class StatisticEntry 
{
public final double percentage;
public final double median, median1, median2, median3;
public final double average, min, max;
public final int medianIndex1, medianIndex2, medianIndex3;

public StatisticEntry( double p,
                       double x1, double x2, double x3, double x4,
                       double y1, double y2, double y3, 
                       int z1, int z2, int z3 )
    {
    percentage = p;     // test progress percentage    
    median = x1;        // calculated median
    median1 = x2;       // lower median element
    median2 = x3;       // higher median element
    median3 = x4;       // center median element
    average = y1;       // average
    min = y2;           // minimum
    max = y3;           // maximum
    medianIndex1 = z1;  // index of lower median element
    medianIndex2 = z2;  // index of higher median element
    medianIndex3 = z3;  // index of center median element
    }
}
