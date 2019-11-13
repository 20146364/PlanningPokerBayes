package hungpt.developer.planningpoker.controller;

public class Main {
    public static void main(String[] args){
        StringBuilder s= new StringBuilder();
        for (int i = 1; i <=800; i++){
           if (i!=800){
               s.append("C").append(i).append("&\";\"&");
           }else {
               s.append("C").append(i);
           }
        }
        System.out.println(s);
    }
}
