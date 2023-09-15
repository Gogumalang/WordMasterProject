package org.example;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WordCRUD implements ICRUD{
    ArrayList<Word> list;
    Scanner s = new Scanner(System.in);
    WordCRUD(Scanner s) {
        list = new ArrayList<> ();
        this.s = s;
    }

    @Override
    public Object add() {
        System.out.print("=> 난이도(1,2,3) & 새 단어 입력 : ");
        int level = s.nextInt();
        String word = s.nextLine();

        System.out.print("뜻 입력 : ");
        String meaning = s.nextLine();
        return new Word(0,level,word,meaning);
    }
    @Override
    public int update(Object obj) {
        Word word = (Word)obj;
        System.out.print("뜻 입력 : ");
        String meaning = s.nextLine();
        word.setMeaning(meaning);
//        int level = word.getLevel();
//        String name = word.getWord();
//        int id = word.getId();
//        list.remove(word);
//        word = new Word(id,level,name,meaning);
//        list.add(word);
        return 0;
    }

    @Override
    public int delete(Object obj) {
        String flag;
        System.out.print("=> 삭제하시겠습니까?(Y/n) : ");
        flag = s.nextLine();
        if(flag.charAt(0) == 'Y') {
            list.remove((Word)obj);
            return 1;
        }
        else return 0;
    }

    @Override
    public void selectOne(int id) {

    }

    public void addWord() {
        Word one = (Word)add();
        list.add(one);
        System.out.println("새 단어가 단어장에 추가되었습니다. ");
    }

    public void listAll() {
        System.out.println("--------------------------------");
        for(int i=0;i<list.size();i++){
            System.out.print((i+1) + " ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("--------------------------------");
    }

    public String findWord() {
        System.out.print("=> 수정할 단어 검색 : ");
        return s.next();
    }
    public ArrayList<Integer> listResult(String findWord) {
        ArrayList<Integer> imsilist = new ArrayList<>();
        System.out.println("--------------------------------");
        int j=1;
        for (int i = 0; i < list.size(); i++) {
            Word word = list.get(i);
            if (word.getWord().contains(findWord)) {
                System.out.print((j) + " ");
                System.out.println(list.get(i).toString());
                imsilist.add(i);
                j++;
            }
        }
        System.out.println("--------------------------------");
        return imsilist;
    }

    public void updateWord() {
        String findword= findWord();
        ArrayList<Integer> resultList = listResult(findword);
        if(resultList.size() == 0) System.out.println("단어가 존재하지 않습니다.");
        else {
            System.out.print("=> 수정할 번호 선택 : ");
            int num = s.nextInt();
            s.nextLine();
            int result = update(list.get(resultList.get(num - 1)));
            System.out.println("단어가 수정되었습니다.");
        }
    }

    public void deleteWord() {
        String findword= findWord();
        ArrayList<Integer> resultList = listResult(findword);
        if(resultList.size() == 0) System.out.println("단어가 존재하지 않습니다.");
        else {
            System.out.print("=> 삭제할 번호 선택 : ");
            int num = s.nextInt();
            s.nextLine();
            int result = delete(list.get(resultList.get(num - 1)));
            if (result == 1) System.out.println("단어가 삭제되었습니다.");
        }
    }

    public void saveList(String filename)  {
        try {
            FileWriter fw = new FileWriter(filename);
            for (Word item : list) {
                fw.write(item.toSaveString());
            }
            fw.close();
            System.out.println("모든 단어가 저장되었습니다.");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadList(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, "##");
                String idstr = st.nextToken();
                int id = Integer.parseInt(idstr);
                String levelstr = st.nextToken();
                int level = Integer.parseInt(levelstr);
                String word = st.nextToken();
                String meaning = st.nextToken();
                Word item = new Word(id,level,word,meaning);
                list.add(item);
                count++;
            }
            br.close();
            System.out.println(count+"개의 단어를 읽었습니다.");
        }catch (Exception e) {
            System.out.println(e);
        }
    }


}