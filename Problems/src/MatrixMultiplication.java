
public class MatrixMultiplication {
    public static  void main(String[] args){
        int[][] arr1={{2,2},{2,3}};
        int[][] arr2={{3,4},{1,2}};

        multiplyMatrix(arr1,arr2);
    }

    private static void multiplyMatrix(int[][] arr1, int[][] arr2) {
        int row1 = arr1.length;
        int col1 = arr1[0].length;
        int row2 = arr2.length;
        int col2 = arr2[0].length;

        int[][] res= new int[row1][col2];

        for(int i = 0; i< row1;i++){
            for(int j = 0; j < col2; j++){
                for(int k = 0; k < col1 ; k++){
                    res[i][j] += arr1[i][k] * arr2[k][j];
                }
            }
        }

        printResultArray(res,row1,col2);
    }

    public  static  void printResultArray(int[][] res,int row,int col){
        for(int i=0; i< row;i++){
            for(int j=0;j<col;j++){
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
    }

}
