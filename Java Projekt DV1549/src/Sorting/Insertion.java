package Sorting;

public class Insertion<T extends Comparable<T>> implements SortInterface<T>
{
	private long timeTaken = 0;
	@Override
	public long Sort(T[] arr, int flag)
	{
		this.startTime();
		this.insertion_sort(arr);
		return this.stopTime();
	}
	
	private void insertion_sort(T[] arr)
	{
		int n = 0;
		for (int i = 0; i < arr.length - 1; i++)
		{
			n = i;
			while (n >= 0 && arr[n].compareTo(arr[n + 1]) > 0)
			{
				swap(arr, n, n + 1);
				n--;
			}
		}
	}
	
	private void swap(T[] arr, int n, int p)
	{
		T tmp = arr[n];
		arr[n] = arr[p];
		arr[p] = tmp;
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
