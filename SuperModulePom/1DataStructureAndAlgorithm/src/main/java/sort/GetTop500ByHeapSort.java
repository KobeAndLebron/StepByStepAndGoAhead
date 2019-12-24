package sort;

import java.util.Arrays;

/**
 * 有 20 个数组，每个数组有 500 个元素，并且有序排列。如何在这 20*500 个数中找出前 500 的数？
 *
 */
public class GetTop500ByHeapSort {

    public static void main(String[] args) {
        int[][] data = {
            {29, 17, 14, 2, 1},
            {19, 17, 16, 15, 6},
            {30, 25, 20, 14, 5},
            {30, 25, 20, 14, 5},
            {30, 25, 20, 14, 5},
        };
        HeapNode.data = data;

        int[] maxValues = new int[7];
        HeapNode[] heapNodes = new HeapNode[5];
        // 1. 构建堆.
        for (int i = 0; i < data.length; i++) {
            HeapNode heapNode = new HeapNode(i, 0);

            heapInsert(heapNodes, i, heapNode);
        }

        // 2. 将堆顶元素弹出, 然后将删除元素所在数组的下一个元素插入, 重新生成大顶堆.
        for (int i = 0; i < maxValues.length; i++) {
            HeapNode max = heapNodes[0];
            maxValues[i] = data[max.rowIndex][max.columnIndex++];

            heapNodes[0] = null;
            heapNodes[0] = new HeapNode(max.rowIndex, max.columnIndex);
            heapify(heapNodes, 0);
        }

        System.out.println(Arrays.toString(maxValues));
    }

    private static void heapInsert(HeapNode[] heapNodes, int i, HeapNode heapNode) {
        int parent;
        heapNodes[i] = heapNode;
        while ((parent = (i - 1) / 2) >= 0) {
            if (heapNodes[parent].compareTo(heapNode) < 0) {
                swap(heapNodes, parent, i);
                i = parent;
            } else {
                break;
            }
        }
    }

    private static void heapify(HeapNode[] heapNodes, int index) {
        // 左节点
        int left;
        while ((left = (2 * index + 1)) < heapNodes.length) {
            int right = left + 1;
            int largest = (right < heapNodes.length && heapNodes[right].compareTo(heapNodes[left]) > 0) ? right : left;
            largest = heapNodes[index].compareTo(heapNodes[largest]) > 0 ? index : largest;
            if (largest == index) {
                break;
            }

            // 如果最大节点不是根节点, 则将最大的孩子节点与它交换, 继续构建最大堆.
            swap(heapNodes, index, largest);
            index = largest;

        } // 判断堆中是否还有节点.
    }
    
    static class HeapNode implements Comparable<HeapNode> {
        int rowIndex;
        int columnIndex;
        static int[][] data;

        public HeapNode(int rowIndex, int columnIndex) {
            this.columnIndex = columnIndex;
            this.rowIndex = rowIndex;
        }


        @Override
        public int compareTo(HeapNode o) {
            if (o == null) {
                return 1;
            } else {
                if (data[rowIndex][columnIndex] > data[o.rowIndex][o.columnIndex]) {
                    return 1;
                } else if (data[rowIndex][columnIndex] < data[o.rowIndex][o.columnIndex]) {
                    return -1;
                } else {
                    return 0;
                }

            }
        }
    }

    private static void swap(HeapNode[] nums, int i, int j) {
        HeapNode temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
