package com.louischow.recite;

import java.util.*;

/*************final禁止继承Sort类，防止其他开发者通过继承和重写类的方法来改变它的行为*****/
public final class Sort {
    /**************private阻止其他代码创建该类的实例，工具类的方法都是静态的*********/
    private Sort() {
    }

    /**********************************************冒泡排序**************************************************/
    public static void bubbleSort(int[] data) {
        if (data == null) return;

        for (int i = 0; i < data.length - 1; i++) {
            boolean token = true;
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    int temp = data[j + 1];
                    data[j + 1] = data[j];
                    data[j] = temp;
                    token = false;
                }
            }
            if (token) break;//如果没交换说明数组已经有序，直接退出循环
        }
    }

    /***********************************************选择排序**************************************************/
    public static void selectSort(int[] data) {
        if (data == null) return;

        for (int i = 0; i < data.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[minIndex]) minIndex = j;
            }
            if (minIndex != i) {//避免不必要的交换
                int temp = data[i];
                data[i] = data[minIndex];
                data[minIndex] = temp;
            }
        }
    }

    /********************************************快速排序*****************************************************/
    private static int quickSortOne(int[] data, int left, int right) {
        int index = left + (int) (Math.random() * (right - left + 1));
        int pivot = data[index];
        data[index] = data[left];// 将左边第一个元素的值放到基准值的原始位置
        //按理说是要将index位置和最左边的值交换，但实际上没必要再另最左边的值为基准值
        while (left < right) {//当 low 等于 high 时，循环不应该继续。继续循环会导致 low 和 high 在同一个位置上卡住，无法结束循环
            while (data[right] >= pivot && left < right) right--;
            data[left] = data[right];//这一步一上来就把左边第一个值替换了
            while (data[left] < pivot && left < right) left++;
            data[right] = data[left];
        }
        data[left] = pivot;
        return left;
    }

    /*******************递归*********************/
    private static void quickSortHelper(int[] data, int left, int right) {
        int mid = quickSortOne(data, left, right);
        if (mid > left) quickSortHelper(data, left, mid - 1);
        if (mid < right) quickSortHelper(data, mid + 1, right);
        // if (low < high) {
        //            int pivot = quickSortOne(data, low, high);
        //            quickSortHelper(data, low, pivot - 1);
        //            quickSortHelper(data, pivot + 1, high);
        //        }
    }

    public static void quickSort(int[] data) {
        if (data == null) return;
        quickSortHelper(data, 0, data.length - 1);
    }

    /*******************非递归（用栈和队列都行）*********************/
    public static void quickSort2(int[] data) {
        if (data == null) return;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(0);
        stack.push(data.length - 1);
        while (!stack.isEmpty()) {
            int right = stack.pop();
            int left = stack.pop();
            int mid = quickSortOne(data, left, right);
            if (left < mid) {
                stack.push(left);
                stack.push(mid - 1);
            }
            if (mid < right) {
                stack.push(mid + 1);
                stack.push(right);
            }
            //            if (left < right) {
            //                int mid = quickSortOne(data, left, right);
            //                stack.push(left);
            //                stack.push(mid - 1);
            //                stack.push(mid + 1);
            //                stack.push(right);
            //            }
        }
    }

    /**********************************************插入排序*************************************************/
    public static void insertionSort(int[] data) {
        if (data == null) return;
        for (int i = 1; i < data.length; i++) {
            int key = data[i];//保存待插入的值
            int j = i - 1;
            while (j >= 0 && key < data[j]) {//从已排序的部分从后往前找
                data[j + 1] = data[j];//比较的时候顺便后移
                j--;
            }
            data[j + 1] = key;
        }
    }

    /*******************折半查找（递归）*********************/
    private static int binarySearch(int[] data, int left, int right, int key) {
        //建立在数组已经有序的情况下
        if (left > right) return -1;
        int mid = (left + right) / 2;
        if (key == data[mid]) return mid;
        if (key < data[mid]) {
            return binarySearch(data, left, mid - 1, key);
        }
        return binarySearch(data, mid + 1, right, key);
    }

    /*******************折半查找（非递归）*********************/
    private static int binarySearch2(int[] data, int len, int key) {
        int left = 0;
        int right = len;
        while (left <= right) {//一定要等号，不然会少判断一个元素
            int mid = (left + right) / 2;//如果数组非常大，这种计算方式可能导致溢出。可以改成 left + (right - left) / 2，更安全。
            if (data[mid] == key) return mid;
            if (data[mid] < key) left = mid + 1;
            else right = mid - 1;
        }
        return left;//返回插入位置,确保在 [0, data.length] 范围内
    }

    /*****************************************折半插入排序***************************************************/
    public static void binaryInsertionSort(int[] data) {
        if (data == null) return;
        for (int i = 1; i < data.length; i++) {
            int key = data[i];//保存待插入的值
            int index = binarySearch2(data, i - 1, key);
            for (int j = i; j > index; j--) data[j] = data[j - 1];
            data[index] = key;
        }
    }

    /*****************************************希尔排序(插入)*************************************************/
    public static void shellSort(int[] data) {
        int n = data.length;

        // 初始增量为数组长度的一半，然后逐渐缩小增量
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // 对每个子序列进行插入排序
            for (int i = gap; i < n; i++) {
                int key = data[i];
                int j = i;
                // 对每个子序列插入排序
                while (j >= gap && data[j - gap] > key) {
                    data[j] = data[j - gap];//将子序列中比key大的后移，找key的插入位置
                    j -= gap;
                }
                data[j] = key;
            }
        }
    }

    /*****************************************大根堆排序*************************************************/
    public static void heapSort(int[] data) {
        if (data == null) return;
        int n = data.length;

        // 构建大根堆，堆（完全二叉树）的最后一个非叶子节点在数组中的索引是(arr.length/2)-1
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(data, n, i);
        }

        // 进行排序
        for (int i = n - 1; i > 0; i--) {
            // 将当前根节点（最大值）移动到数组末尾
            int temp = data[0];
            data[0] = data[i];
            data[i] = temp;

            // 调整堆
            heapify(data, i, 0);
        }
    }

    // 堆调整（维护大根堆性质）,确保以 i 为根的子树满足大根堆的性质
    private static void heapify(int[] data, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        //因为大根堆只需要考虑根结点比左右孩子都大，不需要考虑左右孩子之间的大小，所以下面两个if的顺序可以调换
        if (left < n && data[left] > data[largest]) {
            largest = left;
        }

        if (right < n && data[right] > data[largest]) {
            largest = right;
        }

        if (largest != i) {//隐式的终止条件，如果largest==i递归就停止了
            int swap = data[i];
            data[i] = data[largest];
            data[largest] = swap;

            heapify(data, n, largest);
        }
    }

    /*****************************************归并排序(递归)*************************************************/
    private static void mergeOne(int data[], int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (data[i] < data[j]) temp[k++] = data[i++];
            else temp[k++] = data[j++];
        }
        while (i <= mid) temp[k++] = data[i++];
        while (j <= right) temp[k++] = data[j++];
        System.arraycopy(temp, 0, data, left, right - left + 1);//注意data从left开始，不是0
    }

    private static void mergeSort(int data[], int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(data, left, mid);
            mergeSort(data, mid + 1, right);
            mergeOne(data, left, mid, right);
        }
    }

    public static void mergeSort(int[] data) {
        if (data == null) return;
        mergeSort(data, 0, data.length - 1);
    }

    /*****************************************归并排序(非递归)*************************************************/

    public static void mergeSort1(int[] data) {
        if (data == null) return;
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0; i < data.length; i++) {
            queue.addLast(i);
            queue.addLast(i);
        }
        while (!queue.isEmpty()) {
            int left1 = queue.removeFirst(), right1 = queue.removeFirst();
            int left2 = queue.removeFirst(), right2 = queue.removeFirst();
            if (right1 + 1 != left2) {
                queue.addLast(left1);
                queue.addLast(right1);
                left1 = left2;
                right1 = right2;
                left2 = queue.removeFirst();
                right2 = queue.removeFirst();
            }

            mergeOne(data, left1, right1, right2);
            if (queue.isEmpty()) break;
            queue.addLast(left1);
            queue.addLast(right2);
        }
    }
/*****************************************基数排序*******************************************/
public static void radixSort(int[] array) {
    if (array == null || array.length <= 1) {
        return;
    }

    // 找到数组中的最大值，确定最大位数
    int max = getMax(array);
    int n = 1; // 位数对应的数：1, 10, 100, ...
    List<List<Integer>> buckets = new ArrayList<>();
    //使用一个列表数组来存储桶。
    // 初始化桶,向 buckets 中添加了 10 个新的 ArrayList<Integer> 对象
    for (int i = 0; i < 10; i++) {
        buckets.add(new ArrayList<>());
    }

    while (max / n > 0) {
        // 将数组中的每个数字放入相应的桶中
        for (int num : array) {
            int digit = (num / n) % 10;//提取n位上对应的数（个位，十位...）
            buckets.get(digit).add(num);//将 num 添加到 buckets 中第 digit 个位置
        }

        // 将桶中的数字按顺序放回原数组
        int index = 0;
        for (List<Integer> bucket : buckets) {
            for (int num : bucket) {//会自动跳过空桶
                array[index++] = num;
            }
            bucket.clear(); // 清空桶，准备下一轮排序
        }

        n *= 10; // 下一个位数
    }
}

    // 获取数组中的最大值
    private static int getMax(int[] array) {
        int max = array[0];
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public static void printArray(int[] data) {
        if (data == null || data.length == 0) {
            System.out.println("数组为空");
            return;
        }

        for (int i = 0; i < data.length; i++) {
            // 格式化每个元素，使其占据10个字符宽度
            System.out.printf("%10d", data[i]);
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }

        // 如果数组长度不是10的倍数，最后补一个换行
        if (data.length % 10 != 0) {
            System.out.println();
        }
    }

    public static void main(String[] args){
        int[] data=new int[100];
        for (int i = 0; i < 100; i++) {
            data[i] = (int) (Math.random() * 1000); // 生成 0 到 999 之间的随机整数
        }
        Sort.mergeSort1(data);
        Sort.printArray(data);
    }
}
