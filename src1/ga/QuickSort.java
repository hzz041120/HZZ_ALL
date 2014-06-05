package ga;

public class QuickSort {
	public void quickSort(int[] a, int start, int end) {// 排正序
		int i, j;
		int temp;

		i = start;
		j = end;
		temp = a[start]; // 取第一个元素为标准数据元素

		while (i < j) {
			// 在数组的右端扫描
			while (i < j && temp > a[j])
				j--;
			if (i < j) {
				a[i] = a[j];
				i++;
			}

			// 在数组的左端扫描
			while (i < j && a[i] >= temp)
				i++;
			if (i < j) {
				a[j] = a[i];
				j--;
			}
		}
		a[i] = temp;

		if (start < i)
			quickSort(a, start, i - 1); // 对左端子集合递归
		if (i < end)
			quickSort(a, j + 1, end); // 对右端子集合递归
	}
}
