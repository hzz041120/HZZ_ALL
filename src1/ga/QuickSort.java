package ga;

public class QuickSort {
	public void quickSort(int[] a, int start, int end) {// ������
		int i, j;
		int temp;

		i = start;
		j = end;
		temp = a[start]; // ȡ��һ��Ԫ��Ϊ��׼����Ԫ��

		while (i < j) {
			// ��������Ҷ�ɨ��
			while (i < j && temp > a[j])
				j--;
			if (i < j) {
				a[i] = a[j];
				i++;
			}

			// ����������ɨ��
			while (i < j && a[i] >= temp)
				i++;
			if (i < j) {
				a[j] = a[i];
				j--;
			}
		}
		a[i] = temp;

		if (start < i)
			quickSort(a, start, i - 1); // ������Ӽ��ϵݹ�
		if (i < end)
			quickSort(a, j + 1, end); // ���Ҷ��Ӽ��ϵݹ�
	}
}
