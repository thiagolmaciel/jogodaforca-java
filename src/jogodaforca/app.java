package jogodaforca;

import java.util.Scanner;
import java.io.BufferedReader; //DE caracter em caractere vai entender que é um texto e auxiliar na gestão
import java.io.FileReader; //Consegue ler um arquivo
import java.io.IOException;  //Permite que identifique erros na leitura de arquivos, no caso txt, ajudando na solução
import java.util.Random;

/**
 * Trabalho de LP2 - Professor Gabriel
 * Desenvolvimento de Jogos: Jogo da Forca
 * @autores Thiago Laranjeira Maciel BV3023591
 **/

public class app {
    public static void main(String [] args) throws IOException{
        boolean run=true;
        while(run){
            int input = menu();
            switch(input){
                case 1:
                game(); //Inicia o jogo
                break;
                
                case 2:
                run=false; //Fecha o jogo
                break;
            }
        }
    }
    
    public static int menu(){
        Scanner input = new Scanner(System.in);
        System.out.printf("\n/////// Jogo da Forca ///////"); 
        displayHangmanState(-1); 
        System.out.printf("\n1. Jogar\n2. Sair\n> ");
        return input.nextInt(); //Inicia ou fecha o jogo no MENU
    }
    
    public static void game() throws IOException{
        boolean win=false; //Vitória falso pois não venceu
        String word = getWord(); //Palavra escolhida aleatoriamente
        
        char[] wordArray = word.toCharArray();
        int wordLength = word.length();
        char [] spaceChar = fillBlank(wordLength); //Espaços vazios exibidos ao jogador que serão preenchidos
        char [] guessedLetters = fillBlank(wordLength); //Vai ir acumulando as letras usadas que estão certas!
        char [] incorrectLetters = {'_', '_', '_', '_', '_', '_'}; //Vai ir acumulando as letras usadas que errou!
        int [] binaryCheck = fillBinary(wordLength); //Onde estiver a letra acertada, vira 1
        int guessedLettersCount = -1;
        int wrongLettersCount = -1;
               
        Scanner input = new Scanner(System.in); //Scanner de letra inserida
        int errorCount=0; //Quantidade de erros
        
        while(!win && !(errorCount==6)){ //Enquanto não vencer, ou não perder...
            displayHangmanState(errorCount); //Exibe a cena
            for(int i=0;  i<wordLength; i++){
                System.out.print(spaceChar[i] + " "); //Exibe array simbolos
            }
            System.out.print("\n\n> "); //Pede letra (print)
            String attemptStr = input.next(); //Recebe letra (variavel attemptStr "tentativa";
            attemptStr = attemptStr.toUpperCase();
            char attemptChar = attemptStr.charAt(0);
            if(attemptStr.equals(word)){ //Se acertar a palavra
                System.out.println("Win!");
                win=true;
            }
            
            else if(word.contains(attemptStr)){ //SE ACERTAR LETRA
                String strattemptChar = String.valueOf(attemptChar);
                String strguessedLetters = String.valueOf(guessedLetters);
                if(strguessedLetters.contains(strattemptChar)){
                    System.out.print("\tLetra ja usada!");
                }
                else{            
                    for(int i=0; i<wordLength; i++){
                        if(wordArray[i]==attemptChar){
                            binaryCheck[i]=1;
                            spaceChar[i]=attemptChar;
                        }   
                    }
                    guessedLettersCount++;
                    guessedLetters[guessedLettersCount]=attemptChar;
                    System.out.print("\tAcertou!");
                }
            } 
           
            else{ //SE ERRAR LETRA
                String strattemptChar = String.valueOf(attemptChar);
                String strincorrectLetters = String.valueOf(incorrectLetters);
                if(strincorrectLetters.contains(strattemptChar)){
                    System.out.print("\tLetra ja usada!");
                }
                
                else{
                    errorCount++;
                    System.out.print("\tERROU!"); 
                    wrongLettersCount++;
                    incorrectLetters[wrongLettersCount]=attemptChar;
                }
            }
            
            int check=0;
            for(int i=0; i<wordLength; i++){ //Checar binário 
                if(binaryCheck[i]==1){check++;}
            }
            if(check==wordLength){win=true;}  
            
        }  //ACABOU
        
        if(win==true){
            displayHangmanState(7);
            System.out.println("\nVOCE ACERTOU A PALAVRA!\nPalavra = " + word);
        }
        
        else if(errorCount==6){
            displayHangmanState(6);
            System.out.println("\nVOCE ERROU A PALAVRA... E MORREU!\nPalavra = " + word);
        }
        
    }
    
    static String getWord() throws IOException{
        FileReader fr = new FileReader("file.txt"); 
        BufferedReader br = new BufferedReader(fr);
        String string="";
        Random random = new Random();
        for(int i=1; i<=random.nextInt(100)+1; i++){ 
            string = br.readLine();
        }
        return string.toUpperCase();

    } 
      
    public static char[] fillBlank(int wordLength){        
        char [] array = new char[wordLength];
        for(int i=0; i<wordLength; i++){
            array[i]='_';
        }
        return array;     
    }
    
    public static int[] fillBinary(int wordLength){
        int [] array = new int[wordLength];
        for(int i=0; i<wordLength; i++){
            array[i]=0;
        }
        return array; 
    }
    
    static void displayHangmanState(int errorCount){ //CENA
        switch(errorCount){            
            case -1 -> System.out.println("\n___\n|  \n|   O\n|    |c\n=    /<. . ."); 
            case 0 -> System.out.println("\n___\n|\n|\n|\tErros: " + errorCount + "\n=");            
            case 1 -> System.out.println("\n___\n| O\n|\n|\tErros: " + errorCount + "\n=");           
            case 2 -> System.out.println("\n___\n| O\n| |\n|\tErros: " + errorCount + "\n=");                         
            case 3 -> System.out.println("\n___\n| O\n| |\\\n|\tErros: " + errorCount + "\n=");           
            case 4 -> System.out.println("\n___\n| O\n|/|\\\n|\tErros: " + errorCount + "\n=");            
            case 5 -> System.out.println("\n___\n| O\n|/|\\\n|/\tErros: " + errorCount + "\n=");            
            case 6 -> System.out.println("\n___\n| O\n|///\n|\\\\\tErros: " + errorCount + "\n=");            
            case 7 -> System.out.println("\n___\n|\n|  ~ O\n|  ~ c|v\n=  ~ />");
        }   
    }
}
