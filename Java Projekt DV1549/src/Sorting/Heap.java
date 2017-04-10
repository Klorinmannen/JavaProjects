package Sorting;

public class Heap<T extends Comparable<T>> implements SortInterface<T>
{
	private long timeTaken = 0;

	@Override
	public long Sort(T[] arr, int flag)
	{
		startTime();
		heap_sort(arr);
		return stopTime();
	}
		
	private void heap_sort(T[] arr)
	{
		for (int i = 1; i < arr.length; i++)
		{
			percolateUp(arr, i); //build heap
		}
		for (int i = arr.length - 1; i >= 0; i--)
		{
			swap(arr, 0, i ); //sort array
			percolateDown(arr, 0, i); //restore heap property
		}
	}
	
	private void percolateUp(T[] arr, int index)
	{
		if (arr[index].compareTo(arr[parent(index)]) > 0) //child bigger then its parent
		{
			swap(arr, index, parent(index));
			percolateUp(arr, parent(index));
		}
	}
	
	private void percolateDown(T[] arr, int index, int capacity)
	{
		if (index < capacity)
		{
			if (leftChild(index) < capacity) //first child exists
			{
				if (rightChild(index) < capacity) //second child exists
				{
					if (arr[leftChild(index)].compareTo(arr[rightChild(index)]) > 0)
					{
						if (arr[leftChild(index)].compareTo(arr[index]) > 0)
						{
							swap(arr, leftChild(index), index); //first child is bigger
							percolateDown(arr, leftChild(index), capacity);
						}
					}
					else if(arr[rightChild(index)].compareTo(arr[index]) > 0)
					{
						swap(arr, index, rightChild(index)); // second child is bigger
						percolateDown(arr, rightChild(index), capacity);
					}
				}
				else if(arr[leftChild(index)].compareTo(arr[index]) > 0)
				{
					swap(arr, index, leftChild(index)); //first child is bigger
					percolateDown(arr,  leftChild(index), capacity);
				}
			}
		}
	}
	
	private void swap(T[] arr, int indexOne, int indexTwo)
	{
		T tmp = arr[indexOne];
		arr[indexOne] = arr[indexTwo];
		arr[indexTwo] = tmp;
	}
	
	private int parent(int index)
	{
		return (index - 1) / 2;
	}
	
	private int leftChild(int index)
	{
		return (index * 2) + 1;
	}
	
	private int rightChild(int index)
	{
		return (index * 2) + 2;
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
