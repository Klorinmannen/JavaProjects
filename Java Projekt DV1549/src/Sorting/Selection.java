package Sorting;

public class Selection<T extends Comparable<T>> implements SortInterface<T>
{

	private long timeTaken = 0;
	
	@Override
	public long Sort(T[] arr, int flag)
	{
		startTime();
		selection_sort(arr);
		return stopTime();
	}
	
	private void selection_sort(T[] arr)
	{
		for (int i = 0; i < arr.length; i++)
		{
			int n = i;
			for (int j = i; j < arr.length; j++)
			{
				if (arr[j].compareTo(arr[n]) < 0)
				{
					n = j;
				}
			}
			if (n != i)
			{
				swap(arr, n, i);	
			}
		}
	}
	
	private void swap(T[] arr, int n, int i)
	{
		T tmp = arr[n];
		arr[n] = arr[i];
		arr[i] = tmp;
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
