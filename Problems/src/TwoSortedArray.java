public class TwoSortedArray {
    public static void main(String[] args){
        int[] arr1={10,20,30,40};
        int[] arr2={30,40,70,80};

        mergeSortedArray(arr1,arr2);
    }

    private static void mergeSortedArray(int[] arr1, int[] arr2) {
        int[] res = new int[ arr1.length + arr2.length];

        for(int i=0; i < arr1.length; i++)
            res[i] = arr1[i];

        for(int i=0; i < arr2.length; i++)
            res[arr1.length+i]=arr2[i];

        PrintArray(sortArray(res));
    }


    private static int[] sortArray(int[] res) {
        for(int i=0; i< res.length-1; i++){
            for(int j = 0; j< res.length-1;j++){
                if(res[j+1]< res[j]){
                    int temp = res[j];
                    res[j]=res[j+1];
                    res[j+1]=temp;
                }
            }
        }
        return  res;
    }

    private static void PrintArray(int[] res) {
        for(int i=0; i < res.length; i++)
            System.out.print(res[i] + " ");
    }

}
