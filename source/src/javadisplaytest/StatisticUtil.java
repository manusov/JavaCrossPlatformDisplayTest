/*
Java Display Test and Benchmark Utility. (C)2019 IC Book Labs
--------------------------------------------------------------
Static class with statistic library: min, max, median, average for arrays.
Note array ordering software method don't replaced to java.util.Arrays.sort
because service array with elements indexes required.
*/

package javadisplaytest;

public class StatisticUtil 
{
    
/*
method returns statistics representation of input array, 
*/
public static StatisticEntry getStatistic
        ( double[] array, int countNow, int countMax )
    {
    MedianEntry medianEntry = findMedianIndex( array );
    double average = findAverage( array );
    double min = findMin( array );
    double max = findMax( array );
    
    double cn = countNow;
    double cm = countMax;
    double percentage = cn / cm * 100.0;
    
    StatisticEntry statisticEntry = new StatisticEntry
        ( percentage,
          medianEntry.median, 
          medianEntry.median1,
          medianEntry.median2,
          medianEntry.median3, 
          average, min, max,
          medianEntry.medianIndex1,
          medianEntry.medianIndex2,
          medianEntry.medianIndex3 );
    return statisticEntry;
    }

    
/*
Private helpers.    
*/
    
/*    
method returns minimum of input array, selected part n elements
*/
private static double findMin( double[] array )
    {
    double min = array[0];
    for( int i=0; i<array.length; i++ )
        {
        if ( min > array[i] ) min = array[i];
        }
    return min;
    }

/*
method returns maximum of input array, selected part n elements
*/
private static double findMax( double[] array )
    {
    double max = array[0];
    for( int i=0; i<array.length; i++ )
        {
        if ( max < array[i] ) max = array[i];
        }
    return max;
    }

/*
average of input array
*/
private static double findAverage( double[] array )
    {
    double average = 0.0;
    for( int i=0; i<array.length; i++ )
        {
        average += array[i];
        }
    average /= array.length;
    return average;
    }

/*
method returns median representation of input array
*/
private static MedianEntry findMedianIndex( double[] arrayIn )
    {
    // this copy required because input array modified (sorted) by method
    int n = arrayIn.length;
    double[] array = new double[n];
    System.arraycopy( arrayIn, 0, array, 0, n );
    // operation with copy of array
    int a=-1, b=-1, c=-1;                      // median position indexes
    int[] service = new int[n];
    for(int i=0; i<n; i++)
        {
        service[i] = i;
        }
    double median = Double.NaN;
    double median1 = Double.NaN;
    double median2 = Double.NaN;
    double median3 = Double.NaN;
    if ( ( array != null ) && ( n == 1 ) && ( n <= array.length ) )
        {  // median for one element array
        median = array[0];
        a = b = c = 0;
        median1 = array[a];
        median2 = array[b];
        median3 = array[c];
        }
    else if ( ( array != null ) && ( n > 1 ) && ( n <= array.length ) )
        {
        // sorting array, support values and indexes arrays
        boolean flag = true;
        while (flag)
            {
            flag = false;
            for( int i=0; i<n-1; i++ )
                {
                if ( array[i] > array[i+1] )
                    {
                    double temp1 = array[i];     // values array support
                    array[i] = array[i+1];
                    array[i+1] = temp1;
                    
                    int temp2 = service[i];      // service array support
                    service[i] = service[i+1];
                    service[i+1] = temp2;
                    
                    flag = true;
                    }
                }
            }
/*
Can't sorting by array utilites, because
service array with elements indexes generation required.
Arrays.sort( array, 0, n );
*/            
        // get median from sorted array
        int i = n/2;
        if ( n % 2 == 0 )
            {
            median = ( array[i-1] + array[i] ) / 2.0;
            a = i-1;
            b = i;
            median1 = array[a];
            median2 = array[b];
            }
        else
            {
            median = array[i];
            if ((i==0)||(i==n))
                {
                a = b = c = i;
                }
            else
                {
                a = i-1;
                b = i+1;
                c = i;
                }
            median1 = array[a];
            median2 = array[b];
            median3 = array[c];
            }
        }
    if ( a>=0 ) a = service[a];
    if ( b>=0 ) b = service[b];
    if ( c>=0 ) c = service[c];
    return new MedianEntry( median, median1, median2, median3, a, b, c );
    }

}
