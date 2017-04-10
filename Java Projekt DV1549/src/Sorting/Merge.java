package Sorting;


public class Merge<T extends Comparable<T>> implements SortInterface<T>
{
	private long timeTaken = 0;
	
	
	@Override
	public long Sort(T[] oldArr, int flag)
	{
		Object[] tmpArr = new Object[oldArr.length];
		startTime();
		merge_sort(oldArr, tmpArr, 0, oldArr.length - 1);	
		return stopTime();
	}

	private void merge_sort(T[] oldArr, Object[] tmpArr, int start, int end)
	{
		if (start < end)
		{
			int mid = (start + end) / 2;
			merge_sort(oldArr, tmpArr, start, mid);
			merge_sort(oldArr, tmpArr, mid + 1, end);
			merge(oldArr, tmpArr, start, mid + 1, end);	
		}
	}

	private void merge(T[] oldArr, Object[] tmpArr, int subLeft, int subRight, int subEnd)
	{
		int divider = subRight - 1;
		int mergedIndex = 0;
		int left = subLeft;
		int elements = subEnd - subLeft;
		int n = 0;
		
		while (subLeft <= divider && subRight <= subEnd)
		{
			if (oldArr[subLeft].compareTo(oldArr[subRight]) <= 0)
			{
				tmpArr[mergedIndex] = oldArr[subLeft];
				subLeft++;
			}
			else 
			{
				tmpArr[mergedIndex] = oldArr[subRight];
				subRight++;
			}
			mergedIndex++;
		}
		while (subLeft <= divider)
		{
			tmpArr[mergedIndex] = oldArr[subLeft];
			subLeft++; mergedIndex++;
		}
		while (subRight <= subEnd)
		{
			tmpArr[mergedIndex] = oldArr[subRight];
			subRight++; mergedIndex++;
		}		
		while (n <= elements)
		{
			oldArr[left] = (T) tmpArr[n];
			left++; n++;
		}	
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
