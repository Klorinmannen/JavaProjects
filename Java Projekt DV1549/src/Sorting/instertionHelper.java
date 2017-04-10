package Sorting;

public class instertionHelper<T extends Comparable<T>>
{
	public void insertionSort(T[] arr, int low, int high)
	{
		int n = 0;
		for (int i = low; i < high; i++)
		{
			n = i;
			while (n >= 0 && arr[n].compareTo(arr[n + 1]) > 0)
			{
				swap(arr, n, n + 1);
				n--;
			}
		}
	}
	
	private void swap(T[] arr, int n, int nP)
	{
		T tmp = arr[n];
		arr[n] = arr[nP];
		arr[nP] = tmp;
	}
}
