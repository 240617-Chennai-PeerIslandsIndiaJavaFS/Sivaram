import java.util.Scanner;

public class MatrixWithNoDuplicates {
    public static  void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter No. of row: ");
        int rows = sc.nextInt();
        System.out.print("Enter No. of col: ");
        int cols = sc.nextInt();

        int[][] arr=new int[rows][cols];

        for(int i=0; i<rows; i++){
            for(int j=0; j<cols ;j++){
                System.out.print("Enter value : ");
                int val = sc.nextInt();
                if(duplicateVal(i,j,val,arr)){
                    j--;
                    System.out.println("Duplicate Number");
                }else {
                    arr[i][j]=val;
                }
            }
        }

        printArray(rows, cols, arr);
    }

    public static boolean duplicateVal(int row,int col,int val,int[][] arr){
        for(int i=row;i>=0;i--){
            for(int j=col;j>=0;j--){
                if(arr[i][j]==val)
                    return true;
            }
        }
        return  false;

    }

    private static void printArray(int rows, int cols, int[][] arr) {
        for(int i = 0; i< rows; i++){
            for(int j = 0; j< cols; j++)
                System.out.print(arr[i][j]+" ");
            System.out.println();
        }
    }

}
