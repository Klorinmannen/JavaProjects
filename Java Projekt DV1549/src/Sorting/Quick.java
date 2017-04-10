package Sorting;

public class Quick<T extends Comparable<T>> implements SortInterface<T> 
{
	private final int CUT_OFF = 8;
	private final int MEDIAN_OF_THREE = 40;
	private instertionHelper<T> insertVar = new instertionHelper<T>();
	private long timeTaken = 0;
	
	@Override
	public long Sort(T[] arr, int flag)
	{
		if (flag == 0)
		{
			startTime();
			bad_quick_sort(arr, 0, arr.length - 1);
			return stopTime();
		}
		else
		{
			startTime();
			quick_sort(arr, 0, arr.length - 1);
			return stopTime();
		}
	}
	
	private void bad_quick_sort(T[] arr, int low, int high)
	{
		//posseble optimization with iterative soloution instead of recursive
		//and use of a stack
		int lenght = high - low + 1;
		if(lenght <= CUT_OFF)
		{
			//call insertion sort on the remainder
			insertVar.insertionSort(arr, low, high);
		}
		else
		{
			int pivot = badPartion(arr, low, high);
			bad_quick_sort(arr, pivot + 1, high);
			bad_quick_sort(arr, low, pivot - 1);
		}
	}
	
	private int badPartion(T[] arr, int low, int high)
	{
		int pivotPos = low;
		T pivotValue = arr[low];
		
		for (int i = pivotPos; i <= high; i++)
		{
			if (arr[i].compareTo(pivotValue) < 0)
			{
				swap(arr, i, pivotPos + 1);
				swap(arr, pivotPos, pivotPos + 1);
				pivotPos++;
			}
		}
		return pivotPos;
	}
	
	private void quick_sort(T[] arr, int low, int high)
	{
		int lenght = high - low + 1;
		if (lenght <= CUT_OFF)
		{
			//call insertion sort on the remainder
			insertVar.insertionSort(arr, low, high);
		}
		else
		{
			int pivot = partion(arr, low, high);
			quick_sort(arr, low, pivot - 1);
			quick_sort(arr, pivot + 1, high);
		}
	}
	
	private int partion(T[] arr, int low, int high)
	{ 
		int pivotPos = 0;
		int mid = (low + high) / 2;	
		int lenght = high - low + 1;
		
		if (lenght < MEDIAN_OF_THREE)
		{
			//median of three
			pivotPos = medianOfThree(arr, low, mid, high);
		}
		else
		{
			//Tukey´s ninther, Median of medians
			pivotPos = tukeysMedian(arr, low, mid, high, lenght);
		}
		
		T pivotValue = arr[pivotPos];
		int j = high;
		int i = low;
		while (true)
		{
			while(arr[++i].compareTo(pivotValue) < 0)
			{
			}
			while (pivotValue.compareTo(arr[--j]) < 0)
			{
			}
			if (i <= j)
			{
				swap(arr, i, j);
			}
			else
			{
				break;
			}
		}
		swap(arr, i, high);
		return i;
	}
	
	private int medianOfThree(T[] arr, int low, int mid, int high)
	{
		if (arr[high].compareTo(arr[low]) < 0) //value at index high is less then value at index low
		{
			swap(arr, high, low);
		}
		if (arr[mid].compareTo(arr[low]) < 0) //value at index mid is less than value at index low
		{
			swap(arr, mid, low);
		}
		if(arr[high].compareTo(arr[mid]) < 0) //value at index high is less than value at index mid
		{
			swap(arr, mid, high);
		}
		swap(arr, mid, high);
		return high; 
	}
	
	private int tukeysHelper(T[] arr, int low, int mid, int high)
	{	
		if (arr[high].compareTo(arr[low]) < 0) //value at index high is less then value at index low
		{
			swap(arr, high, low);
		}
		if (arr[mid].compareTo(arr[low]) < 0) //value at index mid is less than value at index low
		{
			swap(arr, mid, low);
		}
		if(arr[high].compareTo(arr[mid]) < 0) //value at index high is less than value at index mid
		{
			swap(arr, mid, high);
		}
		return mid; //median is now placed at index mid and returned
	}
	
	private int tukeysMedian(T[] arr, int low, int mid, int high, int lenght)
	{
		int tLow = lenght / 8;
		int fm = 0,
			sm = 0,
			lm = 0,
			ninther = 0;	
		fm = tukeysHelper(arr, low, low + tLow, low + tLow + tLow); //utgå från low
		sm = tukeysHelper(arr, mid - tLow, mid, mid + tLow);	//utgår från mid
		lm = tukeysHelper(arr, high - tLow - tLow, high - tLow, high); //utgår från high
		ninther = tukeysHelper(arr, fm, sm, lm); //median of medians
		swap(arr, ninther, high);
		return high;
	}
	
	private void swap(T[] arr, int low, int high)
	{
		T tmp = arr[low];
		arr[low] = arr[high];
		arr[high] = tmp;
	}

	private void startTime()
	{
		this.timeTaken = System.nanoTime();
	}

	private long stopTime()
	{
		return System.nanoTime() - this.timeTaken;
	}	
}
